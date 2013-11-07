<%--
    Document   : index
    Created on : Nov 4, 2012, 12:34:09 PM
    Author     : priyank
--%>

<%@page import="java.util.Vector"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Stock Exchange!</title>
    </head>
    <body>
        <%
        session.setAttribute("favpage", 0);
        session.setAttribute("addfavpage", 0);
        response.sendRedirect("showStocks"); %>
    </body>
</html>
