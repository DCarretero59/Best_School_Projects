package monopoly2;

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Banco extends javax.swing.JInternalFrame {

    javax.swing.JTextArea Inf = new javax.swing.JTextArea();
    public int dinero = 15000;
    public String propiedades = "";
    public ArrayList Propiedades = new ArrayList();
    Archivo a = new Archivo();

    public Banco() {
        javax.swing.JTextArea Inf = new javax.swing.JTextArea();
        Propiedad p;
        for (int i = 0, j=1; i < 39; i++,j++) {
            if (j == 10 || j == 20 || j == 30) {
                
            }else{
                String[] arr = a.cargarArreglo("D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Casilla" + j + ".txt");
                String nombre = a.extraerValor("Nombre", arr);
                p = new Propiedad(nombre, j);
                Propiedades.add(p);
                
            }

        }
        
        for (int i = 0; i < Propiedades.size(); i++) {
            propiedades = propiedades + ((Propiedad) Propiedades.get(i)).getNombre() +" (Posicion: "+((Propiedad) Propiedades.get(i)).getPosicion()+""
                        + ")\n";
        }
        this.Inf = Inf;
        this.Inf.setText("Nombre: Banco\n"
                + "Dinero : " + dinero + "\n"
                + "Propiedades:\n " + propiedades);
        this.Inf.enable(false);
        this.Inf.setLocation(0, 0);

        this.add(this.Inf);
        this.setDefaultCloseOperation(2);
        this.setLocation(600, 350);
        this.setSize(250, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(2);





    }

    public void SetText(String cad) {
        this.Inf.setText(cad);
    }
}
