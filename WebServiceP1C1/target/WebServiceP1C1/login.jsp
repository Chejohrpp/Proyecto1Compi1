
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
         <link rel="styleSheet" href="estilos/estiloLogin.css">
    </head>
    <body>
        <%@include file="barraNav.jsp" %>
        <div class="caja">
            <h2>Inicio de Sesion</h2>            
            <c:if test="${success == 0}">
            <label id="error" style="color:red;text-align: center">Usuario o Contraseña Incorrecto</label>            
            </c:if>
            <form action="Login" method="POST">
                    <%--Nombre de usuario--%>
                    <label for="usuario">Usuario</label>
                    <input type="text" name="user" placeholder="Usuario">
                    <%--Contraseña --%>
                    <label for="password">Contraseña</label>
                    <input type="password" name="pass" placeholder="Contraseña"> 
                    <%--boton --%>
                    <input type="submit" value="Ingresar">                
            </form>
        </div>      
    </body>
</html>
