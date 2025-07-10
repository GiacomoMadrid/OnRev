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
}
