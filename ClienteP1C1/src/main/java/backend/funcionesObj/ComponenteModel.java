/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.funcionesObj;

import backend.objetos.LosErrores;
import backend.objetos.Parametro;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sergi
 */
public class ComponenteModel {
    //listas
    private List<Parametro> listaParametros;
    private List<LosErrores> errores;
    //json
    JSONArray arrayComponente = new JSONArray();
    JSONArray arrayComponenteDelete = new JSONArray();
    JSONArray arrayComponenteMod = new JSONArray();

    public ComponenteModel(List<Parametro> listaParametros, List<LosErrores> errores) {
        this.listaParametros = listaParametros;
        this.errores = errores;
    }
    
    public void addComponente(int i){
        int j = 1;
        String id = null;
        String nombreCampo = null;
        String formulario = null;
        String clase = null;
        String textoVisible = null;
        String Alineacion = null;
        String requerido = null;
        String opciones = null;
        String filas = null;
        String columnas = null;
        String url = null;
        
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {                                        
                    if (listaParametros.get(i+j).getKey().getLexema().equals("ID") && id ==null) {                                            
                        id = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("NOMBRE_CAMPO") && nombreCampo ==null){
                        nombreCampo = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("FORMULARIO") && formulario ==null){
                        formulario = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("CLASE") && clase ==null){
                        clase = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("TEXTO_VISIBLE") && textoVisible ==null){
                        textoVisible = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("ALINEACION") && Alineacion ==null){
                        Alineacion = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("REQUERIDO") && requerido ==null){
                        requerido = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("OPCIONES") && opciones ==null){
                        opciones = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("FILAS") && filas ==null){
                        filas = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("COLUMNAS") && columnas ==null){
                        columnas = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("URL") && url ==null){
                        url = listaParametros.get(i+j).getCont().getLexema();
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
        
        if (id != null && formulario != null && clase != null && textoVisible != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("FORMULARIO", formulario);
            jsonObject.put("CLASE", clase);
            jsonObject.put("TEXTO_VISIBLE", textoVisible);            
            jsonObject.put("ALINEACION", verificarAlineacion(Alineacion,i));
            jsonObject.put("REQUERIDO", verificarRequerido(requerido,i));            
            if (clase.equals("CAMPO_TEXTO")) {
                jsonObject.put("NOMBRE_CAMPO", vNull(nombreCampo,i,"NOMBRE_CAMPO"));                
            }else if(clase.equals("AREA_TEXTO")) {
                jsonObject.put("NOMBRE_CAMPO", vNull(nombreCampo,i,"NOMBRE_CAMPO"));            
                jsonObject.put("FILAS", verificarNumber(filas,i,"FILAS"));
                jsonObject.put("COLUMNAS", verificarNumber(columnas,i,"COLUMNAS"));                
            }else if(clase.equals("CHECKBOX")) {
                jsonObject.put("NOMBRE_CAMPO", vNull(nombreCampo,i,"NOMBRE_CAMPO"));
                jsonObject.put("OPCIONES", vNull(opciones,i,"OPCIONES"));                
            }else if(clase.equals("RADIO")) {
                jsonObject.put("NOMBRE_CAMPO", vNull(nombreCampo,i,"NOMBRE_CAMPO"));
                jsonObject.put("OPCIONES", vNull(opciones,i,"OPCIONES"));                
            }else if(clase.equals("FICHERO")) {
                jsonObject.put("NOMBRE_CAMPO", vNull(nombreCampo,i,"NOMBRE_CAMPO"));                
            }else if(clase.equals("IMAGEN")) {
                jsonObject.put("URL", vNull(url,i,"URL"));                
            }else if(clase.equals("COMBO")) {
                jsonObject.put("NOMBRE_CAMPO", vNull(nombreCampo,i,"NOMBRE_CAMPO"));
                jsonObject.put("OPCIONES", vNull(opciones,i,"OPCIONES"));                
            }else if(clase.equals("BOTON")) {                
            }else{
                AddError(i);
                errores.add(new LosErrores("Error en la solicitud anterior:   ^ La CLASE especificada no existen dentro de los parametros" ));
            }            
            arrayComponente.put(jsonObject);
        }else{
            AddError(i);
             errores.add(new LosErrores("Error en la solicitud anterior:   ^ Faltan atributos " ));
        }
        
    }
    
    private String vNull(String text, int i, String valor ){
        if (text == null) {
            AddError(i);
            errores.add(new LosErrores("Error en la solicitud anterior:   ^ No esta el atributo " + valor ));
        }
        return text;
    }
    private String verificarAlineacion(String Alineacion, int i){
        if (Alineacion == null) {
                Alineacion = "IZQUIERDA";
            }else{
                if (!Alineacion.equals("CENTRO") && !Alineacion.equals("IZQUIERDA") && !Alineacion.equals("DERECHA")  && !Alineacion.equals("JUSTIFICAR")) {
                    AddError(i);
                     errores.add(new LosErrores("Error en la solicitud anterior:   ^ la ALINEACION escrita no es valida" ));
                }
            }
        return Alineacion;
    }
    private String verificarNumber(String filas, int i, String texto){
        if (filas == null) {            
            return "0";
        }else{
            try{
                int number = Integer.parseInt(filas);
                int comprobar = number + 5;
            }catch(Exception e){
                AddError(i);
                errores.add(new LosErrores("Error en la solicitud anterior:   ^en " + texto +" no se ingreso un numero entero" ));
            }
        }
        return filas;
    }
    private String verificarRequerido(String requerido, int i){
        if (requerido == null) {
            return "NO";
        }
        if (!requerido.equalsIgnoreCase("SI") && !requerido.equalsIgnoreCase("NO")) {
            AddError(i);
            errores.add(new LosErrores("Error en la solicitud anterior:   ^el contedido de REQUERIDO no es valido"));
        }
        return requerido;
    }
    
    public void eliminarComponente(int i){
        int j = 1;
        String id = null;
        String formulario = null;
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {                                        
                    if (listaParametros.get(i+j).getKey().getLexema().equals("ID") && id ==null) {                                            
                        id = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("FORMULARIO") && formulario ==null){
                        formulario = listaParametros.get(i+j).getCont().getLexema();
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
        if (id != null && formulario != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("FORMULARIO", formulario);
            arrayComponenteDelete.put(jsonObject);
        }else{
            AddError(i);
        }       
    }
    public void modificarComponente(int i){
        int j = 1;
        String id = null;//obligatorio
        String nombreCampo = null;
        String formulario = null;//obligatorio
        String clase = null;
        String indice = null;
        String textoVisible = null;
        String Alineacion = null;
        String requerido = null;
        String opciones = null;
        String filas = null;
        String columnas = null;
        String url = null;
        while(true){
            if (listaParametros.size() >= i+j+1) {
                if (listaParametros.get(i+j).getCont() != null) {                                        
                    if (listaParametros.get(i+j).getKey().getLexema().equals("ID") && id ==null) {                                            
                        id = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("NOMBRE_CAMPO") && nombreCampo ==null){
                        nombreCampo = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("FORMULARIO") && formulario ==null){
                        formulario = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("CLASE") && clase ==null){
                        clase = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("TEXTO_VISIBLE") && textoVisible ==null){
                        textoVisible = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("ALINEACION") && Alineacion ==null){
                        Alineacion = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("REQUERIDO") && requerido ==null){
                        requerido = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("OPCIONES") && opciones ==null){
                        opciones = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("FILAS") && filas ==null){
                        filas = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("COLUMNAS") && columnas ==null){
                        columnas = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("URL") && url ==null){
                        url = listaParametros.get(i+j).getCont().getLexema();
                    }else if(listaParametros.get(i+j).getKey().getLexema().equals("INDICE") && indice ==null){
                        indice = listaParametros.get(i+j).getCont().getLexema();
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
        
        if (id != null && formulario != null) {
             JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("FORMULARIO", formulario);
            jsonObject.put("CLASE", verificarNull(clase));
            jsonObject.put("TEXTO_VISIBLE", verificarNull(textoVisible));            
            jsonObject.put("ALINEACION", verificarNullAlineacion(Alineacion,i));
            jsonObject.put("REQUERIDO", verificarNullRequerido(requerido,i));
            jsonObject.put("INDICE", verificarNullNumber(indice,i,"INDICE"));            
            jsonObject.put("NOMBRE_CAMPO", verificarNull(nombreCampo)); 
            jsonObject.put("FILAS", verificarNullNumber(filas,i,"FILAS"));
            jsonObject.put("COLUMNAS", verificarNullNumber(columnas,i,"COLUMNAS")); 
            jsonObject.put("OPCIONES", verificarNull(opciones));
            jsonObject.put("URL", verificarNull(url)); 
            if (clase != null) {
                if ( !clase.equals("CAMPO_TEXTO") && !clase.equals("AREA_TEXTO") && !clase.equals("CHECKBOX") && !clase.equals("RADIO")
                        && !clase.equals("FICHERO") && !clase.equals("IMAGEN") && !clase.equals("BOTON") && !clase.equals("COMBO") ) {
                    AddError(i);
                    errores.add(new LosErrores("Error en la solicitud anterior:   ^ La CLASE especificada no existen dentro de los parametros" ));                    
                }
            }                 
            arrayComponenteMod.put(jsonObject);
        }else{
            AddError(i);
             errores.add(new LosErrores("Error en la solicitud anterior:   ^ Faltan atributos escenciales " ));
        }       
    }
    
    private String verificarNull(String texto){
        if (texto == null) {
            return "null";
        }
        return texto;
    }
    private String verificarNullNumber(String texto, int i, String cont){
        if (texto == null) {
            return "null";
        }
        return verificarNumber(texto,i, cont);
    }
    private String verificarNullAlineacion(String texto, int i){
        if (texto == null) {
            return "null";
        }
        return verificarAlineacion(texto, i);
    }
    private String verificarNullRequerido(String text, int i){
        if (text == null) {
            return "null";
        }
        return verificarRequerido(text,i);
    }
    
    private void AddError(int index){
        int linea = listaParametros.get(index).getKey().getLinea();
        int colum = listaParametros.get(index).getKey().getColumna();
        String lexema = listaParametros.get(index).getKey().getLexema();
        errores.add(new LosErrores("Error: ( " + lexema +" ) -> { Linea: " + linea + ", columna: " + colum  + " }" ));
    }
    public JSONArray getArrayComponente() {
        return arrayComponente;
    }

    public JSONArray getArrayComponenteDelete() {
        return arrayComponenteDelete;
    }

    public JSONArray getArrayComponenteMod() {
        return arrayComponenteMod;
    }
    
    
}
