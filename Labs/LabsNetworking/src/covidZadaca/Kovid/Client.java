package covidZadaca.Kovid;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{

    private int serverPort;
    private String serverName;

    public Client(int serverPort, String serverName) {
        this.serverPort = serverPort;
        this.serverName = serverName;
    }

    @Override
    public void run() {

        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            socket = new Socket(InetAddress.getByName(serverName), serverPort);
            printWriter= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;

            printWriter.println("Start");
            printWriter.flush();

            while(!(line = bufferedReader.readLine()).isEmpty()){

                System.out.println("Client received " + line);

                if(line.startsWith("HELLO")){
                    printWriter.println("HELLO " + serverPort);
                    printWriter.flush();
                }
                else if(line.startsWith("SEND")){
                    printWriter.println("20, 23, 45");
                    printWriter.println("12, 13, 14");
                    printWriter.flush();
                }
                else{
                    printWriter.println("QUIT");
                    printWriter.flush();
                }

            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        Client client = new Client(8888, "localhost");
        client.start();
    }
}
