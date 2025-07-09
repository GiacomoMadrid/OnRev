/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package onrev;

import controlador.ControladorPrincipal;
import vista.frmPrincipal;

/**
 *
 * @author Giacomo
 */
public class OnRev {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorPrincipal contPrincipal = new ControladorPrincipal(new frmPrincipal());
        contPrincipal.iniciar();
    }
    
}
