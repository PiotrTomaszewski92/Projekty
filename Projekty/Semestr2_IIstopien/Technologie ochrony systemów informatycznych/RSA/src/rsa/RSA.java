/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;
/**
 *
 * @author Procislaw
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RSA {
    BigInteger p;
    BigInteger q;
    BigInteger n;
    BigInteger pt;
    BigInteger qt;
    BigInteger v;
    BigInteger k;
    BigInteger d;
    
    RSA(int wielkosc){
                System.out.println("Wielkosc : "+wielkosc);
        this.p = BigInteger.probablePrime(wielkosc,new Random());
                System.out.println("p : "+this.p);
        this.q = BigInteger.probablePrime(wielkosc,new Random());
                System.out.println("q : "+this.q);
        this.n = p.multiply(this.q);
                System.out.println("n : "+this.n);
        this.pt = this.p.subtract(BigInteger.ONE);
                System.out.println("pt : "+this.pt);
        this.qt = this.q.subtract(BigInteger.ONE);
                System.out.println("qt : "+this.qt);
        this.v = this.pt.multiply(this.qt);
                System.out.println("v : "+this.v);    
        for(;;) {
            this.k = BigInteger.probablePrime(wielkosc, new Random());
            if (this.v.gcd(this.k).equals(BigInteger.ONE)) {         //spr czy najwiekszy wspolny dzielnik v i k to 1
                break;
            }
        }
                System.out.println("k : "+this.k);
        this.d = this.k.modInverse(this.v);
                System.out.println("d : "+this.d);
                System.out.println("rozmiar d : "+this.d.toByteArray().length);
    }
    /*
    odczytanie z pliku tekstu
    */
    private String odczyt() throws FileNotFoundException{
            File file = new File("odczyt.txt");
            Scanner in = new Scanner(file); 
            String zdanie = in.nextLine();
            return zdanie;        
    }
    
    /*
    Zamieniamy tekst na liczby co 10 znakow
    */
    private List<BigInteger> zamianaTekstuNaLiczby(String tekst, int size) {
        List<String> listaLekst = new ArrayList<>();
        List<BigInteger> listaInt = new ArrayList<>();
        
        //dzielimy tekst
        do{
            if(tekst.length()>(tekst.length()%10)){
                listaLekst.add(tekst.substring(0, size));
                tekst = tekst.substring(size);
            } else{
                listaLekst.add(tekst);
                tekst = "";
            }
        }while(tekst.length()!=0);
        
        //zamieniamy na liczby        
        for(String lista : listaLekst) {
            listaInt.add(new BigInteger(lista.getBytes()));
        }
        return listaInt;
    }

    /*
    Metoda szyfrująca metodą RSA
    */
    private List<BigInteger> szyfrowanie(BigInteger klucz, BigInteger n, List<BigInteger> listaInt) {
        List<BigInteger> listaZaszyfr = new ArrayList<>();
        for (BigInteger bloki : listaInt) {
            listaZaszyfr.add(bloki.modPow(klucz, n));   //przechodząc po elemencie listy szyfrujemy blok ^ klucza % n
        }
        return listaZaszyfr;
    }
    
    /*
    Metoda zamieniająca wartości intów z listy na tekst
    */
    private String zamianaIntNaTekst(List<BigInteger> listaInt) {
        String tekst = "";
        for(BigInteger lista : listaInt) {
            tekst+=new String(lista.toByteArray());
        }
        return tekst;
    }
    
    /*
    Metoda spr poprawnosc - jak w szyfrowaniu idealnym
    */
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
        RSA rsa = new RSA(1024);
        String tekstOryginalny = rsa.odczyt();
           
                System.out.println("Tekst oryginalny: "+tekstOryginalny+"\n\n");
        List<BigInteger> listOfMarksBigInteger = rsa.zamianaTekstuNaLiczby(tekstOryginalny, 10);
        List<BigInteger> zaszyfrowanie = rsa.szyfrowanie(rsa.k, rsa.n, listOfMarksBigInteger);
                System.out.println("Tekst zaszyfrowany: "+rsa.zamianaIntNaTekst(zaszyfrowanie)+"\n\n");
        List<BigInteger> odszyfrowanie = rsa.szyfrowanie(rsa.d, rsa.n, zaszyfrowanie);
        String tekstOdszyfrowany = rsa.zamianaIntNaTekst(odszyfrowanie);
                System.out.println("Tekst odszyfrowany: "+tekstOdszyfrowany+"\n\n");
        
                System.out.println("Poprawnosc: "+rsa.sprPoprawnosci(tekstOryginalny,tekstOdszyfrowany));
    }
    
}
