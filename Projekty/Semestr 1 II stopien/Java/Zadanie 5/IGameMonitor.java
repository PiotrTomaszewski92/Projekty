package pl.jrj.game;
import javax.ejb.Remote;

/**
 * Interfejs IGameMonitor posiadający deklarację metod, która pozwalają 
 * na zarejestrowanie użytkownika w systemie, rozpoczęcie rozgrywki i 
 * zweryfikowania rozwiązania zaproponowanego przez algorytm
 * @author Procislaw 
 */
@Remote
public interface IGameMonitor {
    /**
     * Deklaracja metody register rejestrująca użytkownika w systemie
     * - zwraca true jeżeli proces rejestracji zakończył się poprawnie. 
     * Jeżeli rejestracja zakończyła się niepowodzeniem, metoda register 
     * zwraca wartość false.
     * @param hwork - numer zadania
     * @param album – numer albumu studenta
     * @return wartość logiczna czy połączenie przebiegło pomyślnie
     */
public boolean register(int hwork, String album);

/**
 * Deklaracja metody initGame umożliwia zainicjowanie gry. 
 * Ustala poszukiwane przez gracza w kolejnych ruchach 
 * początkowe ustawienie pionków.
 * @param n - ilość kolorów
 * @param k - ilość kolumn
 * @param seed - parametr niesbędny do zainicjowania gry
 */
public void initGame(int n, int k, long seed);

/**
 * Deklaracja metody verify sprawdza aktualne ustawienie 
 * kolorów przez gracza z kolorami ustalonymi podczas
 * inicjowania gry. Zwraca dwucyfrową informację. 
 * @param state - aktualne ustawienie kolorów przez gracza
 * @return - dwie cyfry: pierwsza z nich określa ilość pionów 
 * o właściwym kolorze ustawionych we właściwej kolumnie, 
 * drugi z nich określa ilość pionów o właściwym kolorze 
 * lecz ustawionych w niewłaściwej kolumnie.
 */
public String verify(String state);
}
