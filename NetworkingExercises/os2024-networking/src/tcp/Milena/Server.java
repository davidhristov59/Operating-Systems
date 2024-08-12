package tcp.Milena;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private int port; //da go formiram socket-ot --> IP address i port
    //portata na koja sakam da go otvoram socket-ot

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("SERVER: starting...");

        ServerSocket serverSocket = null; //nadvor inicijaliziram za da imam access

        try {
            serverSocket = new ServerSocket(port); //kreirame serverski socket na odredena porta
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        //celta na serverot e da vrti vo beskonecen loop, da ceka konekcii i vo momentot koga ke dobie konekcija da
        // startuva nekoj Thread

        System.out.println("SERVER: started");
        System.out.println("SERVER: waiting for connections...");

        while (true) { //beskonecen ciklus vo koj serverot ke ceka klientot da se konektiraat na nego

            Socket newClient = null; //nadvor za da imam access
            try {
                //prifakame konekcija od strana na klientot
                newClient = serverSocket.accept(); //accept vraka socket, Client socket vraka
                //so ova prifakame nekakva komunikacija megju klientot i serverot
                //serverot se blokira e dodeka ne dobie konekcija
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            System.out.println("SERVER: new client - creating new worker thread...");
            new Worker(newClient).start(); //se startuva Worker Thread- drugata konekcija koja ja pravi serverot
        }
    }

    public static void main(String[] args) {
        Server server = new Server(7000);
        server.start(); //go startuvam threadot koj sto e server
    }
}