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
 * @author pratik
 */
@WebServlet(name = "sell", urlPatterns = {"/sell"})
public class sell extends HttpServlet {

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
    private static final String CHECK_QUERY = "select volume, User_ID, Stock_ID from ownership where ID=?";
    private static final String INSERT_QUERY = "insert into sell(price, volume, User_ID, Stock_ID, Ownership_ID) values(?,?,?,?,?)";
    private static final String CHECK_BUY_TABLE = "select * from buy where price = ? and Stock_ID = ?";
    private static final String UPDATE_BUY_TABLE = "update buy SET volume = ? where ID = ?";
    private static final String DELETE_BUY_TABLE = "delete from buy where ID = ?";
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
            out.println("<title>Servlet sell</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sell at " + request.getContextPath() + "</h1>");
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
        String strOwner = request.getParameter("own").toString();
        String strPrice = request.getParameter("pric").toString();
        String strVolume = request.getParameter("vol").toString();
        String strErrMsg = null;
        HttpSession session = request.getSession();
        int UID = 0;
        int SID = 0;
        boolean isValid = true;
        boolean isEqual = false;
            Connection con;
            con = null;
            int id = 0;
            ResultSet rs;
            try {
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
            else {
                con = null;
                try {
                con = connect();
                
                int sell_volume = Integer.parseInt(strVolume);
                
                PreparedStatement checkStmt = con.prepareStatement(CHECK_BUY_TABLE);
                checkStmt.setFloat(1, Float.parseFloat(strPrice));
                checkStmt.setInt(2,SID);
                
                ResultSet checkRes = checkStmt.executeQuery();
                if(checkRes.next()){
                    boolean check = true;
                    float buy_price = checkRes.getFloat(2);
                    int buy_volume;
                    int stock_id = checkRes.getInt(5);
                    
                    PreparedStatement addOwnership = con.prepareStatement(ADD_OWNERSHIP);
                    addOwnership.setFloat(1, buy_price);
                    addOwnership.setInt(2, sell_volume);
                    addOwnership.setInt(4, stock_id);
                    
                    
                    
                    PreparedStatement updateOwnership = con.prepareStatement(UPDATE_OWNERSHIP);
                    updateOwnership.setInt(2, Integer.parseInt(strOwner));

                    PreparedStatement addHistory = con.prepareStatement(ADD_HISTORY);
                    addHistory.setFloat(1, buy_price);
                    
                    
                    addHistory.setInt(4, UID);
                    addHistory.setInt(5, SID);
                    
                    do {
                        System.out.println("Entered");
                        
                        buy_volume = checkRes.getInt(3);
                        int user_id = checkRes.getInt(4);
                        addOwnership.setInt(3, user_id);
                        addHistory.setInt(3, user_id);
                        
                        if(buy_volume>sell_volume){
                            PreparedStatement updateprepStmt = con.prepareStatement(UPDATE_BUY_TABLE);
                            updateprepStmt.setInt(1,buy_volume-sell_volume);
                            updateprepStmt.setInt(2,checkRes.getInt(1));
                            int updateRes = updateprepStmt.executeUpdate();
                            check = false;
                            updateOwnership.setInt(1, sell_volume);
                            int upOwn = updateOwnership.executeUpdate();
                            
                            addHistory.setInt(2, sell_volume);
                            int addHis = addHistory.executeUpdate();
                            
                        }
                        
                        else if (buy_volume==sell_volume){
                            PreparedStatement updateprepStmt = con.prepareStatement(DELETE_BUY_TABLE);
                            updateprepStmt.setInt(1,checkRes.getInt(1));
                            int updateRes = updateprepStmt.executeUpdate();
                            check = false;
                            updateOwnership.setInt(1, sell_volume);
                            int upOwn = updateOwnership.executeUpdate();
                            
                            addHistory.setInt(2, sell_volume);
                            int addHis = addHistory.executeUpdate();
                        }
                        
                        else{
                            PreparedStatement updateprepStmt = con.prepareStatement(DELETE_BUY_TABLE);
                            updateprepStmt.setInt(1,checkRes.getInt(1));
                            int updateRes = updateprepStmt.executeUpdate();
                            sell_volume -= buy_volume;
                            
                            addOwnership.setInt(2, buy_volume); 
                            
                            updateOwnership.setInt(1, buy_volume);
                            int updateOwn = updateOwnership.executeUpdate();
                            
                            addHistory.setInt(2, buy_volume);
                            int addHis = addHistory.executeUpdate();
                        }
                        int addRes = addOwnership.executeUpdate();
                        if(addRes == 1){
                            System.out.println("Added to Ownership");
                        }else System.out.println("Not Added to Ownership");
                        
                    }while((check) && (checkRes.next()));
                    
                    if(check == true){
                        PreparedStatement prepStmt = con.prepareStatement(INSERT_QUERY);
                        prepStmt.setFloat(1, Float.parseFloat(strPrice));
                        prepStmt.setInt(2,sell_volume);
                        prepStmt.setInt(3,UID);
                        prepStmt.setInt(4,SID);
                        prepStmt.setInt(5,Integer.parseInt(strOwner));
                        int res = prepStmt.executeUpdate();
                    }
                }
                else{
                    
                    PreparedStatement prepStmt = con.prepareStatement(INSERT_QUERY);
                    prepStmt.setFloat(1, Float.parseFloat(strPrice));
                    prepStmt.setInt(2,Integer.parseInt(strVolume));
                    prepStmt.setInt(3,UID);
                    prepStmt.setInt(4,SID);
                    prepStmt.setInt(5,Integer.parseInt(strOwner));
                    int res = prepStmt.executeUpdate();
                }
                if(isEqual) {
                    PreparedStatement delOwnership = con.prepareStatement(DELETE_OWNERSHIP);
                    delOwnership.setInt(1, Integer.parseInt(strOwner));
                    int delOwn = delOwnership.executeUpdate();
                }
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
