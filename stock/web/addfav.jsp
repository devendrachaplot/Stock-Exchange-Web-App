<%-- 
    Document   : addfav
    Created on : Nov 6, 2012, 8:28:14 PM
    Author     : pratik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Favorite</title>
    </head>
    <body>
        <%
        Integer SID = Integer.parseInt(request.getParameter("s"));
        session.setAttribute("ADDFAV_SID", SID);
        response.sendRedirect("addfav");
        %>
    </body>
</html>
