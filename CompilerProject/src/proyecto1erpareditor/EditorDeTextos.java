/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1erpareditor;

import CustomComponents.ModifiedTextArea;
import Metodos.*;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego
 */
public class EditorDeTextos extends javax.swing.JFrame {

    private final InfixPostfix4 infixPostfix = new InfixPostfix4();
    private boolean cambio = false, lexico = false, sintacticosemantico = false;
    private final String palabrasReservadas[] = {/*Estructuras de control*/"Se", "Altro", "Fare", "Mentre", "Per",/*Tipos de dato*/ "nombro", "decimale", "chain", "exist", "val",/*Logico*/ "true", "false", "scrivere", "leggere"};
    private final static String tokens[][] = {
        {"Project"}, //tkn_project
        {""}, //tkn_ID
        {";"}, //tkn_pun_coma
        {"nombro", "decimale", "chain", "exist", "val"}, //tkn_tipo_dato
        {":="}, //tkn_asignacion
        {"Initialize"}, //tkn_inicio 
        {"'caracter'"}, //tkn_character 
        {"+", "-"}, //tkn_OA1 
        {"/", "*", "%"}, //tkn_OA2 
        {"+!", "-!"}, //tkn_OA3
        {"mentre"}, //tkn_while 
        {"("}, //tkn_PA 
        {")"}, //tkn_PC 
        {"["}, //tkn_CA 
        {"]"}, //tkn_CC 
        {"se"}, //tkn_if 
        {"altro"}, //tkn_else 
        {"fare"}, //tkn_do 
        {">", "<", ">=", "<=", "==", "!=", "="}, //tkn_OR 
        {"per"}, //tkn_for   
        {"scrivere"}, //tkn_write
        {"231312fsdfs"}, //tkn_cadena 
        {"leggere"}, //tkn_read 
        {"Finalize"} /*tkn_end*/,
        {","},
        {"true", "false"},
        {"&&", "||", "!"},
        {"1234567890"}, //tkn_digito
        {"12312312312.3123123123"} //tkn_decimal
    };
    private final static String tokensIds[] = {"tkn_project", "tkn_ID", "tkn_pun_coma", "tkn_tipo_dato",
        "tkn_asignacion", "tkn_inicio", "tkn_caracter", "tkn_OA1", "tkn_OA2", "tkn_OA3",
        "tkn_while", "tkn_PA", "tkn_PC", "tkn_CA", "tkn_CC", "tkn_if", "tkn_else", "tkn_do",
        "tkn_OR", "tkn_for", "tkn_write", "tkn_cadena", "tkn_read", "tkn_end", "tkn_coma", "tkn_TF", "tkn_OL", "tkn_digito", "tkn_decimal"};
    private final char separadores[] = {
        ' ', '{', '}', '(', ')', ';', '&', '|', '<', '>', '+', '-', '=', '*', '/', '%', '!', ',', '"', '\n', '\'', ',', ':', '[', ']', '@',};
    private static final String[][] procesos = {
        /*1 */{"prcs_init", "prcs_ cuerpo", "prcs_end"},
        /*2 */ {"prcs_Dvar", "prcs_cuerpo"},
        /*3 */ {"prcs_if", "prcs_cuerpo"},
        /*4 */ {"prcs_while", "prcs_cuerpo"},
        /*5 */ {"prcs_for", "prcs_cuerpo"},
        /*6 */ {"prcs_read", "prcs_cuerpo"},
        /*7 */ {"prcs_write", "prcs_cuerpo"},
        /*8 */ {"prcs_asig", "prcs_cuerpo"},
        /*9 */ {""},
        /*10 */ {"prcs_id_dato", "prcs_var", "prcs_pyc"},
        /*11*/ {"prcs_id", "prcs_var'"},
        /*12*/ {"prcs_igual", "prcs_operacion", "prcs_var''"},
        /*13*/ {"prcs_var''"},
        /*14*/ {"prcs_coma", "prcs_var"},
        /*15*/ {""},
        /*16*/ {"prcs_se", "prcs_pa", "prcs_condicion", "prcs_pc", "prcs_ca", "prcs_cuerpo", "prcs_cc", "prcs_if'"},
        /*17*/ {"prcs_else", "prcs_ca", "prcs_cuerpo", "prcs_cc"},
        /*18*/ {""},
        /*19*/ {"prcs_id", "prcs_condicion'"},
        /*20*/ {"prcs_OR", "prcs_condicion''"},
        /*21*/ {""},
        /*22*/ {"prcs_varc", "prcs_OL"},
        /*23*/ {"tkn_ID"},
        /*24*/ {"tkn_digito"},
        /*25*/ {"prcs_oplogico", "prcs_condicion"},
        /*26*/ {""},
        /*27*/ {"tkn_OL"},
        /*28*/ {"prcs_mentre", "prcs_pa", "prcs_condicion", "prcs_pc", "prcs_ca", "prcs_cuerpociclo", "prcs_cc"},
        /*29*/ {"prcs_per", "prcs_pa", "prcs_varf", "prcs_condicion", "prcs_pyc", "prcs_id", "prcs_OID", "prcs_pc", "prcs_ca", "prcs_cuerpociclo", "prcs_cc"},
        /*30*/ {"prcs_Dvar"},
        /*31*/ {"prcs_asig"},
        /*32*/ {"prcs_leggere", "prcs_pa", "prcs_varp", "prcs_pc", "prcs_pyc"},
        /*33*/ {"prcs_scrivere", "prcs_pa", "prcs_varp", "prcs_pc", "prcs_pyc"},
        /*34*/ {"tkn_cadena"},
        /*35*/ {"tkn_ID"},
        /*36*/ {"prcs_Dvar", "prcs_cuerpociclo"},
        /*37*/ {"prcs_if", "prcs_cuerpociclo"},
        /*38*/ {"prcs_while", "prcs_cuerpociclo"},
        /*39*/ {"prcs_for", "prcs_cuerpociclo"},
        /*40*/ {"prcs_read", "prcs_cuerpociclo"},
        /*41*/ {"prcs_write", "prcs_cuerpociclo"},
        /*42*/ {"prcs_asig", "prcs_cuerpociclo"},
        /*43*/ {""},
        /*44*/ {"prcs_id", "prcs_igual", "prcs_operacion", "prcs_pyc"},
        /*45*/ {"prcs_T", "prcs_operacion'"},
        /*46*/ {"prcs_OA1", "prcs_T", "prcs_operacion'"},
        /*47*/ {""},
        /*48*/ {"tkn_OA1"},
        /*49*/ {"prcs_P", "prcs_T'"},
        /*50*/ {"prcs_OA2", "prcs_P", "prcs_T'"},
        /*51*/ {""},
        /*52*/ {"prcs_OA1", "prcs_P"},
        /*53*/ {"prcs_x"},
        /*54*/ {"tkn_OA2"},
        /*55*/ {"prcs_pa", "prcs_operacion", "prcs_pc"},
        /*56*/ {"tkn_ID"},
        /*57*/ {"tkn_digito"},
        /*58*/ {"tkn_PA"},
        /*59*/ {"tkn_PC"},
        /*60*/ {"tkn_CA"},
        /*61*/ {"tkn_CC"},
        /*62*/ {"tkn_pun_coma"},
        /*63*/ {"tkn_coma"},
        /*64*/ {"tkn_OR"},
        /*65*/ {"tkn_OA3"},
        /*66*/ {"tkn_asignacion"},
        /*67*/ {"tkn_TF"},
        /*68*/ {"tkn_decimal"},
        /*69*/ {"tkn_decimal"},
        /*70*/ {"tkn_cadena"},
        /*71*/ {"tkn_char"},
        /*72*/ {"tkn_inicio"},
        /*73*/ {"tkn_end"},
        /*74*/ {"tkn_ID"},
        /*75*/ {"tkn_tipo_dato"},
        /*76*/ {"tkn_if"},
        /*77*/ {"tkn_else"},
        /*78*/ {"tkn_while"},
        /*79*/ {"tkn_for"},
        /*80*/ {"tkn_read"},
        /*81*/ {"tkn_write"}
    };
    private int linecount = 1;
    private static int symbolTableCount = 1;
    private static int symbolColumnCount = 0;

