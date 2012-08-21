package ex2;



class Conta{

    private int id;
    private double valor;
    
    
    public Conta(){
        id = 0;
        valor = 0;
    }
    
    public Conta(int id,double valor){
        this.id = id;
        this.valor = valor;
    }
    
    public synchronized void credita(double vUP) {
        valor += vUP;
    }
    
    public synchronized void debita(double vDown) throws InterruptedException {
         while(valor-vDown <0){
            this.wait();
        }
        valor -= vDown;
    }
    
    public double getSaldo(){
        return valor;
    }
   
    
}



public class Banco {
    private Conta[] contas;
  
    Banco(){
        contas = new Conta[10];
        for(int i=0; i<10; i++)
           contas[i] = new Conta(); 
    }
    
    public double getSaldoConta(int c) {
        return contas[c].getSaldo();
    }
    

    public void creditaConta(int c, double vUP) {
        contas[c].credita(vUP);
    }
    
    public void debitaConta(int c, double vDown) {
        try{
            contas[c].debita(vDown);
        }
        catch (InterruptedException e){}
    }
    
    public void tranfereConta (int srcAcc, int dstAcc, double amm) {
        int min=Math.min(dstAcc, dstAcc);
        int max=Math.max(dstAcc, dstAcc);
        Conta minConta=this.contas[min];
        Conta maxConta=this.contas[max];
        synchronized (minConta){
            synchronized (maxConta){
                if(min==srcAcc){
                    try{
                    minConta.debita(amm);
                    maxConta.credita(amm);
                    }
                    catch(InterruptedException e){}
                }
                else{
                    try{
                    maxConta.debita(amm);
                    minConta.credita(amm);
                    }
                    catch(InterruptedException e){}
              
                }
                }
            
            }
        }
    
    
    public double getSaldoTotal(int i){
        double total = 0;
        if(i<this.contas.length){
            synchronized (this.contas[i]){
                total = getSaldoTotal(i+1);
                total += this.contas[i].getSaldo();
                }
        }            
        return total;
    }
} 
    
class ContasBanco implements Runnable{
    Banco b;
    ContasBanco(Banco banco){
        b=banco;
    }
    
    @Override
    public void run(){ 
        b.creditaConta(1,15);
        b.debitaConta(2,30);
        b.creditaConta(7,21);
        b.tranfereConta(1, 2, 10);
    }
    
    
}

class ImplemBanco{
    public static void main(String[] args){
    Banco b = new Banco();    
    Thread t1,t2;
    
    t1 = new Thread(new ContasBanco(b));
    t2 = new Thread(new ContasBanco(b));
    t1.start();
    t2.start();
    try{
        t1.join();
        t2.join();
    }catch (InterruptedException e) {}
    
    
    System.out.println(b.getSaldoConta(1));
    System.out.println(b.getSaldoConta(2));
  }
        
        
}
