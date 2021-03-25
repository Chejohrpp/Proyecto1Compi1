/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import backend.Almacenamiento;
import backend.creacion.*;
import backend.objetos.*;
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
    private FormularioFunctions funForm = new FormularioFunctions(almacenamiento.getForms());
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
        almacenamiento = new Almacenamiento();
        funUser = new UsuarioFunctions(almacenamiento.getUsuarios()); 
        funForm = new FormularioFunctions(almacenamiento.getForms());
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
            modUserName(jsonObject);
            verificarLogin(jsonObject,jsonResponse);  
            addUsers(jsonObject,jsonResponse); 
            EliminarUsers(jsonObject);
            ModUsers(jsonObject);  
            AddForms(jsonObject);
            DeleteForms(jsonObject);
            ModForms(jsonObject);
            jsonResponse.put("RESPUESTAS", arrayRespuesta);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(jsonResponse.toString());            
            }           
        } catch (JSONException e) {
            System.out.println("error en el json: " + e.getMessage());
        }       
    }
    private void modUserName(JSONObject jsonObject){
        try{
            String userName = jsonObject.getString("USER_NAME_SESION");
            if (userName.equals("null")) {
                userNameSesion = null;
            }else{
                userNameSesion = userName;
            }
        }catch(Exception e){
            
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
            if (login == null) {
                arrayRespuesta.put("Los parametros del inicio de " + UserName + " son invalidos");                
            }else{
                arrayRespuesta.put("Se inicio sesion con el usuario: " + userNameSesion);
            }
        }catch (JSONException e) {
          //System.out.println("error en el json de login: " + e.getMessage());
        }
    }
    private void addUsers(JSONObject jsonObject, JSONObject jsonResponse){
        try{
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
        }catch(Exception e){            
        }       
        
    }
    
    private void EliminarUsers(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("ELIMINAR_USUARIOS");
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getString(i);
                boolean eliminado = funUser.EliminarUser(new Usuario(id,null,null,null));
                if (eliminado) {
                    arrayRespuesta.put("Se elimino el usuario: " + id);
                    almacenamiento.setUsuarios(funUser.getListaUsuarios());
                }else{
                     arrayRespuesta.put("Error, el usuario : " + id  + " no fue eliminado");
                }
            }
        }catch(Exception e){
            
        }
        
        
    }
    
    private void ModUsers(JSONObject jsonObject){        
        try{
        JSONArray jsonArray = (JSONArray) jsonObject.get("MODIFICAR_USUARIOS");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonUser = jsonArray.getJSONObject(i);
                String userOld = jsonUser.getString("USUARIO_ANTIGUO");
                String user = jsonUser.getString("USUARIO_NUEVO");
                String pass = jsonUser.getString("PASSWORD");
                String fechaMod = jsonUser.getString("FECHA_MODIFICACION");
                boolean modificado = funUser.setUser(userOld, new Usuario(user,pass,null,fechaMod));
                if (modificado) {
                    arrayRespuesta.put("Se modificaron los datos del usuario: " + userOld);
                    almacenamiento.setUsuarios(funUser.getListaUsuarios());
                }else{
                    arrayRespuesta.put("Error, el usuario: " + userOld  + " no se modificaron sus datos");
                }
            }
        }catch(Exception e){
            System.out.println("error en mod Users :" + e.getMessage());
        }
        
    }

    private boolean AddForms(JSONObject jsonObject){                
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("NUEVO_FORMS");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error: No se puede crear los formularios, no hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonForm = jsonArray.getJSONObject(i);
                String id = jsonForm.getString("ID");
                String titulo = jsonForm.getString("TITULO");
                String nombre = jsonForm.getString("NOMBRE");
                String tema = jsonForm.getString("TEMA");
                String fecha = jsonForm.getString("FECHA_CREACION");
                String user = jsonForm.getString("USUARIO_CREACION");
                Formulario form = new Formulario(id,titulo,nombre,tema,VerUserName(user),fecha);
                boolean add = funForm.addForm(form);
                if (add) {
                    arrayRespuesta.put("Se creo el formulario: " + id);
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error, el id formulario : " + id + " esta repetido");
                }                
            }
        }catch(Exception e){
            System.out.println("error en addForms : " + e.getMessage());
        }
        
        return true;
    }
    private String VerUserName(String s){
        if (s.equals("null")) {
            return userNameSesion;
        }
        return s;
    }
    private boolean DeleteForms(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("ELIMINAR_FORMS");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error: No se pueden eliminar formularios, no hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {                
                String id = jsonArray.getString(i);
                boolean add = funForm.eliminarForm(id);
                if (add) {
                    arrayRespuesta.put("Se elimino el formulario: " + id);
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error, el formulario : " + id + " no se pudo eliminar");
                }                
            }
        }catch(Exception e){
            System.out.println("error en addForms : " + e.getMessage());
        }
        
        return true;
    }
    
    private boolean ModForms(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("MODIFICAR_FORMS");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error: No se puede modificar los formularios, no hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonForm = jsonArray.getJSONObject(i);
                String id = jsonForm.getString("ID");
                String titulo = jsonForm.getString("TITULO");
                String nombre = jsonForm.getString("NOMBRE");
                String tema = jsonForm.getString("TEMA");
                Formulario form = new Formulario(id,titulo,nombre,tema,null,null);
                boolean add = funForm.modForm(form);
                if (add) {
                    arrayRespuesta.put("Se modifico el formulario: " + id);
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error, el formulario : " + id + " no se logro modificar");
                }                
            }
        }catch(Exception e){
            System.out.println("error en addForms : " + e.getMessage());
        }
        
        return true;
        
    }


}
