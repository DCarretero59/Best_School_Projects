package monopoly2;

import java.util.Random;

/**
 *
 * @author Diego
 */
public class Dado extends Thread {
    
    Imagen imgdado;
    public int na;
    
    public Dado(Imagen imgdado){
        this.imgdado=imgdado;
    }
    
    public void run(){
        for (int i = 0; i < 10; i++) {
            try {
                Random numAle = new Random();
                int na = numAle.nextInt(5)+1;
                imgdado.cambiarImagen("D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Dado"+na+".jpg");
                this.na=na;
                this.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}
