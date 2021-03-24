/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.objetos.Usuario;
import backend.reglasGram.LexerUser;
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
    
}
