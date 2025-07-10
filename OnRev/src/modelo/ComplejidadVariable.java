package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadVariable extends Complejidad {
    @Override 
    public Complejidad sumar(Complejidad otra) { 
        return this.maximo(otra); 
    }
    
    @Override 
    public Complejidad multiplicar(Complejidad otra) {     
        if (otra instanceof ComplejidadLogaritmica) {
            return Complejidad.N_LOG_N;
        }
        if (otra instanceof ComplejidadNLogN) {
            return new ComplejidadPotencia(2).multiplicar(Complejidad.LOG_N);
        }
        if (otra instanceof ComplejidadPotencia) {
            return new ComplejidadPotencia(((ComplejidadPotencia) otra).getExponente() + 1);
        }
        return this;
    }
    
    @Override 
    public Complejidad maximo(Complejidad otra) {
        if (otra instanceof ComplejidadConstante) return this;
        if (otra instanceof ComplejidadVariable) return this;
        return otra;
    }
    
    @Override 
    public String toBigO() { 
        return "O(n)"; 
    }
}
