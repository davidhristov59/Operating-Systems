package mailZadaca;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private int port;
    private String fileOutput;

    public Server(int port, String fileOutput) {
        this.port = port;
        this.fileOutput = fileOutput;
    }

    @Override
    public void run() {
        System.out.println("SERVER starting...");

        Socket socket = null;
        ServerSocket serverSocket = null;
        BufferedWriter bufferedWriter = null;

        try {
            serverSocket = new ServerSocket(port);
            bufferedWriter = new BufferedWriter(new FileWriter(fileOutput, true));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SERVER - STARTED..");
        System.out.println("SERVER: waiting for connections...");

        while(true){

            try {
                socket = serverSocket.accept();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Establishing a connection between the Client and the Server");

            //TODO
            new Worker(socket, bufferedWriter).start();
        }


    }

    public static void main(String[] args) {

        Server server = new Server(8765, "server.txt");
        server.start();
    }
}
