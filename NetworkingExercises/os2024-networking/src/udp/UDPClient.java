package udp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread{

    private String serverName;
    private int serverPort;


    private InetAddress inetAddress;
    private DatagramSocket datagramSocket;
    private byte[] buffer;
    private String message;

    public UDPClient(String serverName, int serverPort, String message) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.message = message;

        try {
            datagramSocket = new DatagramSocket();
            inetAddress = InetAddress.getByName(serverName);
            //        inetAddress = datagramSocket.getInetAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        buffer = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,inetAddress, serverPort);

        try {

            //go isprakame paketot preku socket
            datagramSocket.send(datagramPacket);

            //sakame da dobieme odgovor
            //vo istiot buffer sakam da dobijam odgovor
            datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(serverName), serverPort);         //baferot go isprakame i bajti isprakame kolku sto e dolzinata na baferot

            datagramSocket.receive(datagramPacket); //vo toj paket da mi se smesti porakata koja sto ke ja dobijam nazad od serverot

            String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength()); //podatocite gi dobivavame vo bajti, i od kaj do kaj-  od 0 do dolzinata na paketot
            System.out.println("Got message from server: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException{
        UDPClient udpClient = new UDPClient("localhost",8000, "Hello"); //porakata sto sakame da ja ispratime
        udpClient.start();
    }
}

