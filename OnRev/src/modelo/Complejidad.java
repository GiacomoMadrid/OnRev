package modelo;

/**
 *
 * @author Giacomo
 */
public abstract class Complejidad {
    public abstract Complejidad sumar(Complejidad otra);
    public abstract Complejidad multiplicar(Complejidad otra);
    public abstract Complejidad maximo(Complejidad otra);
    public abstract String toBigO();

    public static final Complejidad CTE = new ComplejidadConstante(1);
    public static final Complejidad N = new ComplejidadVariable();
    public static final Complejidad LOG_N = new ComplejidadLogaritmica();
    public static final Complejidad N_LOG_N = new ComplejidadNLogN();
}
