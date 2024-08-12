package covidZadaca.Kovid;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread{

     private Socket socket;
     private String fileOutput;

    public Worker(Socket socket, String fileOutput) {
        this.socket = socket;
        this.fileOutput = fileOutput;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        PrintWriter fileWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            fileWriter = new PrintWriter(new FileOutputStream(fileOutput, true));

            String line;

            while(!(line=bufferedReader.readLine()).equals("QUIT")){

                System.out.println("SERVER received " + line );

                //1 cekor
                if(line.equals("Start")){
                    printWriter.println("Hello " + socket.getInetAddress() + "\n");
                    printWriter.flush();
                }

                else if(line.startsWith("HELLO")){
                    //3 cekor
                    printWriter.println("SEND DAILY DATA  ");
                    printWriter.flush();
                }
                else{ //4 cekor
                    //No. new covid cases, No. hospitalized patients, No. recovered patients
                    String[] parts = line.split(", ");

                    if(parts.length < 3 ){
                        System.err.println("Mora da se 3 vrednosti");
                    }
                    //cekor 5
                    printWriter.println("OK");
                    printWriter.flush();

                    fileWriter.println(line);
                    fileWriter.flush();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
