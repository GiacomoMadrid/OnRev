package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadPotencia extends Complejidad {
    private final int exponente;
    ComplejidadPotencia(int exp) { this.exponente = exp; }
    @Override 
    public Complejidad sumar(Complejidad otra) { 
        return this.maximo(otra); 
    }
    
    @Override 
    public Complejidad multiplicar(Complejidad otra) {
        if (otra instanceof ComplejidadPotencia) {
            return new ComplejidadPotencia(this.exponente + ((ComplejidadPotencia)otra).exponente);
        }
        return this;
    }
    
    @Override 
    public Complejidad maximo(Complejidad otra) {
        if (otra instanceof ComplejidadPotencia) {
            int e2 = ((ComplejidadPotencia)otra).exponente;
            return new ComplejidadPotencia(Math.max(this.exponente, e2));
        }
        return this;
    }
    
    @Override 
    public String toBigO() { 
        return "O(n^" + exponente + ")"; 
    }
}

