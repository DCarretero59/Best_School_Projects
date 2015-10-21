/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_servidor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
public class Cliente extends JFrame {

    private JTextField campoIntroducir;
    private JTextArea areaPantalla;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private String mensaje = "";
    private String servidorChat;
    private Socket cliente;

    public Cliente(String host) {
        super("Cliente");
        servidorChat = host;
        Container contenedor = getContentPane();
        campoIntroducir = new JTextField();
        campoIntroducir.setEditable(false);
        campoIntroducir.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evento) {
                enviarDatos(evento.getActionCommand());
                campoIntroducir.setText("");
            }
        });
        contenedor.add(campoIntroducir, BorderLayout.NORTH);
        areaPantalla = new JTextArea();
        contenedor.add(new JScrollPane(areaPantalla), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    }

    private void ejecutarCliente() {
        try {
            conectarAServidor();
            obtenerFlujos();
            procesarConexion();
        } catch (EOFException execpcionEOF) {
            System.err.println("El cliente termino la conexion");
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
    
    private void conectarAServidor() throws IOException{
        mostrarMensaje("Intentando realizar conexion\n");
        cliente = new Socket(InetAddress.getByName(servidorChat),5050);
        mostrarMensaje("Conectado a " + cliente.getInetAddress().getHostName());
    }
    
    private void obtenerFlujos() throws IOException{
        salida = new ObjectOutputStream(cliente.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(cliente.getInputStream());
        mostrarMensaje("\nSe recibieron los flujos de E/s\n");
    }
    
    private void procesarConexion() throws IOException{
        establecerCampoTextoEditable(true);
        do {            
            try {
                mensaje = (String)entrada.readObject();
                mostrarMensaje("\n" + mensaje);
            } catch (ClassNotFoundException excepcionClaseNoEncontrada) {
                mostrarMensaje("\nSe recibio un objeto de tipo desconocido");
            }
        } while (!mensaje.equals("SERVIDOR>>> TERMINAR"));
    }
    
    private void cerrarConexion(){
        mostrarMensaje("\nCerrando conexion");
        establecerCampoTextoEditable(false);
        try {
            salida.close();
            entrada.close();
            cliente.close();
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }
    
    private void enviarDatos(String mensaje){
        try {
            salida.writeObject("CLIENTE>>> " + mensaje);
            salida.flush();
            mostrarMensaje("\nCLIENTE>>> "+mensaje);
        } catch (Exception e) {
            areaPantalla.append("\nError al escribir el objeto");
        }
    }
    
    private void mostrarMensaje(final String mensaje){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                areaPantalla.append(mensaje);
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
        Cliente aplicacion;
        if (args.length == 0) {
            aplicacion = new Cliente("localhost");
        }else{
            aplicacion = new Cliente(args[0]);
        }
        aplicacion.setDefaultCloseOperation(2);
        aplicacion.ejecutarCliente();
    }
}
