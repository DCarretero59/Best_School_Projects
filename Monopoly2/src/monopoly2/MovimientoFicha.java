package monopoly2;

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class MovimientoFicha extends Thread {

    int casillas_avanza;
    Ficha f;
    boolean posReached;
    int posCasilla;

    public MovimientoFicha(Ficha f, int casillas_avanza) {
        this.casillas_avanza = casillas_avanza;
        this.f = f;
    }

    public void run() {
        int j = 0;
        for (int i = f.pos; i <= f.pos + casillas_avanza; i++) {

            posReached = false;
            if (i >= 1 && i <= 10) {
                f.setLocation(520 - (i * 50), 560);
            }
            if (i >= 11 && i <= 20) {
                f.setLocation(10, 560 - ((i - 10) * 50));
            }
            if (i >= 21 && i <= 30) {
                f.setLocation(10 + ((i - 20) * 50), 60);
            }
            if (i >= 31 && i <= 40) {
                f.setLocation(520, 60 + ((i - 30) * 50));
                if (i == 40) {
                    posReached = true;
                    j = i - 40;
                }
            }
            if (i >= 41 && i <= 49) {

                j = i - 40;
                f.setLocation(520 - (j * 50), 560);
                posReached = true;
            }

            try {
                this.sleep(200);
            } catch (Exception e) {
            }


        }
        if (posReached == true) {
            Tablero.j[f.id - 1].dinero = Tablero.j[f.id - 1].dinero + 200;
            Tablero.b.dinero = Tablero.b.dinero - 200;
            f.pos = j;
            javax.swing.JOptionPane.showMessageDialog(null, "Usted ha recibido 200 pesos");
            Tablero.j[f.id - 1].pos = f.pos;
            Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                    + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                    + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                    + "Ficha Asignada: " + f.id + "\n"
                    + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);

            Tablero.b.SetText("Nombre: Banco\n"
                    + "Dinero: " + Tablero.b.dinero + "\n"
                    + "Propiedades: " + Tablero.b.propiedades);

            if (f.pos == 0) {
                Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                        + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                        + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                        + "Ficha Asignada: " + f.id + "\n"
                        + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                HipotecarPropiedades();
            } else {
                if (Tablero.j[f.id - 1].dinero == 0) {
                    Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                            + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                            + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                            + "Ficha Asignada: " + f.id + "\n"
                            + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                    javax.swing.JOptionPane.showMessageDialog(null, "Usted ha quedado en banca rota");
                    HipotecarPropiedades();
                } else {
                    ComprarPropiedades();
                    HipotecarPropiedades();
                }
            }
        } else {
            f.pos = f.pos + casillas_avanza;
            Tablero.j[f.id - 1].pos = f.pos;
            j = 0;
            if (f.pos == 10) {
                javax.swing.JOptionPane.showMessageDialog(null, "Visitando la carcel");
                Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                        + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                        + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                        + "Ficha Asignada: " + f.id + "\n"
                        + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                HipotecarPropiedades();
            }
            if (f.pos == 20) {
                javax.swing.JOptionPane.showMessageDialog(null, "GRATIS!");
                Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                        + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                        + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                        + "Ficha Asignada: " + f.id + "\n"
                        + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                HipotecarPropiedades();
            }
            if (f.pos == 30) {
                f.pos = 10;
                javax.swing.JOptionPane.showMessageDialog(null, "CARCEL!");
                Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                        + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                        + "Posicion: " + f.pos + "\n"
                        + "Ficha Asignada: " + f.id + "\n"
                        + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                HipotecarPropiedades();
                f.setLocation(20, 560);
            }
            if (f.pos != 10 && f.pos != 20 && f.pos != 30) {
                if (Tablero.j[f.id - 1].dinero == 0) {
                    Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                            + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                            + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                            + "Ficha Asignada: " + f.id + "\n"
                            + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                    javax.swing.JOptionPane.showMessageDialog(null, "Usted ha quedado en banca rota");
                    HipotecarPropiedades();
                } else {
                    ComprarPropiedades();
                    HipotecarPropiedades();
                }
            }
        }
    }

    public static int valid(String cad) {
        int num = 0;
        boolean valid = false;
        do {
            try {
                num = Integer.parseInt(javax.swing.JOptionPane.showInputDialog(cad));
                valid = true;
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Me tienes que dar un numero");
            }
        } while (valid = false);

        return num;
    }

    public void ComprarPropiedades() {
        int opc;
        if (Tablero.casillas[f.pos] != 0) {
            CobrarRenta();
        } else {
            Archivo a = new Archivo();
            String[] arr = a.cargarArreglo("D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Casilla" + f.pos + ".txt");
            String valorString = a.extraerValor("Precio", arr);
            int valor = Integer.parseInt(valorString);
            String nombre = a.extraerValor("Nombre", arr);
            do {
                opc = valid("Desea comprar " + nombre + "  (Valor " + valor + ") ?\n"
                        + "1) Si\n"
                        + "2) No\n"
                        + "Elija una opcion: ");
            } while (opc < 1 || opc > 2);
            switch (opc) {
                case 1:
                    if (Tablero.j[f.id - 1].dinero - valor <= 0) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Usted no tiene los suficientes recursos para comprar esta propiedad");
                    } else {


                        Tablero.j[f.id - 1].dinero = Tablero.j[f.id - 1].dinero - valor;
                        Tablero.b.dinero = Tablero.b.dinero + valor;
                        Tablero.casillas[f.pos] = f.id;
                        Propiedad p;
                        p = new Propiedad(nombre, f.pos);
                        Tablero.j[f.id - 1].Propiedades.add(p);

                        for (int i = 0; i < Tablero.b.Propiedades.size(); i++) {
                            if (f.pos == ((Propiedad) Tablero.b.Propiedades.get(i)).getPosicion()) {
                                Tablero.b.Propiedades.remove(i);
                                break;
                            }
                        }


                        Tablero.j[f.id - 1].propiedades = "";
                        for (int i = 0; i < Tablero.j[f.id - 1].Propiedades.size(); i++) {
                            Tablero.j[f.id - 1].propiedades = Tablero.j[f.id - 1].propiedades
                                    + ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(i)).getNombre()
                                    + " (Posicion :" + ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(i)).getPosicion() + " )\n";
                        }
                    }



                    Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                            + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                            + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                            + "Ficha Asignada: " + f.id + "\n"
                            + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);

                    Tablero.b.propiedades = "";
                    for (int i = 0; i < Tablero.b.Propiedades.size(); i++) {
                        Tablero.b.propiedades = Tablero.b.propiedades
                                + ((Propiedad) Tablero.b.Propiedades.get(i)).getNombre()
                                + " (Posicion :" + ((Propiedad) Tablero.b.Propiedades.get(i)).getPosicion() + " )\n";
                    }

                    Tablero.b.SetText("Nombre: Banco\n"
                            + "Dinero: " + Tablero.b.dinero + "\n"
                            + "Propiedades: " + Tablero.b.propiedades);
                    break;
                case 2:
                    Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                            + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                            + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                            + "Ficha Asignada: " + f.id + "\n"
                            + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
                    break;
            }
        }
    }

    public void CobrarRenta() {
        Archivo a = new Archivo();
        String[] arr = a.cargarArreglo("D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Casilla" + f.pos + ".txt");
        String valorString = a.extraerValor("Renta", arr);
        String nombre = a.extraerValor("Nombre", arr);
        int valor = Integer.parseInt(valorString);
        if (Tablero.j[(Tablero.casillas[f.pos]) - 1].Nombre == Tablero.j[f.id - 1].Nombre) {
            javax.swing.JOptionPane.showMessageDialog(null, "Ha caido en su propia casilla");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Se le cobro " + valor + " de renta de la propiedad " + nombre + " de " + Tablero.j[(Tablero.casillas[f.pos]) - 1].Nombre);
            if (Tablero.j[f.id - 1].dinero - valor <= 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Usted no puede pagar la renta de esta propiedad, ha quedado en banca rota");
                Tablero.j[(Tablero.casillas[f.pos]) - 1].dinero = Tablero.j[f.id - 1].dinero;
                Tablero.j[f.id - 1].dinero = 0;
                HipotecarPropiedades();
            } else {
                Tablero.j[f.id - 1].dinero = Tablero.j[f.id - 1].dinero - valor;
                Tablero.j[(Tablero.casillas[f.pos]) - 1].dinero = Tablero.j[(Tablero.casillas[f.pos]) - 1].dinero + valor;
                Tablero.j[(Tablero.casillas[f.pos]) - 1].IJ.SetText("Nombre: " + Tablero.j[(Tablero.casillas[f.pos]) - 1].Nombre + "\n"
                        + "Dinero: " + Tablero.j[(Tablero.casillas[f.pos]) - 1].dinero + "\n"
                        + "Posicion: " + Tablero.j[(Tablero.casillas[f.pos]) - 1].pos + "\n"
                        + "Ficha Asignada: " + Tablero.j[(Tablero.casillas[f.pos]) - 1].f.id + "\n"
                        + "Propiedades : \n" + Tablero.j[(Tablero.casillas[f.pos]) - 1].propiedades);
            }
        }
        Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                + "Ficha Asignada: " + f.id + "\n"
                + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);
    }

    public void HipotecarPropiedades() {
        int opc;
        int decis;

        do {
            decis = valid("Desea hipotecar una propiedad? \n"
                    + "1) Si\n"
                    + "2) No\n"
                    + "Elija una opcion: ");
            if (decis < 1 || decis > 2) {
                javax.swing.JOptionPane.showMessageDialog(null, "No existe esa opcion.");
            }
        } while (decis < 1 || decis > 2);
        switch (decis) {
            case 1:
                opc = valid("Que propiedad desea empeñar\n"
                        + Tablero.j[f.id - 1].propiedades);

                for (int i = 0; i < Tablero.j[f.id - 1].Propiedades.size(); i++) {
                    if (opc == ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(i)).getPosicion()) {
                        Archivo a = new Archivo();
                        String[] arr = a.cargarArreglo("D:\\Diego\\Google Drive\\Documentos Escuela\\Monopoly\\Casilla"
                                + "" + ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(i)).getPosicion() + ".txt");
                        String valorString = a.extraerValor("Hipoteca", arr);
                        int valor = Integer.parseInt(valorString);
                        Tablero.j[f.id - 1].dinero = Tablero.j[f.id - 1].dinero + valor;
                        Tablero.b.dinero = Tablero.b.dinero - valor;
                        Tablero.b.Propiedades.add(((Propiedad) Tablero.j[f.id - 1].Propiedades.get(i)));
                        posCasilla= ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(i)).getPosicion();
                        Tablero.j[f.id - 1].Propiedades.remove(i);

                        Tablero.j[f.id - 1].propiedades = "";
                        for (int j = 0; j < Tablero.j[f.id - 1].Propiedades.size(); j++) {
                            Tablero.j[f.id - 1].propiedades = Tablero.j[f.id - 1].propiedades
                                    + ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(j)).getNombre()
                                    + " (Posicion :" + ((Propiedad) Tablero.j[f.id - 1].Propiedades.get(j)).getPosicion() + " )\n";
                        }
                    }



                    Tablero.j[f.id - 1].IJ.SetText("Nombre: " + Tablero.j[f.id - 1].Nombre + "\n"
                            + "Dinero: " + Tablero.j[f.id - 1].dinero + "\n"
                            + "Posicion: " + Tablero.j[f.id - 1].pos + "\n"
                            + "Ficha Asignada: " + f.id + "\n"
                            + "Propiedades : \n" + Tablero.j[f.id - 1].propiedades);

                    Tablero.b.propiedades = "";
                    for (int j = 0; j < Tablero.b.Propiedades.size(); j++) {
                        Tablero.b.propiedades = Tablero.b.propiedades
                                + ((Propiedad) Tablero.b.Propiedades.get(j)).getNombre()
                                + " (Posicion :" + ((Propiedad) Tablero.b.Propiedades.get(j)).getPosicion() + " )\n";
                    }

                    Tablero.b.SetText("Nombre: Banco\n"
                            + "Dinero: " + Tablero.b.dinero + "\n"
                            + "Propiedades: " + Tablero.b.propiedades);

                    Tablero.casillas[posCasilla - 1] = 0;
                }
                break;
            case 2:
                break;

        }
    }
}
