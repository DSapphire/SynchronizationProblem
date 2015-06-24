public class ThreadTestForSleepingBarber{
	public static void main(String[] args) {
		new WorkThread().start();
	}
}
class WorkThread extends Thread{
	int i=0;
	Chair chair;
	//default value;
	int chairNo=5;
	int barberNo=3;
	int cusNo=20;
	public WorkThread(){
	}
	public WorkThread(int chairNo,int barberNo,int cusNo){
		this.chairNo=chairNo;
		this.barberNo=barberNo;
		this.cusNo=cusNo;
	}
	public void run(){
		createBarberAndChair();
		while(true){
			createCustomer();
			try{
				sleep(5000L);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	private void createBarberAndChair(){
		chair=new Chair(chairNo,barberNo);
		Barber[] barber=new Barber[barberNo];
		for(int j=0;j<barberNo;j++){
			barber[j]=new Barber(chair,j);
			barber[j].start();
		}

	}
	private void createCustomer(){
		int cnt=0;
		while(cnt++<cusNo){
			try{
				sleep(300L);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			new Customer(chair,"Customer"+(i++)).start();
		}
	}
}
class Chair{
	private int max=10;
	private int used=0;
	private boolean[] sit;
	private boolean[] sleepBarber;
	private String[] cusName;
	private int current=0;
	public Chair(int chair,int barber){
		this.max=chair;
		sit=new boolean[chair];
		sleepBarber=new boolean[barber];
		cusName=new String[chair];
		for(int i=0;i<chair;i++)
			sit[i]=false;
		for(int i=0;i<barber;i++)
			sleepBarber[i]=false;
	}
	public synchronized boolean isEmpty(){
		return used==0;
	}
	public synchronized boolean avaliable(){
		return this.used<this.max;
	}
	private synchronized int sitOnChair(String name){
		if (avaliable()) {
			for(int i=0;i<sit.length;i++){
				if(!sit[i]){
					used++;
					sit[i]=true;
					cusName[i]=name;
					return i;
				}
			}
		}
		return -1;
	}
	public synchronized void sitAndWait(String name){
		int i=sitOnChair(name);
		if(i>-1){
			System.out.println(name +" is sitting now on Chair "+i+" waiting.");
		}
	}
	public synchronized void wakeBarber(String name){
		for(int i=0;i<sleepBarber.length;i++){
			if(sleepBarber[i]){
				sleepBarber[i]=false;
				System.out.println(name+" wake up barber "+i+" .\n");
				sitOnChair(name);
				notifyAll();
				break;
			}
		}
	}
	public synchronized String barberWork(int barber){
		for(int i=0;i<sit.length;i++){
			if(sit[i]){
				used--;
				sit[i]=false;
				System.out.println("Barber"+barber+" begins to work on "
					+cusName[i]+".");
				return cusName[i];
			}
		}
		return null;
	}
	public synchronized void barberSleep(int barber){
		if(barber<sleepBarber.length){
			sleepBarber[barber]=true;
			delayChair();
		}
	}
	private synchronized void delayChair(){
		try{
			wait();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public synchronized boolean isBarberSleep(){
		for(int i=0;i<sleepBarber.length;i++){
			if(sleepBarber[i])
				return true;
		}
		return false;
	}
	public synchronized boolean isBarberSleep(int barber){
		if(barber<sleepBarber.length&&barber>=0){
			return sleepBarber[barber];
		}
		return false;
	}
}
class Barber extends Thread{
	private Chair chair;
	private int barber;
	public Barber(Chair chair,int barber){
		this.chair=chair;
		this.barber=barber;
	}
	public void run(){
		while(true){
			if(chair.isEmpty()){
				barberSleep();
			}
			while(chair.isBarberSleep(barber))
				chair.barberSleep(barber);
			barberWork();
		}
	}
	private void barberSleep(){
		System.out.println("No Customer. Barber"+barber+" begins to sleep.\n");
		chair.barberSleep(barber);
	}
	private void barberWork(){
		String name=chair.barberWork(barber);
		try{
			sleep(1500L);//simulating working
			System.out.println("Barber"+barber+" has finished working on "
				+name+".");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
class Customer extends Thread{
	private String name;
	private Chair chair;
	public Customer(Chair chair,String name){
		this.chair=chair;
		this.name=name;
	}
	public void run(){
		System.out.println(name +" gets in.");
		if(chair.isBarberSleep()){
			chair.wakeBarber(name);
		}else if(chair.avaliable()){
			chair.sitAndWait(name);
		}else{
			System.out.println("No chair. "+name +" is leaving.");
		}
	}
}