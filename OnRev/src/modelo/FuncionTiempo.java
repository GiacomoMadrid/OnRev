package modelo;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Giacomo
 */
public class FuncionTiempo {
    private final Pseudocodigo pseudo;
    private Polinomio total;
    
    public FuncionTiempo(Pseudocodigo pseudo) {
        this.pseudo = pseudo;
    }

    /** Devuelve la función T(n) como String (polinomio) */
    public String calcular() {
        if (!pseudo.esValido())
            throw new IllegalStateException("Pseudocódigo inválido");

        total = analizarBloque(pseudo.getEstructuras(), 0);
        return "T(n) = " + total.toString();
    }

    /** Versión recursiva muy parecida a Analizador. */
    private Polinomio analizarBloque(List<EstructuraControl> ecs, int nivel) {
        Polinomio suma = new Polinomio();
        // constante por operaciones básicas de cada línea fuera de bucles
        Polinomio cte = new Polinomio();
        cte.addTerm(1, 0);

        for (EstructuraControl ec : ecs) {
            if (ec.getNivelAnidamiento() != nivel) continue;

            Polinomio cuerpo = new Polinomio();
            cuerpo.addTerm(1, 0); // costo mínimo línea cabecera

            // sub‑estructuras
            List<EstructuraControl> sub = ecs.stream()
                    .filter(e -> e.getNivelAnidamiento() == nivel + 1)
                    .collect(Collectors.toList());
            if (!sub.isEmpty())
                cuerpo = analizarBloque(ecs, nivel + 1);

            switch (ec.getTipo()) {
                case "PARA":
                    int k = ec.calcularIteraciones();
                    if (k == 1) {
                        // cuenta como n: multiplicamos por variable
                        cuerpo.multiplicarVariable();
                    } else {
                        cuerpo.multiplicar(k);  // constante conocida
                    }
                    break;

                case "MIENTRAS":
                    // asumir O(n) iteraciones
                    cuerpo.multiplicarVariable();
                    break;

                case "SI":
                    // tomamos la rama más costosa (aproximación): cuerpo tal cual
                    break;
            }
            suma.sumar(cuerpo);
            // +1 por la cabecera (comparaciones, etc.)
            suma.sumar(cte);
        }
        return suma;
    }

    
    public Pseudocodigo getPseudo() {
        return pseudo;
    }
    
    public Polinomio getPolinomio(){
        return total;
    }
    
    
}
