package Metodos;

/**
 *
 * @author Diego
 */
public class Archivo {

    public void guardar(String ruta, String contenido) {
        try {
            java.io.FileWriter f = new java.io.FileWriter(ruta);
            f.write(contenido);
            f.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String cargar(String ruta) {

        try {
            java.io.File f = new java.io.File(ruta);
            String contenido = "";
            try (java.io.FileReader r = new java.io.FileReader(f)) {
                java.io.BufferedReader b = new java.io.BufferedReader(r);
                while (b.ready()) {
                    if (contenido.isEmpty()) {
                    contenido = contenido + b.readLine();
                    }
                    contenido = contenido + "\n"+ b.readLine();
                }
            }
            return contenido;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String[] cargarArreglo(String ruta) {
        try {
            java.io.File f = new java.io.File(ruta);
            java.io.FileReader r = new java.io.FileReader(f);
            java.io.BufferedReader b = new java.io.BufferedReader(r);
            String contenido = b.readLine();

            String lineas[];
            lineas = contenido.split(";");
            r.close();

            return lineas;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String extraerValor(String param, String[] arreglo) {
        //PARAM: RENTA
        // DEVUELVE EL VALOR DE LA RENTA. EJEMPLO: 50
        param = param.toLowerCase();
        String res = "";
        for (int i = 0; i < arreglo.length; i++) {
            String x = arreglo[i];
            String[] valores = x.split("=");
            String parametro = valores[0];
            parametro = parametro.trim().toLowerCase();
            String valor = valores[1];
            if (parametro.equals(param) == true) {
                res = valor;
            }

        }
        return res;
    }

}
