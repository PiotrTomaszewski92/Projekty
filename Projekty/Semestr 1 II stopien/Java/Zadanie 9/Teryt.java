
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipInputStream;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Klasa Teryt zwracająca ilość jednostek podziału terytorialnego kraju 
 * na poszczególnych stopniach hierarchii.
 * @author Tomaszewski
 */
@Path("teryt")
public class Teryt {
    private String adres = "http://www.stat.gov.pl/broker/access/prefile/"
            + "downloadPreFile.jspa?id=1358";
    private Document document;
    private boolean flaga=false;
    
    /**
     * Bezparametrowy konstruktor klasy. Wywoływana jest metoda 
     * do zarejestrowania użytkownika.
     */
    public Teryt() {
        rejestracja();
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
        * Metoda wykonująca rejestrację użytkownika. Jeśli rejestracja 
        * przebiegnie pomyślnie to przygotowywany jest dokument 
        * z którego pobierane  będą wartości z wykorzystaniem modelu DOM.
        * @return wartość logiczna, czy rejestracja przebiegła pomyślnie, 
        * bądz nie.
        */
        private boolean rejestracja() {
            try {
                //pl.jrj.game.IGameRemote game = inicjowanie();

                if (inicjowanie().register(9, "104896")){
                    przygotowanieDok(DocumentBuilderFactory.newInstance(),
                            null,null);
                    flaga=true;
                    return true;
                }
                else
                    return false;
            } catch (NamingException ex) {
                System.out.format(Locale.US, "0.0");
                return false;
            } 
        
        }
        
    /**
     * Metoda pobierająca plik i przygotowująca go do wykonywania obliczeń
     * poprzez rozpakowanie paczki. Aby wczytac dokument XML potrzebny 
     * jest obiekt klasy DocumentBuilder ktory mozemy uzyskac z fabryki 
     * documentBuilderFactory
     * @param documentBuilderFactory - dostęp do fabryki z której 
     * otrzymamy dostęp do obiektu klasy DocumentBuilder
     * @param documentBuilder - zmienna pozwalająca na budowę element 
     * typu Document
     * @param zipInputStream - zmienna obsługująca pliki z rozszerzeniem 
     * ZIP.
     */
    private void przygotowanieDok(
            DocumentBuilderFactory documentBuilderFactory, 
            DocumentBuilder documentBuilder, 
            ZipInputStream zipInputStream) {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            URL url = new URL(adres);
            InputStream inputStream = url.openStream();
            zipInputStream = new ZipInputStream(inputStream, StandardCharsets.UTF_8);
            zipInputStream.getNextEntry();
            document = documentBuilder.parse(zipInputStream);
            document.getDocumentElement().normalize();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }    
        
    /**
     * Metoda retWojewodztwo zwracająca listę województw pobranych 
     * z pliku XML.
     * @param listaCol - lista węzłów tagu col
     * @param i - pomocnicza zmienna iteratora
     * @param col - pojedyńczy węzeł w drzewie DOM
     * @return lista województw 
     */
    private List<String> retWojewodztwo (NodeList listaCol, 
            int i, Node col, int size){
        List<String> woj = new ArrayList<String>();
        for (i = 0; i < size; i++) {
            col = listaCol.item(i);
            if (col.getNodeType() == Node.ELEMENT_NODE) {
                Element elemWoj = (Element) col;
                if (elemWoj.getAttribute("name").equalsIgnoreCase("WOJ")){
                    if ((!woj.contains(elemWoj.getTextContent()))
                    &&(elemWoj.getTextContent().length()!=0)) 
                        woj.add(elemWoj.getTextContent());
                }
            }
        }
    return woj;
    }

    /**
     * Metoda zliczWoj wyznaczająca liczbę województw zapisanych 
     * w liście zwróconej z metody retWojewodztwo. Zanim wynik zostanie
     * zwrócony najpierw następuje sprawdzenie, czy rejestracja uzytkownika
     * przebiegła pomyślnie.
     * @return ilość województw.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int zliczWoj() {        
        if (flaga){            
            return retWojewodztwo(
                    document.getElementsByTagName("col"),0,null,
                    document.getElementsByTagName("col").getLength()
            ).size();
        }else
            return 0;
    }
    
    /**
     * Metoda zliczPow zliczająca powiaty dla id województwa podanego 
     * w adresie URL, po sprawdzeniu, czy rejestracja uzytkownika
     * przebiegła pomyślnie.
     * @param woj - numer województwa
     * @return ilość powiatów
     */
    @GET
    @Path("{woj : \\d{2}}") 
    public int zliczPow(@PathParam("woj") String woj) {
        List<String> pow = new ArrayList<String>();
        if (flaga){
            NodeList krawedzKol = document.getElementsByTagName("col");
                    for (int j = 0; j < krawedzKol.getLength(); j++) {
                        Node krawedz = krawedzKol.item(j);
                        if (krawedz.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementKraw = (Element) krawedz;

                            if (elementKraw.getAttribute("name").equalsIgnoreCase("WOJ")) {
                                if (elementKraw.getTextContent().equalsIgnoreCase(woj.trim())) {                                     
                                     NodeList krawedzWoj = ((Element) elementKraw.getParentNode()).getElementsByTagName("col");
                                     for (int l = 0; l < krawedzWoj.getLength(); l++) {
                                                Node kWoj = krawedzWoj.item(l);
                                                Element elementWoj = (Element) kWoj;
                                            if (elementWoj.getAttribute("name").equalsIgnoreCase("POW")) {
                                                if((elementWoj.getTextContent().length()!=0)&&(!pow.contains(elementWoj.getTextContent()))){
                                                    pow.add(elementWoj.getTextContent());
                                                }
                                            }
                                    }
                                }         
                            }
                        }
                    }
            return pow.size();
        }else
            return 0;
    }
    /**
     * Metoda pobierająca nr województwa i powiatu w przypadku, 
     * gdy w linku nie są oddzielone slash'em.
     * @param wojewodztwo - pobrany nr województwa
     * @param powiat - pobrany nr powiatu
     * @return ilość gmin w powiecie
     */
    @GET
    @Path("{woj : \\d{2}}{pow : \\d{2}}") 
    public int zliczGm(@PathParam("woj") String wojewodztwo, @PathParam("pow") String powiat) {
       return obliczGminy(wojewodztwo,powiat);
    }
    
    /**
     * Metoda pobierająca nr województwa i powiatu w przypadku, 
     * gdy w linku dane są oddzielone slash'em.
     * @param wojewodztwo - pobrany nr województwa
     * @param powiat - pobrany nr powiatu
     * @return ilość gmin w powiecie
     */
    @GET
    @Path("{woj : \\d{2}}/{pow : \\d{2}}") 
    public int zliczGm2(@PathParam("woj") String wojewodztwo, @PathParam("pow") String powiat) {
       return obliczGminy(wojewodztwo,powiat);
    }
    
    /**
     * Metoda zliczająca gminy dla podanego województwa i powiatu, 
     * po sprawdzeniu, czy rejestracja uzytkownika przebiegła pomyślnie.
     * @param wojewodztwo - pobrany nr województwa
     * @param powiat - pobrany nr powiatu
     * @return ilość gmin w powiecie
     */
    private int obliczGminy(String wojewodztwo, String powiat){
        List<String> gm = new ArrayList<String>();
        if (flaga){
            NodeList krawedzKol = document.getElementsByTagName("col");
                    for (int j = 0; j < krawedzKol.getLength(); j++) {
                        Node krawedz = krawedzKol.item(j);
                        if (krawedz.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementKraw = (Element) krawedz;
                            if (elementKraw.getAttribute("name").equalsIgnoreCase("WOJ")) {
                                if (elementKraw.getTextContent().equalsIgnoreCase(wojewodztwo.trim())) {                                     
                                    NodeList krawedzWoj = ((Element) elementKraw.getParentNode()).getElementsByTagName("col");
                                    for (int l = 0; l < krawedzWoj.getLength(); l++) {
                                            Node kWoj = krawedzWoj.item(l);
                                            Element elementWoj = (Element) kWoj;
                                            if (elementWoj.getAttribute("name").equalsIgnoreCase("POW")) {
                                                if (elementWoj.getTextContent().equalsIgnoreCase(powiat.trim())) {
                                                    NodeList krawedzPow = ((Element) elementWoj.getParentNode()).getElementsByTagName("col");
                                                    for (int m = 0; m < krawedzPow.getLength(); m++) {
                                                        Node kPow = krawedzPow.item(m);
                                                        Element elementPow = (Element) kPow;
                                                        if (elementPow.getAttribute("name").equalsIgnoreCase("GMI")) {
                                                            if((elementPow.getTextContent().length()!=0)&&(!gm.contains(elementPow.getTextContent())))
                                                                gm.add(elementPow.getTextContent());
                                                        }
                                                    }
                                                }
                                            }
                                    }
                                }         
                            }
                        }
                    }
                        
                    

            return gm.size();
        }else
            return 0;
    }

    
    
    
     
    
}
