package domain;

import domain.model.Label;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Reader {

    private static String readFile() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("\\\\grupa\\ep\\EGTBB\\Informatyka\\Publiczne\\Serwis wydruku Daimler\\etykietaEDI.txt"));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return sb.toString();
    }

    public static String convertFile(Label label) {

        String sb = "";

        try {
            sb = readFile();
            System.out.println(sb);
            sb = sb.replaceAll("StringPar1", label.getOdbiorca());;
            sb = sb.replaceAll("StringPar3", label.getPartNo());
            sb = sb.replaceAll("StringPar4", String.valueOf(label.getQuantity()));
            sb = sb.replaceAll("StringPar2", label.getAdviceNote());
            sb = sb.replaceAll("StringPar5", label.getDescription());
            sb = sb.replaceAll("StringPar6", label.getSupplier());
            sb = sb.replaceAll("StringPar7", label.getSupplierPartNumber());
            sb = sb.replaceAll("StringPar8", label.getLos()); //
            sb = sb.replaceAll("StringPar9", label.getStreet());
            sb = sb.replaceAll("StringParA", label.getDate());
            sb = sb.replaceAll("NumericPar1", label.getGTL());
            sb = sb.replaceAll("NumericPar2", label.getGate());
            sb = sb.replaceAll("NumericPar4", String.valueOf((label.getLabelNo())));
            sb = sb.replaceAll("StringParB", label.getCity());

            System.out.println("Przekonwertowany " +sb);

            return sb;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return sb;
        }
    }

    public static String convertFileExtended(Label label, String quantity, String labelNumber) {

        try {
            String sb = readFile();
            sb = sb.replaceAll("StringPar1", label.getOdbiorca());;
            sb = sb.replaceAll("StringPar3", label.getPartNo());
            sb = sb.replaceAll("StringPar4", quantity);
            sb = sb.replaceAll("StringPar2", label.getAdviceNote());
            sb = sb.replaceAll("StringPar5", label.getDescription());
            sb = sb.replaceAll("StringPar6", label.getSupplier());
            sb = sb.replaceAll("StringPar7", label.getSupplierPartNumber());
            sb = sb.replaceAll("StringPar8", label.getLos()); //
            sb = sb.replaceAll("StringPar9", label.getStreet());
            sb = sb.replaceAll("StringParA", label.getDate());
            sb = sb.replaceAll("NumericPar1", label.getGTL());
            sb = sb.replaceAll("NumericPar2", label.getGate());
            sb = sb.replaceAll("NumericPar4", labelNumber);

            return sb;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



}
