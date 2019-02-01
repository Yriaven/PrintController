package domain.presenter;

import domain.model.Label;
import domain.services.DBService;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraUtils;
import fr.w3blog.zpl.model.element.ZebraNativeZpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
            readerViewer.onError();
            return converted;
        }
    }


    @Override
    public String buildOneTask() throws FileNotFoundException, SQLException {
        return this.dbService.getSingleTask(this.connection);
    }

    @Override
    public void sendToPrinter(String zpl) throws IOException {
        ZebraLabel zebraLabel = new ZebraLabel(912, 912);

        zebraLabel.addElement(new ZebraNativeZpl(zpl));

        if (InetAddress.getByName("172.16.1.151").isReachable(1000)) {
            try {
                ZebraUtils.printZpl(zebraLabel, "172.16.1.151", 9100);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR 2"); //todo
            }
        } else {
            System.out.println("BRAK POŁĄCZENIA");
            readerViewer.onError();
        }
    }

    @Override
    public void callUpdateQuery() throws SQLException {
        this.dbService.callUpdateProcedure(connection);
    }


    public IReaderPresenter(ReaderViewer readerViewer) {
        this.dbService = new DBService();
        this.connection = dbService.initializeConnection();
    }


}
