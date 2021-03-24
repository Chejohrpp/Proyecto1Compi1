var login = document.getElementById('inicioSesion');
var contenido = document.getElementById("Contenido");

function inicioSesion(){
    contenido.innerHTML = '<iframe src="login.jsp" frameborder="0" width="600px" height="550px"></iframe>';
    return false;
}
login.addEventListener('click',inicioSesion);





