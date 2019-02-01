package domain.presenter;

import domain.model.Label;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface ReaderPresenter {


    ObservableList<Label> fillTerminalTable() throws SQLException;
    String readFile() throws FileNotFoundException;
    String convertFile(Label label);
    String buildOneTask() throws FileNotFoundException, SQLException;
    void sendToPrinter(String zpl) throws IOException;
    void callUpdateQuery() throws SQLException;


    interface ReaderViewer {

        void onError();
        void onResult();
        void onProcess();
    }
}
