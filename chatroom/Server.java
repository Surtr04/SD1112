/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    HashMap<String,BufferedWriter> connections;
    ServerSocket sock;
    private int port;        
    
    public Server(int port) {
        this.port = port;
        connections = new HashMap<String,BufferedWriter>();
    }
    
    public void start() {        
            try {
                sock = new ServerSocket(port);
                while (true) {
                    Socket s = sock.accept();
                    ThreadedServer ts = new ThreadedServer(this,s);
                    new Thread(ts).start();
                }
            }
            catch(IOException e) {}        
    }
    
    public static void main(String[] args) {
        Server server = new Server(3030);
        server.start();
               
    }
    
}


class ThreadedServer implements Runnable {
                  
    
    private BufferedWriter out;
    private BufferedReader in;
    private Socket sock;
    Server server; 
    
    
    public ThreadedServer(Server s, Socket sock) {
        
        this.sock = sock;
        try{
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        catch(IOException e){}        
        server = s;
    }
    
    public void run() {
        String nick = null;
        try {
            String s = null;           
            String pm = null;
                    
            out.write("User connected to port: " + sock.getPort());
            out.newLine();
            out.flush();
            out.write("Enter desired nickname: " );
            out.newLine();
            out.flush();
            nick = in.readLine();
         
            synchronized (server.connections) {
                server.connections.put(nick,out);       
            }
            
            while (!((s = in.readLine()).equals(":q"))) {
                
                StringTokenizer st = new StringTokenizer(s);
                if(!(st.nextToken().equals(":p"))) {
                   for(BufferedWriter o : server.connections.values()) {
                       if(!(o.equals(out))) {
                            o.write(nick + " says: " + s);
                            o.newLine();
                            o.flush();      
                        }
                    }
                }
                else {
                    pm = st.nextToken();                    
                    s = in.readLine();
                    BufferedWriter pmOut = server.connections.get(pm);
                    pmOut.write("(private) " + nick + " says: " + s);
                    pmOut.newLine();
                    pmOut.flush();                                        
                }
          }
        } catch (IOException ex) {}                  
        try {
            in.close();
            synchronized(server.connections) {
                server.connections.remove(out);
            }
            for(BufferedWriter o : server.connections.values()) {
                o.write("User " + nick + " has disconnected");
                o.newLine();
                o.flush(); 
            }
        } catch (IOException ex) {            
        }
    }
    
}