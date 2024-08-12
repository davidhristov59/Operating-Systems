package tcp.Milena;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Client2 extends Thread{

    private int server_port;
    private InetAddress server_address;

    public Client2(int server_port, InetAddress server_address) {
        this.server_port = server_port;
        this.server_address = server_address;
    }

    @Override
    public void run() {

        try {
            Socket socket = new Socket(server_address, server_port);
            Random random = new Random();

            //citame od input stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //citame od socket
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //zapisuvame vo socket
            //output stream od samiot socket go zimame


            //zapisuvam
            bufferedWriter.write((random.nextInt(10) % 2 == 0 ? "GET" : "POST") +
                    " /movies/" + random.nextInt(100) + " HTTP/1.1\n");
            bufferedWriter.write("User: FINKI\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public static void main(String[] args) throws UnknownHostException {

        Client2 client2 = new Client2(7000, InetAddress.getLocalHost());
        //isprati gi porakite na localHost na porta 7000 do serverot kako sto naznacivme

        client2.start();

    }
}
