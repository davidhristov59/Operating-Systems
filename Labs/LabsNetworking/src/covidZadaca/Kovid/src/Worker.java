package covidZadaca.Kovid.src;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Worker extends Thread{
    Socket socket;
    String filePath;
    public static boolean written=false;

    public Worker(Socket socket, String filePath) {
        this.socket = socket;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        BufferedReader reader=null;
        PrintWriter writer=null;
        PrintWriter fileWriter=null;
        String line=null;
        try {
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            fileWriter=new PrintWriter(new FileOutputStream(filePath,true));
            if(!written) {
                fileWriter.println("NO. new cases  NO. hospitalized patients NO. recovered patients");
                fileWriter.flush();
                written=true;
            }
            while(!(line=reader.readLine()).equals("QUIT"))
            {

                System.out.println("Server received: "+line);
                if(line.equals("Start"))
                {
                    writer.println("HELLO "+ socket.getInetAddress());
                    writer.flush();
                }
                else if(line.startsWith("HELLO"))
                {
                    writer.println("SEND DAILY DATA");
                    writer.flush();
                }

                else
                {
                    String [] parts=line.split(",");
                    if(parts.length<3) {
                        System.err.println("Mora da se tri vrednosti");
                    }
                    else
                    {
                        writer.println("OK");
                        writer.flush();
                        fileWriter.println(line);
                        fileWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
