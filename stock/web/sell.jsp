<%-- 
    Document   : sell
    Created on : Nov 3, 2012, 11:04:47 PM
    Author     : priyank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            String owner = request.getParameter("owner");
            String tkr = request.getParameter("tkr");
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sell <% out.println(tkr); %></title>
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
            <a class="brand" href="#">Sell <% out.println(tkr); %></a>
            <div style="float:right; width:58%; height:15px; padding:10; margin-top:5px">
                <ul class="nav" style="float:right;">
              <li><a href="showHome">Home</a></li>
              <li><a href="showOrders">Orders</a></li>
              <li><a href="showFavorites">Favorites</a></li>
              <li><a href="showStocks">Tickers</a></li>
              <li><a href="showHistory">History</a></li>
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
          <div class ="row">
              <div class="span6"><p> </p></div>
              <div class="span4">
        <form action="sell" name="sell" method="post" onsubmit="sell">
            <input name="own" size=15 type="text" class="hidden" value="<% out.println(owner); %>"/>
                <table>
                    <tr>
                        <td> Ticker  : </td><td> <% out.println(tkr); %> </td> 
                    </tr>
                    <tr>
                        <td> Price  : </td><td> <input name="pric" size=15 type="text" /> </td> 
                    </tr>
                    <tr>
                        <td> Volume  : </td><td> <input name="vol" size=15 type="text" /> </td> 
                    </tr>
                </table>
                <input type="submit" value="Sell" />
            </form>
              </div>
          </div>
      </div>
    </div>
    </body>
</html>
