/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

/**
 *
 * @author DCarretero
 */
public class Variables {
    private String variable;
    private String tipoVariable;
    private boolean inicializado = false;

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }


    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getTipoVariable() {
        return tipoVariable;
    }

    public void setTipoVariable(String tipoVariable) {
        this.tipoVariable = tipoVariable;
    }

    public Variables(String variable, String tipoVariable) {
        this.variable = variable;
        this.tipoVariable = tipoVariable;
    }
    
    
}
