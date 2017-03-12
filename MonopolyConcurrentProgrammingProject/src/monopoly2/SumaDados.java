package monopoly2;

/**
 *
 * @author Diego
 */
public class SumaDados extends Thread {

    Dado dado1, dado2;
    public int r;
    Jugador jug;

    public SumaDados(Jugador jug, Dado dado1, Dado dado2) {
        this.dado1 = dado1;
        this.dado2 = dado2;
        this.jug = jug;
    }

    public void run() {
        try {
            this.sleep(7000);
            if (dado1.na == dado2.na) {
                javax.swing.JOptionPane.showMessageDialog(null, "Puede tirar doble");
                jug.contDobles++;
                if (jug.contDobles == 3) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Se ha tiro doble 3 veces, carcel!!");
                    jug.contDobles = 0;
                    jug.pos = 10;

                    
                    jug.IJ.SetText("Nombre: " + jug.Nombre + "\n"
                            + "Dinero: " + jug.dinero + "\n"
                            + "Posicion: " + jug.pos + "\n"
                            + "Ficha Asignada: " + jug.f.id + "\n"
                            + "Propiedades : \n" + jug.propiedades);
                    jug.f.setLocation(20, 560);
                    
                    if (jug.id == Tablero.j.length) {
                        Tablero.j[0].boton.setVisible(true);
                    } else {
                        Tablero.j[jug.id].boton.setVisible(true);
                    }
                } else {
                    
                    jug.boton.setVisible(true);
                }
            } else {
                jug.contDobles = 0;
                if (jug.id == Tablero.j.length) {
                    Tablero.j[0].boton.setVisible(true);
                } else {
                    Tablero.j[jug.id].boton.setVisible(true);
                }
            }

            r = dado1.na + dado2.na;
            jug.f.moverFicha(r);
        } catch (Exception e) {
        }
    }
}
