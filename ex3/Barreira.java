/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex3;

/**
 *
 * @author rmb
 */
public class Barreira {
    
    private int wait;
    private int waiting;
    
    public Barreira(int wait) {
        this.wait = wait;
        waiting = 0;
    }
    
    public synchronized void espera() {
        
        waiting++;
        
        while (waiting < wait) {
            try{                
                this.wait();
            }
             catch(InterruptedException e){}       
        }
        
        this.notifyAll();
        
    }
    
    public static void main (String[] args) {
        Barreira b = new Barreira(2);
        
        Thread t = new Thread(new Imp2(b));
        Thread t2 = new Thread(new Imp2(b));
        
        t.start();
        t2.start();
        
        try {
            t.join();
            t2.join();
        }
        catch(InterruptedException e) {}
        
    }
    
}

class Imp2 implements Runnable {
    
    private Barreira b;
    
    public Imp2(Barreira b) {
        this.b = b;
    }
    
    public void run() {
        b.espera();
    }
    
}