<%-- 
    Document   : barraNav
    Created on : 13/03/2021, 11:17:55 AM
    Author     : sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" href="estilos/estiloNav.css">
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("userName") == null) { 
                //response.sendRedirect("../login"); %>
                <nav class="navegador">
                    <a href="/WebServiceP1C1"><img  class="logo" src="Resource/imgs/logo.png" style="max-width:100%;width:auto;height:auto;"></a>
                  <ul>
                      <li><a id="in" href="login.jsp">Iniciar Sesion</a></li>
                      <%--<li><a onclick="hola();return false;" >Iniciar Sesion</a></li> --%>
                  </ul>
                </nav>               
        <% }else{ %>
        
            <nav class="navegador">
                <a href="/WebServiceP1C1"><img  class="logo" src="Resource/imgs/logo.png" style="max-width:100%;width:auto;height:auto;"></a>
              <ul>
                  <li><a id="userName"><%= session.getAttribute("userName")%></a></li>
                  <li><a id="showForms" href="ListaFormularios">Ver formularios</a></li>
                  <li><a id="showForms" href="Logout">Cerrar Sesion</a></li>
                  <%--<li><a onclick="hola();return false;" >Iniciar Sesion</a></li> --%>
              </ul>
            </nav>
                
       <% } %>
           
    </body>
</html>

