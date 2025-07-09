/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.frmComparador;

/**
 *
 * @author Giacomo
 */
public class ControladorComparador {
    protected frmComparador vista;    
    private ControladorPrincipal contPrincipal;
    
    
    
    
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
}
