package domain;

import domain.model.Label;
import domain.presenter.IReaderPresenter;
import domain.presenter.ReaderPresenter;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraUtils;
import fr.w3blog.zpl.model.element.ZebraNativeZpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;
import java.util.HashMap;

public class Controller implements ReaderPresenter.ReaderViewer {


    @FXML
    TextArea console;
    @FXML
    Button pingButton;
    @FXML
    Button printButton;
    @FXML
    Button printButton1;

    @FXML
    Button printButton2;

    @FXML
    Button resetButton;


    @FXML
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    TextField textField3;

    @FXML
    TableView<domain.model.Label> printerTableView;
    @FXML
    TableColumn<Object, Object> t1, t2, t3, t4, t5, t6;


    private SortedList<domain.model.Label> sortedData;
    private String url = "jdbc:sap://172.16.0.54:30015/?currentschema=TEST_20161207";
    private String user = "SYSTEM";
    private String password = "Ep*4321#";
    private Connection connection = null;
    private String resetQuery = "call EP_ResetPrint";



    private IReaderPresenter iReaderPresenter;

    public void initialize() throws SQLException {

        iReaderPresenter = new IReaderPresenter(this);

        t1.setCellValueFactory(new PropertyValueFactory<>("LabelNoProperty"));
        t2.setCellValueFactory(new PropertyValueFactory<>("QuantityProperty"));
        t3.setCellValueFactory(new PropertyValueFactory<>("DocNumberProperty"));
        t4.setCellValueFactory(new PropertyValueFactory<>("PartNoProperty"));
        t5.setCellValueFactory(new PropertyValueFactory<>("DocEntryProperty"));
        t6.setCellValueFactory(new PropertyValueFactory<>("StatusProperty"));

        printerTableView.setItems(iReaderPresenter.fillTerminalTable());

        textField2.setPromptText("Ilość");
        textField3.setPromptText("Numer etykiety");


        pingButton.setOnAction(v -> {
            try {
                printerTableView.setItems(iReaderPresenter.fillTerminalTable());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        printButton.setOnAction(v -> {
            try {
                gatherDataAndPrint();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        resetButton.setOnAction(v -> {
            try {
                connection = DriverManager.getConnection(url, user, password);
                CallableStatement statement = connection.prepareCall(resetQuery);
                statement.execute();
                printerTableView.setItems(iReaderPresenter.fillTerminalTable());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        printButton1.setOnAction(v -> {
            try {
                printSingleLabel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        printButton2.setOnAction(v -> {
            printExtended();
        });
    }


    private void print(String zpl) throws IOException {
        ZebraLabel zebraLabel = new ZebraLabel(912, 912);

        zebraLabel.addElement(new ZebraNativeZpl(zpl));

        if (InetAddress.getByName("172.16.1.151").isReachable(1000)) {
            try {
                ZebraUtils.printZpl(zebraLabel, "172.16.1.151", 9100);
                System.out.println("OK");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR 2"); //todo
            } finally {
                //Thread.sleep(2000);
            }

        } else {
            System.out.println("BRAK POŁĄCZENIA");
        }


    }

    private void gatherDataAndPrint() throws IOException, SQLException {
      print(iReaderPresenter.buildOneTask());
    }

    private void printSingleLabel() throws IOException {

        int labelNo = printerTableView.getSelectionModel().getSelectedItem().getLabelNoProperty();
        Label singleLabel = DataProvider.map.get(labelNo);


        if (singleLabel != null) {
            String zpl = Reader.convertFile(singleLabel);
            print(zpl);
        }
    }

    private void printExtended() {

        int labelNo = printerTableView.getSelectionModel().getSelectedItem().getLabelNoProperty();
        domain.model.Label singleLabel = DataProvider.map.get(labelNo);

        try {
            if (singleLabel != null) {
                if (!textField2.getText().isEmpty() || !textField3.getText().isEmpty()) {
                    print(Reader.convertFileExtended(singleLabel, textField2.getText(), textField3.getText()));
                }
            }
        } catch (Exception ignored) {
        }
    }


    @Override
    public void onError() {

    }

    @Override
    public void onResult() {

    }

    @Override
    public void onProcess() {

    }
}








