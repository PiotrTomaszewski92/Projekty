
package zadanie1_pt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Klaa path implementująca w sobie połączenie z bazą danych, 
 * pobranie danych z bazy i operacje polegające na wyznaczeniu grafu
 * i kosztu transportu.
 * @author Piotr Adam Tomaszewski
 */

class Path {   
    /**
     * Publiczny domyślny konstruktor
     */
    public Path(){}
    /**
     * Metoda dataList dodająca dane pobrane z baz danych do listy typu Edge 
     * zawierającej parę wierzchołków i koszt przepływu pomiędzy nimi.
     * @param result - dane uzyskane z bazy danych przy użyciu zapytania SQL
     * @return - Zwraca zestawienie połączenia krawędziami pomiędzy 
     * wierzchołkami w grafie
     * @throws SQLException 
     */
    private List<Edges> dataList(final ResultSet result) throws SQLException {
        List<Edges> edges = new ArrayList<Edges>();
        
        for (;;){
            if (result.next()) {
                Edges point = new Edges(
                        result.getInt("x"), 
                        result.getInt("y"), 
                        result.getDouble("p")
                );
                edges.add(point);
            }
            else break;
        }

        return edges;
    }
    
    /**
     * Metoda getData łącząca się z bazą danych za pomocą adresu
     * sterownika przekazanego przez parametr i pozwalająca wykonać
     * działania na bazie danych – głównie w tym przypadku pobranie danych
     * i zapisanie ich do listy.
     * @param connect - przekazanie adresu sterownika JDBC pozwalającego
     * na połączenie się z bazą danych.
     * @return Lista par wierzchołków i odpowiadające im wagi.
     */
    protected List<Edges> getData(String connect){
        List<Edges> edge = new ArrayList<Edges>();
        try {
            Connection conn = DriverManager.getConnection(connect);
            Statement statement = conn.createStatement();
            edge = dataList(statement.executeQuery("SELECT * FROM GData"));
            statement.close();
            conn.close();
        }
        catch (SQLException e) {
			System.out.println(e);
        }
        return edge;
    }
    
   /**
    * Metoda searchMax wyznaczająca poprzez instrukcje warunkowe 
    * maksymalną wartość wierzchołka
    * @param x - wartość wierzchołka początkowego
    * @param y - wartość wierzchołka końcowego
    * @param maxX - wartość minimalna Integer 
    * @param maxY - wartość minimalna Integer 
    * @return Maksymalny numer wierzchołka grafu
    */
    
    private int searchMax(int x, int y, int maxX, int maxY){  
            if (maxX<=x) maxX=x;
            if (maxY<=y) maxY=y;
            if (maxX>maxY) 
			   return maxX; 
		   else 
			   return maxY;
    }
    
    /**
     * W metodzie searchMaxEdge pobieramy kolejne wartości wierzchołka 
     * początkowego i końcowego i przesyłamy je 
     * do kolejnej funkcji w celu znalezienia maksimum.
     * @param x - wartość wierzchołka początkowego
     * @param y - wartość wierzchołka końcowego
     * @param maxX - wartość minimalna Integer 
     * @param maxY - wartość minimalna Integer 
     * @param edges - lista wszystkich wierzchołków początkowych i końcowych
     * @return Maksymalny numer wierzchołka grafu
     */
    
    private int searchMaxEdge(
            int x, int y, int maxX, int maxY, List<Edges> edges
    ){  
        int max = 0;
        for (Edges e : edges){
            
            x = e.getX();
            y = e.getY();
            max=searchMax(x,y,maxX,maxY);
        }
        return max;
    }
    
    
    /**
     * Metoda maxVert pozwalająca wyznaczyć maksymalną wartość wierzchołka
     * w grafie
     * @param edges - lista wierzchołków grafu
     * @return - maksymalny numer wierzchołka grafu
     */
    protected int maxVert(List<Edges> edges){
        int x=0;
        int y=0;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;   
        
        return searchMaxEdge(x,y,maxX,maxY,edges);
    }
    /**
     * Metoda previous tworząca listę poprzednich wierzchołków grafu
     * w zależności od wierzchołka docelowego.
     * @param x - wierzchołek dla którego aktualnie są szukani poprzednicy
     * w macierzy
     * @param v - ilość wierzchołków w grafie
     * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
     * wierzchołków, które posiadają krawędź
     * @return - lista poprzedników wierzchołka
     */
    private static List<Integer> previous(int x, int v, double[][] matrix) {
        List<Integer> result = new ArrayList<Integer>(); 
        for (int i=0;i<v;i++)
            if (matrix[i][x]!=0.0)
                result.add(i);

        return result;
    }
    
