import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_Serv {
    public static void main(String[]args) throws IOException, NullPointerException{
        final int PORT = 3000;
        System.out.println("Starting server");

        //creating a socket
        DatagramSocket datagramSocket = new DatagramSocket(PORT);
        byte[] send;
        byte[] recieved = new byte[65535];
        DatagramPacket datagramPacketRecieved;

        //creating a DatagramPacket for recieved data - placing the data in byte buffer
        while(true){
            datagramPacketRecieved = new DatagramPacket(recieved,recieved.length);
            datagramSocket.receive(datagramPacketRecieved);

            String input = new String(datagramPacketRecieved.getData(),0,datagramPacketRecieved.getLength());
            System.out.println("Input from client: " + input);

            //Making the calculator
            int sum = -1;
            String res = null;
            if(input.contains("+")) {
                int one = Integer.parseInt(input.split("\\+")[0]);
                int two = Integer.parseInt(input.split("\\+")[1]);
                sum = one + two;
                res = input + "=" + sum + "\n Continue on calculating or type 'exit' to leave";
            }
            else if(input.contains("-")) {
                int one = Integer.parseInt(input.split("-")[0]);
                int two = Integer.parseInt(input.split("-")[1]);
                sum = one - two;
                res = input + "=" + sum + "\n Continue on calculating or type 'exit' to leave";
            }
            else if(input.contains("/")) {
                int one = Integer.parseInt(input.split("/")[0]);
                int two = Integer.parseInt(input.split("/")[1]);
                sum = one / two;
                res = input + "=" + sum + "\n Continue on calculating or type 'exit' to leave";
            }
            else if(input.contains("*")) {
                int one = Integer.parseInt(input.split("\\*")[0]);
                int two = Integer.parseInt(input.split("\\*")[1]);
                sum = one * two;
                res = input + "=" + sum + "\n Continue on calculating or type 'exit' to leave";
            }
            else if (input.contains("exit")){
                res = "exits calculator";

            }
            send = res.getBytes();

            //Fetching the Ip address of with the packet is getting sent over
            InetAddress IP = datagramPacketRecieved.getAddress();
            int portAdress = datagramPacketRecieved.getPort();

            DatagramPacket datagramPacketSend = new DatagramPacket(send,send.length,IP,portAdress);
            datagramSocket.send(datagramPacketSend);

            if(input.equals("exit")){
                System.out.println("Exiting");
                datagramSocket.close();
                break;
            }

            // Clearing the byte buffer
            recieved = new byte[65535];
        }
    }
}
