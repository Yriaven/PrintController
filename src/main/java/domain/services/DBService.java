package domain.services;

import domain.DataProvider;
import domain.Reader;
import domain.model.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.sql.*;

public class DBService {

    private String daimlerQuery = "SELECT * FROM EP_DaimlerMaleEtykietyDoWydruku";
    private String resetQuery = "call EP_ResetPrint";

    private String url = "jdbc:sap://172.16.0.54:30015/?currentschema=TEST_20161207";
    private String user = "SYSTEM";
    private String password = "Ep*4321#";


    public Connection initializeConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Label> getTaskList (Connection connection) throws SQLException {
        ObservableList<domain.model.Label> TaskList = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(daimlerQuery);
        while (rs.next()) {
            Label label = new Label();

            label.LabelNoProperty.set(rs.getInt("Serien"));
            label.QuantityProperty.set(rs.getInt("U_QtyOnLabel"));
            label.DocNumberProperty.set(rs.getString("Advice note"));
            label.DocEntryProperty.set(rs.getString("DocEntry"));
            label.PartNoProperty.set(rs.getString("Supplier part no"));


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

            TaskList.add(label);
            DataProvider.map.put(label.getLabelNoProperty(), label);
        }
        return TaskList;
    }

    public String getSingleTask(Connection connection) throws SQLException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(daimlerQuery);
        while (rs.next()) {
            Label label = new Label();

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

            sb.append(Reader.convertFile(label));
        }
        return sb.toString();
    }

    public void callUpdateProcedure(Connection connection) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        CallableStatement statement = connection.prepareCall(resetQuery);
        statement.execute();
    }
}
