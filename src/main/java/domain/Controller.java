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
    private ObservableList<domain.model.Label> TaskList;
    private Connection connection = null;
    private Reader reader;
    private String daimlerQuery = "SELECT * FROM EP_DaimlerMaleEtykietyDoWydruku";
    private String resetQuery = "call EP_ResetPrint";
    private HashMap<Integer, domain.model.Label> map;
    private Task task;


    private IReaderPresenter iReaderPresenter;

    public void initialize() throws SQLException {

        iReaderPresenter = new IReaderPresenter(this);

        t1.setCellValueFactory(new PropertyValueFactory<>("LabelNoProperty"));
        t2.setCellValueFactory(new PropertyValueFactory<>("QuantityProperty"));
        t3.setCellValueFactory(new PropertyValueFactory<>("DocNumberProperty"));
        t4.setCellValueFactory(new PropertyValueFactory<>("PartNoProperty"));
        t5.setCellValueFactory(new PropertyValueFactory<>("DocEntryProperty"));
        t6.setCellValueFactory(new PropertyValueFactory<>("StatusProperty"));
        reader = new Reader();
        map = new HashMap<>();

        //fillTerminalTable();
        printerTableView.setItems(iReaderPresenter.fillTerminalTable());

        textField2.setPromptText("Ilość");
        textField3.setPromptText("Numer etykiety");


        pingButton.setOnAction(v -> {
            fillTerminalTable();

        });
        printButton.setOnAction(v -> {
            gatherDataAndPrint();
        });

        resetButton.setOnAction(v -> {
            try {
                connection = DriverManager.getConnection(url, user, password);
                CallableStatement statement = connection.prepareCall(resetQuery);
                statement.execute();
               // fillTerminalTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        printButton1.setOnAction(v -> {
            try {
                printSingleLabel();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        printButton2.setOnAction(v -> {
            printExtended();
        });
    }

    private void fillTerminalTable() {
        try {
            // new Thread(workInBackground("Pobieranie danych...")).start();
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(daimlerQuery);
            TaskList = FXCollections.observableArrayList();

            while (rs.next()) {
                domain.model.Label label = new domain.model.Label();

                label.LabelNoProperty.set(rs.getInt("Serien"));
                label.QuantityProperty.set(rs.getInt("U_QtyOnLabel"));
                label.DocNumberProperty.set(rs.getString("Advice note"));
                label.DocEntryProperty.set(rs.getString("DocEntry"));
                label.PartNoProperty.set(rs.getString("Supplier part no"));

                TaskList.add(label);
                map.put(label.getLabelNoProperty(), label);

            }
            printerTableView.setItems(TaskList);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private void print(String zpl) throws IOException, InterruptedException {
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

    private void gatherDataAndPrint() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(daimlerQuery);


            while (rs.next()) {
                domain.model.Label label = new domain.model.Label();
                label.setSupplier(rs.getString("Supplier"));
                label.setOdbiorca(rs.getString("Odbiorca"));
                label.setPartNo(rs.getString("Part no"));
                label.setQuantity(rs.getInt("U_QtyOnLabel"));
                label.setStreet(rs.getString("Street"));
                label.setAddress(rs.getString("Adres odbiorcy"));
                label.setAdviceNote(rs.getString("Advice note"));
                label.setDescription(rs.getString("Description"));
                label.setGate(rs.getString("Dock/Gate"));
                label.setLabelNo(rs.getInt("Serien"));
                label.setDate(rs.getString("Date"));
                label.setSupplierPartNumber(rs.getString("Supplier part no"));
                label.setGTL(rs.getString("GTL"));
                label.setDocEntry(rs.getString("DocEntry"));
                label.setLos(rs.getString("Los"));
                label.setCity(rs.getString("City"));
                String y = reader.convertFile(label);
                //todo budowa pliku
                print(y);
            }

            //todo drukowanie tutaj
        } catch (SQLException e) {
            System.out.println("ERROR 1");
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            printerTableView.setItems(TaskList);
        }
    }

    private void printSingleLabel() throws IOException, InterruptedException {
     //   getData();
        System.out.println("hello");
        int labelNo = printerTableView.getSelectionModel().getSelectedItem().getLabelNoProperty();

        System.out.println(labelNo);

        Label singleLabel = DataProvider.map.get(labelNo);

        System.out.println(singleLabel);

        if (singleLabel != null) {
            print(reader.convertFile(singleLabel));
        }
    }

    private void printExtended() {
       // getData();
        System.out.println("hello");
        int labelNo = printerTableView.getSelectionModel().getSelectedItem().getLabelNoProperty();
        domain.model.Label singleLabel = map.get(labelNo);

        try {
            if (singleLabel != null) {
                if (!textField2.getText().isEmpty() || !textField3.getText().isEmpty()) {
                    print(reader.convertFileExtended(singleLabel, textField2.getText(), textField3.getText()));
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








