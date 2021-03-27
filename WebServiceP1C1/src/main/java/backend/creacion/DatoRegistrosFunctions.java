/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.creacion;

import backend.objetos.DatoRegistros;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sergi
 */
public class DatoRegistrosFunctions {
    private List<DatoRegistros> listaDatos;
    
    public DatoRegistrosFunctions(List<DatoRegistros> listaDatos){
        this.listaDatos = listaDatos;
    }
    
    public boolean addRegistro(String registro, String nombreCampo){
        for (DatoRegistros listaDato : listaDatos) {
            if (listaDato.getNombreCampo().equals(nombreCampo)) {
                listaDato.getListaRegistros().add(registro);
                return true;
            }
        }
        DatoRegistros datos = new DatoRegistros(nombreCampo);
        datos.getListaRegistros().add(registro);
        listaDatos.add(datos);
        return true;   
    }

    public List<DatoRegistros> getListaDatos() {
        return listaDatos;
    }
    
    
}
