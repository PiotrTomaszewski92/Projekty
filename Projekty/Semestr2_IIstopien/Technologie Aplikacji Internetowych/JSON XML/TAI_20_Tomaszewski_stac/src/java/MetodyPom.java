
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import produkty.Data;
import produkty.Lista;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Procislaw
 */
public class MetodyPom {
    
    public MetodyPom(){
        
    }
    
    public String showData(String head,List<Data> data, boolean sesja) throws IOException{
        String tresc ="<h1>"+head+"</h1>";
        if(sesja)tresc+="<p><button type=\"button\" class=\"btn btn-success\"><a href=\"?event=dodaj\">Dodaj produkt</a></button></p>";
        int i=0;
        for (Data entry : data){
            tresc+="<h2>"+entry.getTitle()+"</h2><div class=\"lewa\">";
             tresc+="<img src=\""+entry.getImg()+"\" ></div>";
             
            tresc+="<div class=\"prawa\">";
            if(sesja) tresc+="<button type=\"button\" class=\"btn btn-success\"><a href=\"?event=edit&list="+(i++)+"&param="+(entry.getId())+"\">Edytuj</button></a>";
             
             tresc+="<p><strong>Opis:</strong> "+entry.getOpis()+"</p><p><strong>Cena: </strong>"+entry.getCena()+"</p>";
             tresc+="<p><strong>Ilość :</strong> "+entry.getIlosc()+"</p><p><strong>Czas: </strong>"+entry.getCzas()+"</p>";
             tresc+="<p><strong>Baza smaków:</strong> ";
             tresc+="<ul>";
             for (String comments : entry.getBaza())
                tresc+="<li>"+comments+"</li>";
             tresc+="</ul>";
             tresc+="</p></div>";
//             out.println();
//             out.println();
//             out.println();
                tresc+="<div style=\"clear: both;\"></div><hr/>";
          }
        
        return tresc;
    }
    
    private String meta(){
    String tresc = "<meta charset=\"UTF-8\">" +
"                   <meta name=\"description\" content=\"Cukiernia Ania oferuje torty dla dzieci na wszystkie okazje. Jesteśmy jubilerami słodyczy pracującymi w Katowicach i na całym Śląsku. W naszej ofercie znajdują się także ciasta, ciasteczka, bankietówki, arabeski i inne słodkości.\" />" +
"                   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />" +
"                   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />" +
"                   <title>Produkty - Torty dla dzieci - Cukiernia Artystyczna ANIA - Jubiler Słodyczy</title>"+
"                   <link rel=\"stylesheet\" type=\"text/css\" href=\"https://www.tortydladzieci.pl/cache/efa73ed16bcb40b13fa5124c58c37053_combined.min.css\" media=\"screen\" />"+
"                   <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\" />"+
"                   <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">" +
"                   <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>" +
"                   <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>"+
"                   <script type=\"text/javascript\" src=\"skrypt.js\" ></script>";
    
    return tresc;
    }
    
    private String header(String login){
        return "<header id=\"top\">\n" +
"                   <div class=\"row naglowek center\" style=\"background: url(/pics/header.jpg);\">\n" +
    "                   <div class=\"large-12 columns logo\">\n" +
    "                       <a href=\"/main/index.html\" title=\"przejdź do strony głównej\"><img src=\"https://www.tortydladzieci.pl/themes/cukiernia/pics/logo.png\" alt=\"cukiernia artystyczna ANIA - jubiler słodyczy\"><span class=\"ir\">cukiernia artystyczna ANIA - jubiler słodyczy</span></a>\n" +
    "                  </div>\n" +
"               </div>\n" +
"       <nav class=\"menu\">\n" +
"      <div class=\"row large-collapse\" style=\"margin:auto;\">\n" +
"            <div class=\"large-7 medium-8 columns menu-ul\">\n" +
                    menu(login)+
"            </div>\n" +
"      </div>\n" +
"</nav>\n" +
"	</header>";
    }
    
    private String footer(){
        return "<footer>" +
                "<div>" +
                "<p style=\"margin:0px;\">Created by Piotr Adam Tomaszewski - projekt na zaliczenie przedmiotu TAI</p>"+
                "</div>	"+
                "</footer>";
    }
    
    private String menu(String login){
        String tresc = "<ul>" +
        "                    <li><a href=\"Glowna\" class=\"\">Strona główna</a></li>" +
        "                    <li><a href=\"Bankietowki\" class=\"\">Bankietówki</a></li>" +
        "                    <li><a href=\"Ciasta\" class=\"\">Ciasta</a></li>" +
        "                    <li><a href=\"Torty\" class=\"\">Torty</a></li>" +
        "                    <li><a href=\"Miodowe\" class=\"\">Miodowe ciastka</a></li>" +
                            login +
        "                </ul>";
        return tresc;
    }
    
