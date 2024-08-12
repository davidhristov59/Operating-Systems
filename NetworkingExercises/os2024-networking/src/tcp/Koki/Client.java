package tcp.Koki;

import java.io.*;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{ //ke go zameni browser-ot

    private int server_port;

    public Client(int server_port) {
        this.server_port = server_port;
    }

    @Override
    public void run()  {

        Socket socket = null;

        try {
            //prvata slobodna porta
            socket = new Socket(InetAddress.getLocalHost(),server_port); //mu kazuvame na klientot sto treba da pristapi - kako serverot da go pristapi

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("HTTP/1.1 200 OK\n");
            bufferedWriter.write("Host: developer.mozilla.org");
            bufferedWriter.write("User-Agent: David Hristov\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

            String line;
            while(!(line = bufferedReader.readLine()).equals("")){
                System.out.println("Client received" + line);

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        Client client = new Client(7002);
        client.start();

    }
}
