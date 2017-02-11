/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import produkty.Lista;
import produkty.Data;
/**
 *
 * @author Procislaw
 */
@WebServlet(urlPatterns = {"/Glowna"})
public class Glowna extends HttpServlet {
    
    private Lista lis;
    private Gson gson;
    private JsonReader reader;
    String plik;
    MetodyPom metody;
    
     
    public Glowna() throws FileNotFoundException {
        metody = new MetodyPom();
        this.plik="C:\\Users\\Procislaw\\Desktop\\Sem2\\TAI\\Cukiernia3\\popularne.json";
        this.gson = new Gson();
            this.reader = new JsonReader(new FileReader(this.plik));
            this.lis = gson.fromJson(reader, Lista.class);
            System.out.println(this.lis.toString());
        //reader.setLenient(true);
        //metody = new MetodyPom();
        //this.plik="C:\\Users\\Procislaw\\Desktop\\Sem2\\TAI\\Cukiernia3\\ciasta.xml";
        //this.lis=metody.odczytaj(this.plik);
    }
    
    private void zapisz (Lista lista) throws IOException{
                 try (Writer writer = new FileWriter("C:\\Users\\Procislaw\\Desktop\\Sem2\\TAI\\Cukiernia3\\popularne.json")) {
                     //Writer newww = new FileWriter();
                       gson.toJson(lista, writer);
                   }
                
             
    }
    
    private void showTempl(HttpServletRequest request, HttpServletResponse response, String content){
        try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                
                if(request.isRequestedSessionIdValid())
                    out.println(metody.templateAdmin(content,request.getSession().getAttribute("username").toString(),request.getSession().getAttribute("typ").toString()));
                else 
                    out.println(metody.templateUser(content));
                
        } catch (IOException ex) {
            Logger.getLogger(Glowna.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/**
 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
 * methods.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
response.setContentType("text/html;charset=UTF-8");
}



// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
   //System.out.println("Jestem get: "+request.getParameter("event")+ " || "+request.getParameter("param"));
//processRequest(request, response);
    request.setCharacterEncoding( "UTF-8" );
    response.setContentType("text/html;charset=UTF-8");
        String event = request.getParameter("event");
        String param = request.getParameter("list");
        System.out.println("Lista w get "+this.lis);
        String content="";
        
        if (event==null){
            //showData(request,userinfo1.getLista());
            showTempl(request,response,metody.showData("Lista najpopularniejszych produktów",lis.getLista(),request.isRequestedSessionIdValid()));
        }
        
        else if (event.equals("edit")&&(request.isRequestedSessionIdValid())){
            
            showTempl(request,response, metody.formEdit(lis, param,request.isRequestedSessionIdValid()));
        }else if(event.equals("del")&&(request.isRequestedSessionIdValid())){
        
                
                content+="<p class=\"bg-success\">USUNIĘTO!</p>";
                
                lis.getLista().remove(Integer.parseInt(request.getParameter("param")));
                zapisz(lis);
            showTempl(request,response, content);
        
        
        }else if(event.equals("dodaj")&&(request.isRequestedSessionIdValid())){
            showTempl(request,response, metody.formAdd());
        }
        else
            showTempl(request,response, "<p class=\"bg-danger\">Nie masz tu dostępu</p>");
}

/**
 * Handles the HTTP <code>POST</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
//processRequest(request, response);
        request.setCharacterEncoding( "UTF-8" );
        response.setContentType("text/html;charset=UTF-8");
        String tekst="";
        
         if (request.getParameterMap().containsKey("zapisz")&&(request.isRequestedSessionIdValid())){
             
             Data newApp = new Data(Integer.parseInt(request.getParameter("zapisz")+1),request.getParameter("title"),request.getParameter("img"),request.getParameter("opis"),Double.parseDouble(request.getParameter("cena")),Integer.parseInt(request.getParameter("ilosc")),Arrays.asList(request.getParameterValues("baza")),request.getParameter("czas"));
             lis.getLista().set(Integer.parseInt(request.getParameter("zapisz")), newApp);
             tekst = metody.showData("Lista najpopularniejszych produktów",lis.getLista(),request.isRequestedSessionIdValid());
         }else if (request.getParameterMap().containsKey("dodaj")&&(request.isRequestedSessionIdValid())){
             Data newApp = new Data(lis.getLista().size(),request.getParameter("title"),request.getParameter("img"),request.getParameter("opis"),Double.parseDouble(request.getParameter("cena")),Integer.parseInt(request.getParameter("ilosc")),Arrays.asList(request.getParameterValues("baza")),request.getParameter("czas"));
                lis.getLista().add(newApp);
                tekst = metody.showData("Lista najpopularniejszych produktów",lis.getLista(),request.isRequestedSessionIdValid());
         }else
             tekst = "<p class=\"bg-danger\">Nie masz tu dostępu</p>";
    zapisz(lis);
    showTempl(request,response,tekst);
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
@Override
public String getServletInfo() {
return "Short description";
}// </editor-fold>

}
