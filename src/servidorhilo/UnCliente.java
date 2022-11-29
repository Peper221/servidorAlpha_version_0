/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidorhilo;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author JOSEMARIO
 */
class UnCliente implements Runnable {
    
    
    final DataInputStream entrada;
    final DataOutputStream salida;
    final String nombre;
 
    public UnCliente(Socket s, String nombre) throws IOException{
    
                entrada = new DataInputStream(s.getInputStream());
                salida = new DataOutputStream(s.getOutputStream());
                this.nombre =  nombre; 
      
    }

    @Override
    public void run() {
        String mensaje;
        while(true){
            try {
                mensaje = entrada.readUTF();
   
                System.out.println(mensaje);
                String[] division = mensaje.split("@");
               
                 for (UnCliente cliente : Servidor.lista.values()) {
                     
                     if (!cliente.nombre.equals(nombre)) {
                        
                         
                         if (mensaje.contains("@")) {
                             if (division.length == 2) {
                                if (cliente.nombre.equalsIgnoreCase(division[1])) {
                                    cliente.salida.writeUTF("Enviado por cliente: "+ nombre + " : " + division[0]);
                                 }
    
                             }
                         }else{
                            cliente.salida.writeUTF("Enviado por cliente: "+ nombre + " : " + mensaje);
                             
                         }
 
                        
                     }else{System.out.println("sex");}
                    
                }
            } catch (Exception e) {
                System.out.println("Error en el servidor");
                System.exit(1);
            }
        }
    }
}
