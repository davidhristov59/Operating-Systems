import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Z2ProducerConsumer {
    static final int NUM_RUNS = 100;
    static final int NUM_CONSUMERS = 50;

    // TODO: Define synchronization elements

    static Semaphore isBufferEmpty; //dali baferot e prazen
    static Lock lock; //go zaklucuvam pristapot do baferot
    static Semaphore[] items ;//niza od semafori

    //ne mozeme eden semafor da koristime , primer 50 dozvoli zoso sekoj si ima poseben resurs
    //threads se, ne go znaeme nivnoto odnesuvanje
    public static void init() {

        // TODO: Initialize synchronization elements
        isBufferEmpty = new Semaphore(1); //1 dozvola bidejki baferot e prazen i producerot koga ke napravi acquire da vleze da go popolni
        //ako pisuvase baferot deka e poln semaforot ke go inicijaliziravme so 0 dozvoli
        lock = new ReentrantLock();

        for (int i = 0; i < NUM_CONSUMERS; i++) { //kolku sto imam consumers - za negoviot proizvod imam signal dali e napraven ili ne
            items[i] = new Semaphore(0);
        }

    }

    public static void main(String[] args) {
        init();

        Buffer sharedBuffer = new Buffer(NUM_CONSUMERS);
        Producer p = new Producer(sharedBuffer);
        p.start();

        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers.add(new Consumer(i,sharedBuffer));
        }

        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers.get(i).start();
        }
    }

    static class Buffer {

        // how many items are in the buffer currently
        private int numItems = 0;

        // total capacity of the buffer
        private final int numConsumers;

        public Buffer(int numConsumers) {
            this.numConsumers = numConsumers;
        }

        public int getBufferCapacity() {
            return numConsumers;
        }

        public void fillBuffer() {
            if (numItems !=0 ) {
                throw new RuntimeException("The buffer is not empty!");
            }
            numItems=numConsumers;
            System.out.println("The buffer is full.");
        }

        public void decrementNumberOfItemsLeft() {
            if (numItems <= 0) {
                throw new RuntimeException("Can't get item, no items left in the buffer!");
            }
            numItems--;
        }

        public boolean isBufferEmpty() {
            return numItems==0;
        }

        public void getItem(int consumerId) {
            System.out.println(String.format("Get item for consumer with id: %d.", consumerId));
        }
    }

    static class Consumer extends Thread {

        private Buffer buffer;
        private int consumerId;

        public Consumer(int consumerId, Buffer buffer) {
            this.buffer = buffer;
            this.consumerId = consumerId;
        }

        public void execute() throws InterruptedException {
            // TODO: Implement item taking logic

            items[consumerId].acquire();
            buffer.getItem(consumerId); //da si go zemam consumerot item so moe id

            lock.lock();
            buffer.decrementNumberOfItemsLeft(); //si go zemav mojot item i dekrementiraj od bufferot

            if(buffer.isBufferEmpty()){
                isBufferEmpty.release();
            }

            lock.unlock(); //ako e prazen baferot ke go otklucam regionot i ke go pustam producerot

        }

        @Override
        public void run() {
            for (int i = 0; i < Z2ProducerConsumer.NUM_RUNS; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Producer extends Thread {
        private Buffer buffer;

        public Producer(Buffer buffer) {
            this.buffer = buffer;
        }

        public void execute() throws InterruptedException {
            // TODO: Implement buffer filling logic with synchronization

            isBufferEmpty.acquire(); //ke mi dozvoli da vlezam vo samiot bafer

            lock.lock();
            buffer.fillBuffer(); //popolni go baferot
            //koga ke go popolnam baferot treba da oslobodam 1 dozvola za consumersot
            lock.unlock();

            for (int i = 0; i < NUM_CONSUMERS; i++) { //sekoj consumer moze da si ja zeme svojata stavka
                    items[i].release();
            }

        }

        @Override
        public void run() {
            for (int i = 0; i < Z2ProducerConsumer.NUM_RUNS; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
