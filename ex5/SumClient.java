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


/**
 *
 * @author rmb
 */
public class SumClient {
    
    private Socket sock;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inServer;
    
    public SumClient(){
        try{
            sock = new Socket("localhost",3030);
        }
        catch(IOException e){}        
    }
    
    public void start() {
        try{
            in = new BufferedReader(new InputStreamReader(System.in));
            inServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            String s = new String();            
            
            while(!s.equals("q")) {                
                s = in.readLine();     
                
                out.write(s);
                out.newLine();
                out.flush();                                                           
            }
                        
            System.out.println(inServer.readLine());
            System.out.println("--END--");
        }
        catch(IOException e){
            System.out.println("erro");
        }
        
        
        
    }
    
    public static void main(String[] args) {
      
        SumClient c = new SumClient();
        c.start();
    }
    
}
