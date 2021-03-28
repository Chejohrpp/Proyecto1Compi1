/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.creacion;

import backend.objetos.Usuario;
import java.util.List;

/**
 *
 * @author sergi
 */
public class UsuarioFunctions {
    
    private List<Usuario> listaUsuarios;

    public UsuarioFunctions(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    
    public Usuario verificarUser(Usuario user){
        for (Usuario listaUsuario : listaUsuarios) {
            if (listaUsuario.getUserName().equals(user.getUserName() )&& listaUsuario.getPassword().equals(user.getPassword()) ) {
                return listaUsuario;
            }
        }      
        return null;
    }
    
    public Boolean setUser(String id, Usuario usuario){
        if (userNameRepetido(usuario.getUserName(),0)) {
            for (Usuario listaUsuario : listaUsuarios) {
                if (listaUsuario.getUserName().equals(id)) {                    
                    listaUsuario.setPassword(usuario.getPassword());
                    listaUsuario.setUserName(usuario.getUserName());
                    listaUsuario.setFechaModificación(usuario.getFechaModificación());
                    return true;               
                }
            }
        }       
        return false;
    }
    public boolean addUser(Usuario usuario){
        if (userNameRepetido(usuario.getUserName(),0)) {
            listaUsuarios.add(usuario);
            return true;
        }
        return false;
    }
    private boolean userNameRepetido(String id, int cant){
        int veces = 0;
        for (Usuario listaUsuario : listaUsuarios) {
            if (listaUsuario.getUserName().equals(id)) {
                veces++;
            }
        }
        if (veces<=cant) {
            return true;
        }
        return false;
    }
    
    public boolean EliminarUser(Usuario user){
        
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getUserName().equals(user.getUserName())) {
                listaUsuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    
    
   
}
