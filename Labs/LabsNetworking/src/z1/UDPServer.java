package z1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Thread {

    private DatagramSocket datagramSocket;
    private byte[] buffer;

    public UDPServer(int port) {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
            return;
        }
        buffer = new byte[256];
    }

    @Override
    public void run() {

        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

        while(true){

            try {
                datagramSocket.receive(datagramPacket);
                String received_message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

                System.out.println("RECEIVED " + received_message);

                String message;

                if(received_message.equals("login")){
                    message = "logged in";
                }
                else if(received_message.equals("logout")){
                    message = "logged out";
                }
                else {
                     message = "echo-" + received_message;
                }

                buffer = message.getBytes();

                datagramPacket = new DatagramPacket(buffer, buffer.length, datagramPacket.getAddress(), datagramPacket.getPort()); //so datagramPacket manipuliram

                datagramSocket.send(datagramPacket);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void main(String[] args)  {

        UDPServer udpServer = new UDPServer(8005);
        udpServer.start();

    }
}
