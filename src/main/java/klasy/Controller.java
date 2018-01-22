package klasy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

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
    Button refreshServerButton;
    @FXML
    Button pingServerButton;
    @FXML
    Button refreshTerminalButton;
    @FXML
    Button pingTerminalButton;
    @FXML
    Label label;
    @FXML
    TableView<Printer> printerTableView;
    @FXML
    TableColumn<Object, Object> t1;
    @FXML
    TableColumn<Object, Object> t2;
    @FXML
    TableColumn<Object, Object> t3;
    @FXML
    TableView<Terminals> TerminalTableView;
    @FXML
    TableColumn<Object, Object> t7;
    @FXML
    TableColumn<Object, Object> t8;
    @FXML
    TableColumn<Object, Object> t9;
    @FXML
    TableView<Servers> serverTableView;
    @FXML
    TableColumn<Object, Object> t4;
    @FXML
    TableColumn<Object, Object> t5;
    @FXML
    TableColumn<Object, Object> t6;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            fillPrinterTable();
            fillServerTable();
            fillTerminalTable();
        }
    };

    @FXML
    ObservableList<Printer> Printer_LIST;
    ObservableList<Servers> Server_LIST;
    ObservableList<Terminals> Terminal_LIST;
    Connection connection = null;
    String queryPrinter = "SELECT \"Name\", \"U_IP\" FROM \"@PRINTERS\"";
    String queryservers = "SELECT \"Name\", \"U_IP\" FROM \"@SERVERS\"";
    String queryterminal = "SELECT \"Name\", \"U_IP\" FROM \"@TERMINALS\"";


    public void initialize() {
        t1.setCellValueFactory(new PropertyValueFactory<>("PrinterName"));
        t2.setCellValueFactory(new PropertyValueFactory<>("PrinterIP"));
        t3.setCellValueFactory(new PropertyValueFactory<>("PrinterStatus"));
        t4.setCellValueFactory(new PropertyValueFactory<>("ServerName"));
        t5.setCellValueFactory(new PropertyValueFactory<>("ServerIP"));
        t6.setCellValueFactory(new PropertyValueFactory<>("ServerStatus"));
        t7.setCellValueFactory(new PropertyValueFactory<>("TerminalName"));
        t8.setCellValueFactory(new PropertyValueFactory<>("TerminalIP"));
        t9.setCellValueFactory(new PropertyValueFactory<>("TerminalStatus"));
        Controller cnt = new Controller();
        timer.schedule(task, 1000, 1000);
        fillPrinterTable();
        fillServerTable();
        fillTerminalTable();
    }


    public void fillPrinterTable() {
        try {
            connection = DriverManager.getConnection("xxx");
            label.setText("Połączono z SBOELECTROPOLI");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryPrinter);
            Printer_LIST = FXCollections.observableArrayList();

            while (rs.next()) {
                Printer print = new Printer();
                print.PrinterName.set(rs.getString("Name"));
                print.PrinterIP.set(rs.getString("U_IP"));
                print.PrinterStatus.set(PrintOperations.checkConnection(print.getPrinterIP()));
                Printer_LIST.add(print);
            }
            printerTableView.setItems(Printer_LIST);
         //   connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void fillServerTable()
    {
        try {
            connection = DriverManager.getConnection("xxx");
            label.setText("Połączono z SBOELECTROPOLI");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryservers);
            Server_LIST = FXCollections.observableArrayList();

            while (rs.next()) {
                Servers obiekt = new Servers();
                obiekt.ServerName.set(rs.getString("Name"));
                obiekt.ServerIP.set(rs.getString("U_IP"));
                obiekt.ServerStatus.set(PrintOperations.checkConnection(obiekt.getServerIP()));
                Server_LIST.add(obiekt);
            }
            serverTableView.setItems(Server_LIST);
           // connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void fillTerminalTable()
    {
        try {
            connection = DriverManager.getConnection("xxx");
            label.setText("Połączono z SBOELECTROPOLI");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryterminal);
            Terminal_LIST = FXCollections.observableArrayList();

            while (rs.next()) {
                Terminals obiekt = new Terminals();
                obiekt.TerminalName.set(rs.getString("Name"));
                obiekt.TerminalIP.set(rs.getString("U_IP"));
                obiekt.TerminalStatus.set(PrintOperations.checkConnection(obiekt.getTerminalIP()));
                Terminal_LIST.add(obiekt);
            }
            TerminalTableView.setItems(Terminal_LIST);
           // connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

//    public void connectToDatabase() {
//        try {
//            connection = DriverManager.getConnection("xx");
//            label.setText("Połączono z SBOELECTROPOLI");
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        }
//    }


    public void HPPrint() {

        try {
            Printer printer = printerTableView.getSelectionModel().getSelectedItem();
            if (printer.getPrinterStatus() == "request timed out") {
                JOptionPane.showMessageDialog(null, "Drukarka niedostępna");
            } else {
                PrintOperations.PrintByHP(printer.getPrinterIP());
            }
        }

        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Wybierz pozycję");
        }

    }

    public void pingServer()
    {
        try {
            Servers server = serverTableView.getSelectionModel().getSelectedItem();
            PrintOperations.ping(server.getServerIP(), console);
        }

        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Wybierz pozycję");
        }

    }

    public void pingPrinter()
    {
        try {
            Printer printer = printerTableView.getSelectionModel().getSelectedItem();
            PrintOperations.ping(printer.getPrinterIP(), console);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Wybierz pozycję");
        }

    }

    public void pingTerminal()
    {
        try {
            Terminals terminals = TerminalTableView.getSelectionModel().getSelectedItem();
            PrintOperations.ping(terminals.getTerminalIP(), console);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Wybierz pozycję");
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

    }//TODO do sprawdzenia





}








