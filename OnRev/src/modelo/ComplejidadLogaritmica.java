/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadLogaritmica extends Complejidad {
    @Override public Complejidad sumar(Complejidad otra) { 
        return this.maximo(otra); 
    }
    
    @Override public Complejidad multiplicar(Complejidad otra) {
        if (otra instanceof ComplejidadVariable) {
            return Complejidad.N_LOG_N;
        }
        
        if (otra instanceof ComplejidadPotencia) {
            return otra.multiplicar(this);
        }
        return this;
    }
    
    @Override public Complejidad maximo(Complejidad otra) {
        if (otra instanceof ComplejidadConstante) return this;
        return otra;
    }
    
    @Override public String toBigO() { 
        return "O(log n)"; 
    }


    
}
