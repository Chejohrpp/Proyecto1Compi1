<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formularios Web</title>
        <link rel="stylesheet" href="estilos/estiloInicio.css">
    </head>
    <body>        
        <%@include file="barraNav.jsp" %>        
        <div id="Contenido">
            <div class="caja">
                <form action="Formulario" method="POST">
                    <h2> Ingrese el link:</h2>
                    <input type="text" name="id_form"/>
                    <input type="submit" value="Enviar Formulario">
                </form>
            </div>
        </div>        
        <script src="scripts/main.js"></script>
    </body>
</html>
