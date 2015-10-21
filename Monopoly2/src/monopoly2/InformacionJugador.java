package monopoly2;
/**
 *
 * @author Diego
 */
public class InformacionJugador extends javax.swing.JInternalFrame{
    public int posicion;
    javax.swing.JTextArea Inf = new javax.swing.JTextArea();
    
    
    public InformacionJugador(Jugador j, int x, int y, int ancho,int alto){
        javax.swing.JTextArea Inf = new javax.swing.JTextArea();
        this.Inf=Inf;
        this.Inf.setText("Nombre: "+j.Nombre+"\n"
                + "Dinero : "+j.dinero+"\n"
                + "Posicion: "+j.pos+"\n"
                + "Ficha asignada: " +j.f.id+"\n"
                + "Propiedades:\n "+j.propiedades);
        this.Inf.enable(false);
        this.Inf.setLocation(0, 0);
        
        this.add(this.Inf);
        this.setDefaultCloseOperation(2);
        this.setLocation(x, y);
        this.setSize(ancho, alto);
        this.setVisible(true);
        this.setDefaultCloseOperation(2);
        
        
    }
    
    public void SetText(String cad){
        this.Inf.setText(cad);
    }
    
}
