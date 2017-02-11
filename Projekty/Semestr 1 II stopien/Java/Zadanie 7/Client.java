
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Klasa Client odpowiada na pytanie jaki procent zaewidencjonowanych 
 * w bazie klientów nie posiada ważnego ubezpieczenia na wskazany model 
 * samochodu oraz dzień. Wynik wyznaczono w procentach z dokładnością 
 * do jednego miejsca dziesiętnego. Pola marka i data są polami 
 * przechowującymi kolejno nazwę marki samochodu oraz dzień sprawdzenia.
 * @author Piotr Tomaszewski
 */
public class Client {
    private String marka;
    private Date data;
    
    /**
     * Metoda sprawdzenieKryterium pobiera wartości z bazy danych niezbędne
     * do wyznaczenia wyniku określonego z warunków zadania. Pobierana jest
     * ilość osób, które mają ważne ubezpieczenie. Ilość osób o 
     * nieważnym ubezpieczeniu określana jest przez różnicę wszystkich 
     * ludzi od osób z ważnym ubezpieczeniem.
     * @param klient - obiekt klient klasy Client
     * @param em - interfejs stosowany do współpracy z persistence context
     * @param ileKlientow - wartość liczbowa przechowująca ilość osób 
     * w bazie
     * @return Procent osób które nie posiadają ważnego ubezpieczenia.
     */
    private float sprawdzenieKryterium(Client klient, EntityManager em, 
            long ileKlientow){
        long aktualne = 0;
        float k=0;
        
            aktualne = klient.pobierzIloscWCzasie(em);
            k=((float)(ileKlientow-aktualne)/(float)ileKlientow)*100;
         
        return k; 
    }
    
    /**
     * Metoda polaczenieDb pozwalająca na połączenie się z bazą danych. 
     * Wywoływana w niej jest również metoda pobierająca ilość wszystkich 
     * użytkowników bazy.
     * @param klient - obiekt klient klasy Client
     * @return Procent osób które nie posiadają ważnego ubezpieczenia.
     */
    private float polaczenieDb(Client klient){
        EntityManagerFactory ef;
        EntityManager em;
          
        ef = Persistence.createEntityManagerFactory("persistence104896");
        em = ef.createEntityManager();
        long iloscOsob = klient.iloscKlientow(em);
               
        return sprawdzenieKryterium(klient,em,iloscOsob);
    }
    
    /**
     * Metoda iloscKlientow która wykonuje zapytanie na bazie danych i 
     * zwraca wartość liczbową wszystkich klientów
     * @param em  - interfejs stosowany do współpracy z persistence context
     * @return Wartość liczbowa wszystkich klientów w bazie
     */
    private long iloscKlientow(EntityManager em) {
        Query allClients = em.createQuery(
            "SELECT COUNT(tbc) FROM TbCustomer tbc"
        );
        long wynik = (long)allClients.getSingleResult();

        return wynik;
    }
    
    /**
     * Metoda pobierzIloscWCzasie wykonuje zapytanie na bazie danych,
     * któej wynikiem jest ilość osób, które mają aktualne ubezpieczenie.
     * @param em  - interfejs stosowany do współpracy z persistence context
     * @return Wartość liczbowa wszystkich klientów w bazie o 
     * aktualnym ubezpieczeniu
     */
    private long pobierzIloscWCzasie(EntityManager em) {
        Query zapytanie = em.createQuery(
                "SELECT COUNT(tbi.customerId) " +
                "FROM TbInsurance tbi " +
                "JOIN TbModel tbm ON tbi.modelId=tbm.id " +
                "WHERE tbm.model = :markasam " +
                "AND :datasam BETWEEN tbi.dateFrom AND tbi.dateTo");

        zapytanie.setParameter("markasam", marka);
        zapytanie.setParameter("datasam",data);       
        return (long) zapytanie.getSingleResult();
    }
    
    /**
     * Metoda formatujDate pozwalająca na poprawne sformatowanie daty 
     * pobranej z bazy danych.
     * @param format1 - druga możliwość przedstawienia daty
     * @param format2 - trzecia możliwość przedstawienia daty
     * @param parser - pierwsza możliwość przedstawienia daty
     * @param dataString - wartość daty pobrana z pliku tekstowego
     */
    private void formatujDate(DateFormat format1, DateFormat format2, 
            DateFormat parser, String dataString){
        
        try {
            data = parser.parse(dataString);
            if (data==null) {
                data = format1.parse(dataString);
            }else if (data==null) {
                data = format2.parse(dataString);
            }
        } catch (ParseException ex) {
            data= null;
        }
            
          
    }    
    
    /**
     * Metoda pobierzZpliku pobierająca wartości z pliku i zapisująca je 
     * do zmiennych globalnych.
     * @param fileName - nazwa pliku potrzebna do jego otwarcia
     * @param dataString - zmienna pomocnicza pobierająca datę w postaci 
     * String
     * @param parser - parser określający przedstawienie daty
     * @throws FileNotFoundException Wyjątek na wypadek problemów 
     * przy otwarciu pliku
     */
    private void pobierzZpliku(String fileName, String dataString, 
            DateFormat parser) throws FileNotFoundException{
        
        Scanner plik = new Scanner(new File(fileName));
        marka = plik.nextLine();
        dataString = plik.nextLine();
        
        formatujDate(
                new SimpleDateFormat("dd/MM/YYYY"), 
                new SimpleDateFormat("MM/dd/YYYY"), 
                parser, dataString
        );
    }
    
    /**
     * Główna metota main w któej tworzony jest obiekt klasy Client 
     * i inicjowane jest połączenie w celu zarejestrowania użytkownika. 
     * Jeśli rejestracja przebiegnie pomyślnie to podejmowane są kroki 
     * do obliczenia procentowej ilości osób, które nie posiadają 
     * ważnego ubezpieczenia.
     * @param args - parametr wejściowy uruchamiany przy kompilacji
     */
    public static void main(String[] args) {
        Client klient = new Client();
        String connect = "java:global/ejb-project/GameManager!"
                    +"pl.jrj.game.IGameRemote";
            
        try {
            InitialContext pol = new InitialContext();
       
            pl.jrj.game.IGameRemote game = (pl.jrj.game.IGameRemote)
                    pol.lookup(connect);
           
            if (game.register(7, "104896")){
                klient.pobierzZpliku(
                        args[0],"",
                        DateFormat.getDateInstance(
                                DateFormat.DEFAULT, 
                                Locale.getDefault()
                        )
                );
                System.out.format(Locale.US, 
                        "%.1f%%\n", klient.polaczenieDb(klient));
            }
        } catch (NamingException | FileNotFoundException ex) {
            System.out.format(Locale.US, "0.0");
        } catch (Exception ex) {
            System.out.format(Locale.US, "1.0");
            
        } 
        
    }
    
  
    
}
