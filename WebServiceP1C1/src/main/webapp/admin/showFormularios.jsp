<%-- 
    Document   : showFormularios
    Created on : 27/03/2021, 17:03:02
    Author     : sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formularios</title>
        <link rel="styleSheet" href="estilos/estiloAdmin/estiloShowForms.css">
    </head>
    <body>
         <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            if (session.getAttribute("userName") == null) {
                response.sendRedirect("http://localhost:8080/WebServiceP1C1/");
            }
        %>        
        <%@include file="../barraNav.jsp" %>
        <div id="contenido">
            <div class="caja">            
                <div class="informacion">
                    <table>
                    <tr>
                        <th>Formulario</th>
                        <th>Link del formulario </th>
                        <th>Exportar formulario</th>
                    </tr>
                        <c:forEach items="${formularios}" var="solicitud">
                            <tr>
                                <td>${solicitud}</td>
                                <td>http://localhost:8080/WebServiceP1C1/Formulario?id_form=${solicitud}</td>
                                <td><a href="ExportarForm?id_form=${solicitud}">Exportar</a></td>                        
                            </tr>
                        </c:forEach>
                    </table>             
                </div>           
            </div>
        </div>
    </body>
</html>
