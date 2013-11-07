<%-- 
    Document   : delete
    Created on : Nov 5, 2012, 5:55:02 AM
    Author     : priyank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>delete</title>
    </head>
    <body>
        <%
        Integer flag = Integer.parseInt(request.getParameter("f"));
        Integer ID = Integer.parseInt(request.getParameter("ch"));
        session.setAttribute("delete_flag", flag); 
        session.setAttribute("delete_ID", ID); 
        response.sendRedirect("delete");
        %>
    </body>
</html>
