package tcp.Milena;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 extends Thread{

    private int port;

    public Server2(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Starting in the server");

        ServerSocket serverSocket = null; //nadvor go stavam za da imam access
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("SERVER: started");
        System.out.println("SERVER: waiting for connections...");

        while (true){

            Socket newClient = null; //nadvor za da imam access

            try {
                newClient = serverSocket.accept();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }

            System.out.println("SERVER: new client - creating new worker thread...");
            new Worker(newClient).start();
        }
    }

    public static void main(String[] args) {

        Server server = new Server(7000);
        server.start();

    }
}
