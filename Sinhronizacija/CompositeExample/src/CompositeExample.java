import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Composite {

    private Composite c = null;

    int x = 0;

    private Lock lock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(1); //1 dozvola - 1 thread - koga ke zeme dozvola ke vleze vnatre vo kriticniot region i ke izleze posle

    public Composite(int b) {
        x=b;
    }

    public Composite(Composite a, int b) {
        c=a;
        x=b;
    }

    //1 nacin so semaphor - kazuvame kolku dozvoli da dade
    public void move() throws InterruptedException {
        semaphore.acquire();
        if(c!=null) c.move(); x++; //tuka e kriticniot region, tuka pristapuvame kon spodeleniot resurs , potocno kon spodeleneta promenliva
        semaphore.release();
    }

    //2 NACIN

    //mozeme da zaklucime kriticniot region so dodavanje na synchronized
    //synchronized - SINHRONIZIRASE na nivo na monitor na samiot objekt
    //1 objekt e spodelen i site threadovi ke cekaat na monitorot

//    public void synchronized move() throws InterruptedException{
//        semaphore.acquire();
//        if(c!= null){
//            c.move();
//            x++;
//        }
//        semaphore.release();
//    }

    //3 NACIN

    //so dodavanje na synchronized(this) za momentalniot objekt i go stavam vnatre kriticniot region

//    public void move() throws InterruptedException {
//        synchronized (this){
//            if(c!=null){
//                x++;
//            }
//        }
//    }

    //4 nacin so lock i unlock - go zaklucuvame regionot i posle da go otklucime

//    public void move() throws InterruptedException {
//
//        lock.lock();
//        if(c!=null){
//            c.move();
//            x++;
//        }
//        lock.unlock();
//    }

    public int getValue() {
        return x;
    }
}

public class CompositeExample {

    private static final Composite shared=new Composite(0);

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread() {

                public void run() {
                    Composite local = new Composite(shared, 0);
                    try {
                        local.move();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(shared.getValue());
    }

}

