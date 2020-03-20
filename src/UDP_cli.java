import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDP_cli {
    public static void main(String args[]) throws IOException {
        final int PORT = 3000;
        System.out.println("Do your calculations or type 'exit' to leave");

        Scanner scanner = new Scanner(System.in);
        DatagramSocket ds = new DatagramSocket();
        InetAddress IP = InetAddress.getLocalHost();

        byte[] send;
        byte[] recieved = new byte[256];

        // Loops until user types exit
        while (true) {
            String input = scanner.nextLine();

            // converts String input into byte array.
            send = input.getBytes();

            // Create a datagramPacket for sending data.
            DatagramPacket dpSend = new DatagramPacket(send, send.length, IP, PORT);

            // Invoke the send call to actually send data.
            ds.send(dpSend);

            DatagramPacket datagramPacketRecieve = new DatagramPacket(recieved, recieved.length);
            ds.receive(datagramPacketRecieve);

            String res = new String(datagramPacketRecieve.getData(), 0, datagramPacketRecieve.getLength());
            System.out.println("Server: " + res);

            if (input.equals("exit")) {
                ds.close();
                break;
            }
        }
    }
}