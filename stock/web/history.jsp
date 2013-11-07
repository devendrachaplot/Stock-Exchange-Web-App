<%-- 
    Document   : history
    Created on : Nov 5, 2012, 11:16:23 AM
    Author     : pratik
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction History</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
          body {
            padding-top: 60px;
            padding-bottom: 40px;
          }
        </style>
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Hello <%=  session.getAttribute( "name" ) %>
          </a>
          <div class="nav-collapse">
            <ul class="nav" style="float:right;">
              <li><a href="showHome">Home</a></li>
              <li><a href="showOrders">Orders</a></li>
              <li><a href="showFavorites">Favorites</a></li>
              <li><a href="showStocks">Tickers</a></li>
              <li class="active"><a href="showHistory">History</a></li>
              <li><a href="buy">Buy</a></li>
              <li><a href="profile">Profile</a></li>
              <li><a href="logout.jsp">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
          <div class="container">
    <div class="hero-unit">
        <% 
         Vector company = (Vector)session.getAttribute("company_h");
         Vector ticker = (Vector)session.getAttribute("ticker_h");
         Vector price = (Vector)session.getAttribute("price_h");
         Vector volume = (Vector)session.getAttribute("volume_h");
         Vector name = (Vector)session.getAttribute("name_h");
         Vector date = (Vector)session.getAttribute("date_h");
         int buy_count = (Integer)session.getAttribute("count_buy");
         int sell_count = (Integer)session.getAttribute("count_sell");
         String buy1 = request.getParameter("buy");
         String sell1 = request.getParameter("sell");
         //int i = (Integer)session.getAttribute("index");
         int j = Integer.parseInt(buy1);
         int i = Integer.parseInt(sell1);
         i = i*10;
         j = j*10;
         int size = ticker.size();
         int x = i/10+1;
         int y = i/10-1;
         int a = j/10+1;
         int b = j/10-1;
         int pageNo = (i/10)+1;                
         int maxpage = ((sell_count-1)/10)+1;
         int pageNo1 = (j/10)+1;
         int maxpage1 = ((buy_count-1)/10)+1;
         if(!(pageNo>=1 && pageNo<=maxpage) && !(pageNo1>=1 && pageNo<=maxpage1))
                    response.sendRedirect("history.jsp?sell=0&buy=0");
         else if(!(pageNo>=1 && pageNo<=maxpage))
                    response.sendRedirect("history.jsp?sell=0&buy="+(pageNo1-1));
         else if(!(pageNo1>=1 && pageNo1<=maxpage1))
                    response.sendRedirect("history.jsp?sell="+(pageNo-1)+"&buy=0");
         
         out.println("<a href=\"#\" style=\"font-size:16px\">Sell Table</a>");
         if(i+10>=sell_count){
                    out.println("<br><span style=\"float:right; style=\"float:right\"\"> Next </span>");
                } else{
                    out.println("<br><a href=\"history.jsp?buy="+j/10+"&sell="+x+" style=\"float:right\"\">Next</a>");
                }
                
                out.print("<span style=\"float:right; margin-right:60px\"> " + pageNo + " of " + maxpage + "</span>");
                if(i-1<0){
                    out.println("<span style=\"float:right; margin-right:60px\"> Previous </span><br>");
                } else{
                    out.println("<a href=\"history.jsp?buy="+j/10+"&sell="+y+" style=\"float:right; margin-right:60px\"\">Previous</a><br>");
                }
             out.print("<table  border=\"1\" cellspacing=\"1\" cellpadding=\"5\" width=\"100%\">");
             out.print("<tr style=\"color:green\">");
             out.print("<th> Company </th>");
             out.print("<th> Ticker </th>");
             out.print("<th> Price </th>");
             out.print("<th> Volume </th>");
             out.print("<th> Buyer </th>");
             out.print("<th> Date </th>");
             out.print("</tr>");
             
         while(i<x*10 && i<sell_count) {
            out.print("<tr>");
            out.println("<td width=\"30%\">");
            out.print(company.get(i));
            out.print("</td><td width=\"11%\">");
            out.print(ticker.get(i));
            out.print("</td><td width=\"7%\">");
            out.print(price.get(i));
            out.print("</td><td width=\"7%\">");
            out.print(volume.get(i));
            out.print("</td><td width=\"30%\">");
            out.print(name.get(i));
            out.print("</td><td width=\"15%\">");
            out.print(date.get(i));
            //out.print("<a href=\"sell.jsp?owner="+owner.get(i)+"&tkr="+ticker.get(i)+"\">Sell</a>");
            out.print("</td>");
            i++;
            out.print("</tr>");
        }
        out.print("</table>");
        out.print("<br>");
        out.print("<hr>");
        out.println("<a href=\"#\" style=\"font-size:16px\">Buy Table</a>");
         if(j+10>=buy_count){
                    out.println("<br><span style=\"float:right; style=\"float:right\"\"> Next </span>");
                } else{
                    out.println("<br><a href=\"history.jsp?buy="+a+"&sell="+i/10+" style=\"float:right\"\">Next</a>");
                }
                
                out.print("<span style=\"float:right; margin-right:60px\"> " + pageNo1 + " of " + maxpage1 + "</span>");
                if(j-1<0){
                    out.println("<span style=\"float:right; margin-right:60px\"> Previous </span><br>");
                } else{
                    out.println("<a href=\"history.jsp?buy="+b+"&sell="+i/10+" style=\"float:right; margin-right:60px\"\">Previous</a><br>");
                }
             out.print("<table  border=\"1\" cellspacing=\"1\" cellpadding=\"5\" width=\"100%\">");
             out.print("<tr style=\"color:green\">");
             out.print("<th width=\"30%\"> Company </th>");
             out.print("<th width=\"11%\"> Ticker </th>");
             out.print("<th width=\"7%\"> Price </th>");
             out.print("<th width=\"7%\"> Volume </th>");
             out.print("<th width=\"30%\"> Seller </th>");
             out.print("<th width=\"15%\"> Date </th>");
             out.print("</tr>");
         
         while(j<a*10 && j<buy_count) {
            out.print("<tr>");
            out.println("<td>");
            out.print(company.get(j+sell_count));
            out.print("</td><td>");
            out.print(ticker.get(j+sell_count));
            out.print("</td><td>");
            out.print(price.get(j+sell_count));
            out.print("</td><td>");
            out.print(volume.get(j+sell_count));
            out.print("</td><td>");
            out.print(name.get(j+sell_count));
            out.print("</td><td>");
            out.print(date.get(j+sell_count));
            out.print("</td>");
            j++;
            out.print("</tr>");
        }
        out.print("</table>");
        %>
    </div>
          </div>
    <div style="position:fixed; width:100%; height: 35px; background-color:#3e3e3e; padding:0px; bottom:0px; ">
      <iframe src="liveticker.jsp" style="width:100%" frameborder="0" scrolling="false" name="myInlineFrame">Your browser does not supports Iframes. Tune in <a href="liveticker.jsp"> here </a> to view it</iframe>
      </div>
    </body>
</html>
