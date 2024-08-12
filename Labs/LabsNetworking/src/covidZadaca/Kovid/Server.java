package covidZadaca.Kovid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true){

            System.out.println("SERVER..Starting");
            Socket socket = null;

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //TODO
            new Worker(socket, fileOutput).start();
        }

    }

    public static void main(String[] args) {

        Server server = new Server(8888, "./data.csv");
        server.start();
    }
}
