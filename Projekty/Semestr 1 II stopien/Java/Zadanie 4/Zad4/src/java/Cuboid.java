import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;

/**
 * Klasa Cuboid implementująca metody interfejsu ICuboidRemote
 * służące do obliczenia objętości graniastosłupa prostego mając jego
 * wierzchołki w przestrzeni 3D przy użyciu m.in. algorytmu Grahama.
 * Posiada pola: stack - reprezentujący  stos zaimplementowany jako lista;
 * points - tablica zawierająca zbiór punktów w przestrzeni 2D 
 * wykorzystywana w algorytmie Grahama.
 * pocz - wartownik wskazujący ilość elementów na stosie
 * z - suma wartości indeksów z w przestrzeni 3D
 * @author Procislaw
 * @version 1.0
 */

@Stateless
public class Cuboid implements ICuboidRemote {
    List<Integer> stack = new ArrayList<Integer>();
    Point2d[] points;
    int pocz=0;
    float z = 0;
    
    /**
     * Główna metoda general wywoływana przez bean z serwletu określająca
     * wykonywanie kolejnych metod w klasie.
     * @param arr - lista wszystkich pobranych punktów z bazy danych
     * @return  - objetość graniastosłupa
     */
    @Override
    public double general (List<Float> arr){
        double wynik=0;
        prepare(arr,0);        
        wynik=actions(0,arr.size()/3,points[0].getX(),points[0].getY(),0);
        return wynik;
    }
    
    /**
     * Metoda prepare przyporządkowująca wartości punktów z listy do 
     * tablicy obiektów w przestrzeni 2D. Metoda ma na celu 
     * odseparowanie ciągu liczb na odpowiadające im współrzędne X i Y. 
     * Współrzędna Z jest od razu sumowana aby wyznaczyć jej średnią.
     * @param arr - lista wszystkich pobranych punktów z bazy danych
     * @param i - zmienna pomocnicza do iteracji po elementach
     */
    @Override
    public void prepare(List<Float> arr, int i) {
        points = new Point2d[arr.size()/3];
        int j=0;
        int arrSize = arr.size();
        for (i=0;i<arrSize;i+=3){
            
            float x = arr.get(i);
            float y = arr.get(i+1);
            z+= arr.get(i+2);
            points[j] = new Cuboid.Point2d(x,y);
            j++;
        }
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
     * graniastosłupa przy wykorzystaniu otoczki wypukłej. Po załadowaniu 
     * elementów do tablic następuje sortowanie ich w tablicy. Potem
     * wywoływane są metody odpowiedzialne za algorytm Grahama i następuje
     * wypisanie objętości graniastosłupa.
     * @param i - zmienna pomocnicza wykorzystywana do iteracji
     * @param n - ilość wierzchołków graniastosłupa
     * @param sX - współrzędna x punktu początkowego
     * @param sY - współrzędna y punktu początkowego
     * @param field - zmienna przechowująca wartość pola
     * @return - objętość graniastosłupa
     */
    @Override
    public double actions(int i, int n, float sX, float sY, double field) {
        Point2d[] tan = new Point2d[n-1];
        Arrays.sort(points,new Compar());
        for (i = 1; i < n;i++){    
            tan[i-1]=new Cuboid.Point2d(i,(
                    (points[i].getX()-sX)/(points[i].getY()-sY)));            
        }
        Arrays.sort(tan,new Compar());
        graham(tan,n);

        field = (Math.abs(countFields(0,0,stack.size())))/2;
        return (field*(z/n));
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
                     while (pocz!=1 && det(stack.get(pocz-2),stack.get(pocz-1),nr)==0 ){ 
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
     * @param n - ilość wierzchołków graniastosłupa
     */
    @Override
    public void graham(Point2d[] tan, int n) {
        int dett;
       addElemStact(0);
       stackAddRem(0,tan,tan.length-1);
       while (det(stack.get(pocz-2),stack.get(pocz-1),0)==0 && pocz!=1) 
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
     * punktów zawartych w tablicy współrzędnych graniastosłupa
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
    
    
}
