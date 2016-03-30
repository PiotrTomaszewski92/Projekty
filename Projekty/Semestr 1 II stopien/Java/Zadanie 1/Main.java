
package zadanie1_pt;

import java.util.List;
import java.util.Locale;

    /**
     * Klasa Main zawierajaca w sobie funkcje main, ktora zostaje wywolana 
     * przez fragment kodu inicjujacego prace programu.
     * Na początku tworzona jest instancja klasy Path a potem tworzone jest 
     * polaczenie z bazą danych przy pomocy JDBC.
     * Następnie pobierane są dane dotyczące ilości wierzchołków 
     * i krawędzi. 
     * Tworzona jest macierz wraz z tablicą przechowującą sume wag 
     * do docelowego punktu.
     * Uzupełniamy macierz danymi pobranymi z bazy danych i wykonujemy funkcje 
     * dotyczące wyznaczania grafu i najkrótszej ścieżki 
     * do wyznaczonego punktu.
     * Funkcja wypisuje na ekran koszt transpotu od wierzchołka oznaczonego 
     * jako 1 do wierzchołka podanego przez użytkownika.
     * 
     * @author Piotr Adam Tomaszewski
     */

public class Main {
    
    /**
     * Metoda calculateGraph przygotowująca dane do przekazania ich do
     * algorytmu rozwiązującego problem znalezienia najkrótszych ścieżek 
     * w grafie, w którym wagi krawędzi mogą być ujemne.
     * Tworzona jest macierz VxV, gdzie V to ilość wierzchołków.
     * Dodatkowo tworzona jest zmienna przechowująca najmniejsze
     * wartości wag do każdego z wierzchołków.
     * Dodatkowo w metodzie wypełniana jest macierz danymi z bazy.
     * Wywoływana jest metoda implementująca algorytm Bellmana-Forda
     * a po niej metoda obliczająca koszt transportu 
     * do konkretnego wierzchołka.
     * @param v - ilość wierzchołków w grafie
     * @param e - ilość krawędzi w grafie
     * @param index - numer docelowego wierzchołka
     * @param list - lista wierzchołków początkowych i końcowych wraz 
     * z wartością krawędzi
     * @param path - instancja klasy Path
     * @return - ostateczny koszt transportu od wierzchołka pierwszego
     * do podanego w parametrze list
     */
    
    private static double calculateGraph 
        (int v, int e, int index, List<Path.Edges> list, Path path)
        {
            double[][] matrix = new double[v][v];
            double[] distance = new double[v];

            for (Path.Edges k : list)     
            {
                matrix[k.getX()-1][k.getY()-1]=k.getP();
            }       
            distance=path.bellmanFordAlg(matrix, v);
            return path.getOneEdge(v,distance,matrix,index);
        }   
    
    /**
     * Metoda main będąca reprezentacyjną metodą projektu implementuje 
     * w sobie obiekt klasy Path. 
     * Dodatkowo implementowane są zmienne pomocnicze. 
     * Do zmiennej typu List przypisujemy wynik metody getData, do której
     * wcześniej przesyłamy adres sterownika do połączenia z BD.
     * Po otrzymaniu listy wierzchołków i krawędzi wyliczamy maksymalną
     * wartość wierzchołka oraz ilość krawędzi.
     * Wywołana metoda calculateGraph przygotowuje dane to obliczeń 
     * związanych z rozwiązaniem problemu i zwraca ona liczbę 
     * w formacie double jako wynik.
     * 
     * @param args - tablica wartości, które automatycznie są przekazywane
     * podczas kompilacji programu.
     */
    public static void main(String[] args) {        
          Path path = new Path();          
          String driver = args[0];          
          int index = Integer.parseInt(args[1]);
          
        List<Path.Edges> list;
        list = path.getData(driver);
        
        
        int v = path.maxVert(list);
        int e = list.size();  
        
        double result = calculateGraph(v,e,index,list,path);

        System.out.format(Locale.ENGLISH, "%.3f", result);

    }  
}
