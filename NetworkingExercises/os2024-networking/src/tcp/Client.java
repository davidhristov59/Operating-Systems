package tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{

    private int server_port;

    public Client(int server_port) {
        this.server_port = server_port;
    }

    @Override
    public void run() {

        Socket socket = null;
        BufferedReader bufferedReader= null;
        BufferedWriter bufferedWriter = null;

        try {
            socket = new Socket(InetAddress.getLocalHost(),server_port);

             bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("GET / HTTP/1.1\n");
            bufferedWriter.write("Host: developer-mozilla.org");
            bufferedWriter.write("User-Agent");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

            String line;

            while((line = bufferedReader.readLine())!=null){
                System.out.println(line);
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                }

                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if(socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        Client client = new Client(7005);
        client.start();
    }
}

