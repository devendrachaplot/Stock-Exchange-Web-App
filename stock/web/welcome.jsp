<%-- 
    Document   : welcome
    Created on : Nov 5, 2012, 2:56:53 AM
    Author     : priyank
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Stock Exchange!</title>
        
        <link href="css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
          body {
            padding-top: 60px;
            padding-bottom: 20px;
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
            <a class="brand" href="#">Welcome to STOCK EXCHANGE</a>
            <div style="float:right; width:58%; height:15px; padding:10; margin-top:5px">
                <span><a href="register.jsp"  style="float:right; margin-right:60px">Register</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span><a href="login.jsp"  style="float:right; margin-right:60px">Login</a></span>
        </div><!--/.nav-collapse -->
        </div>
      </div>
            
    </div>
        
        
    <div class="container">

      <div class="hero-unit">
        <% 
            out.println("<form action=\"showStocks\" name=\"showButton\" method=\"post\"><input type=\"submit\" value=\"Refresh\"  style=\"float:right\"/></form>");
            Vector showCompany = (Vector)session.getAttribute("showCompany");
            Vector showTicker = (Vector)session.getAttribute("showTicker");
            Vector showPrice = (Vector)session.getAttribute("showPrice");
            Vector showVolume = (Vector)session.getAttribute("showVolume");
            Vector showLast = (Vector)session.getAttribute("showLast");
            Vector showChange = (Vector)session.getAttribute("showChange");
            Vector showPercent = (Vector)session.getAttribute("showPercent");
            Vector showData = (Vector)session.getAttribute("showDate");
            String page1 = request.getParameter("page");
            //int i = (Integer)session.getAttribute("index");
            int i = Integer.parseInt(page1);
            i = i*10;
            int size = showTicker.size();
            
            int x = i/10+1;
            int y = i/10-1;
            int pageNo = (i/10)+1;
            int maxpage = ((size-1)/10)+1;
            if(!(pageNo>=1 && pageNo<=maxpage))
                response.sendRedirect("index.jsp?page=0");
            if(i+10>=size){
                out.println("<br><span style=\"float:right; style=\"float:right\"\"> Next </span>");
            } else{
                out.println("<br><a href=\"index.jsp?page="+x+" style=\"float:right\"\">Next</a>");
            }
            
            out.print("<span style=\"float:right; margin-right:60px\"> " + pageNo + " of " + maxpage + "</span>");
            if(i-1<0){
                out.println("<span style=\"float:right; margin-right:60px\"> Previous </span><br>");
            } else{
                out.println("<a href=\"index.jsp?page="+y+" style=\"float:right; margin-right:60px\"\">Previous</a><br>");
            }
            out.print("<table  border=\"1\" cellspacing=\"1\" cellpadding=\"5\" width=\"100%\">");
            out.print("<tr style=\"color:green\">");
            out.print("<th> Company </th>");
            out.print("<th> Ticker </th>");
            out.print("<th> Price </th>");
            out.print("<th> Volume </th>");
            out.print("<th> Last Price </th>");
            out.print("<th> Change </th>");
            out.print("<th> % Change </th>");
            out.print("<th> Date </th>");
            //out.print("<th>  </th>");
            out.print("</tr>");

            while(i<x*10 && i<size) {
                out.println("<tr>");
                out.println("<td>");
                out.print(showCompany.get(i));
                out.print("</td><td>");
                out.print(showTicker.get(i));
                out.print("</td><td>");
                out.print(showPrice.get(i));
                out.print("</td><td>");
                out.print(showVolume.get(i));
                out.print("</td><td>");
                out.print(showLast.get(i));
                out.print("</td><td>");
                out.print(showChange.get(i));
                out.print("</td><td>");
                out.print(showPercent.get(i));
                out.print("</td><td>");
                out.print(showData.get(i));
                out.print("</td>");
                out.print("</tr>");
                i++;
            }
            out.print("</table>");
        %>
      </div>
      <hr>
      <div class="row">
      	
          <div style="float:right; position:relative; right:100px" >
        <h6> Developed by : <br>
        <span style="color:#900; font-size: 10px">
            &nbsp;&nbsp;Devendra Chaplot (100050034)<br>
            &nbsp;&nbsp;Priyank Chhipa (100050033)<br>
            &nbsp;&nbsp;Jayanth Jaiswal (100050041)<br>
            &nbsp;&nbsp;Pratik Kumar (100100018)</span>

        
        </div>
      </div>
      
    </div>
      <div style="position:fixed; width:100%; height: 35px; background-color:#3e3e3e; padding:0px; bottom:0px; ">
      <iframe src="liveticker.jsp" style="width:100%" frameborder="0" scrolling="false" name="myInlineFrame">Your browser does not supports Iframes. Tune in <a href="liveticker.jsp"> here </a> to view it</iframe>
      </div>
    </body>
</html>
