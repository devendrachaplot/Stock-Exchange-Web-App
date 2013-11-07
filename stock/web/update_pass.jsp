<%-- 
    Document   : update
    Created on : Nov 5, 2012, 6:59:20 AM
    Author     : priyank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Password</title>
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
                <form action="updatepass" name="update" method="post" onsubmit="updatepass">
                    <br><table>
                        <tr>
                            <td> Old Password  : </td><td> <input name="oldpass" size=45 type="password"/> </td> 
                        </tr>
                        <tr>
                            <td> New Password  : </td><td> <input name="newpass1" size=45 type="password"/> </td> 
                        </tr>
                        <tr>
                            <td> Confirm Password  : </td><td> <input name="newpass2" size=45 type="password"/> </td> 
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