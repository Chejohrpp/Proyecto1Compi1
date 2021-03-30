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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            addUsers(jsonObject); 
            EliminarUsers(jsonObject);
            ModUsers(jsonObject);  
            AddForms(jsonObject);
            DeleteForms(jsonObject);
            ModForms(jsonObject);
            AddComponente(jsonObject);
            eliminarComponente(jsonObject);
            ModComponentes(jsonObject);
            jsonResponse.put("RESPUESTAS", arrayRespuesta);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(generarEstructuraRespuestas(jsonResponse));            
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
                if (userNameSesion != null) {
                    jsonLoginVerificar.put("USUARIO", userNameSesion);
                }else{
                    jsonLoginVerificar.put("USUARIO", "null");
                }
            }else{                    
                jsonLoginVerificar.put("USUARIO", login.getUserName());
                jsonLoginVerificar.put("PASSWORD", login.getPassword());
                userNameSesion = login.getUserName();
            } 

            jsonResponse.put("LOGIN_USUARIO", jsonLoginVerificar);
            if (login == null) {
                arrayRespuesta.put("Los parametros del inicio de " + UserName + " son invalidos");                
            }else{
                arrayRespuesta.put("Se inicio sesion con el usuario  " + userNameSesion);
            }
        }catch (JSONException e) {
            JSONObject jsonLoginVerificar = new JSONObject();
            if (userNameSesion != null) {
                jsonLoginVerificar.put("USUARIO", userNameSesion);
            }else{
                jsonLoginVerificar.put("USUARIO", "null");
            }            
            jsonResponse.put("LOGIN_USUARIO", jsonLoginVerificar);
        }
    }
    private boolean addUsers(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("CREAR_USUARIO");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error al crear usuarios. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.getJSONObject(i);
                String user = jsonObject1.getString("USUARIO");
                String pass = jsonObject1.getString("PASSWORD");
                String fecha_creacion = jsonObject1.getString("FECHA_CREACION");
                String fecha_mod = "null";
                Usuario newUser = new Usuario(user,pass,fecha_creacion,fecha_mod);
                boolean seCreo = funUser.addUser(newUser);            
                if (seCreo) {
                    arrayRespuesta.put("Se creo el nuevo usuario " + user);
                    almacenamiento.setUsuarios(funUser.getListaUsuarios());
                }else{
                    arrayRespuesta.put("Error en el usuario  " + user + " esta repetido");
                }
            }            
        }catch(Exception e){            
        }       
        return true;
    }
    
    private boolean EliminarUsers(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("ELIMINAR_USUARIOS");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error al eliminar usuarios. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getString(i);
                boolean eliminado = funUser.EliminarUser(new Usuario(id,null,null,null));
                if (eliminado) {
                    arrayRespuesta.put("Se elimino el usuario " + id);
                    almacenamiento.setUsuarios(funUser.getListaUsuarios());
                }else{
                     arrayRespuesta.put("Error en el usuario " + id  + " no fue eliminado");
                }
            }
        }catch(Exception e){
            
        }
        return true;
        
    }
    
    private boolean ModUsers(JSONObject jsonObject){        
        try{
        JSONArray jsonArray = (JSONArray) jsonObject.get("MODIFICAR_USUARIOS");
        if (userNameSesion == null && jsonArray.length() > 0) {
                arrayRespuesta.put("Error al modificar usuarios. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonUser = jsonArray.getJSONObject(i);
                String userOld = jsonUser.getString("USUARIO_ANTIGUO");
                String user = jsonUser.getString("USUARIO_NUEVO");
                String pass = jsonUser.getString("PASSWORD");
                String fechaMod = jsonUser.getString("FECHA_MODIFICACION");
                boolean modificado = funUser.setUser(userOld, new Usuario(user,pass,null,fechaMod));
                if (modificado) {
                    arrayRespuesta.put("Se modificaron los datos del usuario " + userOld);
                    funForm.modUserNameForm(userOld, user);
                    almacenamiento.setUsuarios(funUser.getListaUsuarios());
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error, el usuario: " + userOld  + " no se modificaron sus datos, el USUARIO_NUEVO esta repetido");
                }
            }
        }catch(Exception e){
            System.out.println("error en mod Users :" + e.getMessage());
        }
        return true;
        
    }

    private boolean AddForms(JSONObject jsonObject){                
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("NUEVO_FORMS");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error al crear los formularios. No hay una sesion iniciada");
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
                    arrayRespuesta.put("Se creo el formulario " + id);
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error al crear el formulario " + id + " esta repetido");
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
                 arrayRespuesta.put("Error al eliminar formularios. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {                
                String id = jsonArray.getString(i);
                boolean add = funForm.eliminarForm(id);
                if (add) {
                    arrayRespuesta.put("Se elimino el formulario " + id);
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error en el formulario  " + id + " no se pudo eliminar");
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
                 arrayRespuesta.put("Error al modificar los formularios. no hay una sesion iniciada");
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
                    arrayRespuesta.put("Se modifico el formulario " + id);
                    almacenamiento.setForms(funForm.getListaForms());
                }else{
                    arrayRespuesta.put("Error en el formulario  " + id + " no se logro modificar");
                }                
            }
        }catch(Exception e){
            System.out.println("error en addForms : " + e.getMessage());
        }
        
        return true;
        
    }
    private boolean AddComponente(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("AGREGAR_COMP");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error al agregar componentes. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                while(true){
                    JSONObject jsonComp = jsonArray.getJSONObject(i);                
                    String form = jsonComp.getString("FORMULARIO");
                    String id = jsonComp.getString("ID");
                    Formulario formulario = funForm.getFormulario(form);
                    if (formulario == null) {
                        arrayRespuesta.put("Error al agregar el componente " +id +". No se encotro el formulario especificado");
                        break;
                    }
                    ComponenteFunctions funComponente = new ComponenteFunctions(formulario.getListaComponentes());                        
                    String nombreCampo = verificarNull(jsonComp,"NOMBRE_CAMPO");
                    String clase = jsonComp.getString("CLASE");
                    String indice =  String.valueOf(formulario.getListaComponentes().size()+1);
                    String textoVisible = jsonComp.getString("TEXTO_VISIBLE");
                    String alineacion = jsonComp.getString("ALINEACION");
                    String requerido = jsonComp.getString("REQUERIDO");
                    String opciones = verificarNull(jsonComp,"OPCIONES");            
                    String filas = verificarNull(jsonComp,"FILAS");
                    String columnas = verificarNull(jsonComp,"COLUMNAS");
                    String url = verificarNull(jsonComp,"URL");           
                    Componente componete = new Componente(id,nombreCampo,clase,indice,textoVisible,alineacion,requerido,opciones,filas, columnas,url);
                    boolean add = funComponente.addComponente(componete);
                    if (add) {
                        arrayRespuesta.put("Se agrego el componente  " + id + " al formulario " + form) ;
                        formulario.setListaComponentes(funComponente.getListaComponente());
                        funForm.modListaComp(formulario);
                        almacenamiento.setForms(funForm.getListaForms());
                    }else{
                        arrayRespuesta.put("Error al agregar el componente  " + id + " esta repetido");
                    }
                    break;
                }                
            }
        }catch(Exception e){
            System.out.println("error en agregar componente " + e.getMessage());
        }
        
        return true;
    }
    
    private String verificarNull(JSONObject jsonObject, String parametro){
        try{
            return jsonObject.getString(parametro);
        }catch(Exception e){
            return "null";
        }       
    }
    private boolean eliminarComponente(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("ELIMINAR_COMP");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error al eliminar componentes. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                while(true){
                    JSONObject jsonComp = jsonArray.getJSONObject(i);                
                    String form = jsonComp.getString("FORMULARIO");
                    String id = jsonComp.getString("ID");
                    Formulario formulario = funForm.getFormulario(form);
                    if (formulario == null) {
                        arrayRespuesta.put("Error al elimnar el componente " +id +". No se encotro el formulario especificado");
                        break;
                    }
                    ComponenteFunctions funComponente = new ComponenteFunctions(formulario.getListaComponentes());                   
                    boolean add = funComponente.eliminarComponente(id);
                    if (add) {
                        arrayRespuesta.put("Se elimino el componente " + id + " del formulario " + form) ;
                        formulario.setListaComponentes(funComponente.getListaComponente());
                        funForm.modListaComp(formulario);
                        almacenamiento.setForms(funForm.getListaForms());
                    }else{
                        arrayRespuesta.put("Error en el componente " + id + ". No se logro eliminar");
                    }

                    break;
                }                
            }
        }catch(Exception e){
            System.out.println("error en eliminar componente: " + e.getMessage());
        }
        
        return true;
    }
    
    private boolean ModComponentes(JSONObject jsonObject){
        try{
            JSONArray jsonArray = (JSONArray) jsonObject.get("MODIFICAR_COMP");
            if (userNameSesion == null && jsonArray.length() > 0) {
                 arrayRespuesta.put("Error al modificar componentes. No hay una sesion iniciada");
                return false;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                while(true){
                    JSONObject jsonComp = jsonArray.getJSONObject(i);                
                    String form = jsonComp.getString("FORMULARIO");
                    String id = jsonComp.getString("ID");
                    Formulario formulario = funForm.getFormulario(form);
                    if (formulario == null) {
                        arrayRespuesta.put("Error al modificar el componente "+ id + " No se encotro el formulario especificado");
                        break;
                    }
                    ComponenteFunctions funComponente = new ComponenteFunctions(formulario.getListaComponentes());                  
                    String nombreCampo = verificarNull(jsonComp,"NOMBRE_CAMPO");
                    String clase = jsonComp.getString("CLASE");
                    String indice =  jsonComp.getString("INDICE");
                    String textoVisible = jsonComp.getString("TEXTO_VISIBLE");
                    String alineacion = jsonComp.getString("ALINEACION");
                    String requerido = jsonComp.getString("REQUERIDO");
                    String opciones = verificarNull(jsonComp,"OPCIONES");            
                    String filas = verificarNull(jsonComp,"FILAS");
                    String columnas = verificarNull(jsonComp,"COLUMNAS");
                    String url = verificarNull(jsonComp,"URL");           
                    Componente componete = new Componente(id,nombreCampo,clase,indice,textoVisible,alineacion,requerido,opciones,filas, columnas,url);
                    String add = funComponente.ModComponente(componete,form);
                    arrayRespuesta.put(add);
                    formulario.setListaComponentes(funComponente.getListaComponente());
                    funForm.modListaComp(formulario);
                    almacenamiento.setForms(funForm.getListaForms());
                    break;
                }                
            }
        }catch(Exception e){
            System.out.println("error en modificar componente: " + e.getMessage());
        }        
        return true;        
    }
    private String generarEstructuraRespuestas(JSONObject jsonResponse){        
        String respuestas = "<!INI_RESPUESTAS>\n";
        try{
            JSONObject login = jsonResponse.getJSONObject("LOGIN_USUARIO");
            String userName = login.getString("USUARIO");
            respuestas += "<!ini_respuesta: \"LOGIN_USUARIO\">\n  ";
            respuestas += "{ \"BLOQUE\" : [{ \n  ";
            respuestas += "\"USUARIO\" : \"" + userName + "\"\n  }]}\n";
            respuestas += "<!FIN_RESPUESTA>\n  ";
                       
        }catch (JSONException e) {
            respuestas += "<!ini_respuesta: \"LOGIN_USUARIO\">\n  ";
            respuestas += "{ \"BLOQUE\" : [{ \n  ";
            respuestas += "\"USUARIO\" : \"null\"\n  }]}\n";
            respuestas += "<!FIN_RESPUESTA>\n  ";
        }        
        try{
            JSONArray arrayRespuesta = jsonResponse.getJSONArray("RESPUESTAS");
            for (int i = 0; i < arrayRespuesta.length(); i++) {
                String respuesta = arrayRespuesta.getString(i);
                respuestas += "<!ini_respuesta: \"RESPUESTA_SERVIDOR\">\n  ";
                respuestas += "{ \"BLOQUE\" : [{ \n  ";
                respuestas += "\"RESPUESTA\" : \"" + respuesta + "\"\n  }]}\n";
                respuestas += "<!FIN_RESPUESTA>\n  ";                                
            }
        }catch(Exception e){
            System.out.println("Error en recibir las respuestas: " + e.getMessage());
        }
        respuestas += "<!FIN_RESPUESTAS>";
        return respuestas;
    }

}
