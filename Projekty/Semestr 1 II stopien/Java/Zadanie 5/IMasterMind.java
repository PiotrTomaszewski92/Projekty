import javax.ejb.Local;

/**
 *
 * @author Procislaw
 */
@Local
public interface IMasterMind {
    
    /**
     * Deklaracja metody masterMind określa kolejność realizacji 
     * procesu rozgrywki i zwraca informację o ilości 
     * wykonanych kroków.
     * @return  ilość wykonanych weryfikacji niezbędnych do 
     * znalezienia rozwiązania
     */
    public int masterMind();
     
    /**
     * Deklaracja metody start jest metodą startową uruchamianą 
     * z servletu MGame pobierającą parametry.
     * @param n - ilość kolorów
     * @param k - ilość kolumn
     * @param seed - parametr niezbędny do zainicjowania gry
     * @param game - połączenie dzięki któremu możemy wywoływać metody 
     * z komponentu GameMonitor
     * @return ilość wykonanych weryfikacji niezbędnych do 
     * znalezienia rozwiązania
     */
    public int start(int n, int k, long seed, pl.jrj.game.IGameMonitor game);
    
}