    public String templateUser(String data) throws IOException{
        String tresc = "<!DOCTYPE html>";
                tresc += "<!DOCTYPE html>";
                tresc += "<html>";
                tresc += "<head>";
                tresc += meta();
                tresc += "</head>";
                tresc += "<body>";
                tresc += header("<li><button type=\"button\" class=\"btn btn-info btn-lg\"><a href=\"Login\" class=\"\">Zaloguj</a></button></li");
                tresc += "<div id=\"cont\">";
                tresc += data;
                tresc += "</div>";
                tresc += footer();
                tresc += "</body>";
                tresc += "</html>";
        
        return tresc;
    }
    
    public String templateAdmin(String data, String username, String typ) throws IOException{
        String tresc = "<!DOCTYPE html>";
                tresc += "<!DOCTYPE html>";
                tresc += "<html>";
                tresc += "<head>";
                tresc += meta();
                tresc += "</head>";
                tresc += "<body>";
                //tresc += menu(username,typ);
                tresc += header("<li><a href=\"Login?wyloguj=true\" class=\"\">Wyloguj</a></li>");
                tresc += "<div id=\"cont\">";
                tresc += data;
                tresc += "</div>";
                tresc += "</body>";
                tresc += "</html>";
        
        return tresc;
    }
    
    public void zapisz (Lista lista, String plik){
         try {

		File file = new File(plik);
		JAXBContext jaxbContext = JAXBContext.newInstance(Lista.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(lista, file);
		jaxbMarshaller.marshal(lista, System.out);

	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
    }
    
    public Lista odczytaj (String plik){
         try {

		File file = new File(plik);
		JAXBContext jaxbContext = JAXBContext.newInstance(Lista.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Lista l = (Lista) jaxbUnmarshaller.unmarshal(file);
                
               // showData(l.getLista());
                return l;

	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
         return null;
    }
    
    public String formEdit(Lista lis, String param, boolean sesja) throws IOException{
            String content="";
            Data app = lis.getLista().get(Integer.parseInt(param));
            System.out.println("Param: "+param+" => "+lis.getLista().get(Integer.parseInt(param)));
            //response.getWriter().flush();
            //<a href=\"?event=del&param="+param+"\">
                if(sesja) content+="<div style=\"text-align:center;\"><button type=\"button\" id=\"callConfirm\" event=\"del\" param=\""+param+"\" class=\"btn btn-danger\">Usuń ten produkt</button></div>";
                content+="<form action=\"?zapisz="+param+"\" method=\"post\" >";
                content+="<p><label>Nazwa: </label><input type=\"text\" class=\"form-control\" name=\"title\" value=\""+app.getTitle()+"\"></p>";
                content+="<p><label>Zdjęcie: </label><input type=\"text\" style=\"width:100%;\" class=\"form-control\" name=\"img\" value=\""+app.getImg()+"\"></p>";
                content+="<p><label>Opis: </label><textarea class=\"form-control\" rows=\"3\" name=\"opis\"  >"+app.getOpis()+"</textarea></p>";
                content+="<p><label>Cena[zł]: </label><input type=\"text\" id=\"cena\" class=\"form-control\" name=\"cena\" value=\""+app.getCena()+"\"></p>";
                content+="<p><label>Ilość: </label><input type=\"text\" id=\"ilosc\" class=\"form-control\" name=\"ilosc\" value=\""+app.getIlosc()+"\"></p>";
                content+="<p><label>Czas: </label><input type=\"text\" class=\"form-control\" name=\"czas\" value=\""+app.getCzas()+"\"></p>";
                content+="<p><label>Baza smakowa: </label>";
                for (String comments : app.getBaza())
                    content+="<textarea class=\"form-control\" row=\"2\" name=\"baza\">"+comments+"</textarea></p>";
                
                content+="<input type=\"submit\" id=\"editbutton\" class=\"btn btn-primary\" value=\"Wyślij\">";
                content+=" </form> ";
                return content;
    }
    
    public String formAdd() throws IOException{
            String content="";
            content+="<form action=\"?dodaj\" method=\"post\">";
                content+="<p><label>Nazwa: </label><input type=\"text\" class=\"form-control\" name=\"title\" ></p>";
                content+="<p><label>Zdjęcie: </label><input type=\"text\" style=\"width:100%;\" class=\"form-control\" name=\"img\" ></p>";
                content+="<p><label>Opis: </label><textarea name=\"opis\" class=\"form-control\" rows=\"3\" ></textarea></p>";
                content+="<p><label>Cena[zł]: </label><input type=\"text\" id=\"cena\" class=\"form-control\" name=\"cena\" ></p>";
                content+="<p><label>Ilość: </label><input type=\"text\" id=\"ilosc\"class=\"form-control\" name=\"ilosc\" ></p>";
                content+="<p><label>Czas: </label><input type=\"text\" class=\"form-control\" name=\"czas\"></p>";
                content+="<p><label>Baza smakowa: </label>";
                for (int i=1;i<=6;i++)
                    content+="<textarea class=\"form-control\" row=\"2\" name=\"baza\"></textarea></p>";
                
                content+="<input class=\"btn btn-primary\" id=\"editbutton\" type=\"submit\" value=\"Wyślij\">";
                content+=" </form> ";
                return content;
    }
    
}
