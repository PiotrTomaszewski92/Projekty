
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Klasa PlaneImpl pozwalająca na poszukiwanie maksymalnego pola 
 * obszaru w zależności od położenia współrzędnej Z z dokładnością
 * do 5 miejsc dziesiętnych.
 * @author Procislaw
 */
@Stateless
public class PlaneImpl implements IPlaneRemote {
    List<Integer> stack = new ArrayList<Integer>();
    List<Point3d> points3d = new ArrayList<Point3d>();
    Point2d[] points;
    int pocz=0;
    float z = 0;

    /**
     * Główna metoda general wywoływana przez bean z serwletu określająca
     * wykonywanie kolejnych metod w klasie.
     * @param arr - lista wszystkich pobranych punktów z bazy danych
     * @return  - maksymalne pole przestrzeni
     */
    @Override
    public double general(List<Float> arr) {
        double wynik;
        wynik = prepare(arr,0);  
        return wynik;
    }
    
    /**
     * Metoda prepare pozwala na przyporządkowanie określonych 
     * wartości pobranych z bazy do listy jako współrzędne punktów 
     * w przestrzeni 3D. Jeśli kolejna grupa punktów posiada inną 
     * współrzędną z, to następuje przeniesienie punktow do tablicy 
     * w celu wykonania otoczki wypukłej na tych punktach i obliczeniu 
     * pola. Następnie porównywana jest dotychczasowa maksymalna wartość 
     * pola i jeśli nowy wynik jest większy od istniejącego maksimum 
     * to jest zamieniane. 
     * @param arr - lista wszystkich pobranych punktów z bazy danych
     * @param i - zmienna pomocnicza do iteracji po elementach
     * @return - maksymalna wartość pola w obszarach
     */
    @Override
    public double prepare(List<Float> arr, int i) {
        double max = 0;
        int j;
        double wynik=0;
        float flag = arr.get(2);
        int arrSize = arr.size();
        for (i=0;i<arrSize;i+=3){
            
            float x = arr.get(i);
            float y = arr.get(i+1);
            float zz = arr.get(i+2);
            
            if ((flag==zz)){
               points3d.add(new Point3d(x,y,z));
            }
            if ((flag!=zz)){
                j=0;
                flag=zz;
                points = new Point2d[points3d.size()];                
                for (Point3d str : points3d ){
                    points[j]= new PlaneImpl.Point2d(str.getX(),str.getY());
                    j++;
                }
                if ( points3d.size()>2){
                    wynik=actions(0,points.length,points[0].getX(),
                            points[0].getY(),0);
                    if (wynik>max) max = wynik;
                }
                
                points3d.clear();
                stack.clear();
                pocz=0;
                z = 0;
                points3d.add(new Point3d(x,y,z));
            }
            if (((i+3)==arrSize)){
                j=0;
                flag=zz;
                points = new Point2d[points3d.size()];                
                for (Point3d str : points3d ){
                    points[j]= new PlaneImpl.Point2d(str.getX(),str.getY());
                    j++;
                }
                
                if (points3d.size()>2){
                    wynik=actions(0,points.length,points[0].getX(),
                            points[0].getY(),0);
                    if (wynik>max) max = wynik;
                }
                pocz=0;
                z = 0;
                points3d.clear();
                stack.clear();
            }
            
        }
        
        return max;
    }

    
    /**
     * Metoda countFields pozwalająca obliczyć pole figury zrzutowanej 
     * na przestrzeń dwuwymiarową za pomocą wzoru do analitycznego 
     * obliczania pól.
     * @param i - zmienna pomocnicza wykorzystywana do iteracji
     * @param field - zmienna pomocnicza przechowująca wartość pola
     * @param stcSize - rozmiar stosu
     * @return - ostateczna wartość pola
     */

    @Override
    public double countFields(int i, double field, int stcSize) {
        for (i = 0;i<stcSize;i++ ){
            if (i==0){
                    field+= (points[stack.get(i)].getX())*
                            (points[stack.get(i+1)].getY()-
                            points[stack.get(stack.size()-1)].getY());
                }else if (i==stack.size()-1){
                    field+= (points[stack.get(i)].getX())*
                            (points[stack.get(0)].getY()-
                            points[stack.get(i-1)].getY());
                }else{
                    field+= (points[stack.get(i)].getX())*
                            (points[stack.get(i+1)].getY()-
                            points[stack.get(i-1)].getY());
                }
        }
        return field;
    }
    /**
     * Metoda actions wykonująca kolejne kroki pozwalające wyznaczyć pole 
     * wielokąta przy wykorzystaniu otoczki wypukłej. Po załadowaniu 
     * elementów do tablic następuje sortowanie ich w tablicy. Potem
     * wywoływane są metody odpowiedzialne za algorytm Grahama i następuje
     * wypisanie wartości pola.
     * @param i - zmienna pomocnicza wykorzystywana do iteracji
     * @param n - ilość wierzchołków wielokąta
     * @param sX - współrzędna x punktu początkowego
     * @param sY - współrzędna y punktu początkowego
     * @param field - zmienna przechowująca wartość pola
     * @return - pole wielokąta
     */
  
