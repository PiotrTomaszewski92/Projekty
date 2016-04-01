import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.naming.InitialContext;
import pl.jrj.data.IDataMonitor;

/**
 * Klasa MyClient implementująca w sobie metody do obliczenia 
 * momentu bezwładności punktów pobranych z klasy implementującej interfej.
 * Klasa zawiera w sobie dwie klasy wewnętrzne w celu wygodnego 
 * reprezentowania i przechowywania współrzędnych punktów i ich masy. 
 * @author Tomaszewski
 */

public class MyClient {
    private double a;
    private double b;
    private double c;    
    private final List<Factor> factorList = new ArrayList<Factor>();
    
    /**
     * Metoda addData pozwalająca pobierać dane z komponentu ejb-project
     * i zapisywać dane w parametrach klasy i listy.
     * @param monit - odniesienie do obiektu komponentu
     * @param i - deklaracja zmiennej potrzebnej do iteracji po liście
     */
    private void addData(IDataMonitor monit, int i){
        List<Double> dataMonit = new ArrayList<Double>();
        while (monit.hasNext()){
           dataMonit.add(monit.next());
        }
        a = dataMonit.get(0);
        b = dataMonit.get(1);
        c = dataMonit.get(2);
        int dataSize = dataMonit.size();
        for (i=3;i<dataSize;i+=4){
            factorList.add(
                new Factor(dataMonit.get(i),dataMonit.get(i+1),
                            dataMonit.get(i+2),dataMonit.get(i+3)));
        }
        
    }

    /**
     * Metoda coordinates zwraca współrzędne wektora znając jego 
     * współrzędną początkową i końcową przy wykorzystaniu 
     * wzoru matematycznego
     * @param v0 - współrzędne punktu początkowego wektora
     * @param vk - współrzędne punktu końcowego wektora
     * @return - współrzędne wektora 
     */
    private Point coordinates(Point v0, Point vk){
        return (new Point(
                vk.getX()-v0.getX(),
                vk.getY()-v0.getY(),
                vk.getZ()-v0.getZ()));
    }
    /**
     * Metoda multiplyVectors pozwalająca wyznaczyć iloczyn wektorowy 
     * dwóch wektorów, które są dowolnymi wektorami 
     * w przestrzeni trójwymiarowej.
     * @param p - współrzędne wektora pierwszego 
     * @param v - współrzędne wektora drugiego
     * @return - iloczyn wektorowy dwóch wektorów
     */    
     private Point multiplyVectors(Point p, Point v) {
        return new Point(
                (p.getY()*v.getZ())-(v.getY()*p.getZ()),
                (v.getX()*p.getZ())-(p.getX()*v.getZ()),
                (p.getX()*v.getY())-(v.getX()*p.getY())
        );
     }
    /**
     * Metoda lengthVector oblicza długość wektora z jego współrzędnych 
     * wzorem matematycznym
     * @param mnozenie - współrzędne wektora w przestrzeni 3D
     * @return - długość wektora
     */
    private double lengthVector(Point mnozenie) {
        return Math.sqrt((mnozenie.getX()*mnozenie.getX())
                +(mnozenie.getY()*mnozenie.getY())
                +(mnozenie.getZ()*mnozenie.getZ())
        );
    }
     
    /**
     * Metoda sumInertia liczy dla każdego punktu o masie m odległość 
     * tych elementów od prostej będącej osią obrotu. Moment bezwładności
     * jest iloczynem masy punktu i kwadratem jego odległości od osi obrotu. 
     * Zadanie polega na zsumowaniu wszystkich momentów bezwładności 
     * i wyniku na ekran.
     * @param suma - wartość początkowa zmiennej suma 
     * @param v0 - współrzędna punktu początkowego wektora V
     * @param v - współrzędna wektora V
     * @param licznik - wartość początkowa zmiennej licznik
     * @param mianownik - wartość początkowa zmiennej mianownik
     * @return - suma wartości momentów bezwładności punktów
     */
    private double sumInertia (double suma, Point v0, Point v, 
            double licznik, double mianownik){
        Point p;
        for (Factor xyzt : factorList){
          p = coordinates(v0,new Point(
                  xyzt.getX(),xyzt.getY(),xyzt.getZ()
          ));             
          Point mnozenie = multiplyVectors(p,v);
          licznik = lengthVector(mnozenie);
          mianownik = lengthVector(v);
          double wynik = licznik/mianownik;             
          suma+=(wynik*wynik)*xyzt.getT();         
        }
        return suma;
    }  
    /**
     * Metoda prepare przygotowująca współrzędne punktu wektora v
     * do obliczenia jego dlugosci. Do obliczenia odległości punktów 
     * od prostej wykorzystywana jest metoda równoległoboku, w której 
     * szukana odległość to wysokość równoległoboku.
     */
    private void prepare(){
        Point v0 = new Point(0,0,c);
        Point vk = new Point(1,1,a+b+c);
        Point v = coordinates(v0,vk);
        double suma = sumInertia(0,v0,v,0,0);
        System.out.format(Locale.ENGLISH,"%.5f",suma);
    }
    
    /**
     * Metoda statyczna main, która wywoływana jest jako pierwsza, 
     * gdy uruchamiamy program. Inicjuje połączenie z komponentem
     * ejb-project, który posiada dane do zaimportowania 
     * przez program główny
     * @param args - tablica elementów typu String
     * @throws java.lang.Exception
     */
    public static void main(String[] args)throws Exception{
        MyClient mc = new MyClient();
        String connect = "java:global/ejb-project/DataMonitor!"
                                    +"pl.jrj.data.IDataMonitor";
        
        InitialContext con = new InitialContext();
        pl.jrj.data.IDataMonitor monit = (pl.jrj.data.IDataMonitor)
        con.lookup(connect);
        
        if (monit.register(3, "104896")){
            mc.addData(monit,0);
            mc.prepare(); 
        }
    }

    /**
     * Klasa Point reprezentująca zbiór punktów x,y,z w przestrzeni
     * trójwymiarowej
     */
    private class Point{
        protected double x;
        protected double y;
        protected double z;
        
        public Point(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        protected double getX() {
            return x;
        }

        protected void setX(double x) {
            this.x = x;
        }

        protected double getY() {
            return y;
        }

        protected void setY(double y) {
            this.y = y;
        }

        protected double getZ() {
            return z;
        }

        protected void setZ(double z) {
            this.z = z;
        }
    }
    
    /**
     * Klasa Faktor dziedzicząca pola i metody po klasie Point
     * reprezentująca zbiór punktów x,y,z w przestrzeni 3D
     * oraz masę punktu
     */
    private class Factor extends Point{
        private double t;        
        
        public Factor (double x, double y, double z, double t){
            super(x,y,z);
            this.t = t;
        }

        private double getT() {
            return t;
        }

        private void setT(double t) {
            this.t = t;
        }
    }
}
