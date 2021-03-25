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
public class FormularioModel {
    //var
    private String userName;
    
    //listas
    private List<Parametro> listaParametros;
    private List<LosErrores> errores;
    
    //json
    JSONArray arrayForms = new JSONArray();
    JSONArray arrayFormsDelete = new JSONArray();
    JSONArray arrayFormsMod = new JSONArray();

    public FormularioModel(List<Parametro> listaParametros, List<LosErrores> errores,String userName) {
        this.listaParametros = listaParametros;
        this.errores = errores;
        this.userName = userName;
    }
    
    public void newForm(int i){
        int j = 1;
        String id = null;
        String titulo = null;
        String nombre = null;
        String tema = null;
        String usuarioCreacion = null;
        String fechaCreacion = null;
       while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {
                    if (listaParametros.get(i+j).getKey().getLexema().equals("ID") && id == null){
                        id = listaParametros.get(i+j).getCont().getLexema();
                    } else if (listaParametros.get(i+j).getKey().getLexema().equals("TITULO") && titulo == null){
                        titulo  = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("NOMBRE") && nombre == null){
                        nombre = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("TEMA") && tema == null){
                        tema = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("USUARIO_CREACION") && usuarioCreacion == null){
                        usuarioCreacion = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("FECHA_CREACION") && fechaCreacion == null){
                        fechaCreacion = listaParametros.get(i+j).getCont().getLexema();
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
       
        if (id != null && titulo != null && nombre != null && tema != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("TITULO", titulo);
            jsonObject.put("NOMBRE", nombre);
            jsonObject.put("TEMA", verificarTema(tema,i,null) );
            jsonObject.put("FECHA_CREACION", fecha(fechaCreacion,i));
            if (usuarioCreacion == null) {
                if (userName == null) {                    
                    jsonObject.put("USUARIO_CREACION", "null");
                }else{
                    jsonObject.put("USUARIO_CREACION", userName);
                }
            }else{
                jsonObject.put("USUARIO_CREACION", usuarioCreacion);
            }
            arrayForms.put(jsonObject);
        }else{
            AddError(i);
        }
    }
    private String verificarTema(String tema, int i, String otroParametro){
        if (!tema.equals("violet") && !tema.equals("evergarden") && !tema.equals("dark")) {
            if (otroParametro != null) {
                if (tema.equals(otroParametro)) {
                    return tema;
                }
            }
            AddError(i);
            errores.add(new LosErrores("error de la solicitud anterior:  ^ El TEMA escrito no es valido"));
            return tema;
        }
        return tema;
    }
    
    public void eliminarForm(int i){
        int j = 1;
        String id = null;
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {
                    if (listaParametros.get(i+j).getKey().getLexema().equals("ID") && id == null){
                        id = listaParametros.get(i+j).getCont().getLexema();
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
        if (id != null) {
            arrayFormsDelete.put(id);
        }else{
            AddError(i);
        }
    }
    
    public void ModForm(int i){
        int j = 1;
        String id = null;
        String titulo = null;
        String nombre = null;
        String tema = null;
        
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {
                    if (listaParametros.get(i+j).getKey().getLexema().equals("ID") && id == null){
                        id = listaParametros.get(i+j).getCont().getLexema();
                    } else if (listaParametros.get(i+j).getKey().getLexema().equals("TITULO") && titulo == null){
                        titulo  = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("NOMBRE") && nombre == null){
                        nombre = listaParametros.get(i+j).getCont().getLexema();
                    }else if (listaParametros.get(i+j).getKey().getLexema().equals("TEMA") && tema == null){
                        tema = listaParametros.get(i+j).getCont().getLexema();
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
        if (id != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("TITULO", verificarNull(titulo));
            jsonObject.put("NOMBRE", verificarNull(nombre));
            jsonObject.put("TEMA",verificarTemaNull(tema,i));
            arrayFormsMod.put(jsonObject);            
        }else{
            AddError(i);
        }
    }
    private String verificarNull(String texto){
        if (texto == null) {
            return "null";
        }
        return texto;
    }
    private String verificarTemaNull(String tema, int i){
        if (tema == null) {
            return "null";
        }        
        if (!tema.equals("violet") && !tema.equals("evergarden") && !tema.equals("dark")) {
            AddError(i);
            errores.add(new LosErrores("error de la solicitud anterior:  ^ El TEMA escrito no es valido"));
            return tema;
        }
        return tema;
    }
    
    private String fecha(String fecha, int i){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (fecha == null) {
                Date date = Calendar.getInstance().getTime();                  
                String strDate = dateFormat.format(date);
                return strDate;
            }else{
                try {                   
                    String strDate = dateFormat.format(dateFormat.parse(fecha));
                    return strDate;
                } catch (ParseException ex) {
                    AddError(i);
                }                
            }
        return null;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    private void AddError(int index){
        int linea = listaParametros.get(index).getKey().getLinea();
        int colum = listaParametros.get(index).getKey().getColumna();
        String lexema = listaParametros.get(index).getKey().getLexema();
        errores.add(new LosErrores("Error: ( " + lexema +" ) -> { Linea: " + linea + ", columna: " + colum  + " }" ));
    }

    public JSONArray getArrayForms() {
        return arrayForms;
    }

    public JSONArray getArrayFormsDelete() {
        return arrayFormsDelete;
    }

    public JSONArray getArrayFormsMod() {
        return arrayFormsMod;
    }
    
}
