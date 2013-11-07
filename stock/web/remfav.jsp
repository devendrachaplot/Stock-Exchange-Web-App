<%-- 
    Document   : remfav
    Created on : Nov 6, 2012, 9:06:04 PM
    Author     : pratik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        Integer SID = Integer.parseInt(request.getParameter("s"));
        session.setAttribute("REMFAV_SID", SID);
        response.sendRedirect("remfav");
        %>
    </body>
</html>