    public void validarLexema(String cadena) {
        symbolColumnCount = 0;
        cadena = cadena + " ";
        char[] caracteres = cadena.toCharArray();
        int initPosition = 0;
        String lexema = "";
        boolean flag;
        for (int i = 0; i < caracteres.length; i++) {
            flag = true;
            for (int j = 0; j < separadores.length; j++) {
                if (caracteres[i] == separadores[j]) {
                    for (int k = initPosition; k < i; k++) {
                        lexema = lexema + caracteres[k];
                    }
                    if (!lexema.equals("") && !lexema.equals(" ")) {
                        verificarLexema(lexema.replaceAll("\\s+", ""), initPosition + 1 - symbolColumnCount);
                    }
                    lexema = "";
                    if (caracteres[i] != ' ' && i != caracteres.length - 1 && caracteres[i] != '\n') {
                        lexema = "" + caracteres[i];
                        if (caracteres[i] == '"' || caracteres[i] == 39) {
                            while (flag) {
                                if (i != caracteres.length - 1) {
                                    i++;
                                } else {
                                    break;
                                }
                                lexema = lexema + caracteres[i];
                                for (int k = 0; k < separadores.length; k++) {
                                    if (caracteres[i] == separadores[k] && caracteres[i] != ' ' && caracteres[i] != '\n') {
                                        flag = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            String templexema = lexema;
                            templexema = templexema + caracteres[i + 1];
                            for (int k = 0; k < 24; k++) {
                                for (String token : tokens[k]) {
                                    if (token.equals(templexema)) {
                                        lexema = templexema;
                                        i++;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!lexema.equals("") && !lexema.equals(" ")) {
                            verificarLexema(lexema.replaceAll("\\s+", ""), i + 1 - symbolColumnCount);
                        }
                        lexema = "";
                        if (i != caracteres.length - 1) {
                            if (caracteres[i + 1] == ' ') {
                                i++;
                            }
                        }
                    }
                    if (i != caracteres.length - 1) {
                        if (caracteres[i + 1] == ' ') {
                            initPosition = i;
                        } else {
                            initPosition = i + 1;
                        }
                    }

                }
            }
            if (caracteres[i] == '\n') {
                symbolTableCount++;
                symbolColumnCount = i;
            }

        }
        symbolTable.updateUI();
        jScrollPane4.updateUI();
        for (int i = 0; i < symbolTable.getRowCount(); i++) {
            if (symbolTable.getValueAt(i, 1).toString().equals("Error")) {
                lexico = false;
                javax.swing.JOptionPane.showMessageDialog(this, "Error en lexico");
                break;
            } else {
                lexico = true;
            }
        }
    }

    public static void verificarLexema(String cadena, int columna) {
        char[] caracteres = new char[cadena.length() + 1];
        for (int i = 0; i < caracteres.length; i++) {
            if (i == caracteres.length - 1) {
                caracteres[i] = '`';
            } else {
                caracteres[i] = cadena.charAt(i);
            }
        }

        //   0  1   2   3  4  5  6  7  8   9  10  11  12  13  14  15  16  17  18  19  20   21  22
        //   l  d   @   ;  ,  :  =  '  !   <  >   +   -   *   /   %   (   )   [   ]   "    .   `
        Object[][] tabla = {
            {1, 21, 2, 20, 3, 4, 9, 6, 11, 9, 9, 11, 11, 13, 13, 9, 14, 15, 16, 17, 18, "", "false"}, //->0  Inicio
            {1, 1, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //1    Variables, funciones, etc
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //2    Asignacion_tipo_dato
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //3    Coma
            {"", "", "", "", "", "", 5, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "false"}, //4    Dos puntos
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //5    Asignacion :=
            {7, 7, "", "", "", "", "", 8, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "false"}, //6    Inicio char
            {"", "", "", "", "", "", "", 8, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "false"}, //7   Char con valor
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //8       Fin char
            {"", "", "", "", "", "", 10, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //9      OR >,<,=
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //10     OR >=, <=, ==
            {"", "", "", "", "", "", 12, "", 12, "", "", 12, 12, "", "", "", "", "", "", "", "", "", "true"}, //11    OR !
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //12      OR !=,!+,!-
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //13
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //14
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //15
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //16
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //17
            {18, 18, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 19, "", "false"}, //18
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //19
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}, //20    Punto y Coma
            {"", 21, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 22, "true"}, //21    Punto y Coma
            {"", 23, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "false"}, //22    Punto y Coma
            {"", 23, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "true"}}; //23

        Object Entrada;
        Object Estado = 0;
        int EstadoFinal;

        int i = 0;
        String tokenid = "";
        int tokenidnumber = 0;
        do {
            int valor = caracteres[i];
            if ((valor >= 65 && valor <= 90) || (valor >= 97 && valor <= 122)) { //Letras
                Entrada = 0;
            } else if (valor >= 48 && valor <= 57) { //Digitos
                Entrada = 1;
            } else if (valor == 64) { //@
                Entrada = 2;
            } else if (valor == 59) { //;
                Entrada = 3;
            } else if (valor == 44) { //,
                Entrada = 4;
            } else if (valor == 58) { //:
                Entrada = 5;
            } else if (valor == 61) { //=
                Entrada = 6;
            } else if (valor == 39) { //'
                Entrada = 7;
            } else if (valor == 33) { //!
                Entrada = 8;
            } else if (valor == 60) { //<
                Entrada = 9;
            } else if (valor == 62) { //>
                Entrada = 10;
            } else if (valor == 43) { //+
                Entrada = 11;
            } else if (valor == 45) { //-
                Entrada = 12;
            } else if (valor == 42) { //*
                Entrada = 13;
            } else if (valor == 47) { //'/'
                Entrada = 14;
            } else if (valor == 37) { //%
                Entrada = 15;
            } else if (valor == 40) { //(
                Entrada = 16;
            } else if (valor == 41) { //)
                Entrada = 17;
            } else if (valor == 91) { //[
                Entrada = 18;
            } else if (valor == 93) { //]
                Entrada = 19;
            } else if (valor == 34) { //"
                Entrada = 20;
            } else if (valor == 46) { //.
                Entrada = 21;
            } else if (valor == 96) { //`
                Entrada = 22;
            } else {
                Estado = 0;
                Entrada = 22;
            }
            EstadoFinal = (int) Estado;
            Estado = tabla[(int) Estado][(int) Entrada];
            if (Estado == "") {
                Estado = "false";
            }
            if (Estado == "true" || Estado == "false") {
                break;
            }
            i++;
        } while (true);
        if (EstadoFinal == 21) {
            tokenid = "tkn_digito";
            tokenidnumber = 27;
        } else if (EstadoFinal == 8) {
            tokenid = "tkn_char";
            tokenidnumber = 6;
        } else if (EstadoFinal == 19) {
            tokenid = "tkn_cadena";
            tokenidnumber = 21;
        } else if (EstadoFinal == 23) {
            tokenid = "tkn_decimal";
            tokenidnumber = 28;
        }
        DefaultTableModel dtmTable = (DefaultTableModel) symbolTable.getModel();
        if (Estado == "true") {
            for (int j = 0; j < 28; j++) {
                for (String token : tokens[j]) {
                    if (token.equals(cadena)) {
                        tokenid = tokensIds[j];
                        tokenidnumber = j;
                        break;
                    }
                }
            }
            if (tokenid.equals("") && EstadoFinal == 1) {
                tokenid = "tkn_ID";
                tokenidnumber = 1;
            }

            Object[] row = {cadena, tokenid, tokenidnumber, symbolTableCount, columna};
            dtmTable.addRow(row);
        } else {
            Object[] row = {cadena, "Error", 0, symbolTableCount, columna};
            dtmTable.addRow(row);
        }
        symbolTable.updateUI();
    }

    public void analizadorSintactico_Semantico() {
        int tablePosition = 0;
        String variableType = "";
        String variableToBeAssigned = "";
        boolean variableToBeDeclared = false, assignationFlag = false, operationFlag = false;
        ArrayList<Variables> declaredVariables = new ArrayList();
        Object[] row = {"$", "$", "$", "$", "$"};
        ((DefaultTableModel) (symbolTable.getModel())).addRow(row);
        Object tabla[][] = {
            {"R/C", "$", "tkn_inicio", "tkn_if", "tkn_tipo_dato", "tkn_while", "tkn_for", "tkn_read", "tkn_write", "tkn_ID", "tkn_asignacion", "tkn_coma",
                "tkn_else", "tkn_OR", "tkn_digito", "tkn_decimal", "tkn_OL", "tkn_cadena", "tkn_OA1", "tkn_OA2", "tkn_char", "tkn_PA", "tkn_PC", "tkn_CA", "tkn_CC",
                "tkn_pun_coma", "tkn_OA3", "tkn_TF", "tkn_end"},
            {"prcs_principal", "", 1, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_cuerpo", 9, "", 3, 2, 4, 5, 6, 7, 8, "", "", 3, "", "", "", "", "", "", "", "", "", "", "", 9, "", "", "", 9},
            {"prcs_Dvar", "", "", "", 10, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_var", "", "", "", "", "", "", "", "", 11, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_var'", "", "", "", "", "", "", "", "", "", 12, 13, "", "", "", "", "", "", "", "", "", "", "", "", "", 15, "", "", ""},
            {"prcs_var''", "", "", "", "", "", "", "", "", "", "", 14, "", "", "", "", "", "", "", "", "", "", "", "", "", 15, "", "", ""},
            {"prcs_if", "", "", 16, "", "", "", "", "", "", "", "", 17, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_if'", 18, "", 18, 18, 18, 18, 18, 18, 18, "", "", 17, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_condicion", "", "", "", "", "", "", "", "", 19, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 67, ""},
            {"prcs_condicion'", "", "", "", "", "", "", "", "", "", "", "", "", 20, "", "", "", "", "", "", "", "", 21, "", 21, "", "", "", ""},
            {"prcs_condicion''", "", "", "", "", "", "", "", "", 22, "", "", "", "", 22, 22, "", "", "", "", "", "", "", "", "", "", "", 67, ""},
            {"prcs_varc", "", "", "", "", "", "", "", "", 23, "", "", "", "", 24, 66, "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_OL", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 25, "", "", "", "", "", 26, 26, "", 26, "", "", ""},
            {"prcs_oplogico", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 27, "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_while", "", "", "", "", 28, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_for", "", "", "", "", "", 29, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_varf", "", "", "", 30, "", "", "", "", 31, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_read", "", "", "", "", "", "", 32, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_write", "", "", "", "", "", "", "", 33, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_varp", "", "", "", "", "", "", "", "", 35, "", "", "", "", "", "", "", 34, "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_cuerpociclo", "", "", 37, 36, 38, 39, 40, 41, 42, "", "", 37, "", "", "", "", "", "", "", "", "", "", "", 43, "", "", "", ""},
            {"prcs_asig", "", "", "", "", "", "", "", "", 44, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_operacion", "", "", "", "", "", "", "", "", 45, "", "", "", "", 45, 45, "", 45, 45, 45, 45, "", "", "", "", "", "", 45, ""},
            {"prcs_operacion'", "", "", "", "", "", "", "", "", "", 47, 47, "", "", "", "", "", "", 46, "", "", "", 47, "", "", 47, "", "", ""},
            {"prcs_OA1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 48, "", "", "", "", "", "", "", "", "", ""},
            {"prcs_T", "", "", "", "", "", "", "", "", 49, "", "", "", "", 49, 49, "", 49, 49, "", 49, 49, "", "", "", "", "", 49, ""},
            {"prcs_T'", "", "", "", "", "", "", "", "", "", 51, 51, "", "", "", "", "", "", 51, 50, "", "", 51, "", "", 51, "", 51, ""},
            {"prcs_P", "", "", "", "", "", "", "", 53, 53, "", "", "", 53, 53, "", 53, 53, "", 53, 53, "", "", "", "", "", "", 67, ""},
            {"prcs_OA2", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 54, "", "", "", "", "", "", "", "", ""},
            {"prcs_x", "", "", "", "", "", "", "", "", 56, "", "", "", "", 57, 69, "", 70, "", "", 71, 55, "", "", "", "", "", "", ""},
            {"prcs_pa", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 58, "", "", "", "", "", "", ""},
            {"prcs_pc", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 59, "", "", "", "", "", ""},
            {"prcs_ca", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 60, "", "", "", "", ""},
            {"prcs_cc", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 61, "", "", "", ""},
            {"prcs_pyc", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 62, "", "", ""},
            {"prcs_coma", "", "", "", "", "", "", "", "", "", "", 63, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_OR", "", "", "", "", "", "", "", "", "", "", "", "", 64, "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_OID", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 65, "", ""},
            {"prcs_igual", "", "", "", "", "", "", "", "", "", 66, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_init", "", 72, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_end", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 73},
            {"prcs_id", "", "", "", "", "", "", "", "", 74, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_id_dato", "", "", "", 75, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_se", "", "", 76, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_else", "", "", "", "", "", "", "", "", "", "", "", 77, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_mentre", "", "", "", "", 78, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_per", "", "", "", "", "", 79, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_leggere", "", "", "", "", "", "", 80, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
            {"prcs_scrivere", "", "", "", "", "", "", "", 81, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""}};

        Stack inicio = new Stack();
        inicio.push("$");
        inicio.push("prcs_end");
        inicio.push("prcs_cuerpo");
        inicio.push("prcs_init");
        String infixOperation = "";

        outerloop:
        while (!inicio.empty()) {
            int tableposition = 0;
            String inicioValue = inicio.peek().toString();
            String tableValue = symbolTable.getModel().getValueAt(tablePosition, 1).toString();
            if (inicioValue.equals("$")) {
                if (tableValue.equals(inicio.peek())) {
                    tablePosition++;
                    inicio.pop();
                    outputTxtArea.setText("Cadena valida");
                    break;
                } else {
                    outputTxtArea.setText("Error en final de cadena");
                    break;
                }
            } else if (inicioValue.contains("tkn")) {
                if (tableValue.equals(inicioValue)) {
                    if (inicioValue.equals("tkn_tipo_dato")) {
                        variableType = symbolTable.getModel().getValueAt(tablePosition, 0).toString();
                        variableToBeDeclared = true;
                    } else if (inicioValue.equals("tkn_pun_coma")) {
                        if (operationFlag) {
                            String postFix = infixPostfix.getPostFix(infixOperation);
                            String[] postFixArray = postFix.split(" ");
                            for (int i = 0; i < postFixArray.length; i++) {
                                String val = postFixArray[i];
                                switch (val) {
                                    case "*":
                                    case "/":
                                    case "%": {
                                        String OP1 = postFixArray[i - 2];
                                        String OP2 = postFixArray[i - 1];
                                        Object errorTable[][] = {
                                            {"Accion", "tkn_digito", "tkn_decimal", "tkn_char", "tkn_cadena", "tkn_TF"},
                                            {"tkn_digito", "tkn_digito", "tkn_decimal", "error", "error", "error"},
                                            {"tkn_decimal", "tkn_decimal", "tkn_decimal", "error", "error", "error"},
                                            {"tkn_char", "error", "error", "error", "error", "error"},
                                            {"tkn_cadena", "error", "error", "error", "error", "error"},
                                            {"tkn_TF", "error", "error", "error", "error", "error"}
                                        };
                                        int posOP1 = 0, posOP2 = 0;
                                        for (int j = 0; j < errorTable.length; j++) {
                                            if (OP1.equals(errorTable[j][0].toString())) {
                                                posOP1 = j;
                                                break;
                                            }
                                        }
                                        for (int j = 0; j < errorTable[0].length; j++) {
                                            if (OP2.equals(errorTable[0][j].toString())) {
                                                posOP2 = j;
                                                break;
                                            }
                                        }
                                        String resultado = errorTable[posOP1][posOP2].toString();
                                        if (resultado.equals("error")) {
                                            outputTxtArea.setText("Tipos incompatibles '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                    + "en  "
                                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                            break outerloop;
                                        } else {
                                            postFixArray[i] = resultado;
                                            if (i - 3 > 0) {
                                                postFixArray[i - 1] = postFixArray[i - 3];
                                            }
                                        }
                                        break;
                                    }
                                    case "+": {
                                        String OP1 = postFixArray[i - 2];
                                        String OP2 = postFixArray[i - 1];
                                        Object errorTable[][] = {
                                            {"Accion", "tkn_digito", "tkn_decimal", "tkn_char", "tkn_cadena", "tkn_TF"},
                                            {"tkn_digito", "tkn_digito", "tkn_decimal", "error", "error", "error"},
                                            {"tkn_decimal", "tkn_decimal", "tkn_decimal", "error", "error", "error"},
                                            {"tkn_char", "error", "error", "tkn_char", "error", "error"},
                                            {"tkn_cadena", "tkn_cadena", "tkn_cadena", "tkn_cadena", "tkn_cadena", "error"},
                                            {"tkn_TF", "error", "error", "error", "error", "error"}
                                        };
                                        int posOP1 = 0, posOP2 = 0;
                                        for (int j = 0; j < errorTable.length; j++) {
                                            if (OP1.equals(errorTable[j][0].toString())) {
                                                posOP1 = j;
                                                break;
                                            }
                                        }
                                        for (int j = 0; j < errorTable[0].length; j++) {
                                            if (OP2.equals(errorTable[0][j].toString())) {
                                                posOP2 = j;
                                                break;
                                            }
                                        }
                                        String resultado = errorTable[posOP1][posOP2].toString();
                                        if (resultado.equals("error")) {
                                            outputTxtArea.setText("Tipos incompatibles '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                    + "en  "
                                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                            break outerloop;
                                        } else {
                                            postFixArray[i] = resultado;
                                            if (i - 3 > 0) {
                                                postFixArray[i - 1] = postFixArray[i - 3];
                                            }
                                        }
                                        break;
                                    }
                                    case "-": {
                                        String OP1 = postFixArray[i - 2];
                                        String OP2 = postFixArray[i - 1];
                                        Object errorTable[][] = {
                                            {"Accion", "tkn_digito", "tkn_decimal", "tkn_char", "tkn_cadena", "tkn_TF"},
                                            {"tkn_digito", "tkn_digito", "tkn_decimal", "error", "error", "error"},
                                            {"tkn_decimal", "tkn_decimal", "tkn_decimal", "error", "error", "error"},
                                            {"tkn_char", "error", "error", "tkn_char", "error", "error"},
                                            {"tkn_cadena", "error", "error", "error", "error", "error"},
                                            {"tkn_TF", "error", "error", "error", "error", "error"}
                                        };
                                        int posOP1 = 0, posOP2 = 0;
                                        for (int j = 0; j < errorTable.length; j++) {
                                            if (OP1.equals(errorTable[j][0].toString())) {
                                                posOP1 = j;
                                                break;
                                            }
                                        }
                                        for (int j = 0; j < errorTable[0].length; j++) {
                                            if (OP2.equals(errorTable[0][j].toString())) {
                                                posOP2 = j;
                                                break;
                                            }
                                        }
                                        String resultado = errorTable[posOP1][posOP2].toString();
                                        if (resultado.equals("error")) {
                                            outputTxtArea.setText("Tipos incompatibles '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                    + "en  "
                                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                            break outerloop;
                                        } else {
                                            postFixArray[i] = resultado;
                                            if (i - 3 > 0) {
                                                postFixArray[i - 1] = postFixArray[i - 3];
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            infixOperation = "";

                            String variableTypeToBeAssigned = "";
                            for (Variables declaredVariable : declaredVariables) {
                                if (variableToBeAssigned.equals(declaredVariable.getVariable())) {
                                    variableTypeToBeAssigned = declaredVariable.getTipoVariable();
                                    break;
                                }
                            }
                            if (variableTypeToBeAssigned.equals("nombro")) {
                                if (!postFixArray[postFixArray.length - 1].equals("tkn_digito")) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (variableTypeToBeAssigned.equals("decimale")) {
                                if (!postFixArray[postFixArray.length - 1].equals("tkn_decimal")) {
                                    if (postFixArray[postFixArray.length - 1].equals("tkn_digito")) {
                                        for (Variables declaredVariable : declaredVariables) {
                                            if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                                declaredVariable.setInicializado(true);
                                                break;
                                            }
                                        }
                                    } else {
                                        outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                                + "en  "
                                                + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                        break;
                                    }
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (variableTypeToBeAssigned.equals("val")) {
                                if (!postFixArray[postFixArray.length - 1].equals("tkn_char")) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (variableTypeToBeAssigned.equals("exist")) {
                                if (!postFixArray[postFixArray.length - 1].equals("tkn_TF")) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (variableTypeToBeAssigned.equals("chain")) {
                                if (!postFixArray[postFixArray.length - 1].equals("tkn_cadena")) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        variableType = "";
                        variableToBeDeclared = false;
                        assignationFlag = false;
                        operationFlag = false;
                    } else if (inicioValue.equals("tkn_asignacion")) {
                        variableToBeAssigned = symbolTable.getModel().getValueAt(tablePosition - 1, 0).toString();
                        assignationFlag = true;
                    } else if (inicioValue.equals("tkn_ID") && variableToBeDeclared) {
                        for (Variables declaredVariable : declaredVariables) {
                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                outputTxtArea.setText("Variable duplicada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                        + "en  "
                                        + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                        + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                break outerloop;
                            }
                        }
                        declaredVariables.add(new Variables(symbolTable.getModel().getValueAt(tablePosition, 0).toString(), variableType));
                    } else if ((inicioValue.equals("tkn_char") || inicioValue.equals("tkn_cadena") || inicioValue.equals("tkn_digito")
                            || inicioValue.equals("tkn_decimal") || inicioValue.equals("tkn_TF") || inicioValue.equals("tkn_ID")) && assignationFlag && !operationFlag
                            && symbolTable.getModel().getValueAt(tablePosition + 1, 1).toString().equals("tkn_pun_coma")) {
                        String variableTypeToBeAssigned = "";
                        for (Variables declaredVariable : declaredVariables) {
                            if (variableToBeAssigned.equals(declaredVariable.getVariable())) {
                                variableTypeToBeAssigned = declaredVariable.getTipoVariable();
                                break;
                            }
                        }
                        if (variableTypeToBeAssigned.equals("nombro")) {
                            if (inicioValue.equals("tkn_ID")) {
                                boolean declared = false;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        declared = true;
                                        break;
                                    }
                                }
                                if (!declared) {
                                    outputTxtArea.setText("Variable no declarada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    if (assignationFlag) {
                                        for (Variables declaredVariable : declaredVariables) {
                                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                                if (!declaredVariable.isInicializado()) {
                                                    outputTxtArea.setText("Variable no inicializada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                            + "en  "
                                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                                    break outerloop;
                                                }
                                            }
                                        }
                                    }
                                }
                                String type1 = null, type2 = null;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        type1 = declaredVariable.getTipoVariable();
                                    }
                                    if (declaredVariable.getVariable().equals(symbolTable.getModel().getValueAt(tablePosition, 0).toString())) {
                                        type2 = declaredVariable.getTipoVariable();
                                    }
                                }
                                if (!type1.equals(type2)) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            } else if (!inicioValue.equals("tkn_digito")) {
                                outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                        + "en  "
                                        + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                        + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                break;
                            } else {
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        declaredVariable.setInicializado(true);
                                        break;
                                    }
                                }
                            }
                        }
                        if (variableTypeToBeAssigned.equals("decimale")) {
                            if (inicioValue.equals("tkn_ID")) {
                                boolean declared = false;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        declared = true;
                                        break;
                                    }
                                }
                                if (!declared) {
                                    outputTxtArea.setText("Variable no declarada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    if (assignationFlag) {
                                        for (Variables declaredVariable : declaredVariables) {
                                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                                if (!declaredVariable.isInicializado()) {
                                                    outputTxtArea.setText("Variable no inicializada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                            + "en  "
                                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                                    break outerloop;
                                                }
                                            }
                                        }
                                    }
                                }
                                String type1 = null, type2 = null;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        type1 = declaredVariable.getTipoVariable();
                                    }
                                    if (declaredVariable.getVariable().equals(symbolTable.getModel().getValueAt(tablePosition, 0).toString())) {
                                        type2 = declaredVariable.getTipoVariable();
                                    }
                                }
                                if (!type1.equals(type2)) {
                                    if (type1.equals("decimale")) {
                                        if (!type2.equals("nombro")) {
                                            outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                                    + "en  "
                                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                            break;
                                        }
                                    } else {
                                        outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                                + "en  "
                                                + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                        break;
                                    }
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            } else if (!inicioValue.equals("tkn_decimal")) {
                                if (inicioValue.equals("tkn_digito")) {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                } else {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                }
                            } else {
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        declaredVariable.setInicializado(true);
                                        break;
                                    }
                                }
                            }
                        }
                        if (variableTypeToBeAssigned.equals("val")) {
                            if (inicioValue.equals("tkn_ID")) {
                                boolean declared = false;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        declared = true;
                                        break;
                                    }
                                }
                                if (!declared) {
                                    outputTxtArea.setText("Variable no declarada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    if (assignationFlag) {
                                        for (Variables declaredVariable : declaredVariables) {
                                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                                if (!declaredVariable.isInicializado()) {
                                                    outputTxtArea.setText("Variable no inicializada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                            + "en  "
                                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                                    break outerloop;
                                                }
                                            }
                                        }
                                    }
                                }
                                String type1 = null, type2 = null;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        type1 = declaredVariable.getTipoVariable();
                                    }
                                    if (declaredVariable.getVariable().equals(symbolTable.getModel().getValueAt(tablePosition, 0).toString())) {
                                        type2 = declaredVariable.getTipoVariable();
                                    }
                                }
                                if (!type1.equals(type2)) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            } else if (!inicioValue.equals("tkn_char")) {
                                outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                        + "en  "
                                        + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                        + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                break;
                            } else {
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        declaredVariable.setInicializado(true);
                                        break;
                                    }
                                }
                            }
                        }
                        if (variableTypeToBeAssigned.equals("exist")) {
                            if (inicioValue.equals("tkn_ID")) {
                                boolean declared = false;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        declared = true;
                                        break;
                                    }
                                }
                                if (!declared) {
                                    outputTxtArea.setText("Variable no declarada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    if (assignationFlag) {
                                        for (Variables declaredVariable : declaredVariables) {
                                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                                if (!declaredVariable.isInicializado()) {
                                                    outputTxtArea.setText("Variable no inicializada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                            + "en  "
                                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                                    break outerloop;
                                                }
                                            }
                                        }
                                    }
                                }
                                String type1 = null, type2 = null;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        type1 = declaredVariable.getTipoVariable();
                                    }
                                    if (declaredVariable.getVariable().equals(symbolTable.getModel().getValueAt(tablePosition, 0).toString())) {
                                        type2 = declaredVariable.getTipoVariable();
                                    }
                                }
                                if (!type1.equals(type2)) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            } else if (!inicioValue.equals("tkn_TF")) {
                                outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                        + "en  "
                                        + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                        + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                break;
                            } else {
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        declaredVariable.setInicializado(true);
                                        break;
                                    }
                                }
                            }
                        }
                        if (variableTypeToBeAssigned.equals("chain")) {
                            if (inicioValue.equals("tkn_ID")) {
                                boolean declared = false;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        declared = true;
                                        break;
                                    }
                                }
                                if (!declared) {
                                    outputTxtArea.setText("Variable no declarada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    if (assignationFlag) {
                                        for (Variables declaredVariable : declaredVariables) {
                                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                                if (!declaredVariable.isInicializado()) {
                                                    outputTxtArea.setText("Variable no inicializada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                            + "en  "
                                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                                    break outerloop;
                                                }
                                            }
                                        }
                                    }
                                }
                                String type1 = null, type2 = null;
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        type1 = declaredVariable.getTipoVariable();
                                    }
                                    if (declaredVariable.getVariable().equals(symbolTable.getModel().getValueAt(tablePosition, 0).toString())) {
                                        type2 = declaredVariable.getTipoVariable();
                                    }
                                }
                                if (!type1.equals(type2)) {
                                    outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                            + "en  "
                                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                    break;
                                } else {
                                    for (Variables declaredVariable : declaredVariables) {
                                        if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                            declaredVariable.setInicializado(true);
                                            break;
                                        }
                                    }
                                }
                            } else if (!inicioValue.equals("tkn_cadena")) {
                                outputTxtArea.setText("Asignacion incompatible en variable '" + variableToBeAssigned + "' "
                                        + "en  "
                                        + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                        + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                break;
                            } else {
                                for (Variables declaredVariable : declaredVariables) {
                                    if (declaredVariable.getVariable().equals(variableToBeAssigned)) {
                                        declaredVariable.setInicializado(true);
                                        break;
                                    }
                                }
                            }
                        }
                    } else if ((inicioValue.equals("tkn_OA1") || inicioValue.equals("tkn_OA2")) && !operationFlag) {
                        operationFlag = true;
                        if (symbolTable.getModel().getValueAt(tablePosition - 1, 1).toString().equals("tkn_ID")) {
                            for (Variables declaredVariable : declaredVariables) {
                                if (symbolTable.getModel().getValueAt(tablePosition - 1, 0).toString().equals(declaredVariable.getVariable())) {
                                    String tipo = declaredVariable.getTipoVariable();
                                    switch (tipo) {
                                        case "nombro":
                                            tipo = "tkn_digito";
                                            break;
                                        case "decimale":
                                            tipo = "tkn_decimal";
                                            break;
                                        case "chain":
                                            tipo = "tkn_cadena";
                                            break;
                                        case "val":
                                            tipo = "tkn_char";
                                            break;
                                        case "exist":
                                            tipo = "tkn_TF";
                                            break;
                                    }
                                    infixOperation = tipo + symbolTable.getModel().getValueAt(tablePosition, 0).toString();
                                    break;
                                }
                            }
                        } else {
                            infixOperation = symbolTable.getModel().getValueAt(tablePosition - 1, 1).toString() + symbolTable.getModel().getValueAt(tablePosition, 0).toString();
                        }

                    } else if (operationFlag) {
                        switch (inicioValue) {
                            case "tkn_OA1":
                            case "tkn_OA2":
                                infixOperation += symbolTable.getModel().getValueAt(tablePosition, 0).toString();
                                break;
                            case "tkn_ID":
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        String tipo = declaredVariable.getTipoVariable();
                                        switch (tipo) {
                                            case "nombro":
                                                tipo = "tkn_digito";
                                                break;
                                            case "decimale":
                                                tipo = "tkn_decimal";
                                                break;
                                            case "chain":
                                                tipo = "tkn_cadena";
                                                break;
                                            case "val":
                                                tipo = "tkn_char";
                                                break;
                                            case "exist":
                                                tipo = "tkn_TF";
                                                break;
                                        }
                                        infixOperation += tipo;
                                        break;
                                    }
                                }
                                break;
                            default:
                                infixOperation += symbolTable.getModel().getValueAt(tablePosition, 1).toString();
                                break;
                        }
                    }
                    if (inicioValue.equals("tkn_ID")) {
                        boolean declared = false;
                        for (Variables declaredVariable : declaredVariables) {
                            if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                declared = true;
                                break;
                            }
                        }
                        if (!declared) {
                            outputTxtArea.setText("Variable no declarada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                    + "en  "
                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                            break;
                        } else {
                            if (assignationFlag) {
                                for (Variables declaredVariable : declaredVariables) {
                                    if (symbolTable.getModel().getValueAt(tablePosition, 0).toString().equals(declaredVariable.getVariable())) {
                                        if (!declaredVariable.isInicializado()) {
                                            outputTxtArea.setText("Variable no inicializada '" + symbolTable.getModel().getValueAt(tablePosition, 0).toString() + "' "
                                                    + "en  "
                                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                                            break outerloop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tablePosition++;
                    inicio.pop();
                } else {
                    outputTxtArea.setText("Error en " + symbolTable.getModel().getValueAt(tablePosition, 1).toString()
                            + " con " + inicioValue + " "
                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                    break;
                }
            } else if (inicioValue.contains("prcs")) {
                for (Object[] tabla1 : tabla) {
                    if (inicio.peek().toString() == tabla1[0]) {
                        for (int j = 0; j < tabla[0].length; j++) {
                            if (symbolTable.getModel().getValueAt(tablePosition, 1).toString().equals(tabla[0][j])) {
                                tableposition = j;
                                break;
                            }
                        }
                        try {
                            int process = (int) tabla1[tableposition] - 1;
                            System.out.println("Proceso: " + (process + 1));
                            inicio.pop();
                            for (int j = procesos[process].length - 1; j >= 0; j--) {
                                inicio.push(procesos[process][j]);
                            }
                            break;
                        } catch (Exception e) {
                            outputTxtArea.setText("Error en " + symbolTable.getModel().getValueAt(tablePosition, 1).toString()
                                    + " con " + inicioValue + " "
                                    + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                                    + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                        }
                    }
                }
                if (inicio.peek().equals(inicioValue)) {
                    outputTxtArea.setText("Error en " + symbolTable.getModel().getValueAt(tablePosition, 1).toString()
                            + " con " + inicioValue + " "
                            + "(Columna: " + symbolTable.getModel().getValueAt(tablePosition, 4).toString() + " "
                            + ",Renglon:" + symbolTable.getModel().getValueAt(tablePosition, 3).toString() + ")");
                    break;
                }
            } else if (inicioValue.equals("")) {
                inicio.pop();
            }
            String pila = "Pila:";
            System.out.println("");
            for (Object inicio1 : inicio) {
                pila = pila + " " + inicio1;
            }
            String variables = "Variables:";
            for (Variables declaredVariable : declaredVariables) {
                variables = variables + " " + declaredVariable.getTipoVariable() + "=" + declaredVariable.getVariable() + "/" + declaredVariable.isInicializado();
            }
            System.out.println(pila);
            System.out.println(symbolTable.getModel().getValueAt(tablePosition, 1).toString());
            System.out.println(variables);
        }
        if (outputTxtArea.getText().equals("Cadena valida")) {
            sintacticosemantico = true;
        } else {
            sintacticosemantico = false;
        }
        ((DefaultTableModel) (symbolTable.getModel())).removeRow(symbolTable.getModel().getRowCount() - 1);
    }

    public void codigoIntermedio_Ensamblador() {
        CodigoEnsamblador ce = new CodigoEnsamblador();
        String codigoFinal = "";
        String condicion = "";
        ArrayList<String[][]> sentencia = new ArrayList<>();
        for (int i = 0; i < symbolTable.getRowCount(); i++) {
            String[][] valoresSentencia = new String[1][2];
            valoresSentencia[0][0] = symbolTable.getValueAt(i, 0).toString();
            valoresSentencia[0][1] = symbolTable.getValueAt(i, 1).toString();
            String caso = valoresSentencia[0][0];
            switch (caso) {
                case "Initialize": {
                    codigoFinal += "INICIO\n";
                    break;
                }
                case "Finalize": {
                    codigoFinal += "\nEND";
                    break;
                }
                case "se": {
                    i++;
                    i++;
                    while (!symbolTable.getValueAt(i, 0).toString().equals(")")) {
                        condicion += symbolTable.getValueAt(i, 0).toString();
                        i++;
                    }
                    i++;
                    i++;
                    int currenti = i;
                    boolean falseFlag = false;
                    for (;; currenti++) {
                        if (symbolTable.getValueAt(currenti, 0).toString().equals("]")) {
                            falseFlag = symbolTable.getValueAt(currenti + 1, 0).toString().equals("altro");
                            break;
                        }
                    }

                    if (falseFlag) {
                        int SSiguiente = ce.nuevaEtiqueta();
                        int BTrue = ce.nuevaEtiqueta();
                        int BFalse = ce.nuevaEtiqueta();

                        if (condicion.contains(">=")) {
                            String[] split = condicion.split(">=");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "jl " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "jmp " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("<=")) {
                            String[] split = condicion.split("<=");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "jg " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "jmp " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("<")) {
                            String[] split = condicion.split("<");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "jge " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "jmp " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains(">")) {
                            String[] split = condicion.split(">");
                            codigoFinal += "\ncmp " + split[0] + ", " + split[1] + "\n";
                            codigoFinal += "jle " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "jmp " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("!=")) {
                            String[] split = condicion.split("!=");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "je " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "jmp " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("==")) {
                            String[] split = condicion.split("==");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "je " + BTrue + "\n";
                            codigoFinal += "jmp " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "jmp " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        }
                    } else {
                        int SSiguiente = ce.nuevaEtiqueta();
                        int BTrue = ce.nuevaEtiqueta();
                        int BFalse = SSiguiente;

                        if (condicion.contains(">=")) {
                            String[] split = condicion.split(">=");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "jl " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("<=")) {
                            String[] split = condicion.split("<=");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "jg " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("<")) {
                            String[] split = condicion.split("<");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "jge " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains(">")) {
                            String[] split = condicion.split(">");
                            codigoFinal += "\ncmp " + split[0] + ", " + split[1] + "\n";
                            codigoFinal += "jle " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("!=")) {
                            String[] split = condicion.split("!=");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "je " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("==")) {
                            String[] split = condicion.split("==");
                            codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                            codigoFinal += "je " + BTrue + "\n";
                            codigoFinal += "jmp " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        }
                    }
                    condicion = "";
                    break;
                }
                case "mentre": {
                    i++;
                    i++;
                    while (!symbolTable.getValueAt(i, 0).toString().equals(")")) {
                        condicion += symbolTable.getValueAt(i, 0).toString();
                        i++;
                    }

                    int SSiguiente = ce.nuevaEtiqueta();
                    int inicio = ce.nuevaEtiqueta();
                    int BTrue = ce.nuevaEtiqueta();
                    int BFalse = SSiguiente;
                    codigoFinal += "\n\n" + inicio + ":\n";

                    if (condicion.contains(">=")) {
                        String[] split = condicion.split(">=");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "jl " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (condicion.contains("<=")) {
                        String[] split = condicion.split("<=");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "jg " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (condicion.contains("<")) {
                        String[] split = condicion.split("<");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "jge " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (condicion.contains(">")) {
                        String[] split = condicion.split(">");
                        codigoFinal += "\ncmp " + split[0] + ", " + split[1] + "\n";
                        codigoFinal += "jle " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (condicion.contains("!=")) {
                        String[] split = condicion.split("!=");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "je " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (condicion.contains("==")) {
                        String[] split = condicion.split("==");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "je " + BTrue + "\n";
                        codigoFinal += "jmp " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    }
                    condicion = "";
                    break;
                }
                case "per": {
                    i++;
                    i++;
                    sentencia.removeAll(sentencia);
                    int runCount = 0;
                    ArrayList<String[][]> sentencia2 = new ArrayList<>();
                    while (!symbolTable.getValueAt(i, 0).toString().equals(")")) {
                        if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                            runCount++;
                            if (runCount == 1) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            }
                        } else {
                            if (runCount == 0) {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            } else if (runCount == 2) {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia2.add(valoresSentencia2);
                            }
                        }
                        condicion += symbolTable.getValueAt(i, 0).toString();
                        i++;
                    }
                    String[] splitCondicion = condicion.split(";");
                    int SSiguiente = ce.nuevaEtiqueta();
                    int inicio = ce.nuevaEtiqueta();
                    int BTrue = ce.nuevaEtiqueta();
                    int BFalse = SSiguiente;
                    codigoFinal += "\n\n" + inicio + ":\n";

                    if (splitCondicion[1].contains(">=")) {
                        String[] split = splitCondicion[1].split(">=");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "jl " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (splitCondicion[1].contains("<=")) {
                        String[] split = splitCondicion[1].split("<=");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "jg " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (splitCondicion[1].contains("<")) {
                        String[] split = splitCondicion[1].split("<");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "jge " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (splitCondicion[1].contains(">")) {
                        String[] split = splitCondicion[1].split(">");
                        codigoFinal += "\ncmp " + split[0] + ", " + split[1] + "\n";
                        codigoFinal += "jle " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (splitCondicion[1].contains("!=")) {
                        String[] split = splitCondicion[1].split("!=");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "je " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (splitCondicion[1].contains("==")) {
                        String[] split = splitCondicion[1].split("==");
                        codigoFinal += "cmp " + split[0] + ", " + split[1] + "\n\n";
                        codigoFinal += "je " + BTrue + "\n";
                        codigoFinal += "jmp " + BFalse + "\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "jmp " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    }
                    splitCondicion[1] = "";
                    break;

                }

                case ";": {
                    codigoFinal += ce.ejecutarSentencia(sentencia);
                    sentencia.removeAll(sentencia);
                    break;
                }
                case "scrivere": {
                    codigoFinal += "\n Code WRITE";
                    break;
                }
                case "leggere": {
                    codigoFinal += "\n Code READ";
                    break;
                }
                default: {
                    sentencia.add(valoresSentencia);
                    break;
                }

            }
        }

        outputTxtArea.setText("//Codigo ensamblado//\n"
                + codigoFinal
                + "\n//Termina codigo//");
        if (jFileChooser1.getSelectedFile() != null) {
            Archivo a = new Archivo();
            a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath(), codigoArea.getText());
            this.setTitle(jFileChooser1.getSelectedFile().getAbsolutePath());
            a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".asm", codigoFinal);
            javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");
        } else {
            if (!codigoArea.getText().equals("")) {
                jFileChooser1 = new JFileChooser();
                jFileChooser1.setBounds(0, 0, 650, 480);
                jFileChooser1.setVisible(true);

                int opc = jFileChooser1.showSaveDialog(null);
                if (opc == 0) {
                    Archivo a = new Archivo();
                    a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego", codigoArea.getText());
                    a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".asm", codigoFinal);
                    this.setTitle("Editor de Texto: " + jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego");
                    javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");

                }
            }
        }
        cambio = false;
    }

    public void codigoIntermedio() {
        CodigoIntermedio ce = new CodigoIntermedio();
        String codigoFinal = "";
        String condicion = "";
        ArrayList<String[][]> sentencia = new ArrayList<>();
        for (int i = 0; i < symbolTable.getRowCount(); i++) {
            String[][] valoresSentencia = new String[1][2];
            valoresSentencia[0][0] = symbolTable.getValueAt(i, 0).toString();
            valoresSentencia[0][1] = symbolTable.getValueAt(i, 1).toString();
            String caso = valoresSentencia[0][0];
            switch (caso) {
                case "Initialize": {
                    codigoFinal += "INICIO\n";
                    break;
                }
                case "Finalize": {
                    codigoFinal += "\nEND";
                    break;
                }
                case "se": {
                    i++;
                    i++;
                    while (!symbolTable.getValueAt(i, 0).toString().equals(")")) {
                        condicion += symbolTable.getValueAt(i, 0).toString();
                        i++;
                    }
                    i++;
                    i++;
                    int currenti = i;
                    boolean falseFlag = false;
                    for (;; currenti++) {
                        if (symbolTable.getValueAt(currenti, 0).toString().equals("]")) {
                            falseFlag = symbolTable.getValueAt(currenti + 1, 0).toString().equals("altro");
                            break;
                        }
                    }

                    if (falseFlag) {
                        int SSiguiente = ce.nuevaEtiqueta();
                        int BTrue = ce.nuevaEtiqueta();
                        int BFalse = ce.nuevaEtiqueta();

                        if (condicion.contains(">=")) {
                            String[] split = condicion.split(">=");
                            codigoFinal += "if " + split[0] + ">= " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "goto " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("<=")) {
                            String[] split = condicion.split("<=");
                            codigoFinal += "if " + split[0] + " <= " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "goto " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("<")) {
                            String[] split = condicion.split("<");
                            codigoFinal += "if " + split[0] + "< " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "goto " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains(">")) {
                            String[] split = condicion.split(">");
                            codigoFinal += "\nif " + split[0] + "> " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "goto " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("!=")) {
                            String[] split = condicion.split("!=");
                            codigoFinal += "if " + split[0] + "!= " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "goto " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("==")) {
                            String[] split = condicion.split("==");
                            codigoFinal += "if " + split[0] + "== " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse + "\n";
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "goto " + SSiguiente + "\n";
                            codigoFinal += "\n" + BFalse + ":\n";
                            i = i + 3;
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        }
                    } else {
                        int SSiguiente = ce.nuevaEtiqueta();
                        int BTrue = ce.nuevaEtiqueta();
                        int BFalse = SSiguiente;

                        if (condicion.contains(">=")) {
                            String[] split = condicion.split(">=");
                            codigoFinal += "if " + split[0] + ">= " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("<=")) {
                            String[] split = condicion.split("<=");
                            codigoFinal += "if " + split[0] + "<= " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("<")) {
                            String[] split = condicion.split("<");
                            codigoFinal += "if " + split[0] + "< " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains(">")) {
                            String[] split = condicion.split(">");
                            codigoFinal += "\nif " + split[0] + "> " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        } else if (condicion.contains("!=")) {
                            String[] split = condicion.split("!=");
                            codigoFinal += "if " + split[0] + "!= " + split[1] + " goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;

                        } else if (condicion.contains("==")) {
                            String[] split = condicion.split("==");
                            codigoFinal += "if " + split[0] + "== " + split[1] + "goto " + BTrue + "\n";
                            codigoFinal += "goto " + BFalse;
                            codigoFinal += BTrue + ":\n";
                            sentencia.removeAll(sentencia);
                            while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                                if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                    codigoFinal += ce.ejecutarSentencia(sentencia);
                                    sentencia.removeAll(sentencia);
                                } else {
                                    String[][] valoresSentencia2 = new String[1][2];
                                    valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                    valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                    sentencia.add(valoresSentencia2);
                                }
                                i++;
                            }
                            codigoFinal += "\n" + SSiguiente + ":\n";
                            i++;
                        }
                    }
                    condicion = "";
                    break;
                }
                case "mentre": {
                    i++;
                    i++;
                    while (!symbolTable.getValueAt(i, 0).toString().equals(")")) {
                        condicion += symbolTable.getValueAt(i, 0).toString();
                        i++;
                    }

                    int SSiguiente = ce.nuevaEtiqueta();
                    int inicio = ce.nuevaEtiqueta();
                    int BTrue = ce.nuevaEtiqueta();
                    int BFalse = SSiguiente;
                    codigoFinal += "\n\n" + inicio + ":\n";

                    if (condicion.contains(">=")) {
                        String[] split = condicion.split(">=");
                        codigoFinal += "if " + split[0] + ">= " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (condicion.contains("<=")) {
                        String[] split = condicion.split("<=");
                        codigoFinal += "if " + split[0] + "<= " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (condicion.contains("<")) {
                        String[] split = condicion.split("<");
                        codigoFinal += "if " + split[0] + "< " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (condicion.contains(">")) {
                        String[] split = condicion.split(">");
                        codigoFinal += "\nif " + split[0] + "> " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (condicion.contains("!=")) {
                        String[] split = condicion.split("!=");
                        codigoFinal += "if " + split[0] + "!= " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (condicion.contains("==")) {
                        String[] split = condicion.split("==");
                        codigoFinal += "if " + split[0] + "== " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    }
                    condicion = "";
                    break;
                }
                case "per": {
                    i++;
                    i++;
                    sentencia.removeAll(sentencia);
                    int runCount = 0;
                    ArrayList<String[][]> sentencia2 = new ArrayList<>();
                    while (!symbolTable.getValueAt(i, 0).toString().equals(")")) {
                        if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                            runCount++;
                            if (runCount == 1) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            }
                        } else {
                            if (runCount == 0) {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            } else if (runCount == 2) {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia2.add(valoresSentencia2);
                            }
                        }
                        condicion += symbolTable.getValueAt(i, 0).toString();
                        i++;
                    }
                    String[] splitCondicion = condicion.split(";");
                    int SSiguiente = ce.nuevaEtiqueta();
                    int inicio = ce.nuevaEtiqueta();
                    int BTrue = ce.nuevaEtiqueta();
                    int BFalse = SSiguiente;
                    codigoFinal += "\n\n" + inicio + ":\n";

                    if (splitCondicion[1].contains(">=")) {
                        String[] split = splitCondicion[1].split(">=");
                        codigoFinal += "if " + split[0] + ">= " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (splitCondicion[1].contains("<=")) {
                        String[] split = splitCondicion[1].split("<=");
                        codigoFinal += "if " + split[0] + "<= " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (splitCondicion[1].contains("<")) {
                        String[] split = splitCondicion[1].split("<");
                        codigoFinal += "if " + split[0] + "< " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (splitCondicion[1].contains(">")) {
                        String[] split = splitCondicion[1].split(">");
                        codigoFinal += "\nif " + split[0] + "> " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    } else if (splitCondicion[1].contains("!=")) {
                        String[] split = splitCondicion[1].split("!=");
                        codigoFinal += "if " + split[0] + "!= " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";

                    } else if (splitCondicion[1].contains("==")) {
                        String[] split = splitCondicion[1].split("==");
                        codigoFinal += "if " + split[0] + "== " + split[1] + " goto " + BTrue + "\n";
                        codigoFinal += "goto " + BFalse;
                        codigoFinal += BTrue + ":\n";
                        codigoFinal += BTrue + ":\n";
                        sentencia.removeAll(sentencia);
                        while (!symbolTable.getValueAt(i, 0).toString().equals("]")) {
                            if (symbolTable.getValueAt(i, 0).toString().equals(";")) {
                                codigoFinal += ce.ejecutarSentencia(sentencia);
                                sentencia.removeAll(sentencia);
                            } else {
                                String[][] valoresSentencia2 = new String[1][2];
                                valoresSentencia2[0][0] = symbolTable.getValueAt(i, 0).toString();
                                valoresSentencia2[0][1] = symbolTable.getValueAt(i, 1).toString();
                                sentencia.add(valoresSentencia2);
                            }
                            i++;
                        }
                        codigoFinal += ce.ejecutarSentencia(sentencia2);
                        codigoFinal += "goto " + inicio + "\n";
                        codigoFinal += "\n" + SSiguiente + ":\n";
                    }
                    splitCondicion[1] = "";
                    break;

                }

                case ";": {
                    codigoFinal += ce.ejecutarSentencia(sentencia);
                    sentencia.removeAll(sentencia);
                    break;
                }
                case "scrivere": {
                    codigoFinal += "\n Code WRITE";
                    break;
                }
                case "leggere": {
                    codigoFinal += "\n Code READ";
                    break;
                }
                default: {
                    sentencia.add(valoresSentencia);
                    break;
                }

            }
        }

        outputTxtArea.setText("//Codigo intermedio//\n"
                + codigoFinal
                + "\n//Termina codigo//");
        if (jFileChooser1.getSelectedFile() != null) {
            Archivo a = new Archivo();
            a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath(), codigoArea.getText());
            this.setTitle(jFileChooser1.getSelectedFile().getAbsolutePath());
            a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".txt", codigoFinal);
            javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");
        } else {
            if (!codigoArea.getText().equals("")) {
                jFileChooser1 = new JFileChooser();
                jFileChooser1.setBounds(0, 0, 650, 480);
                jFileChooser1.setVisible(true);

                int opc = jFileChooser1.showSaveDialog(null);
                if (opc == 0) {
                    Archivo a = new Archivo();
                    a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego", codigoArea.getText());
                    a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".asm", codigoFinal);
                    this.setTitle("Editor de Texto: " + jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego");
                    javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");

                }
            }
        }
        cambio = false;
    }

    /**
     * Creates new form EditorDeTextos
     */
    public EditorDeTextos() {
        initComponents();
        jFileChooser1.setSelectedFile(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jDialog2 = new javax.swing.JDialog();
        jColorChooser2 = new javax.swing.JColorChooser();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        codigoArea = new ModifiedTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputTxtArea = new ModifiedTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        lineCounter = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        symbolTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        nuevoMenu = new javax.swing.JMenuItem();
        abrirMenu = new javax.swing.JMenuItem();
        guardarMenu = new javax.swing.JMenuItem();
        guardarComoMenu = new javax.swing.JMenuItem();
        salirMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        formatoMenu = new javax.swing.JMenu();
        fuenteMenu = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jDialog2.setModal(true);

        jButton2.setText("Choose");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jColorChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addComponent(jColorChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Editor de Texto: Sin Titulo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        codigoArea.setColumns(20);
        codigoArea.setLineWrap(true);
        codigoArea.setRows(5);
        codigoArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoAreaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoAreaKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(codigoArea);

        jLabel1.setText("Output");

        outputTxtArea.setColumns(20);
        outputTxtArea.setRows(5);
        outputTxtArea.setEnabled(false);
        jScrollPane2.setViewportView(outputTxtArea);

        lineCounter.setEditable(false);
        lineCounter.setColumns(2);
        lineCounter.setLineWrap(true);
        lineCounter.setRows(2);
        lineCounter.setTabSize(2);
        lineCounter.setText("1");
        lineCounter.setAutoscrolls(false);
        lineCounter.setCaretColor(new java.awt.Color(51, 51, 255));
        lineCounter.setFocusable(false);
        jScrollPane3.setViewportView(lineCounter);

        symbolTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Id_Lexema", "Num Lexema", "Renglon", "Columna"
            }
        ));
        jScrollPane4.setViewportView(symbolTable);

        jMenu1.setText("Archivo");

        nuevoMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        nuevoMenu.setText("Nuevo");
        nuevoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoMenuActionPerformed(evt);
            }
        });
        jMenu1.add(nuevoMenu);

        abrirMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        abrirMenu.setText("Abrir");
        abrirMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirMenuActionPerformed(evt);
            }
        });
        jMenu1.add(abrirMenu);

        guardarMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        guardarMenu.setText("Guardar");
        guardarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarMenuActionPerformed(evt);
            }
        });
        jMenu1.add(guardarMenu);

        guardarComoMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guardarComoMenu.setText("Guardar Como");
        guardarComoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarComoMenuActionPerformed(evt);
            }
        });
        jMenu1.add(guardarComoMenu);

        salirMenu.setText("Salir");
        salirMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenuActionPerformed(evt);
            }
        });
        jMenu1.add(salirMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edicion");

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Deshacer");
        jMenu2.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Cortar");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Copiar");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItem9.setText("Eliminar");
        jMenu2.add(jMenuItem9);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Buscar");
        jMenu2.add(jMenuItem10);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem11.setText("Reemplazar");
        jMenu2.add(jMenuItem11);

        jMenuBar1.add(jMenu2);

        formatoMenu.setText("Formato");

        fuenteMenu.setText("Fuente");
        fuenteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fuenteMenuActionPerformed(evt);
            }
        });
        formatoMenu.add(fuenteMenu);

        jMenuBar1.add(formatoMenu);

        jMenu4.setText("Ayuda");

        jMenuItem13.setText("Acerca del editor");
        jMenu4.add(jMenuItem13);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Validar");

        jMenuItem1.setText("Lexico");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Sintactico y Semantico");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setText("Codigo Intermedio");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem5.setText("Codigo Ensamblador");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem4.setText("Ejecutar Todo");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarMenuActionPerformed
        guardar();
    }//GEN-LAST:event_guardarMenuActionPerformed

    private void salirMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirMenuActionPerformed
        String[] split = this.getTitle().split(": ");
        ventanaSalida ventana = new ventanaSalida(this, split[1], 1);
        ventana.setVisible(true);
        ventana.setModal(true);
    }//GEN-LAST:event_salirMenuActionPerformed

    private void guardarComoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarComoMenuActionPerformed
        jFileChooser1.setBounds(0, 0, 650, 480);
        jFileChooser1.setVisible(true);

        int opc = jFileChooser1.showSaveDialog(null);
        if (opc == 0) {
            Archivo a = new Archivo();
            a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego", codigoArea.getText());
            this.setTitle("Editor de Texto: " + jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego");
            javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");
        }
        cambio = false;
    }//GEN-LAST:event_guardarComoMenuActionPerformed

    private void fuenteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fuenteMenuActionPerformed
        jDialog2.setBounds(0, 0, 665, 450);
        jDialog2.setVisible(true);
    }//GEN-LAST:event_fuenteMenuActionPerformed

    private void abrirMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirMenuActionPerformed
        if (cambio) {
            String[] split = this.getTitle().split(": ");
            ventanaSalida ventana = new ventanaSalida(this, split[1], 3);
            ventana.setVisible(true);
            ventana.setModal(true);
        } else {
            jFileChooser1 = new JFileChooser();
            jFileChooser1.setBounds(0, 0, 650, 700);
            jFileChooser1.setVisible(true);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Diego Files", "diego");
            jFileChooser1.setFileFilter(filter);
            int opc = jFileChooser1.showOpenDialog(null);
            if (opc == 0) {
                Archivo a = new Archivo();
                codigoArea.setText(a.cargar(jFileChooser1.getSelectedFile().getAbsolutePath()));
                this.setTitle("Editor de Texto: " + jFileChooser1.getSelectedFile().getAbsolutePath());
                javax.swing.JOptionPane.showMessageDialog(null, "Se Abrio con exito");
            }
            cambio = false;
        }
        cambio = true;
        if (codigoArea.getLineCount() > linecount) {
            while (codigoArea.getLineCount() > linecount) {
                linecount++;
                lineCounter.setText(lineCounter.getText() + "\n" + linecount);
            }
        } else if (codigoArea.getLineCount() < linecount) {
            while (codigoArea.getLineCount() < linecount) {
                linecount--;
                lineCounter.setText("1");
                for (int i = 2; i <= linecount; i++) {
                    lineCounter.setText(lineCounter.getText() + "\n" + i);
                }
            }
        }
    }//GEN-LAST:event_abrirMenuActionPerformed

    private void nuevoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoMenuActionPerformed
        if (!cambio) {
            jFileChooser1 = new JFileChooser();
            this.setTitle("Editor de Texto: Sin Titulo");
            codigoArea.setText("");
            cambio = false;
        } else {
            String[] split = this.getTitle().split(": ");
            ventanaSalida ventana = new ventanaSalida(this, split[1], 2);
            ventana.setVisible(true);
            ventana.setModal(true);
        }
    }//GEN-LAST:event_nuevoMenuActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!cambio) {
            System.exit(0);
        } else {
            String[] split = this.getTitle().split(": ");
            ventanaSalida ventana = new ventanaSalida(this, split[1], 1);
            ventana.setVisible(true);
            ventana.setModal(true);
        }
    }//GEN-LAST:event_formWindowClosing

    private void codigoAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoAreaKeyTyped
        cambio = true;
        if (codigoArea.getLineCount() > linecount) {
            while (codigoArea.getLineCount() > linecount) {
                linecount++;
                lineCounter.setText(lineCounter.getText() + "\n" + linecount);
            }
        } else if (codigoArea.getLineCount() < linecount) {
            while (codigoArea.getLineCount() < linecount) {
                linecount--;
                lineCounter.setText("1");
                for (int i = 2; i <= linecount; i++) {
                    lineCounter.setText(lineCounter.getText() + "\n" + i);
                }
            }
        }
    }//GEN-LAST:event_codigoAreaKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DefaultTableModel dtmTable = (DefaultTableModel) symbolTable.getModel();
        dtmTable.setNumRows(0);
        symbolTableCount = 1;
        validarLexema(codigoArea.getText());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void codigoAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoAreaKeyPressed
        cambio = true;
        if (codigoArea.getLineCount() > linecount) {
            while (codigoArea.getLineCount() > linecount) {
                linecount++;
                lineCounter.setText(lineCounter.getText() + "\n" + linecount);
            }
        } else if (codigoArea.getLineCount() < linecount) {
            while (codigoArea.getLineCount() < linecount) {
                linecount--;
                lineCounter.setText("1");
                for (int i = 2; i <= linecount; i++) {
                    lineCounter.setText(lineCounter.getText() + "\n" + i);
                }
            }
        }
    }//GEN-LAST:event_codigoAreaKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (lexico) {
            analizadorSintactico_Semantico();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Verifique lexico");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (sintacticosemantico) {
            codigoIntermedio();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Verifique sintactico y semantico");
        }

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        DefaultTableModel dtmTable = (DefaultTableModel) symbolTable.getModel();
        dtmTable.setNumRows(0);
        symbolTableCount = 1;
        validarLexema(codigoArea.getText());
        if (lexico) {
            analizadorSintactico_Semantico();
            if (sintacticosemantico) {
                codigoIntermedio();
                codigoIntermedio_Ensamblador();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Verifique sintactico y semantico");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Verifique lexico");
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (sintacticosemantico) {
            codigoIntermedio_Ensamblador();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Verifique sintactico y semantico");
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    public class ventanaSalida extends JDialog {

        public ventanaSalida(JFrame parent, String nombreArchivo, final int tipo) {
            super(parent, true);
            JPanel jPanel1 = new javax.swing.JPanel();
            JLabel cambiosLbl = new javax.swing.JLabel();
            JLabel tituloLbl = new javax.swing.JLabel();
            JButton guardarButton = new javax.swing.JButton();
            JButton noGuardarButton = new javax.swing.JButton();
            JButton cancelarButton = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Editor de Texto");
            setBackground(new java.awt.Color(255, 255, 255));

            jPanel1.setBackground(new java.awt.Color(255, 255, 255));

            cambiosLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            cambiosLbl.setForeground(new java.awt.Color(0, 51, 255));
            cambiosLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            cambiosLbl.setText("¿Desea guardar los cambio a ");

            tituloLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            tituloLbl.setForeground(new java.awt.Color(0, 51, 255));
            tituloLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            tituloLbl.setText(nombreArchivo + "?");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cambiosLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tituloLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(cambiosLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tituloLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
            );

            guardarButton.setText("Guardar");
            guardarButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    guardar();
                    switch (tipo) {
                        case 1://Salida
                            System.exit(0);
                            break;
                        case 2://Nuevo
                            cambio = false;
                            setTitle("Editor de Texto: Sin Titulo");
                            codigoArea.setText("");
                            break;
                        case 3://Abrir
                            abrir();
                            dispose();
                            break;
                    }
                }
            });

            noGuardarButton.setText("No guardar");
            noGuardarButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    switch (tipo) {
                        case 1://Salida
                            System.exit(0);
                            break;
                        case 2://Nuevo
                            cambio = false;
                            setTitle("Editor de Texto: Sin Titulo");
                            codigoArea.setText("");
                            break;
                        case 3://Abrir
                            dispose();
                            abrir();
                            break;
                    }
                }
            });

            cancelarButton.setText("Cancelar");
            cancelarButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dispose();
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(121, 121, 121)
                            .addComponent(guardarButton)
                            .addGap(18, 18, 18)
                            .addComponent(noGuardarButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                            .addComponent(cancelarButton)
                            .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(guardarButton)
                                    .addComponent(noGuardarButton)
                                    .addComponent(cancelarButton))
                            .addContainerGap())
            );

            pack();
        }
    }

    private void guardar() {
        if (jFileChooser1.getSelectedFile() != null) {
            Archivo a = new Archivo();
            a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath(), codigoArea.getText());
            this.setTitle(jFileChooser1.getSelectedFile().getAbsolutePath());
            javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");
        } else {
            if (!codigoArea.getText().equals("")) {
                jFileChooser1 = new JFileChooser();
                jFileChooser1.setBounds(0, 0, 650, 480);
                jFileChooser1.setVisible(true);

                int opc = jFileChooser1.showSaveDialog(null);
                if (opc == 0) {
                    Archivo a = new Archivo();
                    a.guardar(jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego", codigoArea.getText());
                    this.setTitle("Editor de Texto: " + jFileChooser1.getSelectedFile().getAbsolutePath() + ".diego");
                    javax.swing.JOptionPane.showMessageDialog(null, "Se Guardo con exito");

                }
            }
        }
        cambio = false;
    }

    private void abrir() {
        jFileChooser1 = new JFileChooser();
        jFileChooser1.setBounds(0, 0, 650, 700);
        jFileChooser1.setVisible(true);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Diego Files", "diego");
        jFileChooser1.setFileFilter(filter);
        int opc = jFileChooser1.showOpenDialog(null);
        if (opc == 0) {
            Archivo a = new Archivo();
            codigoArea.setText(a.cargar(jFileChooser1.getSelectedFile().getAbsolutePath()));
            this.setTitle("Editor de Texto: " + jFileChooser1.getSelectedFile().getAbsolutePath());
            javax.swing.JOptionPane.showMessageDialog(null, "Se Abrio con exito");
        }
        cambio = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorDeTextos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorDeTextos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorDeTextos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorDeTextos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorDeTextos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrirMenu;
    private static javax.swing.JTextArea codigoArea;
    private javax.swing.JMenu formatoMenu;
    private javax.swing.JMenuItem fuenteMenu;
    private javax.swing.JMenuItem guardarComoMenu;
    private javax.swing.JMenuItem guardarMenu;
    private javax.swing.JButton jButton2;
    private javax.swing.JColorChooser jColorChooser2;
    private javax.swing.JDialog jDialog2;
    public static javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea lineCounter;
    private javax.swing.JMenuItem nuevoMenu;
    private javax.swing.JTextArea outputTxtArea;
    private javax.swing.JMenuItem salirMenu;
    public static javax.swing.JTable symbolTable;
    // End of variables declaration//GEN-END:variables
}
