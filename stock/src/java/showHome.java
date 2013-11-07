/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pratik
 */
@WebServlet(name = "showHome", urlPatterns = {"/showHome"})
public class showHome extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String DBNAME = "StockExchange";
    private static final String DB_USERNAME = "chhipa";
    private static final String DB_PASSWORD = "";
    private static final String DATA_QUERY = "select o.ID, st.ticker, c.name, case when s.volume is NULL then o.volume else o.volume-s.volume end as volume, o.price from Ownership o left outer join Sell s on o.ID=s.Ownership_ID, Company c, Stock st where o.User_ID=? and o.Stock_ID=st.ID and st.Company_ID=c.ID";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet showHome</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet showHome at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
        /***********************************************************
         Executing Query to get the current Stock of the User
         ***********************************************************/
                Connection con = null;
                HttpSession session = request.getSession();
                System.out.println("asbcd");
                ResultSet res;
                int id = (Integer)session.getAttribute("ID");
                System.out.println(id);
                try {
               
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(DATA_QUERY);
                prepStmt.setInt(1, id);
                res = prepStmt.executeQuery();
                Vector v0 = new Vector();
                Vector v1 = new Vector();
                Vector v2 = new Vector();
                Vector v3 = new Vector();
                Vector v4 = new Vector();
                while(res.next()){
                       v0.add(res.getInt(1));
                       v1.add(res.getString(2));
                       v2.add(res.getString(3));
                       v3.add(res.getInt(4));
                       v4.add(res.getFloat(5));
                }
                session.setAttribute("owner",v0);
                session.setAttribute("ticker",v1);
                session.setAttribute("company",v2);
                session.setAttribute("volume",v3);
                session.setAttribute("price",v4);
            } catch(Exception e) {
                System.out.println("validateLogon: Error while fetching data: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            response.sendRedirect("home.jsp?page=0");
    }

    Connection connect() throws Exception
    {
        Connection con=null;
        try
        {
            String url = "jdbc:mysql://192.168.0.102:3306/"+DBNAME+"?user="+DB_USERNAME;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
        } 
        catch (SQLException sqle) 
        {
            System.out.println("SQLException: Unable to open connection to db: "+sqle.getMessage());
            throw sqle;
        }
         catch(Exception e)
        {
            System.out.println("Exception: Unable to open connection to db: "+e.getMessage());
            throw e;
        }
        
        return con;
        
        
    }
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
