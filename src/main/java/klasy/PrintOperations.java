package klasy;


import java.io.IOException;
import java.net.InetAddress;
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
            System.out.println("UnknownHostException");
        }

        catch (IOException e)
        {
            System.out.println("IOException");
        }

        return status;

    }

}
