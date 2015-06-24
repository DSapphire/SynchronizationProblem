/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Duan
 */
public class Consumer extends Thread{
    
    Production pr=null;
    int con=0;
    int maxcon=10;
    public Consumer(Production pr){
        this.pr=pr;
    }
    @Override
    public void run(){
        Random random=new Random();
        while(maxcon>con){
            synchronized(pr){
                if(pr.available){

                    int i=random.nextInt(pr.production.size());
                    String s=pr.production.get(i);
                    System.out.println("Consumer"+Thread.currentThread().getName()+" consume string :"+s);
                    pr.production.remove(i);
                    con++;
                }
            }   
            if(pr.production.isEmpty()){
                pr.available=false;
            }
        }
        System.out.println("Consumer"+Thread.currentThread().getName()+" ends! Finish consuming "+con+" productions");
    }
    
}
