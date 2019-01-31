package domain.presenter;

import domain.model.Label;
import domain.services.DBService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;


public class IReaderPresenter implements ReaderPresenter {

    private ReaderViewer readerViewer;
    private DBService dbService;
    private Connection connection = null;

    @Override
    public ObservableList<Label> fillTerminalTable() throws SQLException {
        return dbService.getTaskList(connection);
    }

    @Override
    public void readFile() {

    }

    @Override
    public void convertFile() {

    }

    @Override
    public void buildFile() {

    }

    @Override
    public void sendTaskToPrinter() {

    }

    public IReaderPresenter(ReaderViewer readerViewer) {
        this.dbService = new DBService();
        this.connection = dbService.initializeConnection();
    }


}
