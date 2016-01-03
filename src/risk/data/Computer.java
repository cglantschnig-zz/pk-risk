package risk.data;

import java.awt.*;
import java.util.ArrayList;

public class Computer extends Player {

    public Computer(String name, Color color) {
        super(name, color);
    }

    @Override
    public String chooseCountry(ArrayList<Territory> leftTerritories) {
        int randomIndex = (int)(Math.random() * leftTerritories.size());
        Territory assignedTerritory = leftTerritories.remove(randomIndex);
        return assignedTerritory.getName();
    }
}
