package z1;

import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread{

    private String serverName;
    private int serverPort;

    private DatagramSocket datagramSocket;
    private byte[] buffer;
    private InetAddress inetAddress;
    private String message;

    public UDPClient(String serverName, int serverPort, String message) {

        try {
            datagramSocket = new DatagramSocket(serverPort);
            inetAddress = InetAddress.getByName(serverName);
        } catch (SocketException | UnknownHostException e ) {
            e.printStackTrace();
        }

        this.serverName = serverName;
        this.serverPort = serverPort;
        this.message = message;
    }

    @Override
    public void run() {

        buffer = message.getBytes(); //!!!!!!
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, serverPort);

        try {
            datagramSocket.send(datagramPacket);

            datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, serverPort);

            datagramSocket.receive(datagramPacket);

            System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        UDPClient udpClient = new UDPClient("localhost", 8005, "Hello");
        udpClient.start();
    }
}