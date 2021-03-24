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
public class Solicitud {
    private String nombre;
    private Object solicitud;

    public Solicitud(String nombre, Object solicitud) {
        this.nombre = nombre;
        this.solicitud = solicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Object solicitud) {
        this.solicitud = solicitud;
    }
    
    
}
