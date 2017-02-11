
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
 * Klasa Grade wyznaczająca mediane ocen z danego przedmiotu 
 * i ocene konkretnej osoby a następnie określa ile procent wynik 
 * indywidualny jest niższy/wyższy od mediany.
 * @author Piotr Tomaszewski
 */
public class Grade {

   
    private String kurs=null;
    private String imie=null;
    private String nazwisko=null;
    
  
    /**
     * Metoda polaczenieDb pozwalająca na połączenie się z bazą danych.
     * W metodie pobierane są dane po wywołaniu z metod niezbędne 
     * do obliczenia zadania
     * @param grade - obiekt grade klasy Grade
     * @return Ile procent wynik indywidualny jest niższy od mediany.
     */
    private double polaczenieDb(Grade grade){
        EntityManagerFactory ef;
        EntityManager em;
          
        ef = Persistence.createEntityManagerFactory("myPersistence");
        em = ef.createEntityManager();
        long ocena = grade.pobierzOcene(em);             
        double mediana = obliczMediane(pobierzOcenyWszystkich(em),0);        
        return wynik(ocena,mediana);
    }
    /**
     * Metoda pobierzOcene pozwalająca pobrać ocene studenta
     * znając jego imie, nazwisko i przedmiot.
     * @param em - interfejs stosowany do współpracy z persistence 
     * context
     * @return ocena jaką dostał użytkownik z konkretnego przedmiotu
     */
    private long pobierzOcene(EntityManager em) {
        
        Query ilosc = em.createQuery(
                "SELECT sc.mark"
                + " FROM TblCourses c"
                + " JOIN TblStudentCourse sc ON sc.courseId = c.id"
                + " JOIN TblStudents s ON s.id = sc.studentId"
                + " WHERE s.firstName =:imie"
                + " AND s.lastName =:nazwisko"
                + " AND c.courseName =:kurs");
            ilosc.setParameter("imie", imie);
            ilosc.setParameter("nazwisko", nazwisko);
            ilosc.setParameter("kurs", kurs);
        long wynik = (long)ilosc.getResultList().get(0);
        return wynik;
    }
    /**
     * Metoda pobierzOcenyWszystkich wyznaczająca listę ocen 
     * z konkretnego przedmiotu. Dane posortowane są rosnąco.
     * @param em - interfejs stosowany do współpracy z persistence 
     * context
     * @return posortowana lista ocen z przedmiotu
     */
   @SuppressWarnings("unchecked")
   private List<Long> pobierzOcenyWszystkich(EntityManager em) {
       
       Query oceny = em.createQuery(
                "SELECT sc.mark"
                + " FROM TblCourses c"
                + " JOIN TblStudentCourse sc ON sc.courseId = c.id"
                + " JOIN TblStudents s ON s.id = sc.studentId"
                + " WHERE c.courseName =:kurs"
                + " ORDER BY sc.mark ASC");
            oceny.setParameter("kurs", kurs);
       
        List<Long> lista = oceny.getResultList();
        return lista;     
   
   }
   /**
    * Metoda obliczMediane pozwalająca obliczyć mediane ocen 
    * znajdujących się w liście
    * @param lista - lista ocen uporządkowana rosnąco
    * @param mediana - zmienna pomocnicza przechowująca wartość
    * mediany
    * @return wartośc mediany
    */
   private double obliczMediane(List<Long> lista, double mediana) {       
        if (lista.size()%2==0){
            mediana=((double)lista.get((lista.size()/2)-1)
                    +(lista.get(lista.size()/2)))/2;
        } 
        else if (lista.size()%2==1){
            mediana=lista.get(lista.size()/2);
        } 
        return mediana;
    }
   
