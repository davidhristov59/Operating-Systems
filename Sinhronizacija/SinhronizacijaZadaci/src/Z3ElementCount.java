import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Z3ElementCount {

    static long count = 0; //tuka site ke go zapisuvaat brojot na elementi - kriticen region

    //TODO - initialize
    static Lock lock = new ReentrantLock();
    static long max = 0;
    static Semaphore done = new Semaphore(0);
    static Semaphore canCheckMax = new Semaphore(0);


    static final BoundedRandomGenerator random = new BoundedRandomGenerator();
    private static final int ARRAY_LENGTH = 10000;
    private static final int NUM_THREADS = 10;

    private static final int SEARCH_TARGET = 5;  // This is the target element we are looking for

    // TODO: Define synchronization elements and initialize

    // DO NOT CHANGE
    public static int[] getSubArray(int[] array, int start, int end) {
        return Arrays.copyOfRange(array, start, end);
    }

    public static void main(String[] args) throws InterruptedException {

        int[] arr = ArrayGenerator.generate(ARRAY_LENGTH, SEARCH_TARGET);

        // TODO: Make the SearchThread class a thread and start 10 instances
        // Each instance should take a subarray from the original array with equal length

        int elementsPerThread = arr.length / NUM_THREADS;
        List<CountThread> countThreads = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            int[] subarray = getSubArray(arr, elementsPerThread * i, elementsPerThread * (i + 1));
            countThreads.add(new CountThread(subarray, SEARCH_TARGET));
        }


        // check max count
        // TODO: Start the 10 threads

        for (CountThread countThread : countThreads){
            countThread.join();
        }

        done.acquire(NUM_THREADS); // koga ke zavrsat site , oslobodi gi

        canCheckMax


        // DO NOT CHANGE

        System.out.println("The number of total counted elements is: " + count);
        System.out.println("The generated number of elements is: " + ArrayGenerator.elementCount);

        // TODO: The max thread should print the number of occurrences

    }

    // TODO: Make the SearchThread class a thread
// You can add methods or attributes if necessary
    static class CountThread extends Thread {

        private int[] arr;
        private int target;

        public CountThread(int[] arr, int target) {
            this.arr = arr;
            this.target = target;
        }

        public void countElements() {
            for (int num: this.arr) {
                if (num == target) {
                    count++;
                }
            }
        }

        public void countElementsParallel() throws InterruptedException {
            // TODO: Implement and run the parallel counting method from the thread

            int local_count = 0;

            //od gore ja zimam istata
                for (int num: this.arr) {
                    if (num == target) {
//                        lock.lock();
//                        count++; //ova e kriticniot region
//                        lock.unlock();
                        local_count++;
                    }
                }

                lock.lock();
                count += local_count;

                if(local_count > max){
                    max = local_count;
                }

                lock.unlock();

        }

        public void run() {
            try {
                countElementsParallel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //************ DO NOT CHANGE ************//
    static class BoundedRandomGenerator {
        static final Random random = new Random();
        static final int RANDOM_BOUND = 100;

        public int nextInt() {
            return random.nextInt(RANDOM_BOUND);
        }

    }

    static class ArrayGenerator {

        static int elementCount;

        static int[] generate(int length, int target) {
            int[] array = new int[length];

            for (int i = 0; i < length; i++) {
                int element = Z3ElementCount.random.nextInt();

                if (element == target) {
                    elementCount++;
                }

                array[i] = element;
            }

            return array;
        }
    }


}


