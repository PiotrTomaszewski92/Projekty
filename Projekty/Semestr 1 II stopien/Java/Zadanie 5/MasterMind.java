import javax.ejb.Stateless;
import java.util.Random;
/**
 * Klasa MasterMind implementująca interfejs IMasterMind. Zawiera 
 * implementację algorytmu to szybkiego znajdywania rozwiązania gry.
 * @author Procislaw
 */
@Stateless
public class MasterMind implements IMasterMind{
    int n;
    int k;
    long seed;   
    char[] znaki;
    int [] gdziePoprawne;
    int interacja=0;
    pl.jrj.game.IGameMonitor game;
    Random r = new Random(); 
    
    /**
     * Metoda pierwszeVerify pozwala na wypełnienie tablicy 
     * losowymi kolorami i sprawdzaniu czy pierwsza liczba po 
     * weryfikacji (liczba poprawnych kolorów znajdujących się 
     * na swoim miejscu) jest większa od drugiej (liczby kolorów 
     * poprawnych ale nie znajdujących się na swoim miejscu). 
     * @param weryf - deklaracja  zmiennej przechowującej 
     * informację z metody verify
     * @param x - deklaracja  zmiennej przechowującej liczbę 
     * poprawnych kolorów w poprawnych miejscach
     * @param xx - deklaracja  zmiennej przechowującej liczbę 
     * poprawnych kolorów w niepoprawnych miejscach
     * @return - liczba poprawnych kolorów w poprawnych miejscach
     */
    private int pierwszeVerify(String weryf, int x, int xx){
         do{
            wypelnijTab();
            weryf=game.verify(naString());
            x= Character.getNumericValue(weryf.charAt(0));
            xx= Character.getNumericValue(weryf.charAt(1));
			interacja++;
        }while(x<=xx);
         return x;
    }
    
    /**
     * Metoda wypelnijTab wypełniająca tablicę kolorami z przedziału
     * od A do A+n
     */
    private void wypelnijTab(){
        for (int i=0;i<this.k;i++){
            this.znaki[i]=(char)(r.nextInt(this.n+1)+65);
            this.gdziePoprawne[i]=0;
        }
    }
    
    /**
     * Metoda naString zamieniająca elementy tablicy na wartość 
     * tekstową
     * @return ciąg wyrazów tablicy przedstawiony w 
     * tekstowej
     */
    private String naString(){
        String z="" ;
        for (int i=0;i<this.k;i++){
            z+=znaki[i];
        }
        return z;
    }
    
/**
 * Metoda gdziePoprawne która sprawdza czy w tablicy o konkretnym
 * indeksie kolor jest poprawny czy nie. Jeśli kolor jest poprawny,
 * to zapisuje to tablicy gdziePoprawne, o odpowiednim indeksie, wartość 1.
 * @param x - stara wartość pierwszej liczby po poprzedniej weryfikacji
 * @param stary - zmienna pomocnicza przechowująca kolor tablicy przed zmianą
 * @param weryf - zmienna pomocnicza przechowująca wynik weryfikacji.
 */    
    private void gdziePoprawne(int x, char stary, String weryf){
        for (int i=0;i<this.k;i++){
            stary = this.znaki[i];
            this.znaki[i]='0';
            weryf=game.verify(naString());
            if (Character.getNumericValue(weryf.charAt(0))<x){
                this.gdziePoprawne[i]=1;
                this.znaki[i]=stary;
            }
            interacja++;
        
        }
    }
    
    /**
     * Metoda znajdzRozwiazanie sprawdza kolejne ułożenia kolorów 
     * w tablicy o tych indeksach, w których tablica gdziePoprawne 
     * nie przyjmuje wartości 1.
     * @param weryf - deklaracja  zmiennej przechowującej 
     * informację z metody verify
     * @param x - liczbę poprawnych kolorów w poprawnych miejscach 
     * po pierwszej weryfikacji
     * @param i - deklaracja  zmiennej będącej iteratorem pętli
     * @param j - deklaracja  zmiennej będącej iteratorem pętli
     */
    private void znajdzRozwiazanie(String weryf, int x, int i, int j){
        for (i=0;i<this.k;i++){
           
           if (this.gdziePoprawne[i]!=1){
               for (j=65;j<(65+this.n);j++){
                    this.znaki[i]=(char)j;
                    weryf=game.verify(naString());
                    interacja++;
                    if (x<Character.getNumericValue(weryf.charAt(0))){
                        x=Character.getNumericValue(weryf.charAt(0));
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Metoda masterMind zaimplementowana z interfejsu IMasreMind 
     * określa kolejność realizacji procesu rozgrywki i zwraca 
     * informację o ilości wykonanych kroków.
     * @return  ilość wykonanych weryfikacji niezbędnych do 
     * znalezienia rozwiązania
     */
    @Override
    public int masterMind(){
        interacja=0;
        znaki = new char[this.k];
        gdziePoprawne = new int[this.k];
        int x;     
        
        game.initGame(this.n, this.k, this.seed);
        x=pierwszeVerify("",0,0);
        gdziePoprawne(x,'0',"0");
       
        znajdzRozwiazanie("",x,0,0);    
        
    return interacja;   
    }
    
   
    /**
     * Metoda start zaimplementowana z interfejsu IMasreMind 
     * jest metodą startową uruchamianą z servletu MGame pobierającą
     * parametry.
     * @param n - ilość kolorów
     * @param k - ilość kolumn
     * @param seed - parametr niesbędny do zainicjowania gry
     * @param game - połączenie dzięki któremu możemy wywoływać metody 
     * z komponentu GameMonitor
     * @return ilość wykonanych weryfikacji niezbędnych do 
     * znalezienia rozwiązania
     */
    @Override
    public int start(int n, int k, long seed, pl.jrj.game.IGameMonitor game) {
        this.n=(n-1);
        this.k=k;
        this.seed=seed;
        this.game=game;
        
        return masterMind();
    }
    
    
}
