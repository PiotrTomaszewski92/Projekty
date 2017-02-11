/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteki;
import java.io.File;
import java.io.FileNotFoundException;
import sun.misc.BASE64Encoder;
import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.Scanner;

/**
 *
 * @author Procislaw
 */
public class Biblioteki {
    
    private String odczyt() throws FileNotFoundException{
            File file = new File("odczyt.txt");
            Scanner in = new Scanner(file); 
            String zdanie = in.nextLine();
            return zdanie;        
    }
    
    private byte[] aesSymKodowanie(Cipher cipher, SecretKey klucz, String tekst) throws Exception{
            cipher.init(Cipher.ENCRYPT_MODE, klucz);
            byte[] zakod = cipher.doFinal(tekst.getBytes());
            System.out.println("\t\tZakodowane: " + new String(zakod));
            return zakod;
    }
    
    private void aesSymDekodowanie(Cipher cipher, SecretKey klucz, byte[] zakodowane) throws Exception{
            cipher.init(Cipher.DECRYPT_MODE,klucz);
            String odkod = new String(cipher.doFinal(zakodowane));
            System.out.println("\t\tOdkodowane: " + odkod);
    }
    
    private void aesSym(String tekst) throws Exception{
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            SecretKey klucz = kg.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            // zakodowanie:
            byte[] zakodowane = aesSymKodowanie(cipher, klucz, tekst);
            // Odkodowanie:
            aesSymDekodowanie(cipher, klucz, zakodowane);
    }
    
    private byte[] desSymKodowanie(Cipher cipher, SecretKey klucz, String tekst) throws Exception{
            cipher.init(Cipher.ENCRYPT_MODE, klucz);
            byte[] zakod = cipher.doFinal(tekst.getBytes());
            System.out.println("\t\tZakodowane: " + new String(zakod));
            return zakod;
    }
    
    private void desSymDekodowanie(Cipher cipher, SecretKey klucz, byte[] zakodowane) throws Exception{
            cipher.init(Cipher.DECRYPT_MODE,klucz);
            String odkod = new String(cipher.doFinal(zakodowane));
            System.out.println("\t\tOdkodowane: " + odkod);}
    
    private void desSym(String tekst) throws Exception{
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            SecretKey klucz = kg.generateKey();
            Cipher cipher = Cipher.getInstance("DES");
            // Zakodowanie:
            byte[] zakodowane = desSymKodowanie(cipher, klucz, tekst);
            // Odkodowanie:
            aesSymDekodowanie(cipher, klucz, zakodowane);
    }
    
    private byte[] rsaKodowanie (Cipher cipher, Key kluczPubliczny, String tekst) throws Exception{
            cipher.init(Cipher.ENCRYPT_MODE, kluczPubliczny);
            byte[] zakod = cipher.doFinal(tekst.getBytes());
            System.out.println("\t\tZakodowane: " + new String(zakod));
            return zakod;
    }
    
    private void rsaDekodowanie (Cipher cipher, Key kluczPrywatny, byte[] zakodowane) throws Exception{
            cipher.init(Cipher.DECRYPT_MODE, kluczPrywatny);
            String odkod = new String(cipher.doFinal(zakodowane));
            System.out.println("\t\tOdkodowane: " + odkod);
    }
    
    private KeyPair paraKluczy(int kod) throws Exception{
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(kod);
            return kpg.genKeyPair();
    }
    
    private void rsa (String tekst, int kod) throws Exception{
            Key kluczPubliczny, kluczPrywatny;
            KeyPair paraKluczy = paraKluczy(kod);
            kluczPubliczny = paraKluczy.getPublic();
            kluczPrywatny = paraKluczy.getPrivate();
            Cipher cipher = Cipher.getInstance("RSA");
            // Zakodowanie:
            byte[] zakodowane = rsaKodowanie(cipher,kluczPubliczny,tekst);
            // Odkodowanie:
            rsaDekodowanie(cipher,kluczPrywatny,zakodowane);
    }
    
    private String sha512 (String tekst) throws Exception{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] funSkr = md.digest(tekst.getBytes());
            BigInteger bigInt = new BigInteger(1,funSkr);
            return bigInt.toString(16);
    }
    
    private String md2 (String tekst) throws Exception{
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] funSkr = md.digest(tekst.getBytes());
            BigInteger bigInt = new BigInteger(1,funSkr);
            return bigInt.toString(16);
    }
    
    private String shaWithRsa (String tekst) throws Exception{
            String wypisz;
            byte[] dane = tekst.getBytes("UTF8");
            KeyPair paraKluczy = paraKluczy(1024);
            Signature sygnatura = Signature.getInstance("SHA512withRSA");
            sygnatura.initSign(paraKluczy.getPrivate());
            sygnatura.update(dane);
            byte[] signatureBytes = sygnatura.sign();
            wypisz = "\tSygnatura:" + new BASE64Encoder().encode(signatureBytes)+"\n";
            
            sygnatura.initVerify(paraKluczy.getPublic());
            sygnatura.update(dane);
            return wypisz+"\t\tWeryfikacja:"+sygnatura.verify(signatureBytes);
    }
    
    private String md2WithRsa(String tekst) throws Exception{
        String wypisz;
            byte[] dane = tekst.getBytes("UTF8");
            KeyPair paraKluczy = paraKluczy(1024);
            Signature sygnatura = Signature.getInstance("MD2withRSA");
            sygnatura.initSign(paraKluczy.getPrivate());
            sygnatura.update(dane);
            byte[] signatureBytes = sygnatura.sign();
            wypisz = "\tSygnatura:" + new BASE64Encoder().encode(signatureBytes)+"\n";
            
            sygnatura.initVerify(paraKluczy.getPublic());
            sygnatura.update(dane);
            return wypisz+"\t\tWeryfikacja:"+sygnatura.verify(signatureBytes);
    }
    
    public static void main(String[] args) throws Exception {
        Biblioteki biblioteki= new Biblioteki();
        String tekst = biblioteki.odczyt();
        System.out.println("Tekst: " + tekst);
        
        
        System.out.println("Zadanie 1: szyfruje/odszyfrowuje dane z pliku dwoma wybranymi metodami z kluczem symetrycznym");
            System.out.println("\tAES");
            biblioteki.aesSym(tekst);
            System.out.println("\tDES");
            biblioteki.desSym(tekst);
        
        System.out.println("Zadanie 2: szyfruje/odszyfrowuje dane z pliku dwoma wybranymi metodami z kluczem asymetrycznym (prywatny+publiczny)");
            System.out.println("\tRSA");
            biblioteki.rsa(tekst,2048);
                   
        System.out.println("Zadanie 3: Skraca tekst z pliku dwoma wybranymi funkcjami skrótu");
            System.out.println("\tSHA-512: "+biblioteki.sha512(tekst));
            System.out.println("\tMD2: "+biblioteki.md2(tekst));
                

        System.out.println("Zadanie 4: umozliwia zlozenie podpisu cyfrowego ");
            System.out.println("\tSHA512withRSA: \n\t"+biblioteki.shaWithRsa(tekst));
            System.out.println("\tMD2withRSA: \n\t"+biblioteki.md2WithRsa(tekst));
 
    }

    

    

    
    
}
