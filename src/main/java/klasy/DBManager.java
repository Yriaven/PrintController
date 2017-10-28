package klasy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.sql.*;

public class DBManager {

    public static void selectFromPrinters(Connection connection, ObservableList<Printer> observableList, TableView<Printer> tableView)
    {
        String query = "SELECT \"Name\", \"U_IP\" FROM \"@PRINTERS\"";
        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            observableList = FXCollections.observableArrayList();

            while (rs.next())
            {
                Printer print = new Printer();
                print.PrinterName.set(rs.getString("Name"));
                print.PrinterIP.set(rs.getString("U_IP"));
                print.PrinterStatus.set(String.valueOf(PrintOperations.checkConnection(print.getPrinterIP())));
                observableList.add(print);
            }
            tableView.setItems(observableList);
            System.out.println(1);
        }

        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void connectToDatabase(Connection connection)
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:sap://172.16.0.54:30015/?currentschema=SBOELECTROPOLI", "SYSTEM", "Ep*4321#");
            //label.setText("Połączono z SBOELECTROPOLI");
        }

        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


}
