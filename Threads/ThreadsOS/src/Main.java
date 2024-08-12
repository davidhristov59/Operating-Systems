
//NE TREBA NIE DA POVIKUVAME RUN() VO MAIN - POVIKUVAME START() I SO TOA AVTOMATSKI POVIKUVAME RUN()
class ThreadExecutor extends Thread{

    private String name; //lokalna promenliva
    private Incrementor incrementor; //referenca kon zaednicka memorija, moram da cuvam tuka

    public ThreadExecutor(String name, Incrementor incrementor){ //go dodavam kako 2 argument
        this.name = name;
        this.incrementor = incrementor;
    }

    @Override
    public void run() { //sve pisuvame tuka - GLAVNATA LOGIKA NA SEKOJA NITKA
//        incrementor.increment();
        for (int i = 0; i < 20; i++) {
//            System.out.println(name + ":" + i);
            incrementor.increment(); //20 pati sakam da mi se povika
        }
    }
}

class Incrementor{

    private int count = 0;

    public void increment(){ //povikot do increment ne ni e sinhorniziran, poveke niski se natprevaruvaat
        count++;

        //T1=0, T2=0
        //add 1 to count T2=1, T1=1
        //write count --> T1=1, T2=1

        try {
            Thread.sleep(1); //ja blokira niskata za 1 sek, TEKOVNATA NISKA
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //da nemav sleep site isto ke mi dadea 40
        //2 razlicni niski go povikuvaat increment
    }

    public int getCount(){
        return this.count;
    }

}

public class Main {
    public static void main(String[] args) throws InterruptedException {
     //celiot kod sto ke go napisam ovde vo ramki na ovaa funkcija, i site funkcii sto ke gi
        // povikam vo ovaa funkcija se izvrsuvaat vo ramki na MAIN threadot
     //ako sakame poveke niski konkuretno da rabotat gi kreirame vo main threadot
     //od sekoe dete niska moze novi niski da kreirame


        //Методот start() го повикува методот run()– не треба вие да го
        //повикувате методот run(). Nitkata ke terminira koga run() ke zavrsi

        for (int i = 0; i < 10; i++) {


            Incrementor incrementor = new Incrementor();
            Thread threadExecutor = new ThreadExecutor("Thread 1", incrementor); //referenca mu zadavam
            Thread threadExecutor2 = new ThreadExecutor("Thread 2", incrementor);

            //imam 2 paralelni niski koi sto ke se izvrsuvaat paralelno , 20 pati sekoja od niv go povikuva metodot increment

            //scheduler-ot gi stava 2te niski vo redica na cekanje
            threadExecutor.start();
            threadExecutor2.start();
            //ke dade izmesani rezultati - ne mozeme da gi predvidime bidejki so povik na start() se kreira nova niska i se stava vo sostojba ready i schedulerot-ot nepridvidlivo ke mu dodade vremenski interval

            //koga main threadot ke stigne do ovaa linija kod kaj sto e join , tuka ke se blokira
            // i vo sekoj vrmenski interval sto ke mu bide dodelen, blokira se dodeka
            // threadexecutor ne zavrsi so rabota isto i so threadexecutor2.
            // Koga ke zavrsat ke ja zeme vrednosta n a count i ke ispecati taa vred

            threadExecutor.join(5); //cekaj ja prvata niska 5 ms , ako ne zavrsi za tie 5s, teraj si- blokiraj samo za 5ms. Posle ke se odblokira posle 5ms
            threadExecutor2.join();

            if(threadExecutor.isAlive() && threadExecutor2.isAlive()){ //ako 2te niski posle 5ms se zivi i ne se terminirani
                //terminiraj gi i prodolzi so tekot na izvrsuvanje
                threadExecutor.interrupt(); //prekini go izvrsuvanjeto na nitkite
                threadExecutor2.interrupt();
            }

            int count = incrementor.getCount();
            System.out.println("Result is " + count); //postojano ke dava razlicen rezultat


//       threadExecutor.run(); //ne mozam run da go povikam vaka, kodot vo run ke ni se izvrsi vo istata niska, vo ramki na main threadot
            //When you call thread.run(), you are simply invoking the run() method of the ThreadExecutor class in the current thread.

            // The run() method will execute its code sequentially, just like any other method call.
            // It does not start a new thread of execution. Instead, the run() method is executed in
            // the context of the current thread, which in your case is the main thread.

            //ako sakame da go izvrsam vo nova niska, go pravam so povikuvanje na thread.start()

            //so starT() se kreira nova niska i se stava vo redot na cekanje na schedulerot i vo sostojba ready, niskata e spremna za izvrsuvanje i od ready odi vo running kade run() ke se execute-ne
            //Multithreading: start() creates a new thread, allowing the run() method to be executed concurrently with other threads.

//        thread.run(): Executes run() method synchronously in the current thread.
//        thread.start(): Creates a new thread and executes run() method asynchronously in the new thread.


            //dobivame 10 razlicni rezultati site blisku <= 40 bidejki poveke niski se natprevaruvaat
            // da pristapat do ist del od memorijata bezz nikakov sinhronizaciski mehanizam

            //Sinhronizacija - cekame site threadovi da zavrsat i pocnuvam so izvrsuvanje
        }
    }
}