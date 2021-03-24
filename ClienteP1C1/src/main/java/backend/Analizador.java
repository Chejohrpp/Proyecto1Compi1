/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.objetos.LosErrores;
import backend.objetos.Parametro;
import backend.objetos.Usuario;
import backend.reglasGram.LexerIndigo;
import backend.reglasGram.ParserIndigo;
import backend.reglasGram.Token;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author sergi
 */
public class Analizador {
    private String texto;
    private String userNameSesion=null;
    int cantLogin = 0;
    int cantSolicitud = 0;
    private JTextArea txtRespuesta;
    private List<Parametro> listaParametros;
    private List<LosErrores> erroresSintacticos;
    private List<LosErrores> erroresLexicos;
    private List<LosErrores> errores;
    private List<Solicitud> solicitudes;
    //jsonObjects
    private JSONArray arrayRequest = new JSONArray();
    private JSONArray arrayUser = new JSONArray();
    private JSONObject jsonLogin = new JSONObject();
    
    
    public Analizador(String texto,JTextArea txtRespuesta){
        this.texto = texto;
        this.txtRespuesta = txtRespuesta;
        errores = new ArrayList<>();
        solicitudes = new ArrayList<>();
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
                            crearUser(i);
                            break;  
                        }
                        case "MODIFICAR_USUARIO":{
                            
                            
                        }
                        case "ELIMINAR_USUARIO":{
                            
                        }
                        case "LOGIN_USUARIO":{
                            loginUser(i);
                            break;                            
                        }
                        case "NUEVO_FORMULARIO":{
                            
                        }
                        case "ELIMINAR_FORMULARIO":{
                            
                        }
                        case "MODIFICAR_FORMULARIO":{
                            
                        }
                        case "AGREGAR_COMPONENTE":{
                            
                        }
                        case "ELIMINAR_COMPONENTE":{
                            
                        }
                        case "MODIFICAR_COMPONENTE":{
                            
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
    
    private void crearUser(int i){
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
    
    private void loginUser(int i){
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
    
    
    private void conectarServidor() throws FileNotFoundException, IOException, InterruptedException {
        
        JSONObject jsonObject = new JSONObject();        
        jsonObject.put("CREAR_USUARIO", arrayUser);
        jsonObject.put("LOGIN_USUARIO", jsonLogin);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/WebServiceP1C1/conectionAPC"))
                .header("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();        
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());        
        System.out.println(response.body());
        JSONObject jsonRespuestas  = new JSONObject(response.body());        
        try{
            JSONObject login = jsonRespuestas.getJSONObject("LOGIN_USUARIO");
            String userName = login.getString("USUARIO");
            if (!userName.equals("null")) {
                userNameSesion = userName;
            }else{
                System.out.println("No hay usuario");
                userNameSesion = null;                
            }
            JSONArray arrayRespuesta = jsonRespuestas.getJSONArray("RESPUESTAS");
            for (int i = 0; i < arrayRespuesta.length(); i++) {
                String respuesta = arrayRespuesta.getString(i);
                txtRespuesta.setText(txtRespuesta.getText() +  respuesta + "\n");                
            }
        }catch (JSONException e) {
              System.out.println("error en el json de respuesta: " + e.getMessage());
        }
    }
    
    public String getUserNameSesion(){
        return userNameSesion;
    }
    
    private void AddError(int index){
        int linea = listaParametros.get(index).getKey().getLinea();
        int colum = listaParametros.get(index).getKey().getColumna();
        String lexema = listaParametros.get(index).getKey().getLexema();
        errores.add(new LosErrores("Error: ( " + lexema +" ) -> { Linea: " + linea + ", columna: " + colum  + " }" ));
    }
    
    
}
