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
@WebServlet(name = "showHistory", urlPatterns = {"/showHistory"})
public class showHistory extends HttpServlet {

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
    private static final String BUY_HISTORY = "select c.name,st.ticker, h.price, h.volume, u.name, h.date from Company c, Stock st, History h, User u where h.stock = st.ID and st.company_ID=c.ID and u.ID=h.seller and h.buyer=?";
    private static final String SELL_HISTORY = "select c.name,st.ticker, h.price, h.volume, u.name, h.date from Company c, Stock st, History h, User u where h.stock = st.ID and st.company_ID=c.ID and u.ID=h.buyer and h.seller=?";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet showHistory</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet showHistory at " + request.getContextPath() + "</h1>");
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
         HttpSession session = request.getSession();
         int ID=(Integer)session.getAttribute("ID");
         System.out.println(ID);
         boolean isValid = false;
            Connection con;
            con = null;
            int id = 0;
            int count_sell = 0;
            int count_buy = 0;
            ResultSet rs;
            Vector v0 = new Vector();
            Vector v1 = new Vector();
            Vector v2 = new Vector();
            Vector v3 = new Vector();
            Vector v4 = new Vector();
            Vector v5 = new Vector();
            try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(SELL_HISTORY);
                prepStmt.setInt(1, ID);
                rs = prepStmt.executeQuery();
                 while(rs.next()){
                       v0.add(rs.getString(1));
                       v1.add(rs.getString(2));
                       v2.add(rs.getFloat(3));
                       v3.add(rs.getInt(4));
                       v4.add(rs.getString(5));
                       v5.add(rs.getString(6));
                       count_sell++;
                 }
                
            } catch(Exception e) {
                System.out.println("validateLogon: Error while fetching data: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(showHistory.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            
            con = null;
            ResultSet res;
            try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(BUY_HISTORY);
                prepStmt.setInt(1, ID);
                res = prepStmt.executeQuery();
                while(res.next()){
                       v0.add(res.getString(1));
                       v1.add(res.getString(2));
                       v2.add(res.getFloat(3));
                       v3.add(res.getInt(4));
                       v4.add(res.getString(5));
                       v5.add(res.getString(6));
                       count_buy++;
                 }
                
            } catch(Exception e) {
                System.out.println("validateLogon: Error while fetching data: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(showHistory.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            System.out.println(count_buy+" "+count_sell);
            session.setAttribute("company_h",v0);
            session.setAttribute("ticker_h",v1);
            session.setAttribute("price_h",v2);
            session.setAttribute("volume_h",v3);
            session.setAttribute("name_h",v4);
            session.setAttribute("date_h",v5);
            session.setAttribute("count_buy",count_buy);
            session.setAttribute("count_sell",count_sell);
            response.sendRedirect("history.jsp?sell=0&buy=0");
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
       // processRequest(request, response);
        
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
