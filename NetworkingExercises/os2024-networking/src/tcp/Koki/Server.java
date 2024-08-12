package tcp.Koki;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private int port; //mora portata da e slobodno - 7000 ke ja koristime

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        System.out.println("SERVER: starting..");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port); //na ovaa porta
        } catch (IOException e) {
            System.err.println(e.getMessage()); //2 - Error - standardna greska
            return;
        }
        System.out.println("SERVER: started..");
        System.out.println("SERVER: waiting for connections...");

        while(true){

            Socket socket = null;

            try {
                //Accept metodot e blokiracki - ako nema novi konekcii, threadot ke pauzira i ke bide vo blokirana sostojba
                //koga ke prsitigne novo baranje --> cekame nekoj da ja prifati komunikacijata, potocno odgovor da dobieme
                socket = serverSocket.accept(); //prifakame konekcija od strana na klientot. accept vraka socket, Client socket vraka

            } catch (IOException e) {
                System.err.println(e.getMessage());
                return;
            }

            System.out.println("Establishing a connection between the Client and the Server");
            new Worker(socket).start();
        }

    }

    public static void main(String[] args) {
        Server server = new Server(7002);
        server.start();
    }
}
