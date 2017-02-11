
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfejs IPlaneRemote zawierający deklaracje metod wykorzystywanych 
 * w klasie PlaneImpl niezbędny do użycia bean'a przekazującego listę 
 * punktów z servletu do klasy PlaneImpl
 * @author Procislaw
 * @version 1.0
 */
@Remote
public interface IPlaneRemote {
    /**
     * Deklaracja metody wywoływana przez bean z serwletu określająca
     * wykonywanie kolejnych metod w klasie.
     * @param arr - lista wszystkich pobranych punktów z bazy danych
     * @return  - maksymalne pole przestrzeni
     */
    public double general (List<Float> arr);
    /**
     * Deklaracja metody pozwalającej na przyporządkowanie określonych 
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
    public double prepare(List<Float> arr, int i);
    /**
     * Deklaracja metody pozwalającej obliczyć pole figury zrzutowanej 
     * na przestrzeń dwuwymiarową za pomocą wzoru do analitycznego 
     * obliczania pól.
     * @param i - zmienna pomocnicza wykorzystywana do iteracji
     * @param field - zmienna pomocnicza przechowująca wartość pola
     * @param stcSize - rozmiar stosu
     * @return - ostateczna wartość pola
     */
    public double countFields(int i, double field, int stcSize);
    /**
     * Deklaracja metody wykonującej kolejne kroki pozwalające wyznaczyć 
     * pole wielokąta przy wykorzystaniu otoczki wypukłej. Po załadowaniu 
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
    public double actions(int i, int n, float sX, float sY, double field);
    /**
     * Deklaracja metody algorytmu Grahama pobierająca każdorazowo wartość
     * iteracyjną z tablicy (getX) i dodająca do stosu. W zależności od 
     * wyniku wyznacznika wartości są usuwane ze stosu (usuwanie punktów, 
     * któe spowodowały by zbudowanie otoczki wklęsłej)
     * @param i - zmienna pomocnicza wykorzystywana do iteracji
     * @param tan - tangens nachylenia punktu do osi X
     * @param l - rozmiar tablicy tangens
     */
    public void stackAddRem(int i, PlaneImpl.Point2d[] tan, int l) ;
    /**
     * Deklaracja metody implementującej w głównej części algorytm Grahama 
     *  określający kierunki w których przechodzimy do kolejnego punktu tak, 
     * by zbudować otoczkę wypukłą. Do pomocy wykorzystywany jest stos.
     * @param tan - tangens nachylenia punktu do osi X
     * @param n - ilość wierzchołków wielokąta
     */
    public void graham(PlaneImpl.Point2d[] tan, int n);
    /**
     * Deklaracja metody dodającej do stosu wartość i inkrementująca 
     * ilość znajdujących się na nim elementów
     * @param nr - wartość do dodania na stos
     */
    public void addElemStact(int nr);
    /**
     * Deklaracja metody usuwającej wartości ze stosu i dekrementująca 
     * ilość znajdujących się na nim elementów
     */
    public void remElemStact();
    /**
     * Deklaracja metody zwracającej wartość wyznacznika ze współrzędnych 
     * punktów zawartych w tablicy współrzędnych wielokąta
     * @param a - indeks trzeciej współrzędnej 
     * @param b - indeks drugiej współrzędnej
     * @param c - indeks pierwszej współrzędnej
     * @return określenie czy wyznacznik jest dodatni czy ujemny
     */
    public int det(int a, int b, int c);
}
