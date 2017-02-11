#include <iostream>
#include <cstdlib>
#include <cmath>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <mpi.h>
#include <iomanip>
#include <string>
#include <map>

using namespace std;
using namespace cv;

int sMasek(int maska[5][5], int i, int j)
{
	int suma=0;
	
	for(i = 0; i < 5; i++){
        	for(j = 0; j < 5; j++){
			suma += maska[i][j];		//Musi być różna od 0 bo nie można dzielić przez 0.
		}
	}
	
	return suma;
}

int main (int argc, char* argv[])
{
	
	if (argc != 3){	
		cout << "0 ms - brak wszystkich argumentow" << endl; //jesli nie to wypisuje komunikat i wychodzi z programu
		return 0;
	}


	Mat zdjecie;
    
	zdjecie = imread(argv[1], CV_LOAD_IMAGE_COLOR);   // drugi parametr automatycznie konwertuje obrazek do wersji kolorowej

    	if(!zdjecie.data){
        	cout <<  "0 ms - Nie mozna znalezc obrazka" << endl ;
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
	int i,j,k,l,zmI,zmJ, nr_procesu, il_procesow, aaaa=0;
	double tStart=0,tStop=0;
	double r,g,b;
	String nazwa;
	long przedzialStart, rozmiarWiersza;

	Mat zdjecieKopia=Mat_<Vec3b>(zdjecie.rows,zdjecie.cols);
	
	

	MPI_Init(&argc, &argv);
	MPI_Status status;
	MPI_Comm_rank (MPI_COMM_WORLD, &nr_procesu);
	MPI_Comm_size(MPI_COMM_WORLD, &il_procesow);
 

	if (nr_procesu == 0){
		przedzialStart = 0;
		rozmiarWiersza = 0;
		if(il_procesow>1){
			for (int id_zadania = 0; id_zadania<il_procesow; id_zadania++){
				przedzialStart = przedzialStart + rozmiarWiersza;		//indeks poczatkowy w czesci podzielonej
				rozmiarWiersza = (id_zadania + 1)*zdjecie.rows / il_procesow - przedzialStart;	//rozmiar obszaru podzielonego

				MPI_Send(&rozmiarWiersza, 1, MPI_LONG, id_zadania, 0, MPI_COMM_WORLD);	//wyslij te informacje
				MPI_Send(&przedzialStart, 1, MPI_LONG, id_zadania, 0, MPI_COMM_WORLD);
				//cout<<"(PRZYPISUJE row "<<przedzialStart<<" to "<<rozmiarWiersza+przedzialStart<<" - task: "<<id_zadania<<")"<<endl;
			}
		}

	}

	MPI_Barrier(MPI_COMM_WORLD);
	if (nr_procesu == 0){
		
	    	tStart = MPI_Wtime();
	}

	if(il_procesow>1){
	//else{	
		MPI_Recv(&rozmiarWiersza, 1, MPI_LONG, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);		//odczytaj
		MPI_Recv(&przedzialStart, 1, MPI_LONG, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
		//cout<<"(ODBIERAM row "<<przedzialStart<<" to "<<rozmiarWiersza+przedzialStart<<" - task: "<<nr_procesu<<")"<<endl;
		
		for(k=przedzialStart; k<(rozmiarWiersza+przedzialStart); k++){
			for(l=0; l<zdjecie.cols; l++){
				r = g = b = 0;
				for(i=0; i<5; i++){    				
					for(j=0; j<5; j++){                
					    	zmI = l + (i-2);		
					    	zmJ = k + (j-2);
					
						if (zmI < 0){ 		
							zmI = 0;
						}
					    	if (zmJ < 0){ 
							zmJ = 0;
						}					
					    	if (zmI >= zdjecie.cols){ 
							zmI = zdjecie.cols - 1;
						}		                       
					    	if (zmJ >= zdjecie.rows){ 
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
	}else{
		
		for(k=0; k<zdjecie.rows; k++){
			for(l=0; l<zdjecie.cols; l++){
				r = g = b = 0;
				for(i=0; i<5; i++){    				
					for(j=0; j<5; j++){                
					    	zmI = l + (i-2);		
					    	zmJ = k + (j-2);
					
						if (zmI < 0){ 		
							zmI = 0;
						}
					    	if (zmJ < 0){ 
							zmJ = 0;
						}					
					    	if (zmI >= zdjecie.cols){ 
							zmI = zdjecie.cols - 1;
						}		                       
					    	if (zmJ >= zdjecie.rows){ 
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

	}

	MPI_Barrier(MPI_COMM_WORLD);
	if (nr_procesu == 0) {
    		tStop = MPI_Wtime();
    		printf("Czas: %.3f ms\n", 1000 * (tStop - tStart));
    	}

	

    	MPI_Finalize();

	imwrite(argv[2],zdjecieKopia);

  return 0;
}
