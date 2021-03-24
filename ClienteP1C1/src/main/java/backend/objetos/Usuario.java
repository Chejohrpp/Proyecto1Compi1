/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.objetos;

import java.util.Date;

/**
 *
 * @author sergi
 */
public class Usuario {
    private String userName;
    private String password;
    private String fechaCreacion;
    private String fechaModificación;

    public Usuario(String userName, String password, String fechaCreacion, String fechaModificación) {
        this.userName = userName;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificación = fechaModificación;
    }   
    public Usuario(){
        
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificación() {
        return fechaModificación;
    }

    public void setFechaModificación(String fechaModificación) {
        this.fechaModificación = fechaModificación;
    }
    
    
}
