package risk.data;

import java.util.ArrayList;

public class Continent {

    private String name;
    private int bonus = 0;
    private ArrayList<String> countries = new ArrayList<>();

    public Continent(String name, ArrayList<String> countries, int bonus) {
        this.name = name;
        this.countries = countries;
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }
}
