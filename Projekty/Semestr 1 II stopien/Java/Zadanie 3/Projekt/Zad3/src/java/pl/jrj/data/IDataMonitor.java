package pl.jrj.data;
import javax.ejb.Remote;
@Remote
/**
 * Interfejs IDataMonitor posiadający w sobie deklarację trzech metod
 * @author Tomaszewski
 */
public interface IDataMonitor {
    /**
     * Metoda register ma za zadanie zarejestrować użytkownika 
     * w systemie zwracając true jeżeli proces rejestracji 
     * zakończył się poprawnie.
     * @param hwork - numer zadania
     * @param album – numer albumu studenta
     * @return - wartość logiczna true albo false w zależności 
     * od pomyślności rejestracji
     */
    public boolean register(int hwork, String album); 
    /**
     * Metoda hasNext sprawdza, czy istnieją jeszcze dane do pobrania
     * w programie głównym
     * @return - wartość logiczna true albo false w zależności 
     * od istnienia danych (false jeśli rejestracja nie 
     * przebiegła pomyślnie)
     */
    public boolean hasNext();
    /**
     * Metoda next pozwalająca na pobranie kolejnych danych w 
     * programie głównym
     * @return wartość logiczna true albo false w zależności 
     * czy dane zostały poprawnie pobrane (false jeśli rejestracja nie 
     * przebiegła pomyślnie)
     */
    public double next();
} 