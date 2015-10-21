/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly2;

import java.awt.event.MouseEvent;

/**
 *
 * @author Diego
 */
public class Boton extends javax.swing.JButton {

    Jugador j;
    Imagen d1, d2;
    public Boton(Jugador j, Imagen d1, Imagen d2, int x, int y) {

        this.j=j;
        this.d1=d1;
        this.d2=d2;
        this.setText(j.Nombre);
        this.setSize(100,50);
        this.setLocation(x, y);
        java.awt.event.MouseListener evento;
        evento = new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click();

            }

            @Override
            public void mousePressed(MouseEvent e) {
                MouseExited();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MouseOver();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MouseLeaving();
            }
        };

        this.addMouseListener(evento);
        this.setVisible(true);
    }

    public void click() {
        j.tirarDados(d1,d2);
        this.setVisible(false);
    }
    
    public void MouseOver(){
        this.setText("Tirar Dados");
    }
    
    public void MouseLeaving(){
        this.setText(j.Nombre);
    }
    
    public void MouseExited(){
        this.disable();
    }
}
