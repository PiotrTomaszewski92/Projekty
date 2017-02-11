#include <stdlib.h>
#include <stdio.h>
#include <device_launch_parameters.h>
#include <cuda_runtime.h>

#define gpuErrchk(ans) { gpuAssert((ans), __FILE__, __LINE__); }

//-----------------------------------------------------------------------------------------
// Funkcja sprawdza poprawnosc dzialania GPU - kod znaleziony dzieki zyczliwosci StackOverflow

inline void gpuAssert(cudaError_t code, const char *file, int line, bool abort=true)
{
   if (code != cudaSuccess) 
   {
      fprintf(stderr,"GPUassert: %s %s %d\n", cudaGetErrorString(code), file, line);
      if (abort) exit(code);
   }
}

// __global__ - kwalifikator - informuje kompilator, ze dana funkcja powinna byc skompilowana dla urzadzenia a nie dla hosta


//-----------------------------------------------------------------------------------------
// Funkcja wypelnia macierze

__global__ void uzupelnij(long nMacierzy, float *d_A, float *d_B) 
{
	long d_x = threadIdx.x + blockIdx.x * blockDim.x;
	long d_y = threadIdx.y + blockIdx.y * blockDim.y;
	long temp = d_x + nMacierzy * d_y;
	
	if (d_x < nMacierzy && d_y < nMacierzy) 
	{
			d_A[temp] = fmodf(sinf(d_x)*d_x*d_y, 10);
			d_B[temp] = fmodf(cosf(d_y)*(d_x + d_y), 10);
			//printf ("wątek: %dx%d - [%f,%f]\n",threadIdx.x,threadIdx.y,d_A[temp],d_B[temp]);
	}
}

//-----------------------------------------------------------------------------------------
// Funkcja oblicza macierz C

__global__ void obliczC(long nMacierzy, float *d_A, float *d_B, float *d_C) 
{
	long d_x = blockIdx.x * blockDim.x + threadIdx.x;
	long d_y = blockIdx.y * blockDim.y + threadIdx.y;

	if (d_x < nMacierzy && d_y < nMacierzy) 
	{
		for (int i = 0; i < nMacierzy; i++) 
		{
			d_C[d_x + nMacierzy * d_y] += d_A[d_x + i * nMacierzy] * d_B[i + nMacierzy * d_y];
		}
	}
}




int main(int argc, char** argv) 
{

// 1. -----------------------------------------------------------------------
// 	  Warunek sprawdzajacy czy istnieja wszystkie niezbedne argumenty wejsciowe.
	if (!argv[1])
	{
		printf("0 ms - brak rozmiarMacu macierzy \n");	//jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}

	
	
// 2. -----------------------------------------------------------------------
//	  Zapisanie argumentu do zmiennej - rozmiar macierzy

	long rozmiarMac = atol(argv[1]);

	//warunek sprawdzający czy wartosci argumentow nie znajduja sie poza przewidywanym przedzialem wartosci
	if (rozmiarMac <= 1)
	{
		printf("0 ms - zly rozmiarMac macierzy \n");		//jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}

	
	
// 3. -----------------------------------------------------------------------	
//    Definiowanie zmiennych 

	// zmmienne do wyznaczania czasu wykonania
	cudaEvent_t czasStart, czasStop;     
	cudaEventCreate(&czasStart);
	cudaEventCreate(&czasStop);

	// deklaracja tablic , rozmiaru grida oraz rozmiaru bloku
	float *d_A,*d_B,*d_C,*C;
	int rozmiarGrid, rozmiarBlok;

	
	
// 4. -----------------------------------------------------------------------
//    Deklaracja macierzy A, B i C - tablic dwuwymiarowych
/*
	cudaMalloc - alokuje pamiec na na karcie graficznej
	(void**) - wskaznik wskazuje adres nowo alokowanej pamieci
	sizeof() - rozmiar alokowanej pamieci
*/
	
	gpuErrchk(cudaMalloc((void**) &d_A, sizeof(float)*rozmiarMac*rozmiarMac));
	gpuErrchk(cudaMalloc((void**) &d_B, sizeof(float)*rozmiarMac*rozmiarMac));
	gpuErrchk(cudaMalloc((void**) &d_C, sizeof(float)*rozmiarMac*rozmiarMac));
	
	
	
// 5. -----------------------------------------------------------------------
//    Deklaracja rozmiaru grida oraz ilości wątków w bloku
/*
    16 16 16 16
    16 16 16 16
    16 16 16 16
    16 16 16 16
	
	64 x 64 = 1024
*/

	rozmiarGrid = 4;
	rozmiarBlok = 16;

	dim3 grids(rozmiarGrid, rozmiarGrid); 
	dim3 blocks(rozmiarBlok, rozmiarBlok); 
	
	
	
// 6. -----------------------------------------------------------------------
//    Wywolanie funkcji "uzupelnij" z podanym rozmiarem grida (grids) i ilością watków (blocks)

	uzupelnij <<<grids, blocks>>> (rozmiarMac, d_A, d_B);
	cudaDeviceSynchronize(); // blokuje bieżący wątek aplikacji do czasu zakończenia wszystkich oczekiwanych obliczeń na karcie graficznej.

	
	
// 7. -----------------------------------------------------------------------
//    Alokowanie pamięci dla macierzy C - bez użycia CUDA

	C = (float*)malloc(sizeof(float)*rozmiarMac*rozmiarMac);

	
	
// 8. -----------------------------------------------------------------------
//    Rozpoczecie pomiaru czasu obliczen	

	cudaEventRecord(czasStart, 0);
	
	obliczC <<<grids, blocks>>> (rozmiarMac, d_A, d_B, d_C); // 
	
	cudaDeviceSynchronize(); // blokuje bieżący wątek aplikacji do czasu zakończenia wszystkich oczekiwanych obliczeń na karcie graficznej.
	
	//zakończenie pomiaru czasu obliczen
	cudaEventRecord(czasStop, 0);
	cudaEventSynchronize(czasStop); 

	/*
	cudaMemcpy() - kopiuje dane miedzy karta graficzna a pamiecia RAM
	
	C - wskaźnik na obszar pamięci, do której nastąpi kopiowanie
	d_C - wskaźnik na obszar pamięci, z której nastąpi kopiowanie
	sizeof(float)*rozmiarMac*rozmiarMac - liczba bajtów do skopiowania
	cudaMemcpyDeviceToHost - obszar pamięci źródłowej należy do pamięci karty graficznej, natomiast docelowy obszar pamięci należy do komputera (RAM)
	
	*/
	
	gpuErrchk(cudaMemcpy(C, d_C , sizeof(float)*rozmiarMac*rozmiarMac, cudaMemcpyDeviceToHost));
	
	
	
// 9. -----------------------------------------------------------------------
//    Zwalnienie wcześniej zaalokowanej pamięci na karcie graficznej

	cudaFree(d_A);
	cudaFree(d_B);
	cudaFree(d_C);
	
	free(C); // zwalnianie pamięci w typowy sposób (bez CUDA)

	
	
// 10. -----------------------------------------------------------------------
//    Obliczenie czasu wykonywanych obliczeń

	float roznica;
	gpuErrchk(cudaEventElapsedTime(&roznica, czasStart, czasStop));
	
	printf("Czas: %f ms\n",(roznica*1000));

	return 0;
}
