package risk.data;

import java.util.ArrayList;

public class Continent {

    private String name;
    private int bonus = 0;
    private ArrayList<Territory> countries = new ArrayList<>();

    public Continent(String name, ArrayList<Territory> countries, int bonus) {
        this.name = name;
        this.countries = countries;
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public ArrayList<Territory> getCountries() {
        return countries;
    }
}
