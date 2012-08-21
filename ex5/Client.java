/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author rmb
 */
public class Client {
    
    private Socket sock;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inServer;
    
    
    public Client() {
        try{
            sock = new Socket("localhost", 3030);
        }
        catch(UnknownHostException e) {}
        catch(IOException e) {}
    }
    
    public void start() {
        
        try{
            in = new BufferedReader(new InputStreamReader(System.in));        
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            inServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        catch(IOException e) {}
        
        while(sock.isConnected()) {
            try{                
                out.write(in.readLine());
                System.out.println(inServer.readLine());
            }
            catch(IOException e){}            
        }
        
        try{
            sock.shutdownInput();
            sock.shutdownOutput();
            sock.close();
        }
        catch(IOException e) {}
    }
    
    public static void main (String[] args) {
        Client c = new Client();
        c.start();
    }
}
