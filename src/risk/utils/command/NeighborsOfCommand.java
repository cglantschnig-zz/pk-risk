package risk.utils.command;

import java.util.ArrayList;

public class NeighborsOfCommand extends Command {
    private String country;
    private ArrayList<String> neighbors = new ArrayList<>();

    public NeighborsOfCommand(Command cmd) throws InvalidCommandException {
        super(cmd.original);

        int i = 0;
        boolean found = false;
        this.country = "";
        while (!found) {
            if (i >= this.parameters.length) {
                throw new InvalidCommandException(": is missing");
            }
            if (this.parameters[i].compareTo(":") == 0) {
                found = true;
            } else {
                this.country += " " + this.parameters[i];
            }
            i++;
        }
        this.country = this.country.trim();

        while (i < this.parameters.length) {
            String country = this.parameters[i];
            i += 1;
            while (i < this.parameters.length && this.parameters[i].compareTo("-") != 0) {
                country += " " + this.parameters[i];
                i += 1;
            }
            i += 1;
            this.neighbors.add(country.trim());
        }
    }

    public String getCountry() {
        return this.country;
    }

    public ArrayList<String> getNeighbors() {
        return neighbors;
    }
}
