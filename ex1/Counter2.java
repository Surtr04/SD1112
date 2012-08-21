/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex1;

/**
 *
 * @author rmb
 */
public class Counter2 implements Runnable {
 
    private Counter c;
    
    public Counter2(Counter c) {
        this.c = c;
    }
    
    public int getCount() {
        return c.getCount();
    }
    
    public void run() {
        int i = 0;
        while(i < 100) {
            c.incr();
            i++;
        }
    }
    
    public static void main (String[] args) {
        Thread[] tarr = new Thread[10];
        int i;
        Counter2 c = new Counter2(new Counter());
        for(i = 0; i < 10; i++) {
            tarr[i] = new Thread(c);
            tarr[i].start();
        }
        
        for(i = 0; i < 10; i++) {       
            try{
                tarr[i].join();
            }
            catch(InterruptedException e) {}
        }
        
        
        System.out.println(c.getCount());
        
    }
    
    
}
