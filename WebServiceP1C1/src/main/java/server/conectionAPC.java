/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import backend.Almacenamiento;
import backend.creacion.UsuarioFunctions;
import backend.objetos.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author sergi
 */
@WebServlet(name = "conectionAPC", urlPatterns = {"/conectionAPC"})
public class conectionAPC extends HttpServlet {
    private String userNameSesion=null;
    private Almacenamiento almacenamiento = new Almacenamiento();
    private UsuarioFunctions funUser = new UsuarioFunctions(almacenamiento.getUsuarios()); 
    private JSONArray arrayRespuesta =  new JSONArray();
    private int cantRespuestas = 0;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
         arrayRespuesta =  new JSONArray();
       //lee el archivo string de la aplicacion cliente 
       StringBuffer jb = new StringBuffer();
        String line = null;
        try {
          BufferedReader reader = request.getReader();
          while ((line = reader.readLine()) != null)
            jb.append(line);
        } catch (Exception e) { System.out.println("eror reader: " + e.getMessage()); }
        
        //leemos el archivo json enviado desde el cliente       
        try {            
            JSONObject jsonObject =  new JSONObject(jb.toString());
            verificarLogin(jsonObject,jsonResponse);  
            addUsers(jsonObject,jsonResponse); 
                      
            
            
            jsonResponse.put("RESPUESTAS", arrayRespuesta);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(jsonResponse.toString());            
            }           
        } catch (JSONException e) {
            System.out.println("error en el json: " + e.getMessage());
        }       
    }
    
    private void verificarLogin(JSONObject jsonObject, JSONObject jsonResponse){
        try{
            JSONObject jsonLogin = jsonObject.getJSONObject("LOGIN_USUARIO");
            String UserName = jsonLogin.getString("USUARIO");
            String pass = jsonLogin.getString("PASSWORD");                
            Usuario login = funUser.verificarUser(new Usuario(UserName,pass,null,null));

            JSONObject jsonLoginVerificar = new JSONObject();
            if (login == null) {
                jsonLoginVerificar.put("USUARIO", "null");
            }else{                    
                jsonLoginVerificar.put("USUARIO", login.getUserName());
                jsonLoginVerificar.put("PASSWORD", login.getPassword());
                userNameSesion = login.getUserName();
            } 

            jsonResponse.put("LOGIN_USUARIO", jsonLoginVerificar);
            if (userNameSesion == null) {
                arrayRespuesta.put("Los parametros del inicio de " + UserName + " son invalidos");                
            }else{
                arrayRespuesta.put("Se inicio sesion con el usuario: " + userNameSesion);
            }
        }catch (JSONException e) {
          System.out.println("error en el json de login: " + e.getMessage());
        }
    }
    private void addUsers(JSONObject jsonObject, JSONObject jsonResponse){
        
        JSONArray jsonArray = (JSONArray) jsonObject.get("CREAR_USUARIO");           
        for (int i = 0; i < jsonArray.length() ; i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray.getJSONObject(i);
            String user = jsonObject1.getString("USUARIO");
            String pass = jsonObject1.getString("PASSWORD");
            String fecha_creacion = jsonObject1.getString("FECHA_CREACION");
            String fecha_mod = "null";
            Usuario newUser = new Usuario(user,pass,fecha_creacion,fecha_mod);
            boolean seCreo = funUser.addUser(newUser);
            
            if (seCreo) {
                arrayRespuesta.put("Se creo el nuevo usuario: " + user);
                almacenamiento.setUsuarios(funUser.getListaUsuarios());
            }else{
                arrayRespuesta.put("Error, el usuario : " + user + " esta repetido");
            }
            
        }       
        
    }
    
    private void EliminarUsers(JSONObject jsonObject, JSONObject jsonResponse){
        
    }
    
    private void ModUsers(JSONObject jsonObject, JSONObject jsonResponse){
        
    }



}
