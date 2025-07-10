package modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Giacomo
 */
public class EstructuraControl {
    private String tipo;
    private String condicion;
    private String variableControl;
    private String variableFin = "_";
    private int inicio;
    private int fin;
    private int nivelAnidamiento;
    private String cuerpo;
    
    // Patrones mejorados
    private static final Pattern PATRON_PARA = Pattern.compile(
        "Para\\s+(\\w+)\\s+desde\\s+(\\d+)\\s+hasta\\s+(\\w+)\\s+hacer"
    );
    
    private static final Pattern PATRON_MIENTRAS = Pattern.compile(
        "Mientras\\s*\\((.+)\\)\\s+hacer"
    );
    
    private static final Pattern PATRON_SI = Pattern.compile(
        "Si\\s*\\((.+)\\)\\s+entonces"
    );
    
    private static final Pattern PATRON_SINO = Pattern.compile(
        "^\\s*Sino\\s*(//.*)?$"
    );


    public EstructuraControl(String linea, int nivel) {
        this.nivelAnidamiento = nivel;
        this.tipo = identificarTipo(linea);
        extraerParametros(linea);
    }

    private String identificarTipo(String linea) {
        if (linea.trim().startsWith("Para")) {
            return "PARA";
        }
        
        if (linea.trim().startsWith("Mientras")) {
            return "MIENTRAS";
        }
        
        if (linea.trim().startsWith("Sino")) {
            return "SINO";
        }
        
        if (linea.trim().startsWith("Si")) {
            return "SI";
        }
        
        
        return "OTRO";
    }

    private void extraerParametros(String linea) {
        Matcher matcher;
        switch (this.tipo) {
            case "PARA":
                matcher = PATRON_PARA.matcher(linea);
                if (matcher.find()) {
                    this.variableControl = matcher.group(1);
                    this.inicio = Integer.parseInt(matcher.group(2));
                    
                    if(matcher.group(3).matches("\\d+")){ 
                        this.fin =Integer.parseInt(matcher.group(3));
                        
                    }else{ 
                       this.fin = -1; // -1 = variable
                       
                    }
                }
                break;
                
            case "MIENTRAS":
                matcher = PATRON_MIENTRAS.matcher(linea);
                if (matcher.find()) {
                    this.condicion = matcher.group(1);
                }
                
                break;
                
            case "SI":
                matcher = PATRON_SI.matcher(linea);
                if (matcher.find()) {
                    this.condicion = matcher.group(1);
                }
                break;
                
            case "SINO":
                matcher = PATRON_SINO.matcher(linea);
                if (matcher.find()) {
                    this.condicion = matcher.group(1);
                }
                break;
        }
    }

    public int calcularIteraciones() {
        if ("PARA".equals(tipo)) {
            if (fin == -1) {
                variableFin = ""+fin;
                return 1;
            }
            
            return fin - inicio + 1;
        }
        return 1;
    }
    
    public int complejidadCondicion() {
        if (condicion == null) {
            return 1;
        }
        return condicion.split("&&|\\|\\|").length;
    }
    
    // -------------------------- Getters y setters ----------------------------
    public String getTipo() { 
        return tipo; 
    }
    
    public String getCondicion() { 
        return condicion; 
    }
    
    public String getVariableControl() { 
        return variableControl; 
    }
    
    public int getInicio() { 
        return inicio; 
    }
    public int getFin() { 
        return fin; 
    }
    
    public int getNivelAnidamiento() { 
        return nivelAnidamiento; 
    }
    
    public void setCuerpo(String cuerpo) { 
        this.cuerpo = cuerpo; 
    }
    
    public String getCuerpo() { 
        return cuerpo; 
    }
    
    public String getVariableFin(){
        return variableFin;
    }

}