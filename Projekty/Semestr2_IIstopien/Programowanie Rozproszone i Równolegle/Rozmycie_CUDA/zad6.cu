#include <iostream>
#include <stdio.h>
#include <omp.h>
#include <opencv/cv.h>
#include <opencv/highgui.h>

using namespace cv;
using namespace std;

//-----------------------------------------------------------------------------------------
// zdefiniowanie tablicy o statycznym rozmiarze 5x5
__constant__ int maskaGPU[5][5];


//-----------------------------------------------------------------------------------------
// funkcja rozmywajaca zadane zdjecie
// __global__ - kwalifikator - informuje kompilator, ze dana funkcja powinna byc skompilowana dla urzadzenia a nie dla hosta

__global__ void rozmycie(int kanal_zdj, int sumaWag, unsigned char* zdjWe, unsigned char* zdjWy, int wysokosc, int rozmiar) 
{
	int id = blockIdx.x * blockDim.x + threadIdx.x;
	double piksel= 0;
	int indeks,x,y;

			for(x = 0; x < 5; x++ ) 
			{
				for(y = 0; y < 5; y++) 
				{
					indeks = id + ((y - 2) * rozmiar) + ((x - 2) * kanal_zdj);
					piksel+= maskaGPU[x][y] * zdjWe[indeks];
				}
			}
			zdjWy[id] = (unsigned char)(piksel/sumaWag);
			id += blockDim.x * gridDim.x;
			piksel = 0;
}

//-----------------------------------------------------------------------------------------
// funkcja sumujaca maski
int sumaMaski(int maska[5][5], int x, int y, int suma){
	for (x=0; x<5;x++){
		for (y=0; y<5;y++){
			suma+=maska[x][y];
		}
	}
	return suma;
}

