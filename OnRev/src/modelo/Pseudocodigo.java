package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 *
 * @author Giacomo
 */
public class Pseudocodigo {
    private String contenido;
    private boolean valido;
    private List<EstructuraControl> estructuras = new ArrayList<>();
    
    // Patrones 
    private static final Pattern PATRON_ASIGNACION = Pattern.compile(
        "^\\s*([a-zA-Z][\\w\\-]*(?:\\[[^\\]]*\\])?)\\s*←\\s*(.+)\\s*$" // alt+27 = flecha
    );
    
    private static final Pattern PATRON_INICIO = Pattern.compile(
        "^\\s*inicio\\s*\\w*?\\s*$", Pattern.CASE_INSENSITIVE //inicio del pseudo
    );
    
    private static final Pattern PATRON_FIN = Pattern.compile(
        "^\\s*fin\\s*$", Pattern.CASE_INSENSITIVE //fin del pseudo
    );
    
    private static final Pattern PATRON_SINO = Pattern.compile(
        "^\\s*Sino\\s*$", Pattern.CASE_INSENSITIVE
    );

    public Pseudocodigo(String texto) {
        this.contenido = texto;
        this.valido = validarSintaxis();
        
        if (valido) {
            extraerEstructuras();
        }
    }

    //*************************** Métodos ******************************
    public boolean validarSintaxis() {
        String[] lineas = contenido.split("\\r?\\n");
        if (lineas.length < 2) return false;

        // Validar inicio y fin
        if (!PATRON_INICIO.matcher(lineas[0].trim()).matches() || 
            !PATRON_FIN.matcher(lineas[lineas.length - 1].trim()).matches()) {
            return false;
        }

        Stack<String> pilaEstructuras = new Stack<>();
        for (int i = 1; i < lineas.length - 1; i++) {
            String linea = lineas[i].trim();
            
            if (linea.startsWith("Si")) {
                pilaEstructuras.push("SI");
            } 
            else if (PATRON_SINO.matcher(linea).matches()) {
                if (pilaEstructuras.isEmpty() || !"SI".equals(pilaEstructuras.peek())) 
                    return false;
            }
            else if (linea.startsWith("Para")) {
                pilaEstructuras.push("PARA");
            } 
            else if (linea.startsWith("Mientras")) {
                pilaEstructuras.push("MIENTRAS");
            } 
            else if (linea.startsWith("fSi")) {
                if (pilaEstructuras.isEmpty() || !"SI".equals(pilaEstructuras.pop())) 
                    return false;
            } 
            else if (linea.startsWith("fPara")) {
                if (pilaEstructuras.isEmpty() || !"PARA".equals(pilaEstructuras.pop())) 
                    return false;
            } 
            else if (linea.startsWith("fMientras")) {
                if (pilaEstructuras.isEmpty() || !"MIENTRAS".equals(pilaEstructuras.pop())) 
                    return false;
            }
            // Validar asignaciones
            else if (!linea.isEmpty() && !PATRON_ASIGNACION.matcher(linea).matches()) {
                return false;
            }
        }
        return pilaEstructuras.isEmpty();
    }

    private void extraerEstructuras() {
        String[] lineas = contenido.split("\\r?\\n");
        Stack<EstructuraControl> pila = new Stack<>();
        int nivel = 0;
        StringBuilder cuerpoActual = new StringBuilder();
        boolean enSino = false;

        for (int i = 1; i < lineas.length - 1; i++) {
            String linea = lineas[i].trim();
            
            if (linea.startsWith("Si") || linea.startsWith("Para") || 
                linea.startsWith("Mientras")) {
                
                EstructuraControl ec = new EstructuraControl(linea, nivel);
                pila.push(ec);
                nivel++;
                estructuras.add(ec);
                
            }else if (PATRON_SINO.matcher(linea).matches()) {
                if (!pila.isEmpty()) {
                    EstructuraControl ec = pila.peek();
                    ec.setCuerpo(cuerpoActual.toString());
                    cuerpoActual.setLength(0);
                    enSino = true;
                }
                
            }else if (linea.startsWith("f")) {
                if (!pila.isEmpty()) {
                    EstructuraControl ec = pila.pop();
                    if (enSino) {
                        // Guardar cuerpo Sino
                        enSino = false;
                    } else {
                        ec.setCuerpo(cuerpoActual.toString());
                    }
                    cuerpoActual.setLength(0);
                    nivel--;
                }
                
            } else if (!pila.isEmpty()) {
                cuerpoActual.append(linea).append("\n");
                
            }
            
            
        }
        
        
        
    }

   
    
    //*************************** Get y Set ******************************
    
    
    public List<EstructuraControl> getEstructuras() {
        return estructuras;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isValido() {
        return valido;
    }
        
    public boolean esValido() {
        return valido;
    }
    
    
    
}
