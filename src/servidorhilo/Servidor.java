 
package servidorhilo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author JOSEMARIO
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    
    static HashMap<String,UnCliente> lista  = new HashMap<String, UnCliente>();
   
    
     static DataInputStream entrada=null;
     static DataOutputStream salida=null;
    
    static ArrayList<String> nombres = new ArrayList<String>();
    
    public static void main(String[] args){
        try {
             ServerSocket socketServidor = new ServerSocket(8080);
             System.out.println("servidor conectado");
             
       while(true){
        Socket s = socketServidor.accept();
        entrada = new DataInputStream(s.getInputStream());
        salida = new DataOutputStream(s.getOutputStream());
        
        boolean admision= true;
        boolean conexion=false;
        
                String nombre =  entrada.readUTF();
                
                if (!(nombre ==null)) {
                    
                    if (nombres.isEmpty()) {
                        nombres.add(nombre);
                        conexion=true;
                    }else{
                         
                        for (int i = 0; i < nombres.size(); i++) {
                            if (nombre.equals(nombres.get(i))) {
                                salida.writeUTF("El nombre ya esta ocupado");
                                admision=false;
                            }  
                        }
                        if (admision) {
                            nombres.add(nombre);
                            conexion=true; 
                        }
        
                    }
                    if (conexion) {
       
                            UnCliente unCliente = new UnCliente(s, nombre);
                            Thread hilo = new Thread(unCliente);
                            lista.put(nombre, unCliente);
                            hilo.start();
                        }
    
                    
                }else{
                 salida.writeUTF("El nombre ya esta ocupado");
                }
        } 
 
       }catch (Exception e) {
            System.out.println("Error en servidor");
            System.exit(1);
        }
       
    }
    
}
