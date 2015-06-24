/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;

/**
 *
 * @author Duan
 */
public class Test {
    
    public static String RandomString(int n){
        StringBuffer buf=new StringBuffer();
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random ran=new Random();
        for(int i=0;i<n;i++){
            buf.append(str.charAt(ran.nextInt(62)));
        }
        return buf.toString();
    }
    
    public static void main(String[] args){
//        Production pr=new Production();
//        Producer prd=new Producer(pr);
//        Consumer con1=new Consumer(pr);
//        Consumer con2=new Consumer(pr);
//        
//        prd.start();
//        con1.start();
//        con2.start();
        
        Production pr=new Production();
        pr.setPaC(3, 1);
        Producer1 prd=new Producer1(pr,30,0);
        Consumer1 con1=new Consumer1(pr,10,0);
        Consumer1 con2=new Consumer1(pr,10,1);
        Consumer1 con3=new Consumer1(pr,10,2);
        
        prd.start();
        con1.start();
        con2.start();
        con3.start();
    }
}
