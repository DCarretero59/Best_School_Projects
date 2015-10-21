package monopoly2;
/**
 *
 * @author Diego
 */
public class Imagen extends javax.swing.JLabel {
    
    int ancho, alto;
    
    public Imagen(int x, int y, int alto, int ancho, String ruta){
        
        this.setSize(ancho,alto);
        this.setLocation(x,y);
        
        this.alto=alto;
        this.ancho=ancho;
        this.cambiarImagen(ruta);
    }
    
    public Imagen(int x, int y, int alto, int ancho){
        this.setSize(ancho,alto);
        this.setLocation(x,y);
        
        this.alto=alto;
        this.ancho=ancho;
    }
    
    public void cambiarImagen(String ruta){
        javax.swing.ImageIcon icono_grande;
        icono_grande= new javax.swing.ImageIcon(ruta);
        
        java.awt.Image imagen_escalada;
        imagen_escalada =icono_grande.getImage().getScaledInstance(alto, ancho, java.awt.Image.SCALE_DEFAULT);
        
        javax.swing.ImageIcon nuevo_icono;
        nuevo_icono=new javax.swing.ImageIcon(imagen_escalada);
        this.setIcon(nuevo_icono);
        
    }
    
    
}
