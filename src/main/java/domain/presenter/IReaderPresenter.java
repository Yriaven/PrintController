package domain.presenter;

import domain.model.Label;
import domain.services.DBService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class IReaderPresenter implements ReaderPresenter {

    private ReaderViewer readerViewer;
    private DBService dbService;
    private Connection connection = null;

    @Override
    public ObservableList<Label> fillTerminalTable() throws SQLException {
        return dbService.getTaskList(connection);
    }

    @Override
    public String readFile() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("\\\\grupa\\ep\\EGTBB\\Informatyka\\Publiczne\\Serwis wydruku Daimler\\etykietaEDI.txt"));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return sb.toString();
    }

    @Override
    public String convertFile(Label label) {
        String converted = "";

        try {
            converted = readFile();
            converted = converted.replaceAll("StringPar1", label.getOdbiorca());;
            converted = converted.replaceAll("StringPar3", label.getPartNo());
            converted = converted.replaceAll("StringPar4", String.valueOf(label.getQuantity()));
            converted = converted.replaceAll("StringPar2", label.getAdviceNote());
            converted = converted.replaceAll("StringPar5", label.getDescription());
            converted = converted.replaceAll("StringPar6", label.getSupplier());
            converted = converted.replaceAll("StringPar7", label.getSupplierPartNumber());
            converted = converted.replaceAll("StringPar8", label.getLos()); //
            converted = converted.replaceAll("StringPar9", label.getStreet());
            converted = converted.replaceAll("StringParA", label.getDate());
            converted = converted.replaceAll("NumericPar1", label.getGTL());
            converted = converted.replaceAll("NumericPar2", label.getGate());
            converted = converted.replaceAll("NumericPar4", String.valueOf((label.getLabelNo())));
            converted = converted.replaceAll("StringParB", label.getCity());

            System.out.println("Przekonwertowany " +converted);

            return converted;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return converted;
        }
    }


    @Override
    public String buildOneTask() throws FileNotFoundException, SQLException {
        return this.dbService.getSingleTask(this.connection);
    }

    public IReaderPresenter(ReaderViewer readerViewer) {
        this.dbService = new DBService();
        this.connection = dbService.initializeConnection();
    }


}
