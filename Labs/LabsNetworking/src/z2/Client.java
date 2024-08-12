package z2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{

    private int serverPort;

    public Client(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run() {

        String serverName = System.getenv("SERVER_NAME");

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName(serverName), serverPort);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("login\n");
            bufferedWriter.write("Host: developer.mozilla.org\n");
            bufferedWriter.write("User-Agent: OSClient\n");
            bufferedWriter.write("logout\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

            String line;
            while((line=bufferedReader.readLine()) != null){
                System.out.println("Client received: "+ line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }

                if(bufferedWriter != null){
                    bufferedWriter.close();
                }

                if(socket != null){
                    socket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Client client = new Client(8010);
        client.start();

    }
}
