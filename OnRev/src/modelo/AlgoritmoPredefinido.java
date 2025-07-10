/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Luis
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Giacomo
 */
public class AlgoritmoPredefinido extends Pseudocodigo {
    private final String nombre = "BubbleSort";
    private Map<Integer, Long> tiemposReales = new HashMap<>();

    public AlgoritmoPredefinido(String texto) {
        super(texto); 
    }
    
    public String getComplejidadTeorica() {
        return "O(n²)";
    }

    public long ejecutarConEntrada(int n) {
        int[] arreglo = generarArregloAleatorio(n);
        long inicio = System.nanoTime();
        bubbleSort(arreglo);
        long duracion = System.nanoTime() - inicio;
        tiemposReales.put(n, duracion);
        return duracion;
    }

    private int[] generarArregloAleatorio(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1000);
        return arr;
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int aux = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = aux;
                }
    }
}

