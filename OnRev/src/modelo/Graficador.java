package modelo;

import java.awt.*;
import javax.swing.*;
import modelo.FuncionTiempo;
import modelo.Polinomio;

public class Graficador {
    private int escala = 10; // Pixeles por unidad
    
    public void setEscala(int escala) {
        this.escala = escala;
    }
    
    public void dibujar(JPanel pan, FuncionTiempo funcion) {
        if (funcion == null) return;
        
        Graphics2D g = (Graphics2D) pan.getGraphics();
        int width = pan.getWidth();
        int height = pan.getHeight();
        int centroX = width / 2;
        int centroY = height / 2;
        
        // Limpiar el panel
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // Dibujar ejes coordenados
        g.setColor(Color.BLACK);
        g.drawLine(0, centroY, width, centroY);  // Eje X
        g.drawLine(centroX, 0, centroX, height); // Eje Y
        
        // Etiquetas de ejes
        g.drawString("n", width - 10, centroY - 5);
        g.drawString("T(n)", centroX + 5, 10);
        
        // Obtener polinomio
        Polinomio polinomio = funcion.getPolinomio();
        if (polinomio == null) return;
        
        // Dibujar función
        g.setColor(Color.BLUE);
        int prevX = centroX;
        int prevY = centroY - (int)(polinomio.evaluar(0) * escala);
        
        // Calcular n máximo que cabe en el panel
        int nMax = (width - centroX) / escala;
        
        for (int n = 1; n <= nMax; n++) {
            int x = centroX + n * escala;
            double valor = polinomio.evaluar(n);
            int y = centroY - (int)(valor * escala);
            
            // Dibujar línea entre puntos consecutivos
            g.drawLine(prevX, prevY, x, y);
            
            // Dibujar punto en cada valor
            dibujarPunto(pan, n, valor);
            
            prevX = x;
            prevY = y;
        }
        
        // Dibujar punto en n=0
        dibujarPunto(pan, 0, polinomio.evaluar(0));
    }
    
    public void dibujarPunto(JPanel pan, double x, double y) {
        Graphics2D g = (Graphics2D) pan.getGraphics();
        int centroX = pan.getWidth() / 2;
        int centroY = pan.getHeight() / 2;
        
        int xDigital = centroX + (int)(x * escala);
        int yDigital = centroY - (int)(y * escala);
        
        g.setColor(Color.RED);
        g.fillOval(xDigital - 3, yDigital - 3, 6, 6);
    }
    
    public void dibujar(JPanel pan, Polinomio polinomio) {        
        Graphics2D g = (Graphics2D) pan.getGraphics();
        int width = pan.getWidth();
        int height = pan.getHeight();
        int centroX = width / 2;
        int centroY = height / 2;
        
        // Limpiar el panel
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // Dibujar ejes coordenados
        g.setColor(Color.BLACK);
        g.drawLine(0, centroY, width, centroY);  // Eje X
        g.drawLine(centroX, 0, centroX, height); // Eje Y
        
        // Etiquetas de ejes
        g.drawString("n", width - 10, centroY - 5);
        g.drawString("T(n)", centroX + 5, 10);
        
        // Obtener polinomioS
        if (polinomio == null) return;
        
        // Dibujar función
        g.setColor(Color.BLUE);
        int prevX = centroX;
        int prevY = centroY - (int)(polinomio.evaluar(0) * escala);
        
        // Calcular n máximo que cabe en el panel
        int nMax = (width - centroX) / escala;
        
        for (int n = 1; n <= nMax; n++) {
            int x = centroX + n * escala;
            double valor = polinomio.evaluar(n);
            int y = centroY - (int)(valor * escala);
            
            // Dibujar línea entre puntos consecutivos
            g.drawLine(prevX, prevY, x, y);
            
            // Dibujar punto en cada valor
            dibujarPunto(pan, n, valor);
            
            prevX = x;
            prevY = y;
        }
        
        g.setColor(Color.GREEN);
        for (double x = -10; x <= 10; x += 0.1) {
            double y = 2 * x * x - 2 * x;
            dibujarPunto(pan, x, y);
        }
        
        // Dibujar punto en n=0
        dibujarPunto(pan, 0, polinomio.evaluar(0));
    }
    
    public void dibujarP(JPanel pan) {        
        Graphics2D g = (Graphics2D) pan.getGraphics();
        int width = pan.getWidth();
        int height = pan.getHeight();
        int centroX = width / 2;
        int centroY = height / 2;        
                
        // Etiquetas de ejes
        g.drawString("n", width - 10, centroY - 5);
        g.drawString("T(n)", centroX + 5, 10);
        
               
        
        // Dibujar función
        g.setColor(Color.GREEN);
        int prevX = centroX;
        int prevY = centroY;
        
        // Calcular n máximo que cabe en el panel
        int nMax = (width - centroX) / escala;
        
        for (double x = -10; x <= 10; x += 0.1) {
            double y = 2 * x * x - 2 * x;
            dibujarPunto(pan, x, y);
        }
    }
    
    public void dibujarM(JPanel pan) {         
        Graphics2D g = (Graphics2D) pan.getGraphics();
        int width = pan.getWidth();
        int height = pan.getHeight();
        int centroX = width / 2;
        int centroY = height / 2;
        
        // Limpiar el panel
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // Dibujar ejes coordenados
        g.setColor(Color.BLACK);
        g.drawLine(0, centroY, width, centroY);  // Eje X
        g.drawLine(centroX, 0, centroX, height); // Eje Y
        
        // Etiquetas de ejes
        g.drawString("n", width - 10, centroY - 5);
        g.drawString("O(n)", centroX + 5, 10);
        
        
        // Dibujar función
        g.setColor(Color.GREEN);
        int prevX = centroX;
        int prevY = centroY;
        
        // Calcular n máximo que cabe en el panel
        int nMax = (width - centroX) / escala;
        
        for (double x = -10; x <= 10; x += 0.1) {
            double y = 2 * x * x - 2 * x;
            dibujarPunto(pan, x, y);
        }
        
    }
    
    
}