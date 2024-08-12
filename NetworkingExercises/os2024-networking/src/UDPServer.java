import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Thread {

    private DatagramSocket datagramSocket;
    private byte[] buffer;

    public UDPServer(int port ) {
        try {
            this.datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
        this.buffer = new byte[256];
    }

    @Override
    public void run() {

        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

        while(true){

            try {
                datagramSocket.receive(datagramPacket);
                String receivedMessage = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("SERVER RECEIVED: " + receivedMessage);

                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, datagramPacket.getAddress(), datagramPacket.getPort());
                datagramSocket.send(sendPacket);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void main(String[] args) {

        UDPServer udpServer = new UDPServer(8000);
        udpServer.start();
    }
}
