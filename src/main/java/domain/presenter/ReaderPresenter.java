package domain.presenter;

import domain.model.Label;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface ReaderPresenter {


    ObservableList<Label> fillTerminalTable() throws SQLException;
    String readFile() throws FileNotFoundException;
    String convertFile(Label label);
    String buildOneTask() throws FileNotFoundException, SQLException;


    interface ReaderViewer {

        void onError();
        void onResult();
        void onProcess();
    }
}
