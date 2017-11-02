package klasy;

import javafx.beans.property.SimpleStringProperty;

public class Servers {

    public SimpleStringProperty ServerName = new SimpleStringProperty();
    public SimpleStringProperty ServerIP = new SimpleStringProperty();
    public SimpleStringProperty ServerStatus = new SimpleStringProperty();

    public String getServerName() {
        return ServerName.get();
    }


    public String getServerIP() {
        return ServerIP.get();
    }


    public String getServerStatus() {
        return ServerStatus.get();
    }


}
