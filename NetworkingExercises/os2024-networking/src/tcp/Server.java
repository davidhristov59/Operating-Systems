package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        System.out.println("Server STARTING...");

        ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(this.port);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return;
            }

            System.out.println("SERVER - STARTED..");
            System.out.println("SERVER: waiting for connections...");

            while(true){

                Socket socket = null;

                try {
                    //accept metodot e blokiracki
                    socket = serverSocket.accept(); //prifakame konekcija od strana na klientot. accept vraka socket, Client socket vraka
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    return;
                }

                System.out.println("Establishing a connection between the Client and the Server");

                //TODO
                new Worker(socket).start(); //OBAVEZNO START()!!!!!!!!
            }
    }


    public static void main(String[] args) {
        Server server = new Server(7005);
        server.start();
    }
}
