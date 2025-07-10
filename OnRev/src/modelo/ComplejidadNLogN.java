package modelo;

/**
 *
 * @author Giacomo
 */
public class ComplejidadNLogN extends Complejidad {
    @Override 
    public Complejidad sumar(Complejidad otra) { 
        return this.maximo(otra); 
    }
    
    @Override 
    public Complejidad multiplicar(Complejidad otra) {
        if (otra instanceof ComplejidadVariable) return new ComplejidadPotencia(2).multiplicar(Complejidad.LOG_N);
        if (otra instanceof ComplejidadLogaritmica) return new ComplejidadPotencia(1).multiplicar(new ComplejidadPotencia(1));
        return this;
    }
    @Override 
    public Complejidad maximo(Complejidad otra) {
        if (otra instanceof ComplejidadConstante || otra instanceof ComplejidadVariable || otra instanceof ComplejidadLogaritmica) {
            return this;
        }
        return otra;
    }
    
    @Override 
    public String toBigO() { 
        return "O(n log n)"; 
    }
}