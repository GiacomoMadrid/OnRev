package modelo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Giacomo
 */
public class Complex {
    private static Map<String, Double> peso;
    private static Map<Double, String> valor;

    public Complex(){
        peso = new HashMap<>();
        valor = new HashMap<>();
        
        peso.put("O(1)", 1.0); 
        peso.put("O(Log(n))", 1.5);
        peso.put("O(n)", 2.0);
        peso.put("O(nLog(n))", 3.5);
        peso.put("O(n^2)", 4.0);
        peso.put("O(n^3)", 6.0);
        peso.put("O(n^4)", 8.0);
        peso.put("O(n^5)", 10.0);
        peso.put("O(n^6)", 12.0);
        peso.put("O(n^7)", 14.0);
        peso.put("O(n^8)", 16.0);
        peso.put("O(n^9)", 18.0);
        peso.put("O(n^10)", 20.0);

        valor.put(1.0,"O(1)");
        valor.put(1.5,"O(Log(n))");
        valor.put(2.0,"O(n)");
        valor.put(3.5,"O(nLog(n))");
        valor.put(4.0,"O(n^2)");
        valor.put(6.0,"O(n^3)");
        valor.put(8.0,"O(n^4)");
        valor.put(10.0,"O(n^5)");
        valor.put(12.0,"O(n^6)");
        valor.put(14.0,"O(n^7)");
        valor.put(16.0,"O(n^8)");
        valor.put(18.0,"O(n^9)");
        valor.put(20.0,"O(n^10)");


    }
    
    public static String mostrarMayor(String o1, String o2){        
        Double mayor = Math.max(peso.get(o1), peso.get(o2));       
        return valor.get(mayor);
    }



}

