<%-- 
    Document   : index
    Created on : Nov 2, 2012, 2:55:28 AM
    Author     : priyank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Login</title>
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
                <form action="checkLogin" name="frmLogin" method="post" onsubmit="checkLogin">
                    <table width="100%">
                        <tr>
                            <td width="40%"> Username  : </td><td width="60%"> <input name="username" size=15 type="text" /> </td> 
                        </tr>
                        <tr>
                            <td> Password  : </td><td> <input name="password" size=15 type="password" /> </td> 
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="login" /></td>
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