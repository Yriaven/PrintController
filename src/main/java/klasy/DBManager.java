package klasy;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {



    public static void ConnectToDatabase()
    {
        try {
             Connection connection = null;
             connection = DriverManager.getConnection("jdbc:sap://172.16.0.54:30015/?currentschema=SBOELECTROPOLI", "SYSTEM", "Ep*4321#");
            JOptionPane.showMessageDialog(null, "Nawiązano połączenie z bazą danych");
        }

        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
