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
public class Producer1 extends Thread{
    
    Production pr=null;
    int maxpro=20;
    int id;
    
    public Producer1(Production pr){
        this.pr=pr;
        this.id=0;
    }
    public Producer1(Production pr,int maxpro,int id){
        this.pr=pr;
        this.maxpro=maxpro;
        this.id=id;
    }
    public void setId(int id){
        this.id=id;
    }
    @Override
    public void run(){
        
        while(pr.producers[id]<maxpro){
            pr.produce(id);
        }
        System.out.println("Producer"+id+" ends! Finish producing "+maxpro+" productions");
    }
    
}
