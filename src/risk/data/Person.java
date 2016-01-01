package risk.data;

import java.awt.*;
import java.util.ArrayList;

public class Person extends Player {

    public Person(String name, Color color) {
        super(name, color);
    }

    @Override
    public String chooseCountry(ArrayList<Territory> leftTerritories) {
        return null;
    }
}
