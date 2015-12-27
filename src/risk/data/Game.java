package risk.data;

import risk.utils.*;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class Game {

    private HashMap<String, Territory> territories;
    private HashMap<String, Continent> continents;

    /**
     * 1. load map file and create Map data
     */
    public Game() {
        territories = new HashMap<>();
        continents = new HashMap<>();

        // loading the map file
        CommandParser parser = new CommandParser("src/assets/world.map");

        for (Command cmd : parser.getCommands()) {
            try {
                switch (cmd.getCommandName()) {
                    case "patch-of":
                        this.patchOf(new PatchOfCommand(cmd));
                        break;
                    case "capital-of":
                        this.capitalOf(new CapitalOfCommand(cmd));
                        break;
                    case "neighbors-of":
                        this.neighborsOf(new NeighborsOfCommand(cmd));
                        break;
                    case "continent":
                        this.continent(new ContinentCommand(cmd));
                        break;
                    default:
                        System.out.println("invalid command");
                }
            } catch (InvalidCommandException e) {
                System.out.println(e);
            }
        }

    }

    public Collection<Territory> getTerritories() {
        return this.territories.values();
    }

    private void patchOf(PatchOfCommand cmd) {
        Territory tmp = this.findTerritory(cmd.getCountry());
        tmp.addPatch( new Patch(cmd.getPoints()) );
        this.territories.put(cmd.getCountry(), tmp);
    }

    private void capitalOf(CapitalOfCommand cmd) {
        Territory tmp = this.findTerritory(cmd.getCountry());
        tmp.setCapital( new Point(cmd.getX(), cmd.getY()) );
        this.territories.put(cmd.getCountry(), tmp);
    }

    private void neighborsOf(NeighborsOfCommand cmd) {
        Territory home = this.findTerritory(cmd.getCountry());
        for (String neighbor : cmd.getNeighbors()) {
            home.addNeighbor(neighbor);

            Territory other = this.findTerritory(neighbor);
            other.addNeighbor(cmd.getCountry());
            this.territories.put(neighbor, other);
        }
        this.territories.put(cmd.getCountry(), home);
    }

    private void continent(ContinentCommand cmd) {
        Continent tmp = new Continent(cmd.getContinent(), cmd.getCountries(), cmd.getBonus());
        this.continents.put(cmd.getContinent(), tmp);
    }

    private Territory findTerritory(String country) {
        if (this.territories.containsKey(country)) {
            return this.territories.get(country);
        } else {
            return new Territory(country);
        }
    }

}
