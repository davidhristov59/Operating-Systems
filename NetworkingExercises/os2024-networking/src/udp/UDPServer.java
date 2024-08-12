package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//za UDP ne mora da vospostavuvame konekcija prvo
public class UDPServer extends Thread {

    private DatagramSocket socket; //socket za UDP protocol za preku istiot da isprakame podatoci
    private byte[] buffer; //gi isprakame kako byte podatocite vo bafer
    //vo ovoj bafer gi stavame podatocite sto sakame da gi ispratime i podatocite sto ke gi citame preku istiot bafer

    public UDPServer(int port) {
        try {
            this.socket = new DatagramSocket(port); //tuka inicijalizirame
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.buffer = new byte[256];
    }

    @Override
    public void run() {

        //Server - primame paket od klientot i isprakame nazad odgovor
        //porakata sto ja ispratil klientot mu ja vrakame nazad kako server

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) { //serverot vrti beskonecno i ceka dodeka ne primi poraki i koga ke primi gi prepraka nazad do istata adresa
            try {
                socket.receive(packet); //namesto accept, direktno primam paket, ne cekam konekcija
                String receivedMessage = new String(packet.getData(), 0, packet.getLength()); //offset - od kaj pocnuva , znaci pocnuva od 0 baferot do samiot kraj (samiot length) - zemi gi site podatoci
                System.out.println("SERVER RECEIVED: " + receivedMessage);

                //nazad ja vrakam istata poraka do klientot , ja preprakam
                //kreiram packet , go reiskoristuvam noviot packet objekt
                    DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket); //isprati go paketot nazad
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        UDPServer server = new UDPServer(8000);
        server.start();
    }
}