   /**
    * Metoda wynik wyznaczająca ostateczny wynik o ile procent 
    * wynik indywidualny jest niższy/wyższy od mediany.
    * @param ocena - ocena studenta
    * @param mediana - mediana ocen
    * @return ostateczny wynik procentowy
    */
    private double wynik(long ocena, double mediana) {        
        if (ocena>mediana) {
            return((ocena/mediana)-1)*100;
        } 
        else if (ocena<mediana) {
            return((ocena/mediana)-1)*100;
        }  else {
            return 0;
        }        
    }
    
    
   /**
    * Metoda formatujString pozwalająca na odczytanie danych z pliku
    * @param sB - zmienna pomocnicza  pozwalająca tworzyć 
    * i modyfikować łańcuch znaków
    * @param sB2 - zmienna pomocnicza  pozwalająca tworzyć 
    * i modyfikować łańcuch znaków
    * @param k - zmienna pomocnicza przechowująca pobraną nazwę 
    * kursu z pliku
    * @param in - zmienna pomocnicza przechowująca pobrane imie 
    * i nazwisko z pliku
    */
    private void formatujString(StringBuilder sB, StringBuilder sB2, 
            String k, String in, int kSize, int inSize) {
        for (int i=0; i<(kSize-1); i++){
            if ((k.charAt(i)==(char)32)&&(k.charAt(i+1)==(char)32)){}
            else
                sB.append(k.charAt(i));
        }
        for (int i=0; i<(inSize-1); i++){
            if ((in.charAt(i)==(char)32)&&(in.charAt(i+1)==(char)32)){}
            else
                sB2.append(in.charAt(i));
        }
        
        zapisGlobalny(sB,sB2,k,in);
        
        
    }
    /**
     * Metoda zapisGlobalny zapisująca pobrane dane z pliku 
     * do zmiennych globalnych (pól klasy).
     * @param sB - zmienna pomocnicza  pozwalająca tworzyć 
    * i modyfikować łańcuch znaków
    * @param sB2 - zmienna pomocnicza  pozwalająca tworzyć 
    * i modyfikować łańcuch znaków
    * @param k - zmienna pomocnicza przechowująca pobraną nazwę 
    * kursu z pliku
    * @param in - zmienna pomocnicza przechowująca pobrane imie 
    * i nazwisko z pliku
     */    
     private void zapisGlobalny(StringBuilder sB, StringBuilder sB2,
             String k, String in) {
        sB.append(k.charAt(k.length()-1));
        kurs=sB.toString();
        sB2.append(in.charAt(in.length()-1));
        
        String imieNazwisko=sB2.toString();
        String[] dane = imieNazwisko.split(" +");
        imie = dane[0];
        nazwisko = dane[1];
     }
    
    /**
     * Metoda pobierzZpliku pobierająca wartości z pliku.
     * @param fileName - nazwa pliku potrzebna do jego otwarcia
     * @param imieNazwisko - zmienna pomocnicza pobierająca datę w postaci 
     * String
     * @param parser - parser określający przedstawienie daty
     * @throws FileNotFoundException Wyjątek na wypadek problemów 
     * przy otwarciu pliku
     */
     
     /**
      * Metoda pobierzZpliku pobierająca wartości z pliku.
      * @param fileName - nazwa pliku potrzebna do jego otwarcia
      * @param in - zmienna pomocnicza przechowująca imie i nazwisko
      * studenta
      * @param k - zmienna pomocnicza przechowująca nazwę kursu
      * @throws FileNotFoundException Wyjątek na wypadek problemów 
      * przy otwarciu pliku
      */
    private void pobierzZpliku(String fileName, String in, String k) 
            throws FileNotFoundException{
        String delim = "[ /]+";
        
        Scanner plik = new Scanner(new File(fileName)).useDelimiter(delim);
        k = plik.nextLine();
        in = plik.nextLine();
        plik.close();
        
        StringBuilder sB = new StringBuilder();
        StringBuilder sB2 = new StringBuilder();
        
        formatujString(sB,sB2,k,in,k.length(),in.length());
    }
    
    /**
     * Metoda inicjująca rejestracje uzytkownika w systemie
     * @return inicjalizacja użytkownika w systemie
     * @throws NamingException Wyjątek na wypadek problemów 
     * z rejestracją użytkownika 
     */
     private pl.jrj.game.IGameRemote inicjowanie() throws NamingException {
         String connect = "java:global/ejb-project/GameManager!"
                    +"pl.jrj.game.IGameRemote";
        InitialContext pol = new InitialContext();
        
        return (pl.jrj.game.IGameRemote)pol.lookup(connect);
    }
    
    /**
     * Główna metota main w któej tworzony jest obiekt klasy Grade 
     * i inicjowane jest połączenie w celu zarejestrowania użytkownika. 
     * Jeśli rejestracja przebiegnie pomyślnie to podejmowane są kroki 
     * do określenia  o ile procent wynik indywidualny studenta 
     * z przedmiotujest niższy/wyższy od mediany. 
     * @param args - parametr wejściowy uruchamiany przy kompilacji
     */
    public static void main(String[] args) {
        Grade grade = new Grade();
        
            
        try {
            pl.jrj.game.IGameRemote game = grade.inicjowanie();
            
            if (game.register(8, "104896")){
                grade.pobierzZpliku(args[0],"","");
                System.out.format(Locale.US, 
                        "%.1f%%\n", grade.polaczenieDb(grade));
            }
        } catch (NamingException ex) {
            System.out.format(Locale.US, "0.0");
        } catch (FileNotFoundException ex) {
            System.out.format(Locale.US, "1.0");
            
        } 
        
    }

   

   

   
    
  
    
}
