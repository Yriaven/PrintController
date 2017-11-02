package klasy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.omg.SendingContext.RunTime;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class Controller {


    @FXML
    TextArea console;
    @FXML
    Button pingButton;
    @FXML
    Button PrintButton;
    @FXML
    Button InstalledPrintersButton;
    @FXML
    Button DefaultPrinterButton;
    @FXML
    Button TestButton;
    @FXML
    Label label;
    @FXML
    TableView<Printer> tabelka1;
    @FXML
    TableColumn<Object, Object> t1;
    @FXML
    TableColumn<Object, Object> t2;
    @FXML
    TableColumn<Object, Object> t3;
    @FXML
    TableView<Printer> tabelka2;
    @FXML
    TableColumn<Object, Object> t4;
    @FXML
    TableColumn<Object, Object> t5;  //TODO dodac id, dodac klase
    @FXML
    TableColumn<Object, Object> t6;

    @FXML
    ObservableList<Printer> Printer_LIST;
    Connection connection = null;
    String query = "SELECT \"Name\", \"U_IP\" FROM \"@PRINTERS\"";


    public void initialize() {
        t1.setCellValueFactory(new PropertyValueFactory<>("PrinterName"));
        t2.setCellValueFactory(new PropertyValueFactory<>("PrinterIP"));
        t3.setCellValueFactory(new PropertyValueFactory<>("PrinterStatus"));
        connectToDatabase();
        executequery();


    }

    public void pingFromConsole() {
        if (tabelka1.getSelectionModel().getSelectedItem() != null) {
            Printer printer = tabelka1.getSelectionModel().getSelectedItem();
            console.setText("");
            console.appendText(String.valueOf(PrintOperations.checkConnection(printer.getPrinterIP())));
            System.out.println(PrintOperations.checkConnection(printer.getPrinterIP()));
        }
    }

    public void executequery() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Printer_LIST = FXCollections.observableArrayList();

            while (rs.next()) {
                Printer print = new Printer();
                print.PrinterName.set(rs.getString("Name"));
                print.PrinterIP.set(rs.getString("U_IP"));
                print.PrinterStatus.set(PrintOperations.checkConnection(print.getPrinterIP()));
                Printer_LIST.add(print);
            }
            tabelka1.setItems(Printer_LIST);
            System.out.println(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sap://172.16.0.54:30015/?currentschema=SBOELECTROPOLI", "SYSTEM", "Ep*4321#");
            label.setText("Połączono z SBOELECTROPOLI");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public void HPPrint() {

        Printer printer = tabelka1.getSelectionModel().getSelectedItem();
        if (printer.getPrinterStatus() == "request timed out") {
            JOptionPane.showMessageDialog(null, "Drukarka niedostępna");
        } else {
            PrintOperations.PrintByHP(printer.getPrinterIP());
        }
    }

    public void showInstalled() {
        PrintOperations.ShowInstalledPrinters(console);
    }

    public void showDefaultPrinter() {
        PrintOperations.ShowDefaultPrinter(console);
    }

    public void print() {

        PrinterJob pj = PrinterJob.getPrinterJob();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Number of printers configured: " + printServices.length);
        for (PrintService printer : printServices) {
            System.out.println("Printer: " + printer.getName());
            if (printer.getName().equals("HP Laser Jet P3015DN - Inżynieria produkcji")) {
                try {
                    pj.setPrintService(printer);
                } catch (PrinterException ex) {
                }
            }


        }

    }

    public void pingSelectedItem()
    {
        Printer printer = tabelka1.getSelectionModel().getSelectedItem();
        PrintOperations.ping(printer.getPrinterIP(), console);
    }




}








