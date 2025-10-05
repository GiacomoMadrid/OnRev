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
        int contSino = 0;
        
        // Calcular máxima profundidad de anidamiento
        for (EstructuraControl ec : estructuras) {
            if (ec.getNivelAnidamiento() > maxAnidamiento) {
                maxAnidamiento = ec.getNivelAnidamiento();
            }
            
            if ("PARA".equals(ec.getTipo()) || "MIENTRAS".equals(ec.getTipo())) {
                tieneBucles = true;
                
                if(ec.getNivelAnidamiento() == 0){ //Las erstructuras Para y Mientras son O(n)
                    indiceN++;
                
                }
            }     
            
            if("SINO".equals(ec.getTipo())){
                contSino++;
            }
                        
        }
        
        int value = 0;
        // Determinar complejidad
        if (!tieneBucles) {
            complejidadAsintotica = "O(1)";
            
        } else if (maxAnidamiento <= 1 && indiceN == 0) {
            complejidadAsintotica = "O(" + variablePrincipal + ")"; // O(n)
            
        } else if ((maxAnidamiento >= 1 || indiceN >= 1)){ // O(n^2); O(n^3)...     
            value = (maxAnidamiento+indiceN);            
            complejidadAsintotica = "O(" + variablePrincipal + "^" + value +")";
            
            if((maxAnidamiento + indiceN) == 1){
                complejidadAsintotica = "O(" + variablePrincipal + ")"; // O(n)
            }                    
        }
        
        
        
        System.out.println("Indice: "+ indiceN);
        System.out.println("--------------------------------");
        indiceN = 0;
        return complejidadAsintotica;
        
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