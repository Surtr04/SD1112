/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex4;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
        
/**
 *
 * @author rmb
 */
public class BoundedBuffer {
    
    private int[] buffer;
    private int size;
    private int filled;
    private ReentrantLock lock;
    private Condition empty;
    private Condition full;
    
    public BoundedBuffer(int size) {
        this.size = size;
        filled = 0;
        buffer = new int[size];        
        lock = new ReentrantLock();
        empty = lock.newCondition();
        full = lock.newCondition();
    }
    
    
    public void put (int v) {
        
        lock.lock();
       
        try {
            if (filled == size)
                full.await();
            
            buffer[filled] = v;
            filled++;
            empty.signal();
            
        }
        catch(InterruptedException e) {}
        finally {
            lock.unlock();
        }
    }
    
    public int get() {
        lock.lock();
        int ret = -1;
        try{
            if(filled == 0)
                empty.await();
            filled--;
            ret = buffer[filled];
            buffer[filled] = 0;            
            full.signal();
            
        }
        catch(InterruptedException e) {}
        finally {
            lock.unlock();
            return ret;
        }
        
    }
    
    public static void main(String[] args) {
        
        BoundedBuffer b = new BoundedBuffer(2);
        
        Thread t = new Thread(new Impl(b));
        t.start();
        
        try{
            t.join();
        }
        catch(InterruptedException e) {}
        
    }
    
}

class Impl implements Runnable {
    
    private BoundedBuffer b;
    
    public Impl(BoundedBuffer b) {
        this.b = b;
    }
    
    public void run() {
        b.put(2);
        //b.put(3);
        System.out.println(b.get());
        System.out.println(b.get());
    }
    
}
