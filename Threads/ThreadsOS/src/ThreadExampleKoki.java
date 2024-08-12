import java.util.ArrayList;

//Spodelen resurs - kako poveke nitki da go izvrsuvaat istiot kod i da koristat spodeleni resursi
class WorkerThread extends Thread{

    String name;

    @Override
    public void run() {
        super.run();
    }
}

public class ThreadExampleKoki {
    public static void main(String[] args) {
        var threads = new ArrayList<Thread>();

        for (int i = 0; i < 50; i++) {
            var thread = new WorkerThread();
            threads.add(thread);
        }

        threads.forEach(t -> t.start());
        System.out.println(threads);

    }
}
