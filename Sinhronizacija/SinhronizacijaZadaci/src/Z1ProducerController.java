import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Z1ProducerController {

    //DADENO

    public static int NUM_RUN = 50;

    //TODO
    static Lock lock; //za da ja pristapam promenlivata
    static Semaphore accessBuffer; //moze i so Lock
    static int num_controllers = 0; //kako counter
    static Semaphore canCheck; //site kontroli da pristapat
    //10 dozvoli - 10 threadovi istovremeno da vlezat vo regionot

    public static void init() {
        //TODO - inicijalizacija na semafori, lock, brojaci

        accessBuffer = new Semaphore(1); //kako lock e - go zaklucuva resursot - bufferot ke go zaklucuvame  bidejki e spodelen resurs
        lock = new ReentrantLock();
        canCheck = new Semaphore(10); //10 dozvoli - 10 threadovi mozat da vlezat

    }

    public static class Buffer {

        private boolean producing = false;
        private int checkingCount = 0;

        public void produce() { //treba samo da ja povikame od producerot
            producing = true;
            if (checkingCount > 0) {
                throw new RuntimeException("Can't produce if controllers checking");
            }

            System.out.println("Producer is producing...");

            producing = false;
        }

        public synchronized void check() { //treba da ja povikame check funkcijata
            checkingCount++;

            if (producing) {
                throw new RuntimeException("Can't check if producer is producing");
            }

            if (checkingCount > 10) {
                throw new RuntimeException(
                        "No more than 10 checks can be in progress simultaneously"
                );
            }

            System.out.println("Controller is checking...");

            checkingCount--;
        }
    }

    public static class Producer extends Thread {
        private final Buffer buffer; //SPODELENIOT RESURS

        public Producer(Buffer b) {
            this.buffer = b;
        }

        public void execute() throws InterruptedException {

            //TODO

            accessBuffer.acquire(); //ako vleze vo bufferot neka PROIZVEDUVA
            buffer.produce(); //bidejki sme vo Producer
            accessBuffer.release(); //koga ke zavrsi so proizveduvanje iskoci

            //ako imame proizvoditel sto nesto proizveduva ostanatite ne smeat da vlezat vnatre da pravat check
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) { //50 pati
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Controller extends Thread {

        private final Buffer buffer;

        public Controller(Buffer buffer) {
            this.buffer = buffer;
        }

        public void execute() throws InterruptedException {

            //TODO

            lock.lock(); //zaklucuvame

            if(num_controllers == 0){ //proveruvam dali vnatre vo bufferot imam kontroleri
                //ako e 0 nemam kontroleri vnatre - ja otvoram vratata i vnesuvam vo baferot
                accessBuffer.acquire();
                //ke dobie 1 dozvola
            }
            //tuka vise vleguva vo baferot - zgolemuva num_controllers
            num_controllers++;
            lock.unlock();

            canCheck.acquire(); //semaforot so 10 dozvoli vika deka moze da vlezes vo baferot

            buffer.check(); //Controllor ja povikuva - ja proveruvam globalnata promenliva

            lock.lock(); //zaklucuvame pri promena vrednosta na baferot
            num_controllers--; //izleguva od baferot - se namaluva brojot na kontroleri
            canCheck.release();  //release pravime na 1 dozvola - moze da vleze druga sekvencijalno

            if(num_controllers == 0){ //vo slucaj da sum posleden controler
                accessBuffer.release(); //go ispustam baferot - ja ostavam vratata otvorena za da moze drug da vleze
            }

            lock.unlock();

        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        //DADENO
        Buffer buffer = new Buffer();
        Producer p = new Producer(buffer);
        List<Controller> controllers = new ArrayList<>();
        init();
        for (int i = 0; i < 100; i++) {
            controllers.add(new Controller(buffer));
        }
        p.start();
        for (int i = 0; i < 100; i++) {
            controllers.get(i).start();
        }
    }
}

