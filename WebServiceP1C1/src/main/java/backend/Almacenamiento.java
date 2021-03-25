/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.objetos.Componente;
import backend.objetos.DatoRegistros;
import backend.objetos.Formulario;
import backend.objetos.Usuario;
import backend.reglasGram.LexerForm;
import backend.reglasGram.LexerUser;
import backend.reglasGram.ParserForm;
import backend.reglasGram.ParserUser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sergi
 */
public class Almacenamiento {
    
    //paths absolutos, se tienen que cambiar a la hora de moverse de compu
    private static final String PATH_FORMS = "D:\\DocumentosD\\NetBeans\\P1C1\\WebServiceP1C1\\src\\main\\java\\backend\\informacion\\dbForms.txt";
    private static final String PATH_USERS = "D:\\DocumentosD\\NetBeans\\P1C1\\WebServiceP1C1\\src\\main\\java\\backend\\informacion\\dbUsers.txt";
    
    
    public Almacenamiento(){
        
    }
    
    public List getUsuarios(){
        List<Usuario> listaUsuario =  new ArrayList<Usuario>();
        //List<String> errores = new ArrayList();
        try{
            BufferedReader bufer = new BufferedReader(new FileReader(PATH_USERS));
            try{
                LexerUser lexico = new LexerUser(bufer);
                ParserUser parse = new ParserUser(lexico);          
                
                try {
                    parse.parse();
                } catch (Exception ex) {
                 
                }
                listaUsuario = parse.getListaUsuarios();
                //errores = parse.getListaErrores();
                
            
            }catch(Exception e){
               
            }
        }catch(Exception e){           
           
        } 
        
        return listaUsuario;
    }
    
    public void setUsuarios(List<Usuario> listaUusario){
        FileWriter fileWriter = null;
        try {
            String usuarios = "db.users{\n";
            for (Usuario usuario : listaUusario) {
                usuarios += " \"USUARIO\" : [\n";
                usuarios += "{ \"USER\" : \"" +  usuario.getUserName()+"\"},";
                usuarios += "{ \"PASS\" : \"" +  usuario.getPassword() +"\"},";
                usuarios += "{ \"FECHA_CREACION\" : \"" +  usuario.getFechaCreacion() +"\"},";
                usuarios += "{ \"FECHA_MODIFICACION\" : \"" +  usuario.getFechaModificación() +"\"}";
                usuarios += "]\n";
            }   usuarios += "}";
            fileWriter = new FileWriter(PATH_USERS);
            fileWriter.write(usuarios);
        } catch (IOException ex) {
            Logger.getLogger(Almacenamiento.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Almacenamiento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List getForms(){
         List<Formulario> listaForms =  new ArrayList<Formulario>();
         List<String> errores = new ArrayList();
         try{
             BufferedReader bufer = new BufferedReader(new FileReader(PATH_FORMS));
             LexerForm lexico = new LexerForm(bufer);
                ParserForm parse = new ParserForm(lexico);               
                try {
                    parse.parse();
                } catch (Exception ex) {
                    System.out.println("error en el parser " + ex.getMessage());
                }
                listaForms = parse.getListaFormularios(); 
                errores = parse.getListaErrores();
                for (String errore : errores) {
                    System.out.println("errores: " + errore);
             }
         }catch(Exception e){             
             System.out.println("error en el lexer: " + e.getMessage());
         }
         
        return listaForms;
    }
    
    public void setForms(List<Formulario> listaForms){
        FileWriter fileWriter = null;
        try {
            String formularios = "db.formularios(\n";
            for (Formulario form : listaForms) {
                formularios += "\"FORMULARIO\" : [\n";
                formularios += "\"ID_FORMULARIO\" : \"" +  form.getId() +"\",\n";
                formularios += "\"TITULO\" : \"" +  form.getTitulo() +"\",\n";
                formularios += "\"NOMBRE\" : \"" +  form.getNombre() +"\",\n";
                formularios += "\"TEMA\" : \"" +  form.getTema() +"\",\n";
                formularios += "\"USUARIO_CREACION\" : \"" + form.getUsuarioCreacion() +"\",\n";
                formularios += "\"FECHA_CREACION\" : \"" +  form.getFechaCreacion() +"\",\n";
                formularios += "\"ESTRUCTURA\" : (\n";
                for (Componente comp : form.getListaComponentes()) {
                    formularios += "{\n";
                    formularios += "\"ID\" : \"" +  comp.getId() +"\",\n";
                    formularios += "\"NOMBRE_CAMPO\" : \"" +  comp.getNombreCampo() +"\",\n";
                    formularios += "\"CLASE\" : \"" +  comp.getClase() +"\",\n";
                    formularios += "\"INDICE\" : \"" +  comp.getIndice() +"\",\n";
                    formularios += "\"TEXTO_VISIBLE\" : \"" +  comp.getTextoVisible() +"\",\n";
                    formularios += "\"ALINEACION\" : \"" +  comp.getAlineacion() +"\",\n";
                    formularios += "\"REQUERIDO\" : \"" +  comp.getRequerido() +"\",\n";
                    formularios += "\"OPCIONES\" : \"" +  comp.getOpciones() +"\",\n";
                    formularios += "\"FILAS\" : \"" +  comp.getFilas() +"\",\n";
                    formularios += "\"COLUMNAS\" : \"" +  comp.getColumnas() +"\",\n";
                    formularios += "\"URL\" : \"" +  comp.getUrl() +"\"\n";                    
                    formularios += "}\n";
                }               
                formularios += ")\n";
                formularios += "DATOS_RECOPILADOS : (\n";
                for (DatoRegistros datos : form.getListaDatos()) {
                    formularios += "{\n";
                    formularios += "\"NOMBRE_CAMPO\" : \"" + datos.getNombreCampo()   +"\",\n";                    
                    for (String dato : datos.getListaRegistros()) {
                        formularios += "\"REGISTRO\" : \"" + dato +"\"\n";
                    }                    
                    formularios += "}\n";
                }                
                formularios += ")\n";
                formularios += "]\n";
            }   formularios += ")";
            fileWriter = new FileWriter(PATH_FORMS);
            fileWriter.write(formularios);
        } catch (IOException ex) {
            Logger.getLogger(Almacenamiento.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Almacenamiento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    
}
