package mailZadaca;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{

    private int serverPort;
    private String serverName;

    public Client(int serverPort, String serverName) {
        this.serverPort = serverPort;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        Socket socket = null;

        String emailTo = "pazi_ledja@brzina.com";
        String emailFrom = "busam_gumi@grom.com";
        String emailCC = "eat_protein@suplements.com";
        String data = "Benzin\nNafta\nProtein\nSteroidi\nTrenboloneAcetate\nWinstrolce\nChicken\n?";

        try {
            socket = new Socket(this.serverName, serverPort);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("Connected");

            String response;

            while(!(response = bufferedReader.readLine()).isEmpty()){

                if(response.startsWith("START")){
                    System.out.println("MAIL_TO " + emailTo);
                    bufferedWriter.write("MAIL_TO " + emailTo);
                }

                else if(response.startsWith("TNX")){
                    System.out.println("MAIL_FROM " + emailFrom);
                    bufferedWriter.write("MAIL_FROM " + emailFrom);
                    bufferedWriter.flush();
                }

                else if (response.startsWith("200")) {
                    System.out.println("MAIL_CC "+emailCC);
                    bufferedWriter.write("MAIL_CC: "+emailCC);
                }

                else if (response.startsWith("RECEIVERS")){
                    System.out.println(data);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {

        Client client = new Client(8765, "localhost");
        client.start();

    }
}