int main(int argc, char** argv )
{
/*-------------------------------------------------------------------------------------
1. Definiowanie zmiennych.
-------------------------------------------------------------------------------------*/	
	// zmienne do wyznaczenia czasu wykonania zadania (deklaracja zdarzen)
	cudaEvent_t czas_start, czas_stop;
	cudaEventCreate(&czas_start);
	cudaEventCreate(&czas_stop);
	
	unsigned char *zdj_gpu_we, *zdj_gpu_wy;

/*-------------------------------------------------------------------------------------
2. Deklaracja maski w postaci 2-wymiarowej tablicy liczb całkowitych.
-------------------------------------------------------------------------------------*/		
  int maska[5][5] ={
  	{1,1,2,1,1},
  	{1,2,4,2,1},
  	{2,4,8,4,2},
  	{1,2,4,2,1},
  	{1,1,2,1,1}
  };
  
/*-------------------------------------------------------------------------------------
3. Kopiowanie pamięci tablicy maskaGPU do $maska. Kopiowanie danych do pamięci stałej.

	cudaMemcpyToSymbol()
		maskaGPU 		- symbol (zmienna) docelowa na urządzeniu
		$maska 	 		- adres źródłowy pamieci
		sizeof(int)*5*5 - rozmiar pamieci w bajtach
		
-------------------------------------------------------------------------------------*/  
	cudaMemcpyToSymbol(maskaGPU, &maska, sizeof(int) * 5 * 5);
	
	
/*-------------------------------------------------------------------------------------
4. Warunek sprawdzajacy czy istnieja wszystkie niezbedne argumenty wejsciowe.
-------------------------------------------------------------------------------------*/
	if ( argc != 3 )
	{
		cout << "0 ms - brak wszystkich argumentow" << endl;
		return 0;
	}

/*-------------------------------------------------------------------------------------
5. Wczytanie pliku do zmiennej "zdj_we".
-------------------------------------------------------------------------------------*/  
 
	Mat zdj_we,zdj_wy;
  
	zdj_we = imread(argv[1], CV_LOAD_IMAGE_COLOR);
  
	if ( !zdj_we.data )
	{
      cout <<  "0 ms - problem z ladowaniem wartosci" << endl ;
      return 0;
	}
	
/*-------------------------------------------------------------------------------------
6. Podział zadania na gridy oraz bloki.
-------------------------------------------------------------------------------------*/  
 	
	int rozmiarBlok = 32;
	int rozmiarSiatkaSzer = zdj_we.cols/rozmiarBlok;
	int rozmiarSiatkaDlug = zdj_we.rows/rozmiarBlok;

	
	dim3 siatka(rozmiarSiatkaSzer, rozmiarSiatkaDlug);
	dim3 blok(rozmiarBlok, rozmiarBlok);
	
/*-------------------------------------------------------------------------------------
7. Utworzenie kopii wcześniej wczytanego zdjęcia do zmiennej zdj_wy.
-------------------------------------------------------------------------------------*/	
	
	zdj_wy = zdj_we.clone();

/*-------------------------------------------------------------------------------------
8. Alokowanie pamieci na karcie graficznej.

	cudaMalloc 
		(void**) - wskaznik wskazuje adres nowo alokowanej pamieci
		sizeof() - rozmiar alokowanej pamieci
-------------------------------------------------------------------------------------*/	  

	cudaMalloc(&zdj_gpu_we, zdj_we.rows*zdj_we.step*sizeof(unsigned char));
	cudaMalloc(&zdj_gpu_wy, zdj_we.rows*zdj_we.step*sizeof(unsigned char));

/*-------------------------------------------------------------------------------------
9. Kopiowanie danych miedzy pamieci RAM do karty graficznej.

	cudaMemcpy() 
		zdj_gpu_we 				 - wskaźnik na obszar pamięci, do której nastąpi kopiowanie
		zdj_we.ptr()			 - wskaźnik na obszar pamięci, z której nastąpi kopiowanie
		zdj_we.rows*zdj_we.step  - liczba bajtów do skopiowania
		cudaMemcpyHostToDevice   - obszar pamięci źródłowej należy do komputera (RAM), natomiast docelowy obszar pamięci należy do pamięci karty graficznej
	
-------------------------------------------------------------------------------------*/		

	cudaMemcpy(zdj_gpu_we, zdj_we.ptr(), zdj_we.rows*zdj_we.step, cudaMemcpyHostToDevice);

/*-------------------------------------------------------------------------------------
10. Rozpoczecie pomiaru czasu obliczen.
-------------------------------------------------------------------------------------*/		

	cudaEventRecord(czas_start, 0);

	rozmycie<<<siatka,blok>>>(zdj_we.channels(), sumaMaski(maska,0,0,0), zdj_gpu_we, zdj_gpu_wy, zdj_we.rows, zdj_we.step);

	cudaEventRecord(czas_stop, 0);
	cudaEventSynchronize(czas_stop);
	float czas = 0.0;
	cudaEventElapsedTime(&czas, czas_start, czas_stop);

	// blokuje bieżący wątek aplikacji do czasu zakończenia wszystkich oczekiwanych obliczeń na karcie graficznej.
	cudaDeviceSynchronize();

/*-------------------------------------------------------------------------------------
11. Kopiowanie danych z karty graficznej do pamieci RAM.

	cudaMemcpy() 
		zdj_wy.ptr() 			 - wskaźnik na obszar pamięci, do której nastąpi kopiowanie
		zdj_gpu_wy			 	 - wskaźnik na obszar pamięci, z której nastąpi kopiowanie
		zdj_we.rows*zdj_we.step  - liczba bajtów do skopiowania
		cudaMemcpyDeviceToHost   - obszar pamięci źródłowej należy do pamięci karty graficznej, natomiast docelowy obszar pamięci należy do komputera (RAM)
	
-------------------------------------------------------------------------------------*/		
	cudaMemcpy(zdj_wy.ptr(), zdj_gpu_wy, zdj_we.rows*zdj_we.step, cudaMemcpyDeviceToHost);

	
/*-------------------------------------------------------------------------------------
12. Zwolnienie wcześniej zaalokowanej pamięci na karcie graficznej.
-------------------------------------------------------------------------------------*/			
	cudaFree(zdj_gpu_wy);
	cudaFree(zdj_gpu_we);

/*-------------------------------------------------------------------------------------
13. Zapisanie zmodyfikowanego obrazka w miejsce zadane jako drugi argument.
-------------------------------------------------------------------------------------*/
	imwrite(argv[2], zdj_wy);
	printf("Czas: %.3f ms", czas);
	
   return 0;
}
