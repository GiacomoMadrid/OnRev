/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Luis
 */
public class Polinomio {
    private final TreeMap<Integer, Integer> terminos = new TreeMap<>(Collections.reverseOrder());

    public void addTerm(int coef, int exp) {
        terminos.merge(exp, coef, Integer::sum);
    }

    /** Suma otro polinomio */
    public void sumar(Polinomio otro) {
        otro.terminos.forEach((exp, coef) -> addTerm(coef, exp));
    }

    /** Multiplica por una constante k */
    public void multiplicar(int k) {
        terminos.replaceAll((exp, coef) -> coef * k);
    }

    /** Multiplica por n (desplaza todos los exponentes +1) */
    public void multiplicarVariable() {
        TreeMap<Integer, Integer> nuevo = new TreeMap<>(Collections.reverseOrder());
        terminos.forEach((exp, coef) -> nuevo.put(exp + 1, coef));
        terminos.clear();
        terminos.putAll(nuevo);
    }

    @Override
    public String toString() {
        if (terminos.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> e : terminos.entrySet()) {
            int exp = e.getKey(), coef = e.getValue();
            if (coef == 0) continue;
            if (sb.length() > 0) sb.append(" + ");
            if (exp == 0) sb.append(coef);
            else {
                if (coef != 1) sb.append(coef);
                sb.append("n");
                if (exp > 1) sb.append("^").append(exp);
            }
        }
        return sb.toString();
    }
    
    public double evaluar(double n) {
        double resultado = 0;
        for (Map.Entry<Integer, Integer> termino : terminos.entrySet()) {
            int exponente = termino.getKey();
            int coeficiente = termino.getValue();
            resultado += coeficiente * Math.pow(n, exponente);
        }
        return resultado;
    }
    
}

