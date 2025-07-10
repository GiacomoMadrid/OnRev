package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadPotencia extends Complejidad {
    private final int exponente;
    ComplejidadPotencia(int exp) { 
        this.exponente = exp; 
    }
    
    int getExponente() { 
        return exponente; 
    }
    
    @Override 
    public Complejidad sumar(Complejidad otra) { 
        return this.maximo(otra); 
    }
    
    @Override 
    public Complejidad multiplicar(Complejidad otra) {
        if (otra instanceof ComplejidadPotencia) {
            return new ComplejidadPotencia(this.exponente + ((ComplejidadPotencia) otra).exponente);
        } else if (otra instanceof ComplejidadVariable) {
            return new ComplejidadPotencia(this.exponente + 1);
        } else if (otra instanceof ComplejidadLogaritmica) {
            return new ComplejidadNLogN().multiplicar(this);
        }
        return this;
    }
    
    @Override 
    public Complejidad maximo(Complejidad otra) {
        if (otra instanceof ComplejidadPotencia) {
            return this.exponente >= ((ComplejidadPotencia) otra).exponente ? this : otra;
        } else if (otra instanceof ComplejidadConstante) {
            return this;
        } else {
            return otra;
        }
    }
    
    @Override 
    public String toBigO() { 
        return "O(n^" + exponente + ")"; 
    }
}

