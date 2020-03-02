/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compararmatrices;

import java.io.IOException;

import javax.swing.JOptionPane;

/**
 *
 * @author ubuntusb
 */
public class CompararMatrices {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        
        
        
        
        String direccion1 = JOptionPane.showInputDialog(null, "Ingrese ruta de la primera matriz", null);
        
        //  /home/ubuntusb/Documentos/matriz1.txt
        Matriz a = Matriz.crearMatriz(direccion1);
        System.out.println(a);
        String direccion2="";        
        do {
            try {
                direccion2 = JOptionPane.showInputDialog(null, "Ingrese ruta de la siguiente matriz", null);
                //  /home/ubuntusb/Documentos/matriz2.txt
                Matriz b = Matriz.crearMatriz(direccion2);
                System.out.println(b);
                JOptionPane.showMessageDialog(null, "Porcentage de cambio: "+a.calcularPorcentajeCambio(b));  
                a=b;                       
                System.out.println(b);  
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hasta la proxima!");
            }
 
                      
            
        } while (direccion2 != null);
        
        
        
        
        
        
        //String direccion = JOptionPane.showInputDialog(null, "Ingrese ruta de la segunda matriz", null);
    }
    
}
