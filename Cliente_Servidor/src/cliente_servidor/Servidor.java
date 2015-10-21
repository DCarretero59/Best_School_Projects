/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_servidor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Diego
 */
public class Servidor extends JFrame {

    private JTextField campoIntroducir;
    private final JTextArea areaPantalla;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ServerSocket servidor;
    private Socket conexion;
    private int contador = 1;

    //Configurar GUI
    public Servidor() {
        super("Servidor");
        Container contenedor = getContentPane();
        //crear campoIntroducir y registrar componente de escucha
        campoIntroducir = new JTextField();
        campoIntroducir.setEditable(false);
        campoIntroducir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enviarDatos(e.getActionCommand());
                campoIntroducir.setText("");
            }
        });

        contenedor.add(campoIntroducir, BorderLayout.NORTH);

        //crar areaPantalla
        areaPantalla = new JTextArea();
        contenedor.add(new JScrollPane(areaPantalla), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    } //fin del constructor de servidor

    public void ejecutarServidor() {
        try {
            servidor = new ServerSocket(5050, 100);
            while (true) {
                try {
                    esperarConexion();
                    obtenerFlujos();
                    procesarConexion();
                } catch (EOFException e) {
                    System.err.println("El servidor termino la conexion");
                } finally {
                    cerrarConexion();
                    contador++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void esperarConexion() throws IOException {
        mostrarMensaje("Esperando una conexion\n");
        conexion = servidor.accept();
        mostrarMensaje("Conexion " + contador + " recibida de: " + conexion.getInetAddress().getHostName());
    }

    private void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(conexion.getOutputStream());
        salida.flush();

        entrada = new ObjectInputStream(conexion.getInputStream());

        mostrarMensaje("\nSe recibieron los flujos de E.s\n");
    }

    private void procesarConexion() throws IOException {
        String mensaje = "Conexion exitosa";
        enviarDatos(mensaje);
        establecerCampoTextoEditable(true);

        do {
            try {
                mensaje = (String) entrada.readObject();
                mostrarMensaje("\n " + mensaje);
            } catch (ClassNotFoundException excepcionClaseNoEncontrada) {
                mostrarMensaje("\nSe recibio un tipo de objeto desconocido");
            }
        } while (!mensaje.equals("CLIENTE>>> TERMINAR"));
    }

    private void cerrarConexion() {
        mostrarMensaje("\nFinalizando la conexion\n");
        establecerCampoTextoEditable(false);
        try {
            salida.close();
            entrada.close();
            conexion.close();
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }

    private void enviarDatos(String mensaje) {
        try {
            salida.writeObject("SERVIDOR>>> " + mensaje);
            salida.flush();
            mostrarMensaje("\nSERVIDOR>>> " + mensaje);
        } catch (IOException e) {
            areaPantalla.append("\n Error al escribir objeto");
        }
    }
    
    private void mostrarMensaje(final String mensajeAMostrar){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                areaPantalla.append(mensajeAMostrar);
                areaPantalla.setCaretPosition(areaPantalla.getText().length());
            }
        });
    }
    
    private void establecerCampoTextoEditable(final boolean editable){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                campoIntroducir.setEditable(editable);
            }
        });
    }
    
    public static void main(String args[]){
        Servidor aplicacion = new Servidor();
        aplicacion.setDefaultCloseOperation(2);
        aplicacion.ejecutarServidor();
    }
}
