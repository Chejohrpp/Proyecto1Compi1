/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.toolsFronted;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author sergi
 */
public class ArchivosOpciones {
    private JTextArea jtextEditor;
    private String path=null;
    private String nombre = null;
    
    public ArchivosOpciones(JTextArea jtextEditor){
        this.jtextEditor = jtextEditor;
    }
    public void abrir(JMenuItem menuItem) throws FileNotFoundException, IOException{
        JFileChooser chooser = new JFileChooser();
        //filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".txt","txt");
        chooser.setFileFilter(filtro);
        if (path != null) {
            chooser.setCurrentDirectory(new File(path));
        }
        if(chooser.showOpenDialog(menuItem)==JFileChooser.APPROVE_OPTION){
          FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
          
          //System.out.println("nombre :" + chooser.getName(chooser.getSelectedFile()));
          //System.out.println("path" + chooser.getSelectedFile().getAbsolutePath());
          nombre = chooser.getSelectedFile().getName();
          path = chooser.getSelectedFile().getAbsolutePath();
          BufferedReader br = new BufferedReader(new InputStreamReader(fis));
          StringBuffer text = new StringBuffer();
          String read;
          while((read=br.readLine())!=null){
             text.append(read).append("\n");
          }
          jtextEditor.setText(text.toString());  
        }
    }
    public void guardar(JMenuItem menuItem){
        if (path != null) {
            File fichero = new File(path);
             try(FileWriter fw=new FileWriter(fichero)){

                    //Escribimos el texto en el fichero
                    fw.write(jtextEditor.getText());
                    fw.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }            
            
        }else{
            //Creamos el objeto JFileChooser
            JFileChooser fc=new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(".txt","txt");
            fc.setFileFilter(filtro);
            if (path != null) {
            fc.setCurrentDirectory(new File(path));
            }
            //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
            int seleccion=fc.showSaveDialog(menuItem);
            //Si el usuario, pincha en aceptar
            if(seleccion==JFileChooser.APPROVE_OPTION){

                //Seleccionamos el fichero
                File fichero=fc.getSelectedFile();
                nombre = fc.getSelectedFile().getName();
                path = fc.getSelectedFile().getAbsolutePath();
                
                try(FileWriter fw=new FileWriter(fichero)){

                    //Escribimos el texto en el fichero
                    fw.write(jtextEditor.getText());
                    fw.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
        
        
    }
    public void nuevo(JMenuItem menuItem){
        if (path != null) {
            //int opcion = JOptionPane.showInternalConfirmDialog(null, "Desea Guardar : " + titulo() + " antes de cerrarlo","Guardar", JOptionPane.YES_NO_OPTION);
            int opcion = JOptionPane.showConfirmDialog(null, "Desea Guardar : " + titulo() + " antes de cerrarlo");
            if (opcion == JOptionPane.YES_OPTION) {
                guardar(menuItem);
                limpiar();
            }else if(opcion == JOptionPane.NO_OPTION){
                limpiar();
            }
        }else{
            limpiar();
        }        
    }
    private void limpiar(){
        jtextEditor.setText("");
        path = null;
        nombre = null;
    }
    
    public String titulo(){
        if (path != null) {
            return nombre;
        }
        return "Nuevo Archivo";
        
    }
}