    /**
     * Metoda wyznaczająca najmniejszą drogę od wierzchołka początkowego 
	 * do każdego następnego
     * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
     * wierzchołków, które posiadają krawędź
     * @param v - ilość wierzchołków w grafie
     * @param distance - tablica zawierająca  najmniejszą sumę wag
     * od wierzchołka początkowego do każdego następnego. 
     * @param i - zmienna pomocnicza wykorzystywana jako iterator
     * @param j - zmienna pomocnicza wykorzystywana jako iterator
     * @return  - zaktualizowana tablica zawierająca  najmniejszą sumę wag
     * od wierzchołka początkowego do każdego następnego. 
     */
    
    private double[] bellmanFordRoad(
            double[][] matrix, int v, double [] distance, int i, int j
    ){
        for (i=0; i<v; ++i){
            for (j=0; j< v; ++j){
                if (matrix[i][j]!=0.0){
                    int u = i;
                    int vv = j;
                    double weight = matrix[i][j];
                    if (distance[u]!=Integer.MAX_VALUE 
                            && distance[u]+weight<distance[vv]){
                        distance[vv]=distance[u]+weight;                    
                    }
                }                
            }
        }
        return distance;
    }
    
    /**
     * Metoda bellmanFordAlg implementująca główny algorytm Bellmana-Forda 
     * do znalezienia ścieżki o najmniejszej wadze pomiędzy 
     * dwoma wierzchołkami w grafie ważonym.
     * Na samym początku deklarujemy zmienną tablicową distance,
     * która będzie przechowywać najmniejsze wagi do konkretnych 
     * wierzchołków.
     * Następnie algorytm inicjuje odległości do wszystkich pozostałych
     * wierzchołków jak INFINITE. Zaś korzeń ustawia jako wartość 0. 
     * W kolejnym kroku przechodzimy po kolejnych elementach macierzy
     * i sprawdzamy sumę wag, czy jest mniejsza od już istniejącej.
     * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
     * wierzchołków, które posiadają krawędź
     * @param v - ilość wierzchołków w grafie
     * @return - tablica najmniejszych wag do każdego wierzchołka w grafie
     */
    
    protected double[] bellmanFordAlg(double[][] matrix, int v)
    {
        int i;
        int j;
        double[] distance = new double[v];        
        for (i=0; i<v; ++i)
            distance[i] = Integer.MAX_VALUE;   
        distance[0] = 0;
        
        return bellmanFordRoad(matrix,v,distance,0,0);
    }
    
    /**
	 *Metoda stackEdges konstruująca drogę do wierzchołka docelowego 
	 * z wykorzystaniem danych zawartych w liście poprzednicy zawierającej 
	 * informację o wszystkich poprzednikach od wierzchołka początkowego 
	 * do docelowego.
     * @param poprzedniki - lista zawierająca zestawienie wierzchołków
	 * od początkowego do docelowego
     * @param distance - tablica zawierająca najmniejsze rozmiary dróg 
	 * do wierzchołków
     * @param v - ilość wierzchołków grafu
     * @param u - numer poprzedniego wierzchołka na najmniejszej drodze 
	 * od początkowego do końcowego wierzchołka
     * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
     * wierzchołków, które posiadają krawędź 
     * @return - kolejna wartość wierzchołka na najmniejszej drodze 
	 * od początkowego do końcowego wierzchołka
     */
    
     private int stackEdges(
             List<Integer> poprzedniki,double[] distance, 
             int v, int u, double[][] matrix
     ){
         int size = poprzedniki.size();
         int i;
         for (i=0;i<size;i++)
                if (distance[v]==distance[poprzedniki.get(i)]
                        +matrix[poprzedniki.get(i)][v])
                    u=poprzedniki.get(i);
         return u;
     }
    
    /**
     * Metoda polegająca na wypełnienie stosu wierzchołkami będącymi 
     * na drodze od wierzchołka początkowego do końcowego określonego grafu.
     * Metoda wywołuje metodę poprzednicy, która zwraca listę poprzednich
     * wierzchołków grafu w zależności od wierzchołka docelowego.
     * Następnie konstruowana jest droga od wierzchołka docelowego
     * do startowego która dodawana jest do stosu.
     * @param vv - ilość wierzchołków w grafie
     * @param distance - tablica zawierająca  najmniejszą sumę wag
     * od wierzchołka początkowego do każdego następnego. 
     * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
     * wierzchołków, które posiadają krawędź
     * @param stack - pusty stos przechowujący kolejne numery wierzchołków
     * od wierzchołka docelowego do początkowego.
	 * @param v - ilość wierzchołków grafu
     * @param u - numer poprzedniego wierzchołka na najmniejszej drodze 
	 * od początkowego do końcowego wierzchołka
     * @return wypełniony stos kolejnymi wierzchołkami grafu.
     */
     
    
   private List<List<Integer>> stackEdgesPrepare(
           int vv, double[] distance, double[][] matrix, 
           List<List<Integer>> stack, int v,int u
   ){   
       for (int x=0;x<vv;x++){
            stack.get(x).add(x);
            v=x;
            while (v!=0){
                u=stackEdges(previous(v,vv,matrix),distance,v,u,matrix);
                stack.get(x).add(u);
                v=u;
            }
        }
        return stack;
    }
	
