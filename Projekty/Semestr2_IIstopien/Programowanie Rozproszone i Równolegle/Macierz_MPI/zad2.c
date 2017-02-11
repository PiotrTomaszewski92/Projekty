
#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main (int argc, char* argv[])
{

// 1. -----------------------------------------------------------------------
// 	  Warunek sprawdzajacy czy istnieja wszystkie niezbedne argumenty wejsciowe.

	if (!argv[1])
	{
		printf("0 ms - brak rozmiarMacu macierzy \n");	//jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}



// 2. -----------------------------------------------------------------------
//	  Zapisywanie drugiego argumentu do zmiennej - rozmiar macierzy

	int rozmiarMac = atoi(argv[1]);

	//warunek sprawdzający czy wartosci argumentow nie znajduja sie poza przewidywanym przedzialem wartosci
	if (rozmiarMac <= 1)
	{
		printf("0 ms - zly rozmiarMac macierzy \n");		//jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}



// 3. -----------------------------------------------------------------------
	double czas_start=0, czas_stop=0; 			//deklaracja zmiennej do wyznaczania czasu wykonania programu
	int i=0, j=0, k=0, id_zadania, nr_procesu, dostepne_procesy; 			//deklaracja zmiennych pomocniczych (iteratorow)
	long rozmiarStart, rozmiarWiersza;



// 4. -----------------------------------------------------------------------
// Deklaracja macierzy AAA, BBB, CCC, CPOM (tablica pomocnicza) - tablic dwuwymiarowych

	double **AAA = malloc(rozmiarMac * sizeof(double*));
	double **BBB = malloc(rozmiarMac * sizeof(double*));
	double **CCC = malloc(rozmiarMac * sizeof(double*));
	double **CPOM = malloc(rozmiarMac * sizeof(double*));



// 5. -----------------------------------------------------------------------
//  Alokacja pamieci dla tablicy. Standardowa procedura do stworzenia tablicy dwuwymiarowej

	for (i = 0; i < rozmiarMac; i++)
	{
		AAA[i] = malloc(rozmiarMac * sizeof(double));
		BBB[i] = malloc(rozmiarMac * sizeof(double));
		CCC[i] = malloc(rozmiarMac * sizeof(double));
		CPOM[i] = malloc(rozmiarMac * sizeof(double));
	}



// 6. -----------------------------------------------------------------------
//   Uzupelnianie macierzy zgodnie ze specyfikacja zadania
		for (i = 0; i < rozmiarMac; i++)
		{
			for (j = 0; j < rozmiarMac; j++)
			{
				AAA[i][j] = ((int)(sin(i) * i * j)) % 10;
				BBB[i][j] = ((int)(cos(j) * (i + j))) % 10;
				CCC[i][j] = 0;
				CPOM[i][j] = 0;

			}
		}


// 7. -----------------------------------------------------------------------
//   Polecenia inicjujące obliczenia z MPI

	MPI_Init (&argc, &argv);               //  inicjuje obliczenia MPI  (pierwsza funkcja MPI)
	MPI_Status status;    				   //  okreslenie zrodla i typu komunikatu
 	MPI_Comm_rank (MPI_COMM_WORLD, &nr_procesu); //  komunikator, podanie numeru procesu
 	MPI_Comm_size (MPI_COMM_WORLD, &dostepne_procesy); //  komunikator, podanie liczby procesorow


	if (nr_procesu == 0)
	{
		rozmiarStart = 0;
		rozmiarWiersza = 0;

		if(dostepne_procesy>=1)
		{
			for (id_zadania = 0; id_zadania<dostepne_procesy; id_zadania++)
			{
				rozmiarStart = rozmiarStart + rozmiarWiersza;		//indeks poczatkowy w czesci podzielonej
				rozmiarWiersza = (id_zadania + 1)*rozmiarMac / dostepne_procesy - rozmiarStart;	//rozmiar obszaru podzielonego

				/* MPI_Send(..)

				&rozmiarWiersza / &rozmiarStart  - wiadomosc
				1 - rozmiar danych
				MPI_LONG - typ przesyłanych danych
				id_zadania - proces docelowy
				0 - znacznik identyfikujacy rodzaj komunikatu
				MPI_COMM_WORLD - komunikator

				*/

				MPI_Send(&rozmiarWiersza, 1, MPI_LONG, id_zadania, 0, MPI_COMM_WORLD);	//wyslij te informacje
				MPI_Send(&rozmiarStart, 1, MPI_LONG, id_zadania, 0, MPI_COMM_WORLD);

			}
		}

	}


// 8. -----------------------------------------------------------------------
// synchronizacja wszystkich procesow grupy i rozpoczecie pomiaru czasu obliczen

	MPI_Barrier(MPI_COMM_WORLD);
	if (nr_procesu == 0)
		czas_start = MPI_Wtime();


// 9. -----------------------------------------------------------------------
// Odczytanie komunikatow

	if(dostepne_procesy>1)
	{
		/* MPI_Recv(..)
		Wywołanie funkcji MPI_Recv jest blokowane do momentu, gdy komunikat, na który oczekujemy nadejdzie.

				&rozmiarWiersza / &rozmiarStart  - wiadomosc
				1 - rozmiar danych ( Jeśli wysłany komunikat jest dłuższy niż rozmiar bufora do odbioru, nastąpi błąd a nasza 		aplikacja zostanie przerwana )
				MPI_LONG - typ przesyłanych danych
				0 - zrodlo komunikatu (proces o randze 0 ) -  zawierającym wszystkie procesy — MPI_COMM_WORLD
				MPI_ANY_TAG - znacznik typu wiadomosci nie bedzie sprawdzany
				MPI_COMM_WORLD - komunikator
				&status - zawiera dodatkowe info. o komunikacie

		*/

		MPI_Recv(&rozmiarWiersza, 1, MPI_LONG, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);		//odczytaj
		MPI_Recv(&rozmiarStart, 1, MPI_LONG, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
		//printf("(ODBIERAM row %d to %d - task: %d)\n",rozmiarStart, rozmiarStart+rozmiarWiersza, nr_procesu);



		// mnozenie elementow macierzy
		for (i = rozmiarStart; i<(rozmiarStart+rozmiarWiersza); i++)
		{
			for (j = 0; j<rozmiarMac; j++) {
				for (k = 0; k<rozmiarMac; k++) {
					CPOM[i][j] += AAA[i][k] * BBB[k][j];
				}
			}
		}

	}
	else if((dostepne_procesy==1))
	{
		MPI_Recv(&rozmiarWiersza, 1, MPI_LONG, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);		//odczytaj
		MPI_Recv(&rozmiarStart, 1, MPI_LONG, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
		//printf("(ODBIERAM row %d to %d - task: %d)\n",rozmiarStart, rozmiarStart+rozmiarWiersza, 0);

		for (i = rozmiarStart; i<(rozmiarStart+rozmiarWiersza); i++)
		{
			for (j = 0; j<rozmiarMac; j++)
			{
				for (k = 0; k<rozmiarMac; k++)
				{
					CCC[i][j] += AAA[i][k] * BBB[k][j];
				}
			}
		}
	}



// 10. -----------------------------------------------------------------------
/* MPI_Reduce() - wykonywanie operacji sumowania, gdzie wynik jest zwracany do jednego procesora (roota)

	MPI_Reduce(...)

		CPOM - adres bufora wysylanych danych (macierz pomocnicza)
		CCC - adres bufora przechowujacego wynik operacji redukcji
		rozmiarMac*rozmiarMac - liczba przesylanych przez kazdy cpu elementow danych
		MPI_DOUBLE - identyfikator typu przesylanych danych
		MPI_SUM - identyfikator operacji redukcji (sumowanie)
		0 - numer roota
		MPI_COMM_WORLD - komunikator

*/

	if (dostepne_procesy>1)
		MPI_Reduce(CPOM, CCC, rozmiarMac*rozmiarMac, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);



// 11. -----------------------------------------------------------------------
// synchronizacja wszystkich procesow grupy i zakonczenie pomiaru czasu obliczen

	MPI_Barrier(MPI_COMM_WORLD);
 	if (nr_procesu == 0)
	{
		czas_stop=MPI_Wtime();
		printf("Czas: %f ms",(czas_stop-czas_start)*1000);
	}


// 12. -----------------------------------------------------------------------
// Ostatnia instrukcja ktora musi wykonac program korzystajacy z MPI - zakonczenie pracy

	MPI_Finalize();


  return 0;
}

