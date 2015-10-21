/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.util.ArrayList;

public class CodigoEnsamblador {

    private static int numEtiqueta = 99;
    private static String temporales = "t0";

    public String nuevoTemporal() {
        String[] split = temporales.split("t");
        int nuevoTemp = Integer.parseInt(split[1]) + 1;
        temporales = "t" + nuevoTemp;
        return temporales;
    }

    public int nuevaEtiqueta() {
        numEtiqueta++;
        return numEtiqueta;
    }

    /**
     *
     * @param sentencia
     * @return
     */
    public String ejecutarSentencia(ArrayList<String[][]> sentencia) {
        String codigoEnsamblador = "";
        String valorSentencia = sentencia.get(0)[0][1];
        switch (valorSentencia) {
            case "tkn_tipo_dato":
                String assemblyType = "";
                switch (sentencia.get(0)[0][0]) {
                    case "nombro": {
                        assemblyType = "dw";
                        break;
                    }
                    case "decimale": {
                        assemblyType = "dd";
                        break;
                    }
                    case "exist": {
                        assemblyType = "db";
                        break;
                    }
                    case "val": {
                        assemblyType = "db";
                        break;
                    }
                    case "chain": {
                        assemblyType = "dd";
                        break;
                    }
                }
                codigoEnsamblador = sentencia.get(1)[0][0] + " " + assemblyType;
                if (sentencia.size() > 2) {
                    for (int i = 3; i < sentencia.size(); i = i + 2) {
                        codigoEnsamblador += "\n" + sentencia.get(i)[0][0] + " " + assemblyType;
                    }
                }
                codigoEnsamblador += "\n";
                break;
            case "tkn_ID":
                if (sentencia.size() > 3) {
                    String infixOperation = "";
                    for (int i = 2; i < sentencia.size(); i++) {
                        infixOperation += sentencia.get(i)[0][0];
                    }
                    String postFix = infixPostfix.getPostFix(infixOperation);
                    String[] postFixArray = postFix.split(" ");
                    for (int i = 0; i < postFixArray.length; i++) {
                        String val = postFixArray[i];
                        switch (val) {
                            case "+": {
                                String OP1 = postFixArray[i - 2];
                                String OP2 = postFixArray[i - 1];
                                String temporal = nuevoTemporal();
                                codigoEnsamblador += "add " + OP1 + ", " + OP2 + "\n"
                                        + "mov " + temporal + ", " + OP1 + "\n";
                                postFixArray[i] = temporal;
                                if (i - 3 > 0) {
                                    postFixArray[i - 1] = postFixArray[i - 3];
                                }
                                break;
                            }
                            case "-": {
                                String OP1 = postFixArray[i - 2];
                                String OP2 = postFixArray[i - 1];
                                String temporal = nuevoTemporal();
                                codigoEnsamblador += "sub " + OP1 + ", " + OP2 + "\n"
                                        + "mov " + temporal + ", " + OP1 + "\n";
                                postFixArray[i] = temporal;
                                if (i - 3 > 0) {
                                    postFixArray[i - 1] = postFixArray[i - 3];
                                }
                                break;
                            }
                            case "%": {
                                String OP1 = postFixArray[i - 2];
                                String OP2 = postFixArray[i - 1];
                                String temporal = nuevoTemporal();
                                codigoEnsamblador += "idiv " + OP1 + ", " + OP2 + "\n"
                                        + "mov " + temporal + ", " + OP1 + "\n";
                                postFixArray[i] = temporal;
                                if (i - 3 > 0) {
                                    postFixArray[i - 1] = postFixArray[i - 3];
                                }
                                break;
                            }
                            case "*": {
                                String OP1 = postFixArray[i - 2];
                                String OP2 = postFixArray[i - 1];
                                String temporal = nuevoTemporal();
                                codigoEnsamblador += "imul " + OP1 + ", " + OP2 + "\n"
                                        + "mov " + temporal + ", " + OP1 + "\n";
                                postFixArray[i] = temporal;
                                if (i - 3 > 0) {
                                    postFixArray[i - 1] = postFixArray[i - 3];
                                }
                                break;
                            }
                            case "/": {
                                String OP1 = postFixArray[i - 2];
                                String OP2 = postFixArray[i - 1];
                                String temporal = nuevoTemporal();
                                codigoEnsamblador += "idiv " + OP1 + ", " + OP2 + "\n"
                                        + "mov " + temporal + ", " + OP1 + "\n";
                                postFixArray[i] = temporal;
                                if (i - 3 > 0) {
                                    postFixArray[i - 1] = postFixArray[i - 3];
                                }
                                break;
                            }
                        }
                    }
                    codigoEnsamblador += "mov " + sentencia.get(0)[0][0] + ", " + postFixArray[postFixArray.length - 1] + "\n";

                } else if (sentencia.size() == 2) {
                    if (sentencia.get(1)[0][0].contains("+")) {
                        codigoEnsamblador = "add " + sentencia.get(0)[0][0] + ", 1\n";
                    }
                    if (sentencia.get(1)[0][0].contains("-")) {
                        codigoEnsamblador = "sub " + sentencia.get(0)[0][0] + ", 1\n";
                    }
                } else {
                    codigoEnsamblador = "mov " + sentencia.get(0)[0][0] + ", " + sentencia.get(2)[0][0] + "\n";
                }
                break;
        }
        return codigoEnsamblador;
    }

    public String analizarSentenciaOL(String sentencia) {
        String codigoEnsamblador = "";
        return codigoEnsamblador;
    }

    private final InfixPostfix4 infixPostfix = new InfixPostfix4();
}
