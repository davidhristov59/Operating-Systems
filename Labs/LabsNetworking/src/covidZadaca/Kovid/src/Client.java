package covidZadaca.Kovid.src;

import javax.lang.model.SourceVersion;
import javax.lang.model.type.PrimitiveType;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
    InetAddress address;
    int port;

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket=null;
        try {
            socket=new Socket(address,port);
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line=null;
            writer.println("Start");
            writer.flush();
            while(!(line= reader.readLine()).isEmpty())
            {
                System.out.println("Client received "+line);
                if(line.startsWith("HELLO"))
                {
                    writer.println("HELLO "+port);
                    writer.flush();
                }
                else if(line.startsWith("SEND"))
                {
                    writer.println("20, 23, 45");
                    writer.println("12, 13, 14");
                    writer.flush();
                }
                else {
                    writer.println("QUIT");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) throws UnknownHostException {
        Client client=new Client(InetAddress.getLocalHost(),7878);
        client.start();
    }
}
