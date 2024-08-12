package mailZadaca;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread{

    private Socket socket;
    private BufferedWriter fileWriter;

    public Worker(Socket socket, BufferedWriter fileWriter) {
        this.socket = socket;
        this.fileWriter = fileWriter;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        String mailTo = null;
        String mailCC = null;
        String mailFrom=null;
        boolean readData=false;
        int counter = 0;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line;

            while(!(line=bufferedReader.readLine()).equals("EXIT")){

                System.out.println("SERVER received " + line);

                if(line.equals("Connected")){
                    printWriter.println("Start: " + socket.getInetAddress()); //1 cekor
                    printWriter.flush();
                }

                if(line.startsWith("MAIL_TO")){ //2 cekor i 3 cekor
                    String[] parts = line.split("\\s+");
                    if (parts[1].contains("@")){
                        mailTo = parts[1];
                        printWriter.println("TNX");
                        printWriter.flush();
                    }
                    else{ //3 cekor ...
                        throw new Exception("The email is not valid");
                    }
                }
                else if(line.startsWith("MAIL_FROM")){ //4 i 5ti cekor

                    String[] parts = line.split("\\s+");

                    if(parts[1].contains("@")){
                        mailFrom = parts[1];
                        printWriter.println("200");
                        printWriter.flush();
                    }

                    else {
                        throw new Exception("The email is not valid");
                    }
                }

                else if(line.startsWith("MAIL_CC")){ //6 cekor i 7mi cekor
                    String[] parts = line.split("\\s+");
                    if(parts[1].contains("@")){
                        mailCC = parts[1];
                        readData = true;
                        printWriter.println("RECEIVERS: " + mailTo + ", " + mailFrom + "\n");
                        printWriter.flush();
                    }
                }

                if(readData){
                    String data = "";

                    //8mi cekor
                    while(!(data= bufferedReader.readLine()).equals("?")){
                        counter++;
                        fileWriter.write(data);
                    }

                    //9 cekor
                    printWriter.println("RECEIVED " + counter + "\n");
                    fileWriter.flush();
                    printWriter.flush();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        finally {

            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }

                if(fileWriter != null){
                    fileWriter.flush();
                    fileWriter.close();
                }

                if(printWriter != null){
                    printWriter.close();
                }

                if(socket != null){
                    socket.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
