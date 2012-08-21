/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex3;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rmb
 */
public class BoundedBuffer {
    
    private int[] buffer;
    int size;
    int filled;
    
    public BoundedBuffer(int size) {
        buffer = new int[size];
        this.size = size;
        this.filled = 0;        
    }
    
    public synchronized void put(int v) {
        
        while(filled == size) {
            try {
                this.wait();
            } catch (InterruptedException ex) {}        
        }
        
        buffer[filled] = v;
        filled++;
        this.notify();
    }
    
    public synchronized int get() {
        while(filled == 0) {
            try{
                this.wait();
            }
            catch(InterruptedException e) {}
        }
        
        int ret = buffer[filled-1];
        buffer[filled-1] = 0;
        filled--;
        this.notify();
        
        return ret;
    }
        
    public static void main (String[] args) {
        
        BoundedBuffer b = new BoundedBuffer(10);
        Thread t = new Thread(new Imp(b));
        t.start();
        
        try{
            t.join();
        }
        catch(InterruptedException e) {}
    }
    
}

class Imp implements Runnable {
    
    private BoundedBuffer b;
    
    public Imp(BoundedBuffer b) {
        this.b = b;
    }
    
    public void run() {
        b.put(2);
        System.out.println(b.get());       
    }
}
