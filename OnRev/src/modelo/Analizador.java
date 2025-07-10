/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Giacomo
 */
public class Analizador {
    private Pseudocodigo pseudo;
    private String complejidadAsintotica;
    private String funcionTiempo;
    
    //************************** Cosntructor **************************************
    
    public Analizador(Pseudocodigo pseudo) {
        this.pseudo = pseudo;
        this.complejidadAsintotica = "";
    
    }

    //************************** Métodos **************************************
    
    public String calcularComplejidad() {
        complejidadAsintotica = "";
        if (!pseudo.esValido()) {
            return "Pseudocódigo inválido";
        }
        
        List<EstructuraControl> estructuras = pseudo.getEstructuras();
        int maxAnidamiento = 0;
        int indiceN = 0;
        String variablePrincipal = "n";
        boolean tieneBucles = false;

        // Calcular máxima profundidad de anidamiento
        for (EstructuraControl ec : estructuras) {
            if (ec.getNivelAnidamiento() > maxAnidamiento) {
                maxAnidamiento = ec.getNivelAnidamiento();
                System.out.println("ec Anidamiento: " + ec.getNivelAnidamiento());
                System.out.println("Max anidamiento: " + maxAnidamiento);
            }
            
            if ("PARA".equals(ec.getTipo()) || "MIENTRAS".equals(ec.getTipo())) {
                tieneBucles = true;
                
                if(ec.getNivelAnidamiento() == 0){ //Las erstructuras Para y Mientras son O(n)
                    indiceN++;
                
                }
            }            
            
            
        }

        // Determinar complejidad
        if (!tieneBucles) {
            complejidadAsintotica = "O(1)";
            
        } else if (maxAnidamiento <= 1 && indiceN == 0) {
            complejidadAsintotica = "O(" + variablePrincipal + ")"; // O(n)
            
        } else if ((maxAnidamiento >= 1 || indiceN >= 1)){ // O(n^2); O(n^3)...        
            complejidadAsintotica = "O(" + variablePrincipal + "^" + (maxAnidamiento+indiceN) +")";
            
            if((maxAnidamiento + indiceN) == 1){
                complejidadAsintotica = "O(" + variablePrincipal + ")"; // O(n)
            }                    
        }
        
        System.out.println("Indice: "+ indiceN);
        System.out.println("--------------------------------");
        indiceN = 0;
        return complejidadAsintotica;
        
    }

    public String generarFuncionTiempo() {
        if (!pseudo.esValido()) return "Pseudocódigo inválido";
        
        List<EstructuraControl> estructuras = pseudo.getEstructuras();
        Map<String, Integer> variables = new HashMap<>();
        int tiempoTotal = 0;
        Stack<Integer> multiplicadores = new Stack<>();
        multiplicadores.push(1);  // Multiplicador inicial
        
        // Analizar cada estructura
        for (EstructuraControl ec : estructuras) {
            int costoEstructura = 0;
            int iteraciones = 1;
            
            switch (ec.getTipo()) {
                case "PARA":
                    // Costo base: inicialización (1) + comparación (1) + incremento (1)
                    costoEstructura = 3;
                    iteraciones = ec.calcularIteraciones();
                    
                    // Registrar variable
                    variables.put(ec.getVariableControl(), iteraciones);
                    multiplicadores.push(multiplicadores.peek() * iteraciones);
                    break;
                    
                case "MIENTRAS":
                    // Costo base: comparación (1) + salto (1)
                    costoEstructura = 2;
                    multiplicadores.push(multiplicadores.peek() * 5);  // Estimación
                    break;
                    
                case "SI":
                    // Solo costo de comparación
                    costoEstructura = 1;
                    break;
            }
            
            // Calcular costo del cuerpo
            if (ec.getCuerpo() != null) {
                String[] lineasCuerpo = ec.getCuerpo().split("\\r?\\n");
                for (String linea : lineasCuerpo) {
                    if (!linea.trim().isEmpty()) {
                        costoEstructura += analizarLinea(linea.trim());
                    }
                }
            }
            
            tiempoTotal += multiplicadores.peek() * costoEstructura;
            
            // Finalizar estructura (excepto SI que no afecta multiplicador)
            if (!"SI".equals(ec.getTipo())) {
                multiplicadores.pop();
            }
        }
        
        // Calcular líneas principales fuera de estructuras
        String[] lineas = pseudo.getContenido().split("\\r?\\n");
        for (int i = 1; i < lineas.length - 1; i++) {
            String linea = lineas[i].trim();
            if (!linea.isEmpty() && !esPalabraClave(linea)) {
                tiempoTotal += analizarLinea(linea);
            }
        }
        
        funcionTiempo = "T(n) = " + tiempoTotal;
        return funcionTiempo;
    }
    
    private int analizarLinea(String linea) {
        int costo = 0;
        
        // Contar asignaciones
        if (linea.contains("←")) { // alt + 27 = flecha de asignación
            costo++;
        }
        
        // Contar operadores
        Pattern patronOperadores = Pattern.compile("[+\\-*/%=<>]");
        Matcher matcher = patronOperadores.matcher(linea);
        while (matcher.find()) costo++;
        
        return costo;
    }
    
    private boolean esPalabraClave(String linea) {
        return linea.startsWith("Si") || 
               linea.startsWith("Para") || 
               linea.startsWith("Mientras") || 
               linea.startsWith("fSi") || 
               linea.startsWith("fPara") || 
               linea.startsWith("fMientras") || 
               linea.startsWith("Sino");
    }

    //************************** Get y Set **************************************
    
    public Pseudocodigo getPseudo() {
        return pseudo;
    }

    public String getComplejidadAsintotica() {
        return complejidadAsintotica;
    }

    public String getFuncionTiempo() {
        return funcionTiempo;
    }
    
    
    
}
