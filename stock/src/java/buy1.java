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
 * @author Priyank
 */
@WebServlet(name = "buy1", urlPatterns = {"/buy1"})
public class buy1 extends HttpServlet {

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
    private static final String INSERT_QUERY = "insert into buy(price, volume, User_ID, Stock_ID) values(?,?,?,?)";
    private static final String CHECK_SELL_TABLE = "select * from sell where price = ? and Stock_ID = ?";
    private static final String UPDATE_SELL_TABLE = "update sell SET volume = ? where ID = ? and Ownership_ID = ?";
    private static final String DELETE_SELL_TABLE = "delete from sell where ID = ?";
    private static final String UPDATE_OWNERSHIP = "update ownership SET volume = volume - ? where ID = ?";
    private static final String DELETE_OWNERSHIP = "delete from ownership where ID = ?";
    private static final String ADD_OWNERSHIP = "insert into ownership  (price, volume, User_ID, Stock_ID) values(?,?,?,?)";
    private static final String ADD_HISTORY = "insert into history (date,price,volume,buyer,seller,stock) values(NOW(),?,?,?,?,?)";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet buy1</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet buy1 at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String strStock = request.getParameter("sID").toString();
        String strPrice = request.getParameter("pric").toString();
        String strVolume = request.getParameter("vol").toString();
        String strErrMsg = null;
        HttpSession session = request.getSession();
        int UID = (Integer) session.getAttribute("ID");
        int SID = Integer.parseInt(strStock);
        boolean isValid = true;
        boolean isEqual = false;
            Connection con;
            con = null;
            int id = 0;
            ResultSet rs;
            /*try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(CHECK_QUERY);
                prepStmt.setInt(1, Integer.parseInt(strOwner));
                rs = prepStmt.executeQuery();
                if(rs.next()) {
                    System.out.println("User login is valid in DB");
                    int vol = rs.getInt(1);
                    UID = rs.getInt(2);
                    SID = rs.getInt(3);
                    if(vol<Integer.parseInt(strVolume)) {
                        isValid = false;
                    } else if (vol==Integer.parseInt(strVolume)) {
                        isEqual = true;
                    }
                    
                    System.out.println(isValid);
                }
                
            } catch(Exception e) {
                System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(sell.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
           
            if(!isValid) {
                    System.out.println("You don't have sufficient amount of stocks. About to redirect to home.jsp");
                    response.sendRedirect("home.jsp?page=0");
                }
            else {*/
                con = null;
                try {
                con = connect();
                
                int buy_vol = Integer.parseInt(strVolume);
                
                PreparedStatement checkStmt = con.prepareStatement(CHECK_SELL_TABLE);
                checkStmt.setFloat(1, Float.parseFloat(strPrice));
                checkStmt.setInt(2,SID);
                
                ResultSet checkRes = checkStmt.executeQuery();
                if(checkRes.next()){
                    boolean check = true;
                    float buy_price = checkRes.getFloat(2);
                    int sell_vol;
                    int stock_id = checkRes.getInt(5);
                    
                    PreparedStatement addOwnership = con.prepareStatement(ADD_OWNERSHIP);
                    addOwnership.setFloat(1, buy_price);
                    addOwnership.setInt(3, UID);
                    addOwnership.setInt(4, stock_id);
                    
                    
                    
                    PreparedStatement updateOwnership = con.prepareStatement(UPDATE_OWNERSHIP);
                    //updateOwnership.setInt(2, Integer.parseInt(strOwner));

                    PreparedStatement addHistory = con.prepareStatement(ADD_HISTORY);
                    addHistory.setFloat(1, buy_price);
                    
                    
                    addHistory.setInt(3, UID);
                    addHistory.setInt(5, SID);
                    
                    do {
                        System.out.println("Entered");
                        
                        sell_vol = checkRes.getInt(3);
                        int user_id = checkRes.getInt(4);
                        updateOwnership.setInt(2, user_id);
                        addOwnership.setInt(3, UID);
                        addHistory.setInt(4, user_id);
                        
                        if(sell_vol>buy_vol){
                            System.out.println("priyank");
                            PreparedStatement updateprepStmt = con.prepareStatement(UPDATE_SELL_TABLE);
                            updateprepStmt.setInt(1,sell_vol-buy_vol);
                            updateprepStmt.setInt(2,checkRes.getInt(1));
                            updateprepStmt.setInt(3,checkRes.getInt(6));
                            int updateRes = updateprepStmt.executeUpdate();
                            check = false;
                            
                            updateOwnership.setInt(1, buy_vol);
                            updateOwnership.setInt(2, checkRes.getInt(6));
                            int upOwn = updateOwnership.executeUpdate();
                            
                            addOwnership.setInt(2, buy_vol);
                            int addOwn = addOwnership.executeUpdate();
                            
                            addHistory.setInt(2, buy_vol);
                            int addHis = addHistory.executeUpdate();
                            
                        } else if (sell_vol==buy_vol){
                            System.out.println("priyank");
                            PreparedStatement updateprepStmt = con.prepareStatement(DELETE_SELL_TABLE);
                            updateprepStmt.setInt(1,checkRes.getInt(1));
                            int updateRes = updateprepStmt.executeUpdate();
                            check = false;
                            
                            updateOwnership.setInt(1, sell_vol-buy_vol);
                            updateOwnership.setInt(2, checkRes.getInt(1));
                            int upOwn = updateOwnership.executeUpdate();
                            
                            addOwnership.setInt(2, buy_vol);
                            int addOwn = addOwnership.executeUpdate();
                            
                            addHistory.setInt(2, buy_vol);
                            int addHis = addHistory.executeUpdate();
                        } else{
                            PreparedStatement updateprepStmt = con.prepareStatement(DELETE_SELL_TABLE);
                            updateprepStmt.setInt(1,checkRes.getInt(1));
                            int updateRes = updateprepStmt.executeUpdate();
                            buy_vol -= sell_vol;
                            
                            updateOwnership.setInt(1, 0);
                            updateOwnership.setInt(2, checkRes.getInt(1));
                            int upOwn = updateOwnership.executeUpdate();
                            
                            addOwnership.setInt(2, sell_vol);
                            int addOwn = addOwnership.executeUpdate();
                            
                            
                            addHistory.setInt(2, sell_vol);
                            int addHis = addHistory.executeUpdate();
                        }
                        
                    }while((check) && (checkRes.next()));
                    
                    if(check == true){
                        PreparedStatement prepStmt = con.prepareStatement(INSERT_QUERY);
                        prepStmt.setFloat(1, Float.parseFloat(strPrice));
                        prepStmt.setInt(2,buy_vol);
                        prepStmt.setInt(3,UID);
                        prepStmt.setInt(4,SID);
                        int res = prepStmt.executeUpdate();
                    }
                } else{
                    
                    PreparedStatement prepStmt = con.prepareStatement(INSERT_QUERY);
                    prepStmt.setFloat(1, Float.parseFloat(strPrice));
                    prepStmt.setInt(2,Integer.parseInt(strVolume));
                    prepStmt.setInt(3,UID);
                    prepStmt.setInt(4,SID);
                    int res = prepStmt.executeUpdate();
                }
                
                PreparedStatement delOwnership = con.prepareStatement("delete from ownership where volume = 0;");
                int delOwn = delOwnership.executeUpdate();
            } catch(Exception e) {
                    try {
                        System.out.println("validateLogon: Error while validating password: "+e.getMessage());
                        throw e;
                    } catch (Exception ex) {
                        Logger.getLogger(sell.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(sell.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
                
            response.sendRedirect("showHome");
            
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
