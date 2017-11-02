package klasy;

import javafx.beans.property.SimpleStringProperty;

public class Terminals {

    public SimpleStringProperty TerminalName = new SimpleStringProperty();
    public SimpleStringProperty TerminalIP = new SimpleStringProperty();
    public SimpleStringProperty TerminalStatus = new SimpleStringProperty();
    public SimpleStringProperty TerminalDescription = new SimpleStringProperty();

    public String getTerminalName() {
        return TerminalName.get();
    }


    public String getTerminalIP() {
        return TerminalIP.get();
    }


    public String getTerminalStatus() {
        return TerminalStatus.get();
    }


    public String getTerminalDescription() {
        return TerminalDescription.get();
    }

}
