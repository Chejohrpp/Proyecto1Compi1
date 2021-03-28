/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.creacion;

import backend.objetos.Formulario;
import java.util.List;

/**
 *
 * @author sergi
 */
public class FormularioFunctions {
    private List<Formulario> listaForms;
    
    public FormularioFunctions(List<Formulario> listaForms){
        this.listaForms = listaForms;
    }
    
    public boolean addForm(Formulario formulario){
        if (!verificarForm(formulario.getId())) {
            listaForms.add(formulario);
            return true;
        }       
        return false;
    }
    
    public boolean eliminarForm(String id){
        for (int i = 0; i < listaForms.size(); i++) {
            if (listaForms.get(i).getId().equals(id)) {
                listaForms.remove(i);
                return true;
            }
        }        
        return false;
    }
    public void modUserNameForm(String userOld, String userNew){
        for (Formulario listaForm : listaForms) {
            if (listaForm.getUsuarioCreacion().equals(userOld)) {
                listaForm.setUsuarioCreacion(userNew);
            }
        }
    }
    
    public boolean modForm(Formulario formulario){
        for (Formulario form : listaForms) {
            if (form.getId().equals(formulario.getId())) {
                if (verificarNull(formulario.getTitulo())) {
                    form.setTitulo(formulario.getTitulo());
                }
                if (verificarNull(formulario.getTema())) {
                    form.setTema(formulario.getTema());
                }
                if (verificarNull(formulario.getNombre())) {
                    form.setNombre(formulario.getNombre());
                }               
                return true;
            }
        }
        
        return false;
    }
    public boolean modListaComp(Formulario formulario){
        for (Formulario listaForm : listaForms) {
            if (listaForm.getId().equals(formulario.getId())) {
                listaForm.setListaComponentes(formulario.getListaComponentes());
                return true;
            }
        }
        return false;
    }
    public boolean modListaDatos(Formulario formulario){
        for (Formulario listaForm : listaForms) {
            if (listaForm.getId().equals(formulario.getId())) {
                listaForm.setListaDatos(formulario.getListaDatos());
                return true;
            }
        }
        return false;
    }
    private boolean verificarNull(String str){
        if (str.equals("null")) {
            return false;
        }
        return true;
    }
    private boolean verificarForm(String id){
        for (Formulario listaForm : listaForms) {
            if (listaForm.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Formulario> getListaForms() {
        return listaForms;
    }
    
    public Formulario getFormulario(String id){
        for (Formulario listaForm : listaForms) {
            if (listaForm.getId().equals(id)) {
                return listaForm;
            }
        }        
        return null;
    }
    
}
