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
                analizador = new Analizador(algo);
            }
        });
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

        vista.lblComplejidad.setText(complejidad);
        vista.lblTiempo.setText(ft);
        
        Graficador graficador = new Graficador();
        graficador.setEscala(15); // Ajustar zoom
        graficador.dibujar(vista.panGraficaTiempo, funcionTiempo);
    }

}


