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
public class Producer extends Thread{
    
    Production pr=null;
    int pro=0;
    int maxpro=20;
    
    public Producer(Production pr){
        this.pr=pr;
    }
    
    @Override
    public void run(){
        Random random=new Random();
        while(pro<maxpro){
            synchronized(pr){
                String s=Test.RandomString(random.nextInt(5)+5);
                System.out.println("Producer produce string :"+s);
                pr.production.add(s);
            }
            pr.available=true;
            pro++;
        }
        System.out.println("Producer ends! Finish producing "+pro+" productions");
    }
    
}
