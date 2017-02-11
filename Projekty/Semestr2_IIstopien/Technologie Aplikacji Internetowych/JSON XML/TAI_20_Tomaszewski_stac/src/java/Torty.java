/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import produkty.Data;
import produkty.Lista;

/**
 *
 * @author Procislaw
 */
@WebServlet(urlPatterns = {"/Torty"})
public class Torty extends HttpServlet {
    
     Lista lis;
     String plik;
     MetodyPom metody;
    
    public Torty(){
         metody = new MetodyPom();
        this.plik="C:\\Users\\Procislaw\\Desktop\\Sem2\\TAI\\Cukiernia3\\torty.xml";
        this.lis=metody.odczytaj(this.plik);
    }
    
   
    private void showTempl(HttpServletRequest request, HttpServletResponse response, String content){
        try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
               
                if(request.isRequestedSessionIdValid())
                    out.println(metody.templateAdmin(content,request.getSession().getAttribute("username").toString(),request.getSession().getAttribute("typ").toString()));
                else 
                    out.println(metody.templateUser(content));
                
        } catch (IOException ex) {
            Logger.getLogger(Torty.class.getName()).log(Level.SEVERE, null, ex);
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
        //processRequest(request, response);
        request.setCharacterEncoding( "UTF-8" );
        response.setContentType("text/html;charset=UTF-8");
        String event = request.getParameter("event");
        String param = request.getParameter("list");
        
        String content="";
        
        if (event==null){
            showTempl(request,response,metody.showData("Torty",lis.getLista(),request.isRequestedSessionIdValid()));
        }
        
        else if (event.equals("edit")&&(request.isRequestedSessionIdValid())){
            showTempl(request,response, metody.formEdit(lis, param,request.isRequestedSessionIdValid()));
        }else if(event.equals("del")&&(request.isRequestedSessionIdValid())){
        
                
                content+="<p class=\"bg-success\">USUNIĘTO!</p>";
                
                lis.getLista().remove(Integer.parseInt(request.getParameter("param")));
                metody.zapisz(lis,this.plik);
            showTempl(request,response, content);
        
        
        }else if(event.equals("dodaj")){
            showTempl(request,response, metody.formAdd());
        }else
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
            tekst = metody.showData("Torty",lis.getLista(),request.isRequestedSessionIdValid());
        }else if (request.getParameterMap().containsKey("dodaj")&&(request.isRequestedSessionIdValid())){
         
            Data newApp = new Data(lis.getLista().size(),request.getParameter("title"),request.getParameter("img"),request.getParameter("opis"),Double.parseDouble(request.getParameter("cena")),Integer.parseInt(request.getParameter("ilosc")),Arrays.asList(request.getParameterValues("baza")),request.getParameter("czas"));
            lis.getLista().add(newApp);
            tekst = metody.showData("Torty",lis.getLista(),request.isRequestedSessionIdValid());
         }else
             tekst = "<p class=\"bg-danger\">Nie masz tu dostępu</p>";
         metody.zapisz(lis,this.plik);
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
