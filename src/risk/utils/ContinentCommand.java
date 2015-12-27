package risk.utils;

import java.util.ArrayList;

public class ContinentCommand extends Command {
    private String continent;
    private int bonus = 0;
    private ArrayList<String> countries;
    public ContinentCommand(Command cmd) throws InvalidCommandException {
        super(cmd.original);

        int i = 0;
        this.continent = "";
        while (true) {
            if (this.parameters[i].compareTo(":") == 0) {
                break;
            }
            i++;
        }
        for (int j = 0; j < i - 1; j++) {
            this.continent += " " + this.parameters[j];
        }
        this.continent = this.continent.trim();
        this.bonus = Integer.parseInt(this.parameters[i - 1]);

        i += 1;

        this.countries = new ArrayList<>();

        while (i < this.parameters.length) {
            String country = this.parameters[i];
            i += 1;
            while (i < this.parameters.length && this.parameters[i].compareTo("-") != 0) {
                country += " " + this.parameters[i];
                i += 1;
            }
            i += 1;
            this.countries.add(country.trim());
        }
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public int getBonus() {
        return bonus;
    }

    public String getContinent() {
        return continent;
    }
}
