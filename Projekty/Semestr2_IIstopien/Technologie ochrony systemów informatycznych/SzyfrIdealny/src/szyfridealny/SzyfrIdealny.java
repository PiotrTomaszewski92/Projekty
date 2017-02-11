/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szyfridealny;


import java.io.File;
import java.util.List;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;

/*
Klasa główna szyfrująca i deszyfrująca przy użyciu Szyfru Idealnego Vernama
*/
public class SzyfrIdealny {
    
    private List<Integer> listaPierwszych;
    private Random random = new Random();
    private Double  n,s,x[];
    
    private String odczyt() throws FileNotFoundException{
            File file = new File("odczyt.txt");
            Scanner in = new Scanner(file); 
            String zdanie = in.nextLine();
            return zdanie;        
    }
    
    private byte[] generujKlucz(int rozmiarTekstu){
       double start;
       int i;
        BitSet zbiorBitow=new BitSet(rozmiarTekstu*8); //deklaracja zbioru bitów
        x= new Double[rozmiarTekstu*8]; //tworzenie tablicy o rozmiarze tekstu * rozmiar double
        generowanieLiczPierwsz();
        start=Math.abs((s*s)%n); //obliczenie wartości start listy
        x[0]=(start*start)%n; //obliczenie elementu pierwszego do zaszyfrowania
        if((x[0]%2)==1){    //jeśli pierwsza liczba jest nieparzysta to ustaw pietwszy bit na 0
            zbiorBitow.set(0);
        }
            for (i=1;i<rozmiarTekstu*8;i++) {
                x[i]=(x[i-1]*x[i-1])%n; //wyznacz kolejne wartości wg algorytmu X(i)=X(i-1)*X(i-1) (mod n) 
                if ((x[i]%2)==1) {
                    zbiorBitow.set(i);      //ustaw wartosci kolejnych bitu
                }
            }
        int wartoscIndeksu;    
        byte[] tabBitow = new byte[zbiorBitow.length()/8+1];
            for (i=0;i<zbiorBitow.length(); i++) {          //zamiana z BitSet na byte
                if (zbiorBitow.get(i)) {
                    wartoscIndeksu = tabBitow.length-i/8-1;
                    tabBitow[wartoscIndeksu] = (byte) (tabBitow[wartoscIndeksu] | 1<<(i%8));
                }
            }
        return tabBitow;

    }
    /*
    Generowanie listy l. pierwszych
    */
    private List<Integer> generuj(int zakrOd,int zakrDo){
            List<Integer> lista = new ArrayList<>();
            boolean czyPierwsza;
            int i, j;
            for(i=zakrOd;i<=zakrDo;i++){
                czyPierwsza=true;
                for(j=2;j<=i/2;j++){
                    if(i%j==0){
                        czyPierwsza=false;
                        break;
                    }
                }
                if(czyPierwsza){
                    lista.add(i);
                }
            }
            return lista;
    }
    
    private int generowanieIndeksu(){
        return Math.abs(random.nextInt()% listaPierwszych.size());
    }
    /*
    Metoda generująca p i q 
    */
    private void generowanieLiczPierwsz(){
       listaPierwszych =generuj(1000000,1019999); //generowanie liczb pierwszych i zapis do listy
        int indeks;
        double p, q;
        System.out.println("Lista: "+listaPierwszych.size());
        
        p=listaPierwszych.remove(generowanieIndeksu()); //generowanie p
        q=listaPierwszych.remove(generowanieIndeksu()); // generowanie q
        n=p*q;  //obliczanie iloczynu z p*q liczb pierwszych
        s=Math.abs(random.nextLong()%n)+1; //wybór losowego ziarna
        //wybieranie p q i s
    }
    
    private byte[] zakoduj(byte[] tekstByte,byte[] kluczByte, int rozmiar){
        byte[] tymczasowaTab=new byte[rozmiar];   //tymczasowa tabkica bitow
        for(int i=0;i<rozmiar;i++){     //przechodzenie po kazdym elemencie tekstu
            tymczasowaTab[i]=(byte) (tekstByte[i]^kluczByte[i]); //Obliczenie XOR z dwóch bitów i zapisanie wyniku to elementu tablicy tymczasowej
        }
        return tymczasowaTab;
    }
    
    private boolean sprPoprawnosci(String oryginal,String odkodowane){
        int iloscPoprawnychZnakow=0;
        char oryg,odkod;
        if(oryginal.length()!=odkodowane.length()) { //spr czy rozmiary sie zgadzaja
            return false;
        }        
        for(int i=0;i<oryginal.length();i++){    //przechodzenie po kazdym elemencie i spr znaku czy sie zgadza
            oryg=oryginal.charAt(i);
            odkod=odkodowane.charAt(i);
            if(oryg==odkod){
                iloscPoprawnychZnakow++;
            }
        }
        return iloscPoprawnychZnakow==oryginal.length(); //jesli ilosc poprawnych znakow sie nie zgadza to gdzies jest blad;
    }
    
    
    public static void main(String[] args) throws Exception {
        SzyfrIdealny szyfr = new SzyfrIdealny();
        String zdanie= szyfr.odczyt();
               
        System.out.println("Tekst: " + zdanie+"\n\n");

        byte[] kluczByte = szyfr.generujKlucz(zdanie.getBytes().length);
        byte[] zaszyfrowane=szyfr.zakoduj(zdanie.getBytes(),kluczByte, zdanie.getBytes().length);
        System.out.println("Zaszyfrowane: "+new String(zaszyfrowane)+"\n\n");

        byte[] odszyfrowane=szyfr.zakoduj(zaszyfrowane,kluczByte,zaszyfrowane.length);
        System.out.println("Odszyfrowywanie: "+new String(odszyfrowane)+"\n\n");
        
        System.out.println("Poprawnosc: "+szyfr.sprPoprawnosci(zdanie,new String(odszyfrowane)));
    }
    
}
