#include <iostream>
#include <cstdlib>
#include <cmath>
#include <omp.h>

using namespace std;

void liczenieMacierzy(double **AAA, double **BBB, double **CCC, int rozmiarMac) {	//funkcja zliczajaca mnozenie macierzy metoda naiwna
	int i,j,k;			//deklaracja zmiennych pomocniczych
	
	#pragma omp parallel for default(none) shared(AAA, BBB, CCC) firstprivate(rozmiarMac) private(i, j, k)
	for (i = 0; i < rozmiarMac; i++) {
		for (j = 0; j < rozmiarMac; j++) {			
			for (k = 0; k < rozmiarMac; k++) {
				CCC[i][j] += AAA[i][k] * BBB[k][j];	//mnozenie elementow macierzy
			}
		}
	}
}


int main(int argc, char* argv[]) {

	if (!argv[1] || !argv[2]) {			//warunek sprawdzajacy czy istnieja wszystkie niezbedne argumenty wejsciowe
		cout << "0 ms" << endl;			//jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}

	int ilWatkow = atoi(argv[1]);			//zapisywanie pierwszego argumentu do zmiennej - ilosc watkow
	int rozmiarMac = atoi(argv[2]);			//zapisywanie drugiego argumentu do zmiennej - rozmiar macierzy
	
	if (ilWatkow < 1 || rozmiarMac <= 1) {		//warunek sprawdzający czy wartosci argumentow nie znajduja sie poza przewidywanym przedzialem wartosci
		cout << "0 ms" << endl;			//jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}

	double startTime=0, stop=0; 			//deklaracja zmiennej do wyznaczania czasu wykonania programu
	int i=0, j=0; 					//deklaracja zmiennych pomocniczych (iteratorow)

	double **AAA = new double*[rozmiarMac];		//deklaracja macierzy AAA - tablicy dwuwymiarowej
	double **BBB = new double*[rozmiarMac];		//deklaracja macierzy BBB - tablicy dwuwymiarowej
	double **CCC = new double*[rozmiarMac];		//deklaracja macierzy CCC - tablicy dwuwymiarowej

	for (i = 0; i < rozmiarMac; i++) {	//Alokacja pamieci dla tablicy. Standardowa procedura do stworzenia tablicy dwuwymiarowej
		AAA[i] = new double[rozmiarMac];
		BBB[i] = new double[rozmiarMac];
		CCC[i] = new double[rozmiarMac];
	}
	
						
	for (i = 0; i < rozmiarMac; i++) {		//uzupelnianie macierzy zgodnie ze specyfikacja zadania
		for (j = 0; j < rozmiarMac; j++) {
			AAA[i][j] = ((int)(sin(i) * i * j)) % 10;
			BBB[i][j] = ((int)(cos(j) * (i + j))) % 10;
			CCC[i][j] = 0;
			
		}
	}

	omp_set_num_threads(ilWatkow); 			//Ustawienie liczby watkow dla zrownoleglonych obszarow
	
	startTime = omp_get_wtime(); 			//Moment uruchomienia mierzenia czasu wykonywania sie zadania
	liczenieMacierzy(AAA, BBB, CCC, rozmiarMac);	//Uruchomienie funkcji wykonujacej mnozenie macierzy
	stop = omp_get_wtime(); 			//Moment zakonczenia mierzenia czasu wykonania sie zadania
	
	delete[] AAA;					// zwolnienie zaalokowanej pamieci dla tablic
	delete[] BBB;
	delete[] CCC;

	cout << "Czas: " << (stop - startTime) * 1000 << "ms" << endl;	//wypisanie czasów wykonywania programow

	return 0;
}