    @Override
    public double actions(int i, int n, float sX, float sY, double field) {
        Point2d[] tan = new Point2d[n-1];
        System.out.println("size: "+points.length);
        Arrays.sort(points,new Compar());
        for (i = 1; i < n;i++){    
            tan[i-1]=new PlaneImpl.Point2d(i,(
                    (points[i].getX()-sX)/(points[i].getY()-sY)));            
        }
        Arrays.sort(tan,new Compar());
       graham(tan,n);

        field = (Math.abs(countFields(0,0,stack.size())))/2;
        //return (field*(z/n));
        return field;

    }
    
    /**
     * Metoda stackAddRem algorytmu Grahama pobierająca każdorazowo wartość
     * iteracyjną z tablicy (getX) i dodająca do stosu. W zależności od 
     * wyniku wyznacznika wartości są usuwane ze stosu (usuwanie punktów, 
     * któe spowodowały by zbudowanie otoczki wklęsłej)
     * @param i - zmienna pomocnicza wykorzystywana do iteracji
     * @param tan - tangens nachylenia punktu do osi X
     * @param l - rozmiar tablicy tangens
     */
    
    @Override
    public void stackAddRem(int i, Point2d[] tan, int l) {
        for (i = l; i >=0;i--){
                int nr=(int) tan[i].getX();
                if (pocz==1) addElemStact(nr);
                else{
                     while (pocz!=1 && det(stack.get(pocz-2),
                             stack.get(pocz-1),nr)==0 ){ 
                         remElemStact();
                     }
                    addElemStact(nr);
                }
        }
    }


    /**
     * Metoda graham implementująca w głównej części algorytm Grahama 
     *  określający kierunki w których przechodzimy do kolejnego punktu tak, 
     * by zbudować otoczkę wypukłą. Do pomocy wykorzystywany jest stos.
     * @param tan - tangens nachylenia punktu do osi X
     * @param n - ilość wierzchołków wielokąta
     */
   
    @Override
    public void graham(Point2d[] tan, int n) {
        int dett;
       addElemStact(0);
       stackAddRem(0,tan,tan.length-1);
       while (pocz!=1 && det(stack.get(pocz-2),stack.get(pocz-1),0)==0) 
           remElemStact();
        if (pocz!=n)
           addElemStact(0);
    }
    /**
     * Metoda addElemStact dodająca do stosu wartość i inkrementująca 
     * ilość znajdujących się na nim elementów
     * @param nr - wartość do dodania na stos
     */
    
    @Override
    public void addElemStact(int nr) {
        stack.add(nr);
        pocz++;
    }

    /**
     * Metoda remElemStact usuwająca wartości ze stosu i dekrementująca 
     * ilość znajdujących się na nim elementów
     */
    
    @Override
    public void remElemStact() {
        stack.remove(stack.size()-1);
        pocz--;
    }
    /**
     * Metoda det zwracająca wartość wyznacznika ze współrzędnych 
     * punktów zawartych w tablicy współrzędnych wielokąta
     * @param a - indeks trzeciej współrzędnej
     * @param b - indeks drugiej współrzędnej
     * @param c - indeks pierwszej współrzędnej
     * @return określenie czy wyznacznik jest dodatni czy ujemny
     */
    
    @Override
    public int det(int a, int b, int c) {
        double p=points[a].getX()*points[b].getY()+
                points[b].getX()*points[c].getY()+
                points[c].getX()*points[a].getY()-
                points[c].getX()*points[b].getY()-
                points[a].getX()*points[c].getY()-
                points[b].getX()*points[a].getY();
        if (p>=0) 
            return 1;
        else
            return 0;
    }

   
    
    
    /**
     * Klasa wewnętrzna Compar implementująca interfejs i udostępniająca 
     * metodę compare, która przyjmuje 2 obiekty i zawiera utworzony schemat 
     * postępowania w przypadku różnych lub równych obiektów.
     */    
    private class Compar implements Comparator<Point2d>{

        @Override
        public int compare(Point2d o1, Point2d o2) {
            Float y1 = o1.getY();
            Float y2 = o2.getY();
            int sComp = y1.compareTo(y2);
            
            if (sComp!=0){
                return sComp;
            } else{
                Float x1 = o1.getX();
                Float x2 = o2.getX();
                return x1.compareTo(x2);
            }
        }
        
    }
    
    /**
     * Klasa Point2d reprezentująca zbiór punktów x,y w przestrzeni
     * dwuwymiarowej
     */
    public class Point2d {
        float x;
        float y;
        
        public Point2d(){}
        
        public Point2d (float x, float y){
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

    }
    
    /**
     * Klasa Point3d reprezentująca zbiór punktów x,y,z w przestrzeni
     * trójwymiarowej
     */
    public class Point3d {
        float x;
        float y;
        float z;
        
        public Point3d(){}
        
        public Point3d (float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
         
        public float getZ() {
            return z;
        }

        public void setZ(float z) {
            this.z = z;
        }

    }
}
