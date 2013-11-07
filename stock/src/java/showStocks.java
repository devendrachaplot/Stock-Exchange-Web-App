/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
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
 * @author jayanth
 */
@WebServlet(name = "showStocks", urlPatterns = {"/showStocks"})
public class showStocks extends HttpServlet {

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
    private static final String STOCK_QUERY = "select u.name,u.ticker,u.price,u.volume,w.price as last_price,(u.price-w.price) as change_price,(100*(u.price-w.price)/w.price) as change_percent,u.date from (select v1.idhistory,v1.stock,v1.price,v1.date,v1.volume,v1.ticker,v1.name,count(*) as rank from (SELECT History.id AS idhistory, History.stock, History.price, History.date, History.volume, Stock.ticker, Company.name FROM History, Stock, Company WHERE History.stock=Stock.id AND Stock.Company_ID=Company.id ORDER BY History.date DESC) as v1 join (SELECT History.id AS idhistory, History.stock, History.price, History.date, History.volume, Stock.ticker, Company.name FROM History, Stock, Company WHERE History.stock=Stock.id AND Stock.Company_ID=Company.id ORDER BY History.date DESC) as v2 on (v2.date,v2.idhistory) >= (v1.date,v1.idhistory) and v1.stock=v2.stock group by v1.stock,v1.date having rank=1) as u,(select v3.idhistory,v3.stock,v3.price,v3.date,v3.volume,v3.ticker,v3.name,count(*) as rank from (SELECT History.id AS idhistory, History.stock, History.price, History.date, History.volume, Stock.ticker, Company.name FROM History, Stock, Company WHERE History.stock=Stock.id AND Stock.Company_ID=Company.id ORDER BY History.date DESC) as v3 join (SELECT History.id AS idhistory, History.stock, History.price, History.date, History.volume, Stock.ticker, Company.name FROM History, Stock, Company WHERE History.stock=Stock.id AND Stock.Company_ID=Company.id ORDER BY History.date DESC) as v4 on (v4.date,v4.idhistory) >= (v3.date,v3.idhistory) and v3.stock=v4.stock group by v3.stock,v3.date having rank=2) as w where u.stock = w.stock;";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        /*
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet showStocks</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet showStocks at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        */
        
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
            Connection con;
            con = null;
            int id = 0;
            System.out.println(id);
            String name = "Anonymous";
            System.out.println(name);
            boolean loginflag;
            if((Boolean)session.getAttribute("loginflag")==null)
                loginflag=false;
            else
                loginflag=(Boolean)session.getAttribute("loginflag");
            /***********************************************************
             Executing Query to get the current Stock of the User
             ***********************************************************/

            System.out.println("jayanth");
            ResultSet res;     
            try {
                con=connect();
                PreparedStatement prepStmt;
                prepStmt = con.prepareStatement(STOCK_QUERY);
                res = prepStmt.executeQuery();
                Vector v0 = new Vector();
                Vector v1 = new Vector();
                Vector v2 = new Vector();
                Vector v3 = new Vector();
                Vector v4 = new Vector();
                Vector v5 = new Vector();
                Vector v6 = new Vector();
                Vector v7 = new Vector();
                while(res.next()){
                       v0.add(res.getString(1));
                       v1.add(res.getString(2));
                       v2.add(res.getFloat(3));
                       v3.add(res.getInt(4));
                       v4.add(res.getFloat(5));
                       v5.add(res.getFloat(6));
                       v6.add(res.getFloat(7));
                       v7.add(res.getString(8));
                }
                session.setAttribute("showCompany",v0);
                session.setAttribute("showTicker",v1);
                session.setAttribute("showPrice",v2);
                session.setAttribute("showVolume",v3);
                session.setAttribute("showLast",v4);
                session.setAttribute("showChange",v5);
                session.setAttribute("showPercent",v6);
                session.setAttribute("showDate",v7);
            } catch(Exception e) {
                System.out.println("settingAttribute: Error while executing querry and setting attribute: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            if(!loginflag) {
                    response.sendRedirect("welcome.jsp?page=0");
                }
            else {
                    response.sendRedirect("ticker.jsp?page=0");
                }
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
            //processRequest(request, response);
            HttpSession session = request.getSession();
            Connection con;
            con = null;
            int id = 0;
            System.out.println(id);
            String name = "Anonymous";
            System.out.println(name);
            session.setAttribute("ID", id);
            session.setAttribute("name", name);
            boolean loginflag;
            if((Boolean)session.getAttribute("loginflag")==null) {
                    loginflag=false;
                }
            else {
                    loginflag=(Boolean)session.getAttribute("loginflag");
                }
            /***********************************************************
             Executing Query to get the current Stock of the User
             ***********************************************************/

            System.out.println("jayanth");
            ResultSet res;     
            try {
                con=connect();
                PreparedStatement prepStmt;
                prepStmt = con.prepareStatement(STOCK_QUERY);
                res = prepStmt.executeQuery();
                Vector v0 = new Vector();
                Vector v1 = new Vector();
                Vector v2 = new Vector();
                Vector v3 = new Vector();
                Vector v4 = new Vector();
                Vector v5 = new Vector();
                Vector v6 = new Vector();
                Vector v7 = new Vector();
                while(res.next()){
                       v0.add(res.getString(1));
                       v1.add(res.getString(2));
                       v2.add(res.getFloat(3));
                       v3.add(res.getInt(4));
                       v4.add(res.getFloat(5));
                       v5.add(res.getFloat(6));
                       v6.add(res.getFloat(7));
                       v7.add(res.getString(8));
                }
                session.setAttribute("showCompany",v0);
                session.setAttribute("showTicker",v1);
                session.setAttribute("showPrice",v2);
                session.setAttribute("showVolume",v3);
                session.setAttribute("showLast",v4);
                session.setAttribute("showChange",v5);
                session.setAttribute("showPercent",v6);
                session.setAttribute("showDate",v7);
            } catch(Exception e) {
                System.out.println("settingAttribute: Error while executing querry and setting attribute: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            if(!loginflag) {
                    response.sendRedirect("welcome.jsp?page=0");
                }
            else {
                    response.sendRedirect("ticker.jsp?page=0");
                }
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
