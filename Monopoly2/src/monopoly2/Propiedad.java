package monopoly2;

import java.io.Serializable;

/**
 *
 * @author Diego
 */
public class Propiedad implements Serializable {
    public String Nombre;
    public int posicion;

    public Propiedad(String Nombre, int posicion) {
        this.Nombre = Nombre;
        this.posicion = posicion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    
}
