package risk.data;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {

    public String name;
    public Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public abstract String chooseCountry(ArrayList<Territory> leftTerritories);

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }
}
