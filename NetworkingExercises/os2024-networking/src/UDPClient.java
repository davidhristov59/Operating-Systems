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
            InetAddress.getByName(serverName);

        } catch (SocketException | UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        buffer = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, serverPort);

        try {
            datagramSocket.send(datagramPacket);
            datagramPacket = new DatagramPacket(buffer, buffer.length,InetAddress.getByName(serverName), serverPort);
            datagramSocket.receive(datagramPacket);

            String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            System.out.println("Got message from server" + message);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        UDPClient udpClient = new UDPClient("localhost", 8000, "Hello");
        udpClient.start();
    }
}
