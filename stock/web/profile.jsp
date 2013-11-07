<%-- 
    Document   : profile
    Created on : Nov 5, 2012, 7:00:26 AM
    Author     : priyank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Your Profile</title>
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
        <%
            String login = (String)session.getAttribute("up_login");
            String name = (String)session.getAttribute("up_name");
            Integer age = (Integer)session.getAttribute("up_age");
            Float cash = (Float)session.getAttribute("up_cash");
        %>
        <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
          <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </a>
            <a class="brand" href="#">Welcome to STOCK EXCHANGE</a>
             <ul class="nav" style="float:right;">
              <li><a href="showHome">Home</a></li>
              <li><a href="showOrders">Orders</a></li>
              <li><a href="showFavorites">Favorites</a></li>
              <li><a href="showStocks">Tickers</a></li>
              <li><a href="showHistory">History</a></li>
              <li><a href="buy">Buy</a></li>
              <li class="active"><a href="profile">Profile</a></li>
              <li><a href="logout.jsp">Logout</a></li>
            </ul>
            <div style="float:right; width:58%; height:15px; padding:10; margin-top:5px">
                
        </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    <div class="container">
        <div class="hero-unit">
          <div class ="row">
              <div class="span6"><p> </p></div>
              <div class="span4">
                  Update you password <a href="update_pass.jsp">here</a>.<br>
                <form action="profile" name="update" method="post" onsubmit="profile">
                    <br><table>
                        <tr>
                            <td> Username  : </td><td> <input name="username" disabled size=45 type="text" value="<% out.print(login); %>" /> </td> 
                        </tr>
                        <tr>
                            <td> Name  : </td><td> <input name="name" size=45 type="text" value="<% out.print(name); %>"/> </td> 
                        </tr>
                        <tr>
                            <td> Age  : </td><td> <input name="age" size=11 type="text" value="<% out.print(age); %>"/> </td> 
                        </tr>
                        <tr>
                            <td> Cash  : </td><td> <input name="cash" disabled size=11 type="text" value="<% out.print(cash); %>"/> </td> 
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="Update" /></td>
                        </tr>
                    </table>
                    
                </form>
</div>
          </div>
      </div>
      </div>
      <div style="position:fixed; width:100%; height: 35px; background-color:#3e3e3e; padding:0px; bottom:0px; ">
      <iframe src="liveticker.jsp" style="width:100%" frameborder="0" scrolling="false" name="myInlineFrame">Your browser does not supports Iframes. Tune in <a href="liveticker.jsp"> here </a> to view it</iframe>
      </div>
    </body>
</html>