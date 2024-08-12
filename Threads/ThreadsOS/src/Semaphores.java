import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Incremented{

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(1); //bara inicijalen broj na dozvoli
    // mozat da rabotat so poveke dozvoli

    public int getCount(){
        return this.count;
    }

    public void safeSynchronizedWithSemaphores() throws InterruptedException {

        //sekoja niska sto saka da vleze vo kriticniot region ke bara dozvola od semaforot da vleze

        semaphore.acquire(2); //poveke dozvoli bara
        count++; //vleguva vo krit. region i T1 blokira
        semaphore.release();

    }

    public void safeSynchronizedIncrement(){
        synchronized (this) {
            count++;
        }
    }

    public void unsafeSynchronizedIncrement(){
        count++;
    }

    public void safeLockedIncrement(){
        //obicen mutex
        lock.lock();
            count++;
        lock.unlock();
    }
}

class ThreadExe extends Thread{

    Incremented incremented = new Incremented();
    String name;

    public ThreadExe(String name, Incremented incremented) {
        this.incremented = incremented;
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            incremented.safeSynchronizedIncrement();
        }
    }
}

public class Semaphores {
    public static void main(String[] args) throws InterruptedException {
        //sakame poveke niski da vleguvaat vo toj kriticen region

        Incremented incremented = new Incremented();
        Thread exe1 = new ThreadExe("Thread 1", incremented);
        Thread exe2 = new ThreadExe("Thread 2", incremented);

        exe1.start();
        exe2.start();

        exe1.join();
        exe2.join();

        int count = incremented.getCount();
        System.out.println(count);

    }
}
