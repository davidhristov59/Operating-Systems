package z2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread{

    private Socket socket;
    private static int counter=0;
    private Lock lock;
    private int localcounter;

    public Worker(Socket socket){

        this.socket = socket;
        localcounter = 0;
        lock = new ReentrantLock();
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            List<String> inputs = new ArrayList<>();
            String line;

            while(!(line = bufferedReader.readLine()).equals("")){
                inputs.add(line);
            }

            String firstLine = inputs.get(0);

            if(!firstLine.equals("login")){ //3. Серверот враќа потврдна порака logged in. Доколку првата порака од клиентот не е login, серверот веднаш ја затвора конекцијата
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            }

            localcounter++; //Incremented to count the "login" message.
            bufferedWriter.write("logged in " + "\n"); //Acknowledgement: The server sends a "logged in" message back to the client.

            for (int i = 1; i < inputs.size(); i++) {
                if(inputs.get(i).equals("logout")){ //5. Ако клиентот на крај прати logout, серверот треба да врати logged out и да ја затвори конекцијата
                    bufferedWriter.write("logged out\n");
                    localcounter++;
                    break;
                }
                //4. Клиентот може да прати било каква порака, на која серверот враќа echo: истата порака
                bufferedWriter.write("echo: " + inputs.get(i) + "\n");
                localcounter++;
            }

            System.out.println("here");

            //IMPORTANT!!!
            lock.lock();
            counter += localcounter;
            bufferedWriter.write(counter + "\n");
            lock.unlock();

            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }

                if(bufferedWriter != null){
                    bufferedWriter.close();
                }

                if(!socket.isClosed()){
                    socket.close();
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
