<%-- 
    Document   : liveticker
    Created on : Nov 7, 2012, 11:34:55 PM
    Author     : priyank
--%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
    <style>
        body {
            padding:0px;
        }
        #marqueeborder {
            color: #cccccc;
            background-color: #3e3e3e;
            position:relative;
            height:25px; 
            bottom:0px;
            overflow:hidden;
            font-size: 0.7em;
        }
        #marqueecontent {
            position:absolute;
            left:0px;
            line-height:20px;
            white-space:nowrap;
        }
        .stockbox {
            margin:0 10px;
            vertical-align: middle;
        }
        .stockbox a, span {
            color: #cccccc;
            font-size: 16px;
            text-decoration : none;
            vertical-align: middle;
        }
        .stockbox span img{
            vertical-align: middle;
            height: 15px;
        }
     </style>
     
     <script type="text/javascript">
        // Set an initial scroll speed. This equates to the number of pixels shifted per tick
        var scrollspeed=2;
        var pxptick=scrollspeed;
        function startmarquee(){
                // Make a shortcut referencing our div with the content we want to scroll
                marqueediv=document.getElementById("marqueecontent");
                // Get the total width of our available scroll area
                marqueewidth=document.getElementById("marqueeborder").offsetWidth;
                // Get the width of the content we want to scroll
                contentwidth=marqueediv.offsetWidth;
                // Start the ticker at 50 milliseconds per tick, adjust this to suit your preferences
                // Be warned, setting this lower has heavy impact on client-side CPU usage. Be gentle.
                setInterval("scrollmarquee()",15);
        }
        function scrollmarquee(){
                // Check position of the div, then shift it left by the set amount of pixels.
                if (parseInt(marqueediv.style.left)>(contentwidth*(-1)))
                        marqueediv.style.left=parseInt(marqueediv.style.left)-pxptick+"px";
                // If it's at the end, move it back to the right.
                else
                        marqueediv.style.left=parseInt(marqueewidth)+"px";
        }
        window.onload=startmarquee;
    </script>
</head>
<body>
    <%
        Connection con;
        ResultSet rs = null;
        try {
            // Step 1. Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://192.168.0.102:3306/StockExchange?user=chhipa";
                
                con = DriverManager.getConnection(url);

            // Step 3. Create a Statement object and call its executeUpdate 
            // method to insert a record
            Statement s = con.createStatement();

            // Step 4. Use the same Statement object to obtain a ResultSet object
            String sql = "select A.name,A.ticker,A.price,A.volume,ifnull(B.last_price,0),ifnull(B.change_price,0),ifnull(B.change_percent,0),A.date,A.stock from (select stock,price,date,volume,ticker,name from (select v1.idhistory,v1.stock,v1.price,v1.date,v1.volume,v1.ticker,v1.name,count(*) as rank from history_stock_company as v1 join history_stock_company as v2 on (v2.date,v2.idhistory) >= (v1.date,v1.idhistory) and v1.stock=v2.stock group by v1.stock,v1.date order by v1.stock, rank) as u where u.rank=1) as A left outer join (select u.name,u.ticker,u.price,u.volume,w.price as last_price,(u.price-w.price) as change_price,(100*(u.price-w.price)/w.price) as change_percent,u.date,u.stock from (select v1.idhistory,v1.stock,v1.price,v1.date,v1.volume,v1.ticker,v1.name,count(*) as rank from history_stock_company as v1 join history_stock_company as v2 on (v2.date,v2.idhistory) >= (v1.date,v1.idhistory) and v1.stock=v2.stock group by v1.stock,v1.date having rank=1) as u,(select v3.idhistory,v3.stock,v3.price,v3.date,v3.volume,v3.ticker,v3.name,count(*) as rank from history_stock_company as v3 join history_stock_company as v4 on (v4.date,v4.idhistory) >= (v3.date,v3.idhistory) and v3.stock=v4.stock group by v3.stock,v3.date having rank=2) as w where u.stock = w.stock) as B on A.stock=B.stock and A.price=B.price and A.date=B.date and A.volume=B.volume and A.ticker=B.ticker and A.name=B.name;";
            rs = s.executeQuery(sql);
            /*while (rs.next()) {
                out.println(rs.getString(1) + " " + rs.getString(2) + "<br>");
            }
            rs.close();
            s.close();
            con.close();*/
        } catch (ClassNotFoundException e1) {
            // JDBC driver class not found, print error message to the console
            System.out.println(e1.toString());
        } catch (SQLException e2) {
            // Exception when executing java.sql related commands, print error message to the console
            System.out.println(e2.toString());
        } catch (Exception e3) {
            // other unexpected exception, print error message to the console
            System.out.println(e3.toString());
        }
    %>
    <div id="marqueeborder" onmouseover="pxptick=0" onmouseout="pxptick=scrollspeed" >
        <div id="marqueecontent"><b>
            <%   
            while(rs.next()) {
                if(rs.getFloat(7) < 0) {
                    out.print("<span class=\"stockbox\"><a>"+rs.getString(2)+"</a> <span class=\"price\" style=\"color: #ff0000;\">"+rs.getFloat(3)+"<img src=\"img/down.png\" />"+(-1)*rs.getFloat(7)+"</span></span>");
                } else {
                    out.print("<span class=\"stockbox\"><a>"+rs.getString(2)+"</a> <span class=\"price\" style=\"color: #009900;\">"+rs.getFloat(3)+"<img src=\"img/up.png\" />"+rs.getFloat(7)+"</span></span>");
                }
            }
            %>
        </div>
    </div>
</body>
</html>