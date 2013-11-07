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
 * @author priyank
 */
@WebServlet(name = "buy", urlPatterns = {"/buy"})
public class buy extends HttpServlet {

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
    private static final String SELECT_ALL_QUERY = "select v1.stock, v1.ticker, v1.name, v1.price from (select history.ID, history.stock, history.price, company.name, stock.ticker from stock, history, company where stock.ID=history.stock and stock.Company_ID=company.ID order by history.ID desc) as v1 join (select history.ID, history.stock, history.price, company.name, stock.ticker from stock, history, company where stock.ID=history.stock and stock.Company_ID=company.ID order by history.ID desc) as v2 on v2.ID >= v1.ID and v1.stock=v2.stock group by v1.stock, v1.ID having count(*)=1;";
    
    private static final String SELECT_QUERY = "select v1.stock, v1.ticker, v1.name, v1.price from (select history.ID, history.stock, history.price, company.name, stock.ticker from stock, history, company where stock.ID=history.stock and stock.Company_ID=company.ID and (stock.ticker like ? or company.name like ?) order by history.ID desc) as v1 join (select history.ID, history.stock, history.price, company.name, stock.ticker from stock, history, company where stock.ID=history.stock and stock.Company_ID=company.ID and (stock.ticker like ? or company.name like ?) order by history.ID desc) as v2 on v2.ID >= v1.ID and v1.stock=v2.stock group by v1.stock, v1.ID having count(*)=1;";
    //private static final String SELECT_QUERY = "select  stock.ID, ticker, company.name, price from stock, history, company where stock.ID=history.stock and stock.Company_ID=company.ID and (stock.ticker like ? or company.name like ?) order by stock.ID asc, history.ID desc;";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet buy</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet buy at " + request.getContextPath() + "</h1>");
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
            String strErrMsg = null;
            HttpSession session = request.getSession();
            Connection con;
            con = null;
            ResultSet res;

            try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(SELECT_ALL_QUERY);
                res = prepStmt.executeQuery();
                Vector v0 = new Vector();
                Vector v1 = new Vector();
                Vector v2 = new Vector();
                Vector v3 = new Vector();
                while(res.next()){
                       v0.add(res.getInt(1));
                       v1.add(res.getString(2));
                       v2.add(res.getString(3));
                       v3.add(res.getFloat(4));
                }
                session.setAttribute("SID",v0);
                session.setAttribute("ticker",v1);
                session.setAttribute("company",v2);
                session.setAttribute("price",v3);
            } catch(Exception e) {
                System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            response.sendRedirect("buy1.jsp?page=0");
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
        String strTkr = request.getParameter("tkr").toString();
        strTkr = "%" + strTkr + "%";
        String strErrMsg = null;
        HttpSession session = request.getSession();
        Connection con;
        con = null;
        ResultSet res;

           try {
               con = connect();
               PreparedStatement prepStmt = con.prepareStatement(SELECT_QUERY);
               prepStmt.setString(1, strTkr);
               prepStmt.setString(2, strTkr);
               prepStmt.setString(3, strTkr);
               prepStmt.setString(4, strTkr);
               res = prepStmt.executeQuery();
               Vector v0 = new Vector();
               Vector v1 = new Vector();
               Vector v2 = new Vector();
               Vector v3 = new Vector();
               while(res.next()){
                      v0.add(res.getInt(1));
                      v1.add(res.getString(2));
                      v2.add(res.getString(3));
                      v3.add(res.getFloat(4));
               }
               session.setAttribute("SID",v0);
               session.setAttribute("ticker",v1);
               session.setAttribute("company",v2);
               session.setAttribute("price",v3);
           } catch(Exception e) {
               System.out.println("validateLogon: Error while validating password: "+e.getMessage());
           } finally {
                   try {
                       con.close();
                   } catch (SQLException ex) {
                       Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                   }
           }
           response.sendRedirect("buy1.jsp?page=0");
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
}
