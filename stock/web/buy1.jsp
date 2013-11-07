<%-- 
    Document   : buy1
    Created on : Nov 4, 2012, 10:45:29 PM
    Author     : priyank
--%>

<%@page import="java.util.Vector"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buy Stock</title>
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
              <li><a href="showHistory">History</a></li>
              <li class="active"><a href="buy">Buy</a></li>
              <li><a href="profile">Profile</a></li>
              <li><a href="logout.jsp">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
          <div class="container">
    <div class="hero-unit">
        <form action="buy" name="filter" method="post" onsubmit="buy">
            Ticker / Company Name&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;<input name="tkr" size=15 type="text" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Filter" /><a href="buy">&nbsp;&nbsp;&nbsp;&nbsp;<button type="button"> Reset </button></a>
        </form>
        <% Vector sID = (Vector)session.getAttribute("SID");
         Vector ticker = (Vector)session.getAttribute("ticker");
         Vector company = (Vector)session.getAttribute("company");
         Vector price = (Vector)session.getAttribute("price");
         String page1 = request.getParameter("page");
         //int i = (Integer)session.getAttribute("index");
         int i = Integer.parseInt(page1);
         i = i*10;
         int size = ticker.size();
         int x = i/10+1;
         int y = i/10-1;
         int pageNo = (i/10)+1;
         int maxpage = ((size-1)/10)+1;
         if(!(pageNo>=1 && pageNo<=maxpage))
             response.sendRedirect("buy1.jsp?page=0");
         if(i+10>=size){
                    out.println("<span style=\"float:right; style=\"float:right\"\"> Next </span>");
                } else{
                    out.println("<a href=\"buy1.jsp?page="+x+"\" style=\"float:right\"\">Next</a>");
                }
                
                out.print("<span style=\"float:right; margin-right:60px\"> " + pageNo + " of " + maxpage + "</span>");
                if(i-1<0){
                    out.println("<span style=\"float:right; margin-right:60px\"> Previous </span><br>");
                } else{
                    out.println("<a href=\"buy1.jsp?page="+y+"\" style=\"float:right; margin-right:60px\"\">Previous</a><br>");
                }
             out.print("<table  border=\"1\" cellspacing=\"1\" cellpadding=\"5\" width=\"100%\">");
             out.print("<tr style=\"color:green\">");
         out.print("<th width=\"25%\"> Ticker </th>");
         out.print("<th width=\"50%\"> Company </th>");
         out.print("<th width=\"15%\"> Price </th>");
         out.print("<th width=\"10%\">  </th>");
         out.print("</tr>");
             
         while(i<x*10 && i<size) {
            out.print("<tr>");
            out.println("<td>");
            out.print(ticker.get(i));
            out.print("</td><td>");
            out.print(company.get(i));
            out.print("</td><td>");
            out.print(price.get(i));
            out.print("</td><td>");
            out.print("<a href=\"buy2.jsp?s="+ sID.get(i) +"&tkr="+ticker.get(i)+"\">Buy</a>");
            out.print("</td>");
            i++;
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
