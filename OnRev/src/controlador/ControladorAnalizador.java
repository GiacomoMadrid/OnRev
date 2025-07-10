package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Analizador;
import modelo.FuncionTiempo;
import modelo.Graficador;
import modelo.Pseudocodigo;
import vista.frmAnalizador;

/**
 *
 * @author Giacomo
 */
public class ControladorAnalizador {
    protected frmAnalizador vista;    
    private ControladorPrincipal contPrincipal;
    public Analizador analizador;
    public Pseudocodigo pseudo;
    public FuncionTiempo funcionTiempo;
            
    public ControladorAnalizador(frmAnalizador vista, ControladorPrincipal cont){
        this.vista = vista;
        this.contPrincipal = cont;
        
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
                limpiarComponentes();
            }
        });
        
        this.vista.btnVerificar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                limpiarLabels();
                analizarPseudocodigo();
                
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
        graficador.dibujar(vista.panCuerpoGrafica, funcionTiempo);
    }
    
}