package domain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Label {

    private String Supplier;
    private String Odbiorca;
    private String PartNo;
    private int Quantity;
    private String Street;
    private String Address;
    private String City;
    private String AdviceNote;
    private String Description;
    private String Gate;
    private int LabelNo;
    private String Date;
    private String SupplierPartNumber;
    private String GTL;
    private String DocEntry;
    private String Los;

    public SimpleIntegerProperty LabelNoProperty = new SimpleIntegerProperty();
    public SimpleIntegerProperty QuantityProperty = new SimpleIntegerProperty();
    public SimpleStringProperty DocNumberProperty = new SimpleStringProperty();
    public SimpleStringProperty DocEntryProperty = new SimpleStringProperty();
    public SimpleStringProperty StatusProperty = new SimpleStringProperty();
    public SimpleStringProperty PartNoProperty = new SimpleStringProperty();

    public int getLabelNoProperty() {
        return LabelNoProperty.get();
    }

    public int getQuantityProperty() {
        return QuantityProperty.get();
    }

    public String getDocNumberProperty() {
        return DocNumberProperty.get();
    }

    public String getDocEntryProperty() {
        return DocEntryProperty.get();
    }

    public String getPartNoProperty() {
        return PartNoProperty.get();
    }




}
