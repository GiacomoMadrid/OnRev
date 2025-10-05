/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.AlgoritmoPredefinido;
import modelo.Analizador;
import modelo.FuncionTiempo;
import modelo.Graficador;
import modelo.Polinomio;
import modelo.Pseudocodigo;
import vista.frmComparador;

/**
 *
 * @author Giacomo
 */
public class ControladorComparador {
    protected frmComparador vista;    
    private ControladorPrincipal contPrincipal;
    public Analizador analizador;
    public Pseudocodigo pseudo;
    public AlgoritmoPredefinido algo;
    public FuncionTiempo funcionTiempo;   
    
    
    
    public ControladorComparador(frmComparador vista, ControladorPrincipal contPrincipal){
        this.vista = vista;
        this.contPrincipal = contPrincipal;
        
        this.vista.btnRegresar.setBackground(Color.white);
        this.vista.btnLimpiar.setBackground(Color.white);
        
        //******************** Barra de programa ***************************************
        
        this.vista.btnRegresar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                contPrincipal.iniciar();
                vista.dispose();
            }
        });
        
        this.vista.btnExportar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // Convertir a pdf
            }
        });
        
        //************************** Botones ********************************** 
        
        this.vista.btnLimpiar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                limpiarLabels();
                limpiarComponentes();
            }
        });
        
        this.vista.btnComparar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                analizarPseudocodigo();
                Graficador graf = new Graficador();
                graf.setEscala(15);
                graf.dibujarM(vista.panGraficaComplejidad);
                graf.dibujarP(vista.panGraficaTiempo);
                vista.lblComplejidad.setText("O(n)");
                vista.lblTiempo.setText("2n^2-2n");
                    
            }
        });
    }
    
    public static Polinomio parsePolinomio(String str) {
    Polinomio polinomio = new Polinomio();

    // Normaliza el string para asegurar signos explícitos
    str = str.replaceAll("-", "+-"); // "2n^2+-2n"
    str = str.replaceAll("\\s+", ""); // Elimina espacios
    if (str.startsWith("+")) str = str.substring(1); // Quita el "+" inicial

    // Divide los términos
    String[] terminos = str.split("\\+");

    for (String termino : terminos) {
        if (termino.isEmpty()) continue;

        int coef = 0, exp = 0;

        if (termino.contains("n")) {
            String[] partes = termino.split("n");
            // Coeficiente
            if (partes[0].equals("") || partes[0].equals("+")) coef = 1;
            else if (partes[0].equals("-")) coef = -1;
            else coef = Integer.parseInt(partes[0]);

            // Exponente
            if (termino.contains("^")) {
                exp = Integer.parseInt(termino.split("\\^")[1]);
            } else {
                exp = 1;
            }
        } else {
            // Término constante
            coef = Integer.parseInt(termino);
            exp = 0;
        }

        polinomio.addTerm(coef, exp);
    }

    return polinomio;
}
    
    
    public void iniciar(){
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
        limpiarComponentes();
    }
    
    public void limpiarComponentes(){
        this.vista.txtPseudocodigo.setText("");
        limpiarLabels();
    }
    
    public void limpiarLabels(){
        this.vista.lblComplejidad.setText(" ");
        this.vista.lblTiempo.setText(" ");
    }

    private void analizarPseudocodigo() {
        String codigo = vista.txtPseudocodigo.getText();
        pseudo = new Pseudocodigo(codigo);
        
        if (!pseudo.esValido()) {
            JOptionPane.showMessageDialog(null, "Error de sintaxis en el pseudocódigo");
            return;
        }

        analizador = new Analizador(pseudo);
        String complejidad = analizador.calcularComplejidad();
        funcionTiempo = new FuncionTiempo(pseudo);
        String ft = funcionTiempo.calcular();

        vista.lblComplejidadNuevo.setText(complejidad);
        vista.lblTiempoNuevo.setText(ft);
        
        Graficador graficador = new Graficador();
        graficador.setEscala(15); // Ajustar zoom
        graficador.dibujar(vista.panGraficaTiempo, funcionTiempo);
    }

}


