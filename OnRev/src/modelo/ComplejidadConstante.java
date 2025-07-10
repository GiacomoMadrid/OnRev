package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadConstante extends Complejidad {
    private final int valor;
    
    ComplejidadConstante(int valor) { 
        this.valor = valor; 
    }
    
    @Override 
    public Complejidad sumar(Complejidad otra) { 
        return otra.maximo(this); 
    }
    @Override 
    public Complejidad multiplicar(Complejidad otra) { 
        return otra; 
    }
    
    @Override 
    public Complejidad maximo(Complejidad otra) { 
        return otra instanceof ComplejidadConstante ? this : otra; 
    }
    
    @Override 
    public String toBigO() { 
        return "O(1)"; 
    }

    
}
