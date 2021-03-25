/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.objetos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sergi
 */
public class DatoRegistros {
    private String nombreCampo;
    private List<String> ListaRegistros = new ArrayList<>();

    public DatoRegistros() {
    }

    public DatoRegistros(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }
    

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public List<String> getListaRegistros() {
        return ListaRegistros;
    }

    public void setListaRegistros(List<String> ListaRegistros) {
        this.ListaRegistros = ListaRegistros;
    }
    
    
    
}
