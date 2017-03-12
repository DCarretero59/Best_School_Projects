package monopoly2;

/**
 *
 * @author Diego
 */
public class Ficha extends Imagen {
    public int pos=0;
    public int id;
    
    public Ficha(int id){
        super(520,560,20,20,"D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Ficha"+id+".jpg");
        this.id=id;
    }
    
    public void moverFicha(int avanza_casillas){
        MovimientoFicha f = new MovimientoFicha(this,avanza_casillas);
        f.start();
    }
}
