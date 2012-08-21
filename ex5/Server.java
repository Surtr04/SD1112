/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex5;

import com.sun.tools.internal.ws.processor.util.IndentingWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rmb
 */
public class Server {
 
    private ServerSocket s;
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    
    public Server() {
        try{
            s = new ServerSocket(3030);
        }
        catch(IOException e) {}             
    }
    
    public void start() {
        try {
            client = s.accept();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));                        
            String s = in.readLine();
            while(!s.equals("q")) {                                
                out.write(s);
                out.newLine();
                out.flush();
                s = in.readLine();
            }
            client.shutdownInput();
            client.shutdownOutput();
            out.close();
            in.close();         
            client.close();
        }
        catch(IOException e) {}
        
    }
    
    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }
    
}
