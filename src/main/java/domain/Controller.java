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

    private IReaderPresenter iReaderPresenter;

    public void initialize() throws SQLException {

        iReaderPresenter = new IReaderPresenter(this);
        setColumns();
        setListeners();
        printerTableView.setItems(iReaderPresenter.fillTerminalTable());

    }

    private void setListeners() {
        pingButton.setOnAction(v -> {
            try {
                printerTableView.setItems(iReaderPresenter.fillTerminalTable());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        printButton.setOnAction(v -> {
            try {
                iReaderPresenter.sendToPrinter(iReaderPresenter.buildOneTask());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        resetButton.setOnAction(v -> {
            try {
                iReaderPresenter.callUpdateQuery();
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


    private void printSingleLabel() throws IOException {

        int labelNo = printerTableView.getSelectionModel().getSelectedItem().getLabelNoProperty();
        Label singleLabel = DataProvider.map.get(labelNo);


        if (singleLabel != null) {
            String zpl = Reader.convertFile(singleLabel);
            iReaderPresenter.sendToPrinter(zpl);
        }
    }

    private void printExtended() {

        int labelNo = printerTableView.getSelectionModel().getSelectedItem().getLabelNoProperty();
        domain.model.Label singleLabel = DataProvider.map.get(labelNo);

        try {
            if (singleLabel != null) {
                if (!textField2.getText().isEmpty() || !textField3.getText().isEmpty()) {
                    iReaderPresenter.sendToPrinter(Reader.convertFileExtended(singleLabel, textField2.getText(), textField3.getText()));
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void setColumns() {
        t1.setCellValueFactory(new PropertyValueFactory<>("LabelNoProperty"));
        t2.setCellValueFactory(new PropertyValueFactory<>("QuantityProperty"));
        t3.setCellValueFactory(new PropertyValueFactory<>("DocNumberProperty"));
        t4.setCellValueFactory(new PropertyValueFactory<>("PartNoProperty"));
        t5.setCellValueFactory(new PropertyValueFactory<>("DocEntryProperty"));
        t6.setCellValueFactory(new PropertyValueFactory<>("StatusProperty"));
        textField2.setPromptText("Ilość");
        textField3.setPromptText("Numer etykiety");
    }


    @Override
    public void onError() {
        JOptionPane.showMessageDialog(null, "Wystąpił nieoczekiwany błąd. Skontaktuj się z działem IT");
    }

    @Override
    public void onResult() {

    }

    @Override
    public void onProcess() {

    }
}








