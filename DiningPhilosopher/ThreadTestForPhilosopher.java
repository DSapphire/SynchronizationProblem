public class ThreadTestForPhilosopher{
	public static void main(String[] args) {
		Fork fork=new Fork();
		for(int i=0;i<5;i++){
			new Philosopher(i+"",fork).start();
		}
	}
}
class Philosopher extends Thread{
	private String name;
	private Fork fork;
	public Philosopher(String name,Fork fork){
		super(name);
		this.name=name;
		this.fork=fork;
	}
	public void run(){
		while(true){
			think();
			fork.takeFork();
			eat();
			fork.putFork();
		}
	}
	public void think(){
		System.out.println("I am Philosopher "+name+",I am thinking.");
		try{
			sleep(1000L);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public void eat(){
		System.out.println("I am Philosopher "+name+",I am eating.");
		try{
			sleep(1000L);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
class Fork{
	private boolean[] beingUsed={false,false,false,false,false};
	public synchronized void takeFork(){
		String name=Thread.currentThread().getName();
		int i=Integer.parseInt(name);
		while(beingUsed[i]||beingUsed[(i+1)%5]){
			try{
				wait();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.println("I am Philosopher "+name+",I have taken forks.");
		beingUsed[i]=true;
		beingUsed[(i+1)%5]=true;
	}
	public synchronized void putFork(){
		String name=Thread.currentThread().getName();
		int i=Integer.parseInt(name);
		beingUsed[i]=false;
		beingUsed[(i+1)%5]=false;
		System.out.println("I am Philosopher "+name+",I have put fork.");
		notifyAll();
	}
}