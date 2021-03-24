/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import backend.Almacenamiento;
import backend.creacion.*;
import backend.objetos.Solicitud;
import backend.objetos.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author sergi
 */
@WebService(serviceName = "WebServiceInicio")
public class WebServiceInicio {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "recibirSolicitudes")
    public Object recibirSolicitudes(@WebParam(name = "lista") Object listaSolicitudes) {
        List<Solicitud> respuesta = new ArrayList<Solicitud>();
        
        Almacenamiento almacenamiento = new Almacenamiento();
        List<Usuario> listaUusario = almacenamiento.getUsuarios();
        for (Usuario usuario : listaUusario) {
            System.out.println("usuarios: " + usuario.getUserName());
        }
        UsuarioFunctions userFuntion = new UsuarioFunctions(listaUusario);
        Usuario userLogin = new Usuario();        
        List<Solicitud> lista =  (List<Solicitud>) listaSolicitudes;
        for (Solicitud solicitud : lista) {
            System.out.println("lista servidor: " +  solicitud.getNombre());
            String nombreSolicitud = solicitud.getNombre();            
            if (nombreSolicitud.equals("LOGIN_USUARIO")) {
                Usuario verificarUser = (Usuario) solicitud.getSolicitud();
                userLogin = userFuntion.VerificarLogin(verificarUser); 
                respuesta.add(new Solicitud("LOGIN_USUARIO",userLogin));
            }         
        }
        
        return respuesta;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listaUsuarios")
    public String listaUsuarios() {
        String hola = "";
        //TODO write your implementation code here:
        Almacenamiento almacenamiento = new Almacenamiento();
        List<Usuario> listaUusario = almacenamiento.getUsuarios();
        for (Usuario usuario : listaUusario) {
            hola+= "\nusuarios: " + usuario.getUserName();
        }       
        return hola;
    }
    
    
    
}
