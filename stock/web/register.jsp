<%-- 
    Document   : register
    Created on : Nov 2, 2012, 3:11:50 AM
    Author     : priyank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register at Stock Exchange</title>
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
            <div style="float:right; width:58%; height:15px; padding:10; margin-top:5px">
            <ul class="nav" style="float:right;">             
              <li><a href="showStocks">Home</a></li>
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
                <form action="register" name="register" method="post" onsubmit="register">
                    <table>
                        <tr>
                            <td> Username  : </td><td> <input name="username" size=45 type="text" /> </td> 
                        </tr>
                        <tr>
                            <td> Password  : </td><td> <input name="password" size=45 type="password" /> </td> 
                        </tr>
                        <tr>
                            <td> Name  : </td><td> <input name="name" size=45 type="text" /> </td> 
                        </tr>
                        <tr>
                            <td> Age  : </td><td> <input name="age" size=11 type="text" /> </td> 
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="register" /></td>
                        </tr>
                    </table>
                    
                </form>
</div>
          </div>
      </div>
      </div>
    </body>
</html>