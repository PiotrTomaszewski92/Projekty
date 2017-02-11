/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha256;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.ByteBuffer;

/**
 *
 * @author Procislaw
 */
public class Sha256 {
    /*
    Pozwoliłem sobie troche pomóc wikipedią - zmienne h i k... :)
    Tylko nie stosuje tozmiaru 512 a 64 (przeskalowałem sobie wszystko 8-krotnie)
    */
    int h0; 
    int h1; 
    int h2; 
    int h3;
    int h4; 
    int h5; 
    int h6; 
    int h7;
    int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };
    
    Sha256(){
        h0 = 0x6a09e667;
        h1 = 0xbb67ae85;
        h2 = 0x3c6ef372;
        h3 = 0xa54ff53a;
        h4 = 0x510e527f;
        h5 = 0x9b05688c;
        h6 = 0x1f83d9ab;
        h7 = 0x5be0cd19;
    }
    
    private String odczyt() throws FileNotFoundException{
            File file = new File("odczyt.txt");
            Scanner in = new Scanner(file); 
            String zdanie = in.nextLine();
            return zdanie;        
    }
    
    private void tablicaDoListy(List<Byte> listaBajtow, byte[] bajty) {
        int i;
        for(i=0;i<bajty.length;i++){
            listaBajtow.add(bajty[i]);
        }
    }
    
    /*
    Metoda przygotowująca tekst skladajacy sie z rozmiaru o wielokrotnosci 512
    */
    private Byte[] przygotowanieTekst(String tekst) {
        List<Byte> listaBajtow = new ArrayList();
        tablicaDoListy(listaBajtow, tekst.getBytes());         //kopiujemy wiadomość do bloku

        listaBajtow.add((byte) 0x80);                             //dodanie wartości 1 na koniec tekstu(w bitach)

        
        while (listaBajtow.size() % 64 != 56) {                   //bloki wypełnia się zerami aż do ostatnich 64 bitów
            listaBajtow.add((byte) 0);
        }

        long dlugoscBitow = tekst.length() * 8;    
        
        ByteBuffer bufor = ByteBuffer.allocate(Long.BYTES);
        bufor.putLong(dlugoscBitow);
        tablicaDoListy(listaBajtow, bufor.array());             //dodanie 64 bitowego bloku razem z dlugoscia wiadomosi

        return listaBajtow.toArray(new Byte[listaBajtow.size()]);   //zwracamy wygenerowany blok
    }
    
    private String metodaSHA(Byte[] bity){
        int i, j, s0, s1;
        ByteBuffer bufor;
        for (i=0; i<bity.length; i+=64) {
            int[] kawalki = new int[64];
            
            //podzial kawalkow na 4-bitowe slowa w [0..15]
            for (j=0; j<16; j++) {  
                bufor = ByteBuffer.allocate(Integer.BYTES);
                bufor.put(bity[i + j * 4]);
                bufor.put(bity[i + j * 4 + 1]);
                bufor.put(bity[i + j * 4 + 2]);
                bufor.put(bity[i + j * 4 + 3]);
                kawalki[j] = bufor.getInt(0);
            }
            
             //Rozszerzenie szesnaście 4-bitowych słów na 8 4-bitowe słowa:
            while(j < 64) {    
                s0 = Integer.rotateRight(kawalki[j - 15], 7) ^ Integer.rotateRight(kawalki[j - 15], 18) ^ (kawalki[j - 15] >>> 3);
                s1 = Integer.rotateRight(kawalki[j - 2], 17) ^ Integer.rotateRight(kawalki[j - 2], 19) ^ (kawalki[j - 2] >>> 10);
                kawalki[j] = kawalki[j - 16] + s0 + kawalki[j - 7] + s1;
                j++;
            }

            //Inicjalizuj wartość skrótu dla tego kawałka:
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;
            int f = h5;
            int g = h6;
            int h = h7;

            //główna pętla szyfrująca
            int S1, S0, ch, t1, maj, t2;
            for (j = 0; j < 64; j++) {
                S1 = Integer.rotateRight(e, 6) ^ Integer.rotateRight(e, 11) ^ Integer.rotateRight(e, 25);
                ch = (e & f) ^ ((~e) & g);
                t1 = h + S1 + ch + k[j] + kawalki[j];
                S0 = Integer.rotateRight(a, 2) ^ Integer.rotateRight(a, 13) ^ Integer.rotateRight(a, 22);
                maj = (a & b) ^ (a & c) ^ (b & c);
                t2 = S0 + maj;

                h = g;
                g = f;
                f = e;
                e = d + t1;
                d = c;
                c = b;
                b = a;
                a = t1 + t2;
            }
            //Dodanie hash'u kawałka do bieżącego rezultatu: 
            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
            h5 += f;
            h6 += g;
            h7 += h;
        }
        //Zwrócenie ostatecznej wartości skrótu:
        return Integer.toHexString(h0) + Integer.toHexString(h1)
                + Integer.toHexString(h2) + Integer.toHexString(h3)
                + Integer.toHexString(h4) + Integer.toHexString(h5)
                + Integer.toHexString(h6) + Integer.toHexString(h7);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Sha256 szyfr = new Sha256();
        String zdanie= szyfr.odczyt();
               
        System.out.println("Tekst: " + zdanie+"\n\n");
        
        Byte[] bajty = szyfr.przygotowanieTekst(zdanie);
        System.out.println("bajty sajz: "+bajty.length);
        
        
        System.out.println(szyfr.metodaSHA(bajty));
    }
    
}
