package domain;

import domain.model.Label;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class DataProvider {

    public static HashMap<Integer, Label> map = new HashMap<>();
}
