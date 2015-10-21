package monopoly2;

import java.awt.event.MouseEvent;

/**
 *
 * @author Diego
 */
public class Casilla extends Imagen {
    int id;
    
    public Casilla(int id){
        super (0,0,50,70,"D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Casilla"+id+".jpg");
        
        if (id>=1 && id<=11) {
            this.setLocation(550-(id*50), 550);
        }
        if (id>=12 && id<=21) {
            this.setLocation(0, 550-((id-11)*50));
        }
        if (id>=22 && id<=31) {
            this.setLocation(0+((id-21)*50), 50);
        }
        if (id>=32 && id<=40) {
            this.setLocation(500, 50+((id-31)*50));
        }
        
        

        if (id==1) {
            this.id=40;
        }else{
        this.id=id-1;
        }
        
    }
    
}
