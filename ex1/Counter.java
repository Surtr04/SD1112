/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rmb
 */
public class Counter {

    
    private int count;
    
    public Counter() {
        count = 0;
    }
    
    
    public void incr() {
        count++;
    }

    public int getCount() {
        return count;
    }        
    
    
    public static void main(String[] args) {
        
        Counter c = new Counter();
        Thread t = new Thread(new Incr(c));
        Thread t2 = new Thread(new Redirect());
        
        int i = 0;
        t.start();
        t2.start();
        try {
            t.join();
            t2.join();
        } catch (InterruptedException ex) {}
        
    }
   
}

class Redirect implements Runnable {
    
    private BufferedReader in;
    
    public Redirect(){
        in = new BufferedReader(new InputStreamReader(System.in));        
    }
    
    public void run() {
        try{
            System.out.println(in.readLine());
        }
        catch(IOException e) {}
    }
    
}

class Incr implements Runnable {
    
    Counter c;
    
    public Incr(Counter c) {
        this.c = c;
    }
    
    public void run() {
       
            int i = 0;
            while (i < 10) {
                System.out.println(c.getCount());
                c.incr();
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e) {}
                i++;
            }        
    }
}


