package klasy;

import javafx.beans.property.SimpleStringProperty;

public class Printer {

    public SimpleStringProperty PrinterName = new SimpleStringProperty();
    public SimpleStringProperty PrinterIP = new SimpleStringProperty();
    public SimpleStringProperty PrinterStatus = new SimpleStringProperty();

    public String getPrinterName() {
        return PrinterName.get();
    }

    public String getPrinterIP() {
        return PrinterIP.get();
    }

    public String getPrinterStatus() {
        return PrinterStatus.get();
    }


}
