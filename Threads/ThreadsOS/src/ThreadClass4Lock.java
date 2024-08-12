import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Incrementor4{

    private int count = 0;
    private Lock lock = new ReentrantLock(); //mutex od tipot Lock

    public void increment(){

        //T1 - increment()  - vleguva vnatre i zastanuva kaj lock.lock(), bara dozvola od Lock - dali mozam da vlezam vnatre - moze - odi vo count++ - zavrsuva i nema dozvola za T2 uste bidejki ja drzi T1

        //sakam da go sinhroniziram

        lock.lock();
        count++; //ova ni e kriticen region - poveke niski paralelno sakaat da pristpat do zaednicka memorija i sakaat da zapisuvaat

        //otkako ke zavrsi sinhronizranjeto na promenlivata go osloboduvam

        lock.unlock(); //ako nema unlock ke ima deadlock, se ceka na resursi koi sto ne se zavrseni
        //moram da go oslobodam
    }

    public int getCount(){
        return this.count;
    }

}

class ThreadExecutor4 extends Thread{

    private String name;
    Incrementor4 incrementor4;

    public ThreadExecutor4(String name, Incrementor4 incrementor4){
        this.name = name;
        this.incrementor4 = incrementor4;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            incrementor4.increment();
        }
    }
}

public class ThreadClass4Lock{

    public static void ThreadWithRunnable(){

        //DRUG NACIN NA KREIRANJE NA NISKA SO RUNNABLE

        Runnable target;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable Thread");
            }
        });

        //obavezno
        t1.start();
    }

    public static void main(String[] args) throws InterruptedException {

        //DRUG NACIN NA KREIRANJE NA NISKA SO RUNNABLE

        Incrementor4 incrementor4 = new Incrementor4();

        Thread executor1 = new ThreadExecutor4("Thread 1", incrementor4);
        Thread executor2 = new ThreadExecutor4("Thread 2", incrementor4);

        executor1.start();
        executor2.start();

        executor1.join(1000);
        executor2.join(1000);

        if(executor1.isAlive() || executor2.isAlive()){
            System.out.println("Deadlock");
            executor1.interrupt();
            executor2.interrupt();
            //zavrsi gi 2te
        }

        int count = incrementor4.getCount();
        System.out.println(count);

    }
}