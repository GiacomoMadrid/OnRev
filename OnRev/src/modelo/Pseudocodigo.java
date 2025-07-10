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

        // 1) Verificar 'inicio' y 'fin'
        if (!PATRON_INICIO.matcher(lineas[0].trim()).matches() ||
            !PATRON_FIN.matcher(lineas[lineas.length - 1].trim()).matches()) {
            return false;
        }

        Stack<String> pila = new Stack<>();
        for (int i = 1; i < lineas.length - 1; i++) {
            String linea = lineas[i].trim();
            if (linea.isEmpty()) continue;

            // Extraer solo la primera palabra clave
            String clave = linea.split("\\s+")[0];

            if (clave.equalsIgnoreCase("Si")) {
                pila.push("SI");

            } else if (clave.equalsIgnoreCase("Para")) {
                pila.push("PARA");

            } else if (clave.equalsIgnoreCase("Mientras")) {
                pila.push("MIENTRAS");

            } else if (clave.equalsIgnoreCase("Sino")) {
                // 1) Sintaxis exacta
                if (!PATRON_SINO.matcher(linea).matches()) {
                    return false;
                }
                // 2) Debe haber un SI abierto
                if (pila.isEmpty() || !"SI".equals(pila.peek())) {
                    return false;
                }
                // NO pop aquí; 'fSi' lo hará

            } else if (clave.equalsIgnoreCase("fSi")) {
                if (pila.isEmpty() || !"SI".equals(pila.pop())) {
                    return false;
                }

            } else if (clave.equalsIgnoreCase("fPara")) {
                if (pila.isEmpty() || !"PARA".equals(pila.pop())) {
                    return false;
                }

            } else if (clave.equalsIgnoreCase("fMientras")) {
                if (pila.isEmpty() || !"MIENTRAS".equals(pila.pop())) {
                    return false;
                }

            } else {
                // Asignación u otra línea
                if (!PATRON_ASIGNACION.matcher(linea).matches()) {
                    return false;
                }
            }
        }
        
        return pila.isEmpty();
    }

    private List<EstructuraControl> extraerEstructuras() {
        List<EstructuraControl> listaEstructuras = new ArrayList<>();
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
                    nivel--;
                    
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
        
        return listaEstructuras;
        
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