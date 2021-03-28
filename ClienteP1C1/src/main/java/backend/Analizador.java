/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.funcionesObj.ComponenteModel;
import backend.funcionesObj.FormularioModel;
import backend.funcionesObj.UsuarioModel;
import backend.objetos.LosErrores;
import backend.objetos.Parametro;
import backend.reglasGram.LexerIndigo;
import backend.reglasGram.LexerServidor;
import backend.reglasGram.ParserIndigo;
import backend.reglasGram.ParserServidor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author sergi
 */
public class Analizador {
    //variables
    private String texto;
    private String userNameSesion;
    //int cantLogin = 0;
    //int cantSolicitud = 0;
    //objetos visuales
    private JTextArea txtRespuesta;
    //listados
    private List<Parametro> listaParametros;
    private List<LosErrores> erroresSintacticos;
    private List<LosErrores> erroresLexicos;
    private List<LosErrores> errores;
    //private List<Solicitud> solicitudes;
    //jsonObjects
    //private JSONArray arrayRequest = new JSONArray();    
    
    //private JSONObject jsonLogin = new JSONObject();
    
    //funciones
    private UsuarioModel usuarioModel;
    private FormularioModel formularioModel;
    private ComponenteModel componenteModel;
    
    
    public Analizador(String texto,JTextArea txtRespuesta, String userNameSesion){
        this.texto = texto;
        this.txtRespuesta = txtRespuesta;
        errores = new ArrayList<>();
        this.userNameSesion = userNameSesion;
        //solicitudes = new ArrayList<>();        
    }
    public void analizar(){
        Reader inputString = new StringReader(texto);
        BufferedReader reader = new BufferedReader(inputString);
        try{
            LexerIndigo lexicoIdigo = new LexerIndigo(reader);
            ParserIndigo parserIndigo = new ParserIndigo(lexicoIdigo);
            try{
                parserIndigo.parse();                
            }catch(Exception e){
                System.out.println("error al parser: " + e.getMessage());
            }
            try{
                listaParametros = parserIndigo.getListaParametros();
                erroresSintacticos = lexicoIdigo.getErroresLexicos();
                erroresLexicos = parserIndigo.getListaErrores();                
            }catch(Exception e){
                System.out.println("error en las listas: " + e.getMessage());
            }
            revisarListaParametros();
        }catch(Exception e){
            System.out.println("error al lexer: " + e.getMessage());
        }
        
    }   
    private void revisarListaParametros(){
        String solicitud = null;
        usuarioModel = new UsuarioModel(listaParametros,errores);
        formularioModel = new FormularioModel(listaParametros,errores,userNameSesion);
        componenteModel = new ComponenteModel(listaParametros,errores);
        for (int i = 0; i < listaParametros.size() ; i++) {    
            if (listaParametros.get(i).getCont() == null) {
                try{
                    solicitud = listaParametros.get(i).getKey().getLexema(); 
                }catch(Exception e){
                    System.out.println("error en la solicitud: " + e.getMessage());
                }                           
                try{
                    switch (solicitud) 
                    {
                        case "CREAR_USUARIO":{
                            usuarioModel.crearUser(i);
                            break;  
                        }
                        case "MODIFICAR_USUARIO":{
                            usuarioModel.modUsers(i);
                            break;                            
                        }
                        case "ELIMINAR_USUARIO":{
                            usuarioModel.eliminarUsers(i);
                            break;
                        }
                        case "LOGIN_USUARIO":{
                            usuarioModel.loginUser(i);
                            break;                            
                        }
                        case "NUEVO_FORMULARIO":{
                            formularioModel.newForm(i);
                            break;                            
                        }
                        case "ELIMINAR_FORMULARIO":{
                            formularioModel.eliminarForm(i);
                            break;
                        }
                        case "MODIFICAR_FORMULARIO":{
                            formularioModel.ModForm(i);
                            break;
                        }
                        case "AGREGAR_COMPONENTE":{
                            componenteModel.addComponente(i);
                            break;                            
                        }
                        case "ELIMINAR_COMPONENTE":{
                            componenteModel.eliminarComponente(i);
                            break;
                        }
                        case "MODIFICAR_COMPONENTE":{
                            componenteModel.modificarComponente(i);
                            break;
                        }
                        case "CONSULTAR_DATOS":{
                            
                        }
                        default:{
                            break;
                        }
                    }
                }catch(Exception e){
                    //errores.add(new LosErrores("Hubo un error al leer las listas: "  + e.getMessage()));
                    System.out.println("Hubo un error al leer las listas: "  + e.getMessage());
                }
            }
        }
        txtRespuesta.setText("");
        if ((erroresSintacticos.size() == 0 | erroresSintacticos == null) && (erroresLexicos.size()== 0 | erroresLexicos ==  null) &&
                (errores.size() == 0 | errores == null)) {
            try {
                txtRespuesta.setText("Todo esta bien \nEnviando Datos al servidor....\n");
                conectarServidor();
            } catch (FileNotFoundException ex) {
                System.out.println("error al conectar: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("error al conectar: " + ex.getMessage());
            } catch(IOException e){
                System.out.println("error al conectar: " + e.getMessage());
            }
        }else{
            for (LosErrores erroresLexico : erroresLexicos) {
                txtRespuesta.setText(txtRespuesta.getText() + erroresLexico.getMensaje() + "\n" );
            }
            for (LosErrores error : erroresSintacticos) {
                txtRespuesta.setText(txtRespuesta.getText() + error.getMensaje() + "\n");
            }
            for (LosErrores error : errores) {
                txtRespuesta.setText(txtRespuesta.getText() +  error.getMensaje() + "\n");
            }
        }
    }   
    
    private void conectarServidor() throws FileNotFoundException, IOException, InterruptedException {
        
        JSONObject jsonObject = new JSONObject();        
        jsonObject.put("USER_NAME_SESION", verificarNull(userNameSesion));
        jsonObject.put("CREAR_USUARIO", usuarioModel.getArrayUser());
        jsonObject.put("LOGIN_USUARIO", usuarioModel.getJsonLogin());
        jsonObject.put("ELIMINAR_USUARIOS", usuarioModel.getArrayUserDelete());
        jsonObject.put("MODIFICAR_USUARIOS", usuarioModel.getArrayUserMod());
        jsonObject.put("NUEVO_FORMS", formularioModel.getArrayForms());
        jsonObject.put("ELIMINAR_FORMS", formularioModel.getArrayFormsDelete());
        jsonObject.put("MODIFICAR_FORMS", formularioModel.getArrayFormsMod());
        jsonObject.put("AGREGAR_COMP", componenteModel.getArrayComponente());
        jsonObject.put("MODIFICAR_COMP", componenteModel.getArrayComponenteMod());
        jsonObject.put("ELIMINAR_COMP", componenteModel.getArrayComponenteDelete());
        

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/WebServiceP1C1/conectionAPC"))
                .header("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();        
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());        
        //System.out.println(response.body());
        getRespuestasServer(response.body());
        
        /*JSONObject jsonRespuestas  = new JSONObject(response.body());        
        try{
            JSONObject login = jsonRespuestas.getJSONObject("LOGIN_USUARIO");
            String userName = login.getString("USUARIO");
            if (!userName.equals("null")) {
                userNameSesion = userName;
                formularioModel.setUserName(userNameSesion);
            }else{
                System.out.println("No hay usuario");
                userNameSesion = null;                
            }            
        }catch (JSONException e) {
              //System.out.println("error en el json de respuesta: " + e.getMessage());
        }
        
        try{
            JSONArray arrayRespuesta = jsonRespuestas.getJSONArray("RESPUESTAS");
            for (int i = 0; i < arrayRespuesta.length(); i++) {
                String respuesta = arrayRespuesta.getString(i);
                txtRespuesta.setText(txtRespuesta.getText() +  respuesta + "\n");                
            }
        }catch(Exception e){
            System.out.println("Error en recibir las respuestas: " + e.getMessage());
        }*/
    }
    
    public String getUserNameSesion(){
        return userNameSesion;
    }
    private String verificarNull(String s){
        if (s == null) {
            return "null";
        }
        return s;
    }
    
    private void getRespuestasServer(String respuestas){
        Reader inputString = new StringReader(respuestas);
        BufferedReader reader = new BufferedReader(inputString);        
        try{
            LexerServidor lexer = new LexerServidor(reader);
            ParserServidor parser = new ParserServidor(lexer);
            try{
                parser.parse();
            }catch(Exception e){
                System.out.println("error al parser las respuestas del servidor: " + e.getMessage());
            }
            List<Parametro> listaRespuestas = parser.getListaParametros();
            for (Parametro listaRespuesta : listaRespuestas) {
                try{
                    if (listaRespuesta.getKey().getLexema().equals("USUARIO")) {
                        String userName = listaRespuesta.getCont().getLexema();
                        if (!userName.equals("null")) {
                            userNameSesion = userName;
                            formularioModel.setUserName(userNameSesion);
                        }else{
                            System.out.println("No hay usuario");
                            userNameSesion = null;                
                        }
                    }else if(listaRespuesta.getKey().getLexema().equals("RESPUESTA")) {
                        String respuesta = listaRespuesta.getCont().getLexema();
                        txtRespuesta.setText(txtRespuesta.getText() +  respuesta + "\n");
                    }
                }catch(Exception e){
                    System.out.println("error en el ciclo del servidor: " + e.getMessage());
                }                
            }
            List<LosErrores> errores = parser.getListaErrores();
            for (LosErrores errore : errores) {
                System.out.println("error parser: " + errore.getMensaje());
            }
            List<LosErrores> erro = lexer.getErroresLexicos();
            for (LosErrores errore : erro) {
                System.out.println("error lexer: " + errore.getMensaje());
            }
            
        }catch(Exception e){
            System.out.println("error al lexer de las respuestas del servidor: " + e.getMessage());            
        } 
        
    }
    
    
}