	/**
    * Metoda getOneEdge3 wyznaczająca z podanych danych 
	* wartość k, określającą oszt transportu definiowany 
	* dla każdego z węzłów ścieżki. 
    * @param k - wartość sumy odwrotności kolejnych wartości wag krawędzi 
    * @param pyz - waga krawędzi między drugim a trzecim wierzchołkiem 
    * @return odwrócona wartość wagi wierzchołka zsumowana 
	* z wartością początkową k.
    */
	
	
    private double getOneEdge3(double k, double pyz){
        if (pyz!=0)
            k+=1/(pyz);
        else
            k=0.0;
        return k;
    }
   
   /**
    * Metoda getOneEdge2 rozszerzająca funkcjonalność metody getOneEdge2
    * @param px - zmienna przechowująca wartość wierzchołka x
    * @param py - zmienna przechowująca wartość wierzchołka y
    * @param pxy - zmienna przechowująca wartość krawędzi między x i y
    * @param pyz - zmienna przechowująca wartość krawędzi między y i z
    * @param b - zmienna będąca iteratorem w pętli
    * @param index - numer wierzchołka docelowego
    * @param k - wartość sumy odwrotności kolejnych wartości wag krawędzi 
    * @param stack - stos przechowujący kolejne drogi do wierzchołka 
	* docelowego
    * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
    * wierzchołków, które posiadają krawędź
    * @param abss - zmienna przechowująca wynik wartości bezwzględnej 
	* różnicy krawędzi.
	* @param size - rozmiar stosu
    * @return suma wartości bezwzględnej z odwrotnościami kolejnych 
	* wartości wag krawędzi
    */
   
   private double getOneEdge2(int px, int py, double pxy, double pyz, int b, 
           int index, double k, List<List<Integer>> stack, double[][] matrix,  
           double abss,  int size
           ){
     
    for (b=1;b<size;b++){
        py=stack.get(index).get(b);        
        pyz=matrix[py][px];
        
        k=getOneEdge3(k,pyz);
        
        if (pxy!=0){
            abss+=Math.abs(pxy-pyz);
        }
        pxy=pyz;        
        px=py;
    }
    return  abss+k;
   }
   
   /**
    * Metoda zwracająca wartość kosztu transportu dla każdego z węzłów 
    * ścieżki od wierzchołka początkowego do wierzchołka podanego 
    * przez użytkownika.
    * Tworzony jest stos dla każdego wierzchołka grafu - przechowywane
    * w nim będą pośrednie wierzchołki grafu.
    * Za pomocą metody stackEdges wypełniamy stos wierzchołkami.
    * W następnym kroku obliczany jest koszt transportu między wierzchołkiem
    * początkowym a wierzchołkami końcowymi.
    * @param vv - ilość wierzchołków w grafie
    * @param distance - tablica zawierająca  najmniejszą sumę wag
    * od wierzchołka początkowego do każdego następnego. 
    * @param matrix - macierz zawierająca zestawienie wag dla konkretnych
    * wierzchołków, które posiadają krawędź.
    * @param index - numer docelowego wierzchołka grafu
    * @return koszt transportu między wierzchołkiem początkowym
    * a wierzchołkiem końcowym, który został podany przez użytkownika.
    */
   
protected double getOneEdge(
        int vv, double[] distance, double[][] matrix, int index
){    
      List<List<Integer>> stack = new ArrayList<List<Integer>>(vv); 
     for (int i = 0; i < vv; i++) {
          stack.add(i,new ArrayList<Integer>());
     }
     
    stack=stackEdgesPrepare(vv,distance,matrix,stack,0,0); 
    
    
   return getOneEdge2(stack.get(index-1).get(0), 0, 0, 0, 0, index-1, 0, 
           stack, matrix, 0, stack.get(index-1).size());
}
    /**
     * Ostatni element to klasa wewnętrzna Edges, zawierająca dane 
     * dotyczące początku i końca krawędzi wraz z wartością 
     * określającą przepustowość krawędzi.
     */
    protected class Edges {
        private int x; //wierzcholek poczatkowy
        private int y; //wierzcholek koncowy
        private double p; //przepustowosc (waga) krawedzi
        
        public Edges(){}

        public Edges(int x, int y, double p) {
            this.x = x;
            this.y = y;
            this.p = p;
        }

        //Akcesory
        protected int getX() {
            return x;
        }

        protected void setX(int x) {
            this.x = x;
        }

        protected int getY() {
            return y;
        }

        protected void setY(int y) {
            this.y = y;
        }

        protected double getP() {
            return p;
        }

        protected void setP(double p) {
            this.p = p;
        }
        
        //Metoda toString wypisujaca wartosci pol klasy.
        public String toString() {
            return String.format("%d - %d : %f", x, y, p);
        }
        
    }
}
