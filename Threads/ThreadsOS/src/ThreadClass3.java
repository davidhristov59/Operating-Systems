import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Incrementor3{

    private int count = 0;

    public void increment(){ //morame metodot da go napravime da bide sinhroniziran
        //da go sinhroniziram na nivo na site objekti od tipot Incrementor

        synchronized (Incrementor3.class) { //ke se sinhronizira na monitorot na samata klasa, tekovniot objekt - zaednicka e memorijata za cela klasa
            count++;
        }
    }

    public int getCount(){
        return this.count;
    }

}

class ThreadExecutor3 extends Thread{

    private String name;
    Incrementor3 incrementor3;

    public ThreadExecutor3(String name, Incrementor3 incrementor3){
        this.name = name;
        this.incrementor3 = incrementor3;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            incrementor3.increment();
        }
    }
}

public class ThreadClass3{

    public static void main(String[] args) throws InterruptedException {

        Incrementor3 incrementor3 = new Incrementor3();

        Thread executor1 = new ThreadExecutor3("Thread 1", incrementor3);
        Thread executor2 = new ThreadExecutor3("Thread 2", incrementor3);

        executor1.start();
        executor2.start();

        executor1.join();
        executor2.join();

        int count = incrementor3.getCount();
        System.out.println(count);

    }
}