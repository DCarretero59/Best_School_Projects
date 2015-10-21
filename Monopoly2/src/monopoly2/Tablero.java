package monopoly2;

/**
 *
 * @author Diego
 */
public class Tablero extends javax.swing.JFrame {

    public static int casillas[] = new int[40];
    public static Jugador j[];
    public static Imagen dado[];
    public static Banco b = new Banco();

    public Tablero(String nombres[]) {
        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = 0;
        }

        this.setSize(1450, 1300);
        this.setLayout(null);
        this.setTitle("Monopoly");
        this.setDefaultCloseOperation(2);
        Jugador j[] = new Jugador[nombres.length];
        Boton tirar[] = new Boton[nombres.length];
        Imagen dado[] = new Imagen[2];
        this.dado = dado;

        //DADOS
        
                this.dado[0] = new Imagen(600, 50, 50, 50, "D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Dado1.jpg");
                this.add(dado[0]);
                this.dado[1] = new Imagen(600, 150, 50, 50, "D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Dado2.jpg");
                this.add(dado[1]);

                

        //JUGADOR, FICHA, Boton
        for (int i = 0, k = 1; i < nombres.length; i++, k++) {
            j[i] = new Jugador(k, nombres[i], dado[0], dado[1]);
        }
        this.j = j;

        for (int i = 0, k = 1; i < nombres.length; i++, k++) {
            this.add(this.j[i].f);
            this.add(this.j[i].boton);
            this.add(this.j[i].IJ);
            if (i>=1) {
                this.j[i].boton.setVisible(false);
            }
        }
        
        //BANCO
        this.add(b);

        //IMAGEN DE FONDO
        Imagen imgcentro = new Imagen(50, 100, 450, 450, "D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Centro.jpg");
        this.add(imgcentro);

        //CASILLAS
        for (int casilla = 1; casilla <= 40; casilla++) {
            Casilla c = new Casilla(casilla);
            this.add(c);
        }
        
        

        //MOSTRAR
        this.setVisible(true);
    }
}
