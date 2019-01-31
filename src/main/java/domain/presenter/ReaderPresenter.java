package domain.presenter;

import domain.model.Label;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public interface ReaderPresenter {


    ObservableList<Label> fillTerminalTable() throws SQLException;
    void readFile();
    void convertFile();
    void buildFile();
    void sendTaskToPrinter();


    interface ReaderViewer {

        void onError();
        void onResult();
        void onProcess();
    }
}
