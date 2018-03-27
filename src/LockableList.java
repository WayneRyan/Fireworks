import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class LockableList<T> extends ArrayList<T>{
	
	Semaphore lock;
	
	public LockableList(){
		lock = new Semaphore(1);
	}
	
	public void acquire(){
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void release(){
		lock.release();
	}

}
