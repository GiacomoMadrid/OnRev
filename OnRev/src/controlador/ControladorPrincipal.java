package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.frmAnalizador;
import vista.frmComparador;
import vista.frmPrincipal;

/**
 *
 * @author Giacomo
 */
public class ControladorPrincipal {
    protected frmPrincipal vista;
    private ControladorAnalizador contAnalizador;
    private ControladorComparador contComparador ;
    
    public ControladorPrincipal(frmPrincipal vista){
        this.vista = vista;
        this.contAnalizador = new ControladorAnalizador(new frmAnalizador(), this);
        this.contComparador  = new ControladorComparador(new frmComparador(), this);
        
     
        this.vista.btnAnalizador.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                contAnalizador.iniciar();
                vista.dispose();
            }
        });

        this.vista.btnComparador.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                contComparador.iniciar();
                vista.dispose();
            }
        });
    }
    
    
    
    public void iniciar(){
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }
}
