/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.creacion;

import backend.objetos.Componente;
import java.util.List;

/**
 *
 * @author sergi
 */
public class ComponenteFunctions {
    private List<Componente> listaComponente;
    
    public ComponenteFunctions(List<Componente> listaComponente){
        this.listaComponente = listaComponente;
    }
    public boolean addComponente(Componente componente){
        if (!verificarComponente(componente.getId())) {           
            listaComponente.add(componente);
            return true;
        }        
        return false;
    }
    private boolean verificarComponente(String id){
        for (Componente componente : listaComponente) {
            if (componente.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean eliminarComponente(String id){
        for (int i = 0; i < listaComponente.size(); i++) {
            if (listaComponente.get(i).getId().equals(id)) {
                listaComponente.remove(i);
                //mover los indices
                for (int j = i; j < listaComponente.size(); j++) {
                    listaComponente.get(j).setIndice(String.valueOf(j+1));
                }
                return true;
            }
        }
        return false;
    }
    
    public String ModComponente(Componente componente1, String form){
        for (Componente componente : listaComponente) {
            if (componente.getId().equals(componente1.getId())) {
                int index = Integer.parseInt(componente.getIndice());
                if (verificarNull(componente1.getClase())) {                    
                    String clase = componente1.getClase();
                    switch (clase){
                        case "CAMPO_TEXTO": case "FICHERO":{
                            if (!componente1.getNombreCampo().equals("null")) {
                                componente.setNombreCampo(componente1.getNombreCampo());
                            }else if(componente.getNombreCampo().equals("null")){
                                return "Error en el componente: " + componente.getId() + " no hay una NOMBRE_CAMPO";
                            }
                            break;
                        }
                        case "AREA_TEXTO":{
                            if (!componente1.getNombreCampo().equals("null")) {
                                componente.setNombreCampo(componente1.getNombreCampo());
                            }else if(componente.getNombreCampo().equals("null")){
                                return "Error en el componente: " + componente.getId() + " no hay una NOMBRE_CAMPO";
                            }
                            if (!componente1.getFilas().equals("null")) {
                                componente.setFilas(componente1.getFilas());
                            }
                            if (!componente1.getColumnas().equals("null")) {
                                componente.setColumnas(componente1.getColumnas());
                            }
                            break;    
                        }
                        case "CHECKBOX": case "RADIO": case "COMBO":{
                            if (!componente1.getNombreCampo().equals("null")) {
                                componente.setNombreCampo(componente1.getNombreCampo());
                            }else if(componente.getNombreCampo().equals("null")){
                                return "Error en el componente: " + componente.getId() + " no hay una NOMBRE_CAMPO";
                            }
                            if (!componente1.getOpciones().equals("null")) {
                                componente.setOpciones(componente1.getOpciones());
                            }else if(componente.getOpciones().equals("null")){
                                return "Error en el componente: " + componente.getId() + " no hay OPCIONES";
                            }
                            break;    
                        }
                        case "IMAGEN":{
                            if (!componente1.getUrl().equals("null")) {
                                componente.setUrl(componente1.getUrl());
                            }else if(componente.getUrl().equals("null")){
                                return "Error en el componente: " + componente.getId() + " no hay una URL";
                            }
                            break;    
                        }                        
                    }
                    componente.setClase(componente1.getClase());
                }
                if (verificarNull(componente1.getTextoVisible())) {
                    componente.setTextoVisible(componente1.getTextoVisible());
                }
                if (verificarNull(componente1.getAlineacion())) {
                    componente.setAlineacion(componente1.getAlineacion());
                }
                if (verificarNull(componente1.getRequerido())) {
                    componente.setRequerido(componente1.getRequerido());
                }
                if (verificarNull(componente1.getIndice())) {
                    if (!componente.getIndice().equalsIgnoreCase(componente1.getIndice())) {
                        int indexNew = Integer.parseInt(componente1.getIndice());
                        Componente compActual = new Componente(componente);
                        listaComponente.remove(index-1);
                        if (indexNew < 1) {               
                            compActual.setIndice(String.valueOf(1));
                            listaComponente.add(0, compActual);
                        }else if(indexNew > listaComponente.size()){
                            compActual.setIndice(String.valueOf(listaComponente.size() + 1));
                            listaComponente.add(compActual);
                        }else{
                            compActual.setIndice(String.valueOf(indexNew));
                            listaComponente.add(indexNew-1, compActual);                            
                        }
                        
                        for (int i = 0; i < listaComponente.size(); i++) {
                            listaComponente.get(i).setIndice(String.valueOf(i+1));
                        }
                    }
                }
                return "se modifico el componente: " + componente.getId() + " del formulario: " + form;
            }
            
        }
        
        return "Error, no se modifico el componente: " + componente1.getId() + ", no se encontro en el formulario: " + form;
    }

    public List<Componente> getListaComponente() {
        return listaComponente;
    }
    private boolean verificarNull(String str){
        if (str.equals("null")) {
            return false;
        }
        return true;
    }
    
    
}
