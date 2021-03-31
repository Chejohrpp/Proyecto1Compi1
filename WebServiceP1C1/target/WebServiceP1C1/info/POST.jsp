<%-- 
    Document   : POST
    Created on : 21/03/2021, 13:20:35
    Author     : sergi
--%>

<%@page import="java.net.http.HttpClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Recibir Datos Cliente</h1>
        <div>
            <ul>
            <li><p><b>First Name:</b>
               <%= request.getParameter("user")%>
               </p></li>
            <li><p><b>Last Name:</b>
               <%= request.getParameter("password")%>
               </p></li>
         </ul>
        </div>
    </body>
</html>
