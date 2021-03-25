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
public class Formulario {
    private String id;
    private String titulo;
    private String nombre;
    private String tema;
    private String usuarioCreacion;
    private String fechaCreacion;
    private List<Componente> listaComponentes =  new ArrayList<>();
    private List<DatoRegistros> listaDatos = new ArrayList<>();

    public Formulario(String id, String titulo, String nombre, String tema, String usuarioCreacion, String fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.nombre = nombre;
        this.tema = tema;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Componente> getListaComponentes() {
        return listaComponentes;
    }

    public void setListaComponentes(List<Componente> listaComponentes) {
        this.listaComponentes = listaComponentes;
    }   

    public List<DatoRegistros> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<DatoRegistros> listaDatos) {
        this.listaDatos = listaDatos;
    }
    
}
