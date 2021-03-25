/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.objetos;

/**
 *
 * @author sergi
 */
public class Componente {
    private String id;
    private String nombreCampo;
    private String clase;
    private String indice;
    private String textoVisible;
    private String alineacion;
    private String requerido;
    private String opciones;
    private String filas;
    private String columnas;
    private String url;

    public Componente(String id, String nombreCampo, String clase, String indice, String textoVisible, String alineacion, String requerido, String opciones, String filas, String columnas, String url) {
        this.id = id;
        this.nombreCampo = nombreCampo;
        this.clase = clase;
        this.indice = indice;
        this.textoVisible = textoVisible;
        this.alineacion = alineacion;
        this.requerido = requerido;
        this.opciones = opciones;
        this.filas = filas;
        this.columnas = columnas;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getTextoVisible() {
        return textoVisible;
    }

    public void setTextoVisible(String textoVisible) {
        this.textoVisible = textoVisible;
    }

    public String getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(String alineacion) {
        this.alineacion = alineacion;
    }

    public String getRequerido() {
        return requerido;
    }

    public void setRequerido(String requerido) {
        this.requerido = requerido;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    public String getFilas() {
        return filas;
    }

    public void setFilas(String filas) {
        this.filas = filas;
    }

    public String getColumnas() {
        return columnas;
    }

    public void setColumnas(String columnas) {
        this.columnas = columnas;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
    
}
