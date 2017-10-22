package klasy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

public class Controller {


    @FXML TextArea console;
    @FXML Button pingButton;
    @FXML public TableView<Printer> tabelka1;
    @FXML public TableColumn<Object, Object> t1;
    @FXML public TableColumn<Object, Object> t2;
    @FXML public TableColumn<Object, Object> t3;
    @FXML public ObservableList<Printer> Printer_LIST;
    Thread thread = new Thread();

    public void initialize() {
        t1.setCellValueFactory(new PropertyValueFactory<>("PrinterName"));
        t2.setCellValueFactory(new PropertyValueFactory<>("PrinterIP"));
        t3.setCellValueFactory(new PropertyValueFactory<>("PrinterStatus"));
        Printer_LIST = FXCollections.observableArrayList();
        Printer print = new Printer();
        print.PrinterIP.set("172.16.0.53");
        print.PrinterName.set("Sala szkoleń");
        print.PrinterStatus.set(String.valueOf(PrintOperations.checkConnection(print.getPrinterIP())));
        Printer_LIST.add(print);
        tabelka1.setItems(Printer_LIST);

    }

    public void pingFromConsole()
    {


                Printer printer = tabelka1.getSelectionModel().getSelectedItem();
                console.appendText(String.valueOf(PrintOperations.checkConnection(printer.getPrinterIP())));
                System.out.println(PrintOperations.checkConnection(printer.getPrinterIP()));






    }

}
