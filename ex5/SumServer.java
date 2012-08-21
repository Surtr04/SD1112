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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rmb
 */
public class SumServer {
   
    private ServerSocket s;
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private int sum;
    
    public SumServer() {
        try{
            s = new ServerSocket(3030);
            sum = 0;
        }
        catch(IOException e){}        
    }
    
    public void start() {
        
        try{
            client = s.accept();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            
            String s = in.readLine();
            
            while(!s.equals("q")) {
                try{
                    sum += Integer.parseInt(s);                    
                    System.out.println(s);
                }
                catch(NumberFormatException e){
                    e.toString();
                }
                s = in.readLine();
            }                                                                                    
            
            out.write(String.valueOf(sum));            
            out.flush();
            
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
        }
        catch(IOException e) {}
        
        
        
    }
    
    public static void main(String[] args) {
        
        SumServer s = new SumServer();
        s.start();
        
    }
    
}
