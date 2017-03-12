package monopoly2;

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Jugador {
    public Ficha f;
    public String Nombre;
    public InformacionJugador IJ;
    public int id;
    public int pos;
    public int dinero=1500;
    public String propiedades="";
    public Boton boton;
    public ArrayList Propiedades = new ArrayList();
    public int contDobles=0;
    
    public  Jugador(int id, String nombre, Imagen dado1, Imagen dado2){
        f = new Ficha(id);
        pos = f.pos;
        this.id=id;
        this.Nombre=nombre;
        if (id==1 || id==2) {
            IJ = new InformacionJugador(this, 900, (370*(id-1)), 200, 370);
        }
        else{
            IJ = new InformacionJugador(this, 1100, (370*(id-3)), 200, 370);
        }
        
        this.boton= new Boton(this, dado1, dado2, 600, 250);
        
    }
    
    
    
    public void tirarDados(Imagen dado1, Imagen dado2){
        Dado d1 = new Dado(dado1);
        Dado d2 = new Dado(dado2);
        d1.start();
        d2.start();
        
        SumaDados s = new SumaDados(this,d1,d2);
        s.start();
    }
}
