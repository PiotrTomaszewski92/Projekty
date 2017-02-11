/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import login.User;

/**
 *
 * @author Procislaw
 */
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    private String JDBC_DRIVER;  
    private String DB_URL;
    private String USER;
    private String PASS;
    private List<User> users;
    private MetodyPom metody;
    
    
    public Login(){
        this.JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
        this.DB_URL = "jdbc:mysql://localhost:3306/pilka";
        this.USER = "root";
        this.PASS = "root";
        this.users = new ArrayList<User>();
        this.metody = new MetodyPom();
        initial();
    }
    
    private void initial() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             String sql = "SELECT id_user, login, haslo, typ, imie, nazwisko, email FROM uzytkownik";
             ResultSet rs = stmt.executeQuery(sql);
             while(rs.next()){
                 this.users.add(
                         new User(
                         rs.getLong("id_user"),
                         rs.getString("login"),
                                 rs.getString("haslo"),
                                 rs.getString("typ"),
                                 rs.getString("imie"),
                                 rs.getString("nazwisko"),
                                 rs.getString("email")
                         )
                 );        
        }
        System.out.println(users);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
        String event = request.getParameter("wyloguj");
        
        System.out.println("Event: "+event);
        
        if (event==null){
            String tekst="  <form action=\"Login\" class=\"form_login\" method=\"post\">\n" +
"                               <p><label class=\"\">Login:</label><input class=\"\" type=\"text\" class=\"form-control\" name=\"username\" /></p>\n" +
"                               <p><label class=\"\">Hasło:</label><input class=\"\" type=\"password\" class=\"form-control\" name=\"password\" /></p>\n" +
"                               <p><input type=\"submit\" class=\"btn btn-primary btn-lg\" value=\"Zaloguj\" /></p>\n" +
"                           </form>";
            try (PrintWriter out = response.getWriter()) {
                out.println(this.metody.templateUser(tekst));
            }
        }  
        else{
            HttpSession session=request.getSession();  
            session.invalidate();  
             request.getRequestDispatcher("index.html").include(request, response);
        }
        
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
       // processRequest(request, response);
        request.setCharacterEncoding( "UTF-8" );
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();  
        //request.getRequestDispatcher("link.html").include(request, response);  
          
        String name=request.getParameter("username");  
        String password=request.getParameter("password"); 
        
        User user;
        boolean flag = false;
        
        for(Iterator<User> i = users.iterator(); i.hasNext(); ) {
            user = i.next();
            if((user.getLogin().equals(name))&&(user.getHaslo().equals(password))){
                HttpSession session=request.getSession();  
                session.setAttribute("username",name);
                session.setAttribute("typ",password);
                flag = true;
                System.out.println(session.getAttribute("username")+" || "+session.getAttribute("typ"));
                
                request.getRequestDispatcher("index.html").include(request, response);
            }
            
        }
        if(flag==false){
                 out.print("Nazwa użytkownika lub hasło jest niepoprawne!"); 
                request.getRequestDispatcher("login.html").include(request, response);  
            }
        out.close();  
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
