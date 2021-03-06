#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <highgui.h>
#include <string.h>

//using namespace std;
//using namespace cv;

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

int indeks_poczatek(int nr_procesu,int rozm_kolumny,int il_procesow){
	return nr_procesu * rozm_kolumny / il_procesow;
}

int indeks_koniec(int nr_procesu,int rozm_kolumny,int il_procesow){
	return (nr_procesu+1) * rozm_kolumny / il_procesow;
}

int main (int argc, char* argv[])
{
	
	if (argc != 4) 								
	{	
		printf("0 ms - brak wszystkich argumentow\n"); //jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}


	int ilWatkow = atoi(argv[1]);	
	
	//atoi(argv[2]) - ciąg znaków - lokalizacja pliku źródłowego
	//atoi(argv[3]) - ciąg znaków - zapis pliku końcowego
	
	if (ilWatkow < 1) 
	{			
		printf("0 ms - nie poprawna l. wątkow\n");	//jesli tak to wypisuje komunikat i wychodzi z programu
		return 0;
	}

	Mat zdjecie;
    
	zdjecie = imread(argv[2], CV_LOAD_IMAGE_COLOR);   // drugi parametr automatycznie konwertuje obrazek do wersji kolorowej

    	if(!zdjecie.data)
	{
        	printf("0 ms - Nie mozna znalezc obrazka\n");
        	return 0;
    	}

	int maska[5][5]=
			{{1,1,2,1,1},
			 {1,2,4,2,1},
		 	 {2,4,8,4,2},
		 	 {1,2,4,2,1},
		 	 {1,1,2,1,1},
			};
	
	int sumaMasek=sMasek(maska,0,0);

	
	Vec3b zmPiksel, nowyPiksel;	
	int i,j,k,l,zmI,zmJ,przedzialStart, przedzialStop, nr_procesu, il_procesow;
	double tStart=0,tStop=0;
	double r,g,b;
	
	// Rozpoczenie pomiaru czasu rozmywania obrazu wraz z przesylaniem miedzy procesami
	//tStart = omp_get_wtime();

MPI_Init(&argc, &argv);
MPI_Comm_rank (MPI_COMM_WORLD, &nr_procesu);
MPI_Comm_size(MPI_COMM_WORLD, &il_procesow);


	przedzialStart = indeks_poczatek(nr_procesu,zdjecie.cols,il_procesow);    
    	przedzialStop = indeks_koniec(nr_procesu,zdjecie.cols,il_procesow);  

	if (nr_procesu == 0)
	    	tStart = MPI_Wtime();    
	
	for(k=0; k<zdjecie.rows; k++)
	{
        for(l=0; l<zdjecie.cols; l++)
		{
            r = g = b = 0;

	
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
	
	if (nr_procesu == 0) {
    		tStop = MPI_Wtime();
    		printf("Czas: %.3f ms\n", 1000 * (tStop - tStart));
    	}

    MPI_Finalize();

	imwrite(zdjecieKopia);

  return 0;
}

