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
public class AlgoritmoPredefinido extends Pseudocodigo{
    private String nombre;
    private Map<Integer, Long> tiemposReales;
    
    public AlgoritmoPredefinido(String nombre, String texto) {
        super(texto);
        this.nombre = nombre;
    }
    
    // ******************************* Métodos *********************************
    
    public Long ejecutarConEntrada(int n){
        int[] arreglo = generarArregloAleatorio(n);

        long inicio = System.nanoTime();

        // El algoritmo se ejecutará segun el nombre
        if (this.nombre.equalsIgnoreCase("BubbleSort")) {
            bubbleSort(arreglo);
        } else if (this.nombre.equalsIgnoreCase("QuickSort")) {
            quickSort(arreglo, 0, arreglo.length - 1);
        }

        long fin = System.nanoTime();
        long duracion = fin - inicio;

        if (tiemposReales == null) {
            tiemposReales = new HashMap<>();
        }

        tiemposReales.put(n, duracion);
        return duracion;
    }
    
    private int[] generarArregloAleatorio(int n) {
        Random rand = new Random();
        int[] arreglo = new int[n];
        for (int i = 0; i < n; i++) {
            arreglo[i] = rand.nextInt(1000);
        }
        return arreglo;
    }
    
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int aux = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = aux;
                }
            }
        }
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = particion(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int particion(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }    
}
