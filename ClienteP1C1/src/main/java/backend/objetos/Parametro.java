/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.objetos;

import backend.reglasGram.Token;

/**
 *
 * @author sergi
 */
public class Parametro {
    private Token key;
    private Token cont;

    public Parametro(Token key, Token cont) {
        this.key = key;
        this.cont = cont;
    }

    public Token getKey() {
        return key;
    }

    public Token getCont() {
        return cont;
    }
    
}
