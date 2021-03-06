/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.funcionesObj;

import backend.objetos.LosErrores;
import backend.objetos.Parametro;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sergi
 */
public class UsuarioModel {
    int cantLogin = 0;
    //listas
    private List<Parametro> listaParametros;
    private List<LosErrores> errores;
    //json
    private JSONArray arrayUser = new JSONArray();
    private JSONObject jsonLogin = new JSONObject();
    private JSONArray arrayUserDelete = new JSONArray();
    private JSONArray arrayUserMod = new JSONArray();

    public UsuarioModel(List<Parametro> listaParametros, List<LosErrores> errores) {
        this.listaParametros = listaParametros;
        this.errores = errores;
    }    
    public void crearUser(int i){
        int j = 1;        
        String user = null;
        String pass = null;
        String fecha = null;
        while(true) {
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {
                    if (listaParametros.get(i+j).getKey().getLexema().equals("USUARIO") && user == null) {
                        user = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("PASSWORD") && pass ==null){
                        pass = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("FECHA_CREACION") && fecha ==null){
                        fecha = listaParametros.get(i+j).getCont().getLexema();
                    }else{
                        AddError(i+j);
                    }
                }else{
                    break;                    
                }
            }else{
                break;
            }
            j++;
        }        
        if (user != null && pass != null) {
            
            JSONObject newUser = new JSONObject();
            newUser.put("USUARIO", user);
            newUser.put("PASSWORD", pass);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (fecha == null) {
                Date date = Calendar.getInstance().getTime();                  
                String strDate = dateFormat.format(date);
                newUser.put("FECHA_CREACION", strDate);
            }else{
                try {                   
                    String strDate = dateFormat.format(dateFormat.parse(fecha));
                    newUser.put("FECHA_CREACION", strDate);
                } catch (ParseException ex) {
                    AddError(i);
                }                
            }            
            arrayUser.put(newUser);
        }else{
            AddError(i);
        }
    }
    
    public void loginUser(int i){
        int j = 1; 
        boolean user = false;
        boolean password = false;
        String userName = null;
        String pass = null;
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {                                        
                    if (listaParametros.get(i+j).getKey().getLexema().equals("USUARIO") && user ==false) {                                            
                        userName = listaParametros.get(i+j).getCont().getLexema();
                        user = true;
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("PASSWORD") && password ==false){
                        pass = listaParametros.get(i+j).getCont().getLexema();
                        password = true;
                    }else{
                        AddError(i+j);
                    }

                }else{
                    break;
                }

            }else{
                break;
            }
            j++;
        }
        if (user && password) {                                
            jsonLogin.put("USUARIO", userName);
            jsonLogin.put("PASSWORD", pass);                                
            cantLogin++;
            if (cantLogin > 1) {
                AddError(i);
            }
        }else{
            AddError(i);
        }
    }
    
    public void eliminarUsers(int i){
        String user = null;
        int j =1;
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {
                    if (listaParametros.get(i+j).getKey().getLexema().equals("USUARIO") && user ==null){
                        user = listaParametros.get(i+j).getCont().getLexema();
                    }else{
                        AddError(i+j);
                    }
                }else{
                    break;
                }
            }else{
                break;
            }
            j++;
        }
        
        if (user != null) {
            arrayUserDelete.put(user);
        }else{
            AddError(i);
        }
        
    }
    
    public void modUsers(int i){
        int j =1;
        String userOld = null;
        String user = null;
        String pass = null;
        String fechaMod = null;
        
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {
                    if (listaParametros.get(i+j).getKey().getLexema().equals("USUARIO_ANTIGUO") && userOld ==null){
                        userOld = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("USUARIO_NUEVO") && user ==null){
                        user = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("NUEVO_PASSWORD") && pass ==null){
                        pass = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("FECHA_MODIFICACION") && fechaMod ==null){
                        fechaMod = listaParametros.get(i+j).getCont().getLexema();
                    }else{
                        AddError(i+j);
                    }
                }else{
                    break;
                }
            }else{
                break;
            }
            j++;
        }
        
        if (userOld != null && user != null && pass != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("USUARIO_ANTIGUO", userOld);
            jsonObject.put("USUARIO_NUEVO", user);
            jsonObject.put("PASSWORD", pass);  
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (fechaMod == null) {
                Date date = Calendar.getInstance().getTime();                  
                String strDate = dateFormat.format(date);
                jsonObject.put("FECHA_MODIFICACION", strDate);
            }else{
                try {                   
                    String strDate = dateFormat.format(dateFormat.parse(fechaMod));
                    jsonObject.put("FECHA_MODIFICACION", strDate);
                } catch (ParseException ex) {
                    AddError(i);
                }                
            }
            arrayUserMod.put(jsonObject);
            
        }else{
            AddError(i);
        }
    }
    
    private void AddError(int index){
        int linea = listaParametros.get(index).getKey().getLinea();
        int colum = listaParametros.get(index).getKey().getColumna();
        String lexema = listaParametros.get(index).getKey().getLexema();
        errores.add(new LosErrores("Error: ( " + lexema +" ) -> { Linea: " + linea + ", columna: " + colum  + " }" ));
    }

    public JSONArray getArrayUser() {
        return arrayUser;
    }

    public JSONObject getJsonLogin() {
        return jsonLogin;
    }

    public JSONArray getArrayUserDelete() {
        return arrayUserDelete;
    }

    public JSONArray getArrayUserMod() {
        return arrayUserMod;
    }
    
    
}
