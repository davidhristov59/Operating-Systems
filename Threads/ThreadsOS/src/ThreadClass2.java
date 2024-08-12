class Incrementor2{

    private int count = 0;

    //synchronized - nikoja druga niska ne moze da vleze vo odreden del dodeka niskata sto e veke vnatre ne go oslobodi kriticniot region
    public synchronized void increment(){ //morame metodot da go napravime da bide sinhroniziran
        count++;

        //T1 --> increment() - prviot thread sto go povikuva increment()
        //T1 gleda vo monitorot na objektot na increment() - dali jas imam dozvola da vlezam vo increment - go prasuva Monitorot, mutex e sloboden, vleguva
        //T2 sega go povikuva increment, no bidehki imame synchronized keyword T2 go prasuva monitorot dali imam dozvola da vlezam, ama taa dozvola ne e oslobodena zosto uste T1  e vnatre
        //T1 uste ne zavrsil i ne ja vraka dozvolata. Ja vraka dozvolata koga ke zavrsi so izvrsuvanje na metodot
        //synchronized ima samo 1 dozvola
    }

    public int getCount(){
        return this.count;
    }

}

class ThreadExecutor2 extends Thread{

    private String name;
    Incrementor2 incrementor2;

    public ThreadExecutor2(String name, Incrementor2 incrementor2){
        this.name = name;
        this.incrementor2 = incrementor2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            incrementor2.increment();
        }
    }
}

public class ThreadClass2{

    public static void main(String[] args) throws InterruptedException {


        Incrementor2 incrementor2 = new Incrementor2();
        Incrementor2 incrementor2_2 = new Incrementor2();

        Thread threadExecute = new ThreadExecutor2("Thread 1", incrementor2);
        Thread threadExecute2 = new ThreadExecutor2("Thread 2", incrementor2_2); //ima pristap do razlicna memorija
            //2te niski imaat pristap do razlicna memorija - MOZE BEZ SYNCHRONIZED


        threadExecute.start();
        threadExecute2.start();

        //vo programa so poveke nitki ne znaeme koja nitka ke zavrsi prva

        //mozeme da gi cekame nitkite da zavrsat - so KORISTENJE NA METODOT join() o
        threadExecute.join();
        threadExecute2.join();

        int count = incrementor2.getCount();
        System.out.println(count);


    }
}