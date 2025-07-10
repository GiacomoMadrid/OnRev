package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadVariable extends Complejidad {
    @Override 
    public Complejidad sumar(Complejidad otra) { 
        if(otra instanceof ComplejidadConstante){
            return this;
        }
        
        return this.maximo(otra); 
    }
    
    @Override 
    public Complejidad multiplicar(Complejidad otra) { 
        return this; 
    }
    
    @Override 
    public Complejidad maximo(Complejidad otra) { 
        return otra instanceof ComplejidadConstante ? this : otra; 
    }
    
    @Override 
    public String toBigO() { 
        return "O(n)"; 
    }
}
