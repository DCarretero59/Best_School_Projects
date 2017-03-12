package monopoly2;
/**
 *
 * @author Diego
 */
public class Monopoly2 {
    public static void main(String[] args) {
        int n;
        do{
        n = MovimientoFicha.valid("Cuantos jugadores van a jugar (2-4)");
            if (n<2 || n>4) {
                javax.swing.JOptionPane.showMessageDialog(null, "El juego solo esta designado de 2 a 4 jugadores");
            }
        }while(n<2 || n>4);
        String nombres[] = new String[n];
        for (int i = 0, j=1; i < nombres.length; i++, j++) {
            nombres[i] = javax.swing.JOptionPane.showInputDialog("Deme el nombre de su jugador "+j);
        }
        
        Tablero t = new Tablero(nombres);
    }
}
