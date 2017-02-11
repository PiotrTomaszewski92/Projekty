
package pl.jrj.game; 
import javax.ejb.Remote;
/**
 * Interfejs IGameRemote posiadający deklarację metody, która pozwala 
 * na zarejestrowanie użytkownika w systemie.
 * @author Piotr Tomaszewski
 */
@Remote
public interface IGameRemote {
    /**
     * Metoda register rejestrująca użytkownika w systemie - zwraca true 
     * jeżeli proces rejestracji zakończył się poprawnie. 
     * Jeżeli rejestracja zakończyła się niepowodzeniem, metoda register 
     * zwraca wartość false.
     * @param hwork - numer zadania
     * @param album – numer albumu studenta
     * @return wartość logiczna czy połączenie przebiegło pomyślnie
     */
public boolean register(int hwork, String album); 
} 
