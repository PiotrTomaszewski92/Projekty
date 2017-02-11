import java.io.IOException;
import java.util.Locale;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Procislaw
 */

public class MGame extends HttpServlet {
    @EJB 
    private IMasterMind imm;
    private static final long serialVersionUID = 1121L;
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
        response.setContentType("text/html;charset=UTF-8");
     
    }
    
    /**
     * Metoda IGameMonitor, dzięki której po przekazaniu parametru 
     * będącym adresem połączenia, następuje wyszukiwanie paczki 
     * ejb-project i jeśli go znajdzie to następuje szukanie klasy 
     * GameMonitor i wykonuje działanie metod poprzez przesłonięcie 
     * interfejsem.
     * @param connect - adres do wyszukania i połączenia się 
     * z interfejsem
     * @return połączenie dzięki któremu możemy wywoływać metody 
     * z komponentu GameMonitor
     */
    private pl.jrj.game.IGameMonitor getConnection(String connect){
        pl.jrj.game.IGameMonitor game = null;
        try {
           
            InitialContext con = new InitialContext();
            game = (pl.jrj.game.IGameMonitor) con.lookup(connect);  
        } catch (NamingException ex ) {
            game=null;
        } 
        return game;
    }

   
    /**
     * Metoda doGet, dzięki której po przekazaniu parametru w żądaniu
     * URL, następuje rejestracja użytkownika i jeśli przebiegnie 
     * pomyślnie  wykonywane są metody obliczające ilość kroków 
     * potrzebnych do znalezienia rozwiązania.
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
            HttpServletResponse response)
            throws ServletException, IOException {
       pl.jrj.game.IGameMonitor game;
       game=getConnection("java:global/ejb-project/GameMonitor"
               + "!pl.jrj.game.IGameMonitor");
       
       if (game.register(5, "104896")){               
                response.getWriter().format(Locale.ENGLISH, "%d", 
                    imm.start(Integer.parseInt(request.getParameter("n")), 
                    Integer.parseInt(request.getParameter("k")), 
                    Long.parseLong(request.getParameter("s")),
                    game)
                );
            } else 
                response.getWriter().format(Locale.ENGLISH, "%.5f", 0.0);
        processRequest(request, response);
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
