package klasy;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PrintOperations {

    public static boolean checkConnection (String ip)
    {
        boolean status = false;

        InetAddress IPAdress = null;
        try {

                    IPAdress = InetAddress.getByName(ip);
                    status = IPAdress.isReachable(1000);
             }

        catch (UnknownHostException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return status;

    }

    public static void PrintByHP(String ip)
    {
        try {
            Socket sock = new Socket(ip, 9100);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            oStream.println(JOptionPane.showInputDialog(null));
            oStream.println("\n\n\n");
            oStream.close();
            sock.close();
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void printZebra()
    {
        try {
            Socket clientSocket=new Socket("172.16.1.151",6101);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream() );
            outToServer.writeBytes("! 0 200 200 203 1" + 'n' + "CENTER" + 'n');
            outToServer.writeBytes("TEXT 0 3 10 50 JAVA TEST" + 'n' + "PRINT" + 'n');
            clientSocket.close();
        }

        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
