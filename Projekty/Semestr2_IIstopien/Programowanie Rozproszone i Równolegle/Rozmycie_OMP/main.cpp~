#include <iostream>
#include <cstdlib>
#include <cmath>
#include <omp.h>
#include <cv.h>
#include <highgui.h>
#include <iomanip>
#include <string>

using namespace std;
using namespace cv;

/*-------------------------------------------------------------------------------------
sMasek - funkcja liczaca sume masek dla wybranego obrazu.
maska[5][5] - maska nakladana na obraz
-------------------------------------------------------------------------------------*/
int sMasek(int maska[5][5], int i, int j)
{
	int suma=0;
	
	for(i = 0; i < 5; i++)
	{
        for(j = 0; j < 5; j++)
		{
			suma += maska[i][j];		//Musi być różna od 0 bo nie można dzielić przez 0.
		}
	}
	
	return suma;
}

/*-------------------------------------------------------------------------------------
** Zadanie było testowane dla zdjęcia "f1.jpg"!
rozmycie() - funkcja rozmywajaca obraz
-------------------------------------------------------------------------------------*/
Mat rozmycie(int sumaMasek, Mat zdjecieKopia, Mat zdjecie, int maska[5][5])
{		

	Vec3b zmPiksel, nowyPiksel;	
	int i,j,k,l,zmI,zmJ;
	double tStart=0,tStop=0;
	double r,g,b,staryR,staryG,staryB;
	
	// Rozpoczenie pomiaru czasu rozmywania obrazu wraz z przesylaniem miedzy procesami
	tStart = omp_get_wtime();
	
	/* przyspieszenie obliczen za pomoca dyrektywy OpenMP w celu zrownoleglenia petli
	
	parallel 		- wskazanie kompilatorowi obszaru kodu, ktory bedzie zrownoleglany
	omp      		- slowo kluczowe odnoszace sie do OpenMP
	for      		- informuje ze zrownoleglana bedzie petla for
	default(shared) - utawienie zasiegu zmiennych wewnatrz rownolegle przetwarzanego obszaru, 
					  gdzie parametr "shared" powoduje, ze domyslnie wszystkie zmienne w petli 
					  sa wspolne
	private(..)		- okreslenie zmiennych prywatnych (do ktorych tylko jeden wątek ma dostep)
	
	*/
	
	#pragma omp parallel for default(shared) private(i,j,k,l,zmPiksel,r,g,b,zmI,zmJ)
	
	for(k=0; k<zdjecie.rows; k++)
	{
        for(l=0; l<zdjecie.cols; l++)
		{
            r = g = b = 0;

			// Iterator indeksów maski 5x5  
            for(i=0; i<5; i++) 			
			{    				
                for(j=0; j<5; j++)      
				{                
                    zmI = l + (i-2);		
                    zmJ = k + (j-2);
					
		//nawigacja aby wspolrzedne tymczasowe nie wykroczyly poza obraz	
                    if (zmI < 0)	  
					{ 		
						zmI = 0;
					}
		    if (zmJ < 0){ 
						zmJ = 0;
					}					
		    if (zmI >= zdjecie.cols)
					{ 
						zmI = zdjecie.cols - 1;
					}		                       
                    if (zmJ >= zdjecie.rows)
					{ 
						zmJ = zdjecie.rows - 1;
					}			
		    
		// pobranie wartosci pixela w postaci 3 bitowego wektora (RGB)
                    zmPiksel = zdjecie.at<Vec3b>(Point(zmI, zmJ));
		    
                    r+=zmPiksel.val[0]*maska[i][j];
                    g+=zmPiksel.val[1]*maska[i][j];
                    b+=zmPiksel.val[2]*maska[i][j];
                }
            }
			//spr czy wartosci znajduja sie w przedziale [0,255]	
			((r/sumaMasek)>255)?(r=255) : (r=r/sumaMasek);
			((g/sumaMasek)>255)?(g=255) : (g=g/sumaMasek);
			((b/sumaMasek)>255)?(b=255) : (b=b/sumaMasek);

			// naniesienie nowych wartosci pixeli RGB
			zdjecieKopia.at<Vec3b>(Point(l,k))=Vec3b(r,g,b);
		}
	}
	
	// zakonczenie czasu obliczen
	tStop = omp_get_wtime();
	
	cout<<"Czas: "<<(tStop - tStart) * 1000<<"ms"<<endl;

	return zdjecieKopia;
}


int main(int argc, char* argv[]) 
{

/*-------------------------------------------------------------------------------------
1. Warunek sprawdzajacy czy istnieja wszystkie niezbedne argumenty wejsciowe.
-------------------------------------------------------------------------------------*/
	if (!argv[1] || !argv[2] || !argv[3]) 								
	{	
		cout << "0 ms - brak wszystkich argumentow" << endl; //jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}

/*-------------------------------------------------------------------------------------
2. Zapisanie pierwszego argumentu do zmiennej - ilosc watków. Sprawdzenie ilosci watków.
-------------------------------------------------------------------------------------*/
	int ilWatkow = atoi(argv[1]);	
	
	//atoi(argv[2]) - ciąg znaków - lokalizacja pliku źródłowego
	//atoi(argv[3]) - ciąg znaków - zapis pliku końcowego
	
	if (ilWatkow < 1) 
	{			
		cout << "0 ms - nie poprawna l. wątkow" << endl;	//jesli tak to wypisuje komunikat i wychodzi z programu
		return 0;
	}

/*-------------------------------------------------------------------------------------
3. Wczytanie pliku do zmiennej "zdjecie".
-------------------------------------------------------------------------------------*/
	Mat zdjecie;
    
	zdjecie = imread(argv[2], CV_LOAD_IMAGE_COLOR);   // drugi parametr automatycznie konwertuje obrazek do wersji kolorowej

    if(!zdjecie.data)
	{
        cout <<  "0 ms - Nie mozna znalezc obrazka" << endl ;
        return 0;
    }

/*-------------------------------------------------------------------------------------
4. Deklaracja maski w postaci 2-wymiarowej tablicy liczb całkowitych.
-------------------------------------------------------------------------------------*/	
	int maska[5][5]=
			{{1,1,2,1,1},
			 {1,2,4,2,1},
		 	 {2,4,8,4,2},
		 	 {1,2,4,2,1},
		 	 {1,1,2,1,1},
			};

/*-------------------------------------------------------------------------------------
5. Zsumowanie maski za pomoca wczesniej zadeklarowanej funkcji sMasek().
-------------------------------------------------------------------------------------*/		
	int sumaMasek=sMasek(maska,0,0);
	
/*-------------------------------------------------------------------------------------
6. Ustawienie liczby watkow dla zrownoleglonych obszarow.
-------------------------------------------------------------------------------------*/
	omp_set_num_threads(ilWatkow); 			
	
/*-------------------------------------------------------------------------------------
7. Zapisanie zmodyfikowanego obrazka w wybrane miejsce - argv[3].
-------------------------------------------------------------------------------------*/
	imwrite(argv[3], rozmycie(sumaMasek,zdjecie.clone(),zdjecie, maska));
	

	return 0;
}
