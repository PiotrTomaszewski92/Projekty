/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Klasa MPlane będąca servletem pozwalającym na połączenie między 
 * aplikacją a bazą danych
 * @author Procislaw
 */
public class MPlane extends HttpServlet {
   @EJB
   private IPlaneRemote plane;       
   List<Float> points = new ArrayList<Float>();
   private static final long serialVersionUID = 123;
    
   /**
     * Metoda getConnection łącząca się z bazą danych za pomocą nazwy 
     * JNDI ustalonej na serwerze Glassfish.
     * @param ds - nazwa JNDI (parametr poprany metodą GET)
     * @return - połączenie z bazą
     * @throws SQLException - zapewnie informacje o błędzie dostępu 
     * do bazy danych
     * @throws NamingException - zapewnia informacje o błędzie gdy 
     * nazwa JNDI jest niepoprawna
     */
    private Connection getConnection(String ds) throws 
            SQLException,NamingException {
        Connection connection = null;
        InitialContext context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup(ds);
        connection = dataSource.getConnection();
        
        return connection;
    }
    /**
     * Metoda addPointsToList pobierająca wartości punktów z bazy 
     * i zapisująca je do listy. 
     * @param rs - zbiór rekordów zwróconych po wykonaniu zapytania 
     * w bazie danych
     * @throws SQLException - zapewnie informacje o błędzie dostępu 
     * do bazy danych     
     */
    private void addPointsToList(ResultSet rs) throws SQLException{
        for (;;){
                    if (rs.next()){
                        points.add(rs.getFloat("x"));
                        points.add(rs.getFloat("y"));
                        points.add(rs.getFloat("z"));
                    }else break;
                }
    }
    /**
    * Metoda getPoints w której pobierane są wyniki zapytania w bazie danych 
    * i przekazywane są do zapisania na liście.
    * @param connection - referencja do otwartego połączenia 
    * z bazą danych
    * @param sql - treść zapytania niezbędna do pobrania rezultatu
    * @param response - odpowiedź servletu
    * @throws Exception -przechwycenie zdarzenia, które może zakłócić 
    * poprawne działanie programu
    * @throws IOException – w przypadku wystąpienia błędu 
    * wejścia/wyjścia
    * 
    */
    private void getPoints(Connection connection, String sql,
            HttpServletResponse response) throws  Exception,IOException {
        Statement stmt;
        ResultSet rs;
        if (connection != null) {
                stmt =  connection.createStatement();
                rs = stmt.executeQuery(sql);
                addPointsToList(rs);
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().format(Locale.ENGLISH, "%.5f", 
                plane.general(points));
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
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
    }

   /**
     * Metoda doGet, dzięki której po przekazaniu parametru w żądaniu
     * URL, następuje rejestracja użytkownika i jeśli przebiegnie pomyślnie
     * wykonywane są metody określające połączenie z bazą 
     * i pobranie danych z bazy.
     *
     * @param request - żądanie servletu
     * @param response - odpowiedź servletu
     * @throws ServletException – w przypadku wystąpienia błędu 
     * związanego z servletem
     * @throws IOException – w przypadku wystąpienia błędu 
     * wejścia/wyjścia
     */
    @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)throws ServletException, IOException {
       //  response.getWriter().format(Locale.ENGLISH, "%.5f", 0.0);
        try {
            String connect = "java:global/ejb-project/GameManager!"
                    +"pl.jrj.game.IGameRemote";
            
            InitialContext con = new InitialContext();
            pl.jrj.game.IGameRemote game = (pl.jrj.game.IGameRemote)
                    con.lookup(connect);
           
            if (game.register(6, "104896")){
                getPoints(getConnection(request.getParameter("ds"))
                        ,"SELECT * FROM Otable order by z, y, x; ",response);
            }
         //   processRequest(request, response);
        } catch (NamingException ex) {
            response.getWriter().format(Locale.ENGLISH, "%.5f", 0.0);
        } catch (Exception e) {
            response.getWriter().format(Locale.ENGLISH, "%.5f", 0.0);
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
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
