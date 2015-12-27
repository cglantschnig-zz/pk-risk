package risk.data;

import risk.utils.CommandParser;
import risk.utils.InvalidCommandException;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class Game {

    private HashMap<String, Territory> territories;

    /**
     * 1. load map file and create Map data
     */
    public Game() {
        territories = new HashMap<>();

        // loading the map file
        CommandParser parser = new CommandParser("src/assets/world.map");

        for (Command cmd : parser.getCommands()) {
            try {
                switch (cmd.getName()) {
                    case "patch-of":
                        this.patchOf(new PatchOfCommand(cmd));
                        break;
                    case "capital-of":
                        this.capitalOf(new CapitalOfCommand(cmd));
                        break;
                    case "neighbors-of":
                        System.out.println("neighbors-of");
                        break;
                    case "continent":
                        System.out.println("continent");
                        break;
                    default:
                        System.out.println("invalid command");
                }
            } catch (InvalidCommandException e) {
                System.out.println(e);
            }
        }

        for (Territory t : this.territories.values()) {
            System.out.println(t);
        }

    }

    public Collection<Territory> getTerritories() {
        return this.territories.values();
    }

    private void patchOf(PatchOfCommand cmd) {
        Territory tmp = null;
        if (this.territories.containsKey(cmd.getCountry())) {
            tmp = (Territory)this.territories.get(cmd.getCountry());
        } else {
            tmp = new Territory(cmd.getCountry());
        }
        tmp.addPatch( new Patch(cmd.getPoints()) );
        this.territories.put(cmd.getCountry(), tmp);
    }

    private void capitalOf(CapitalOfCommand cmd) {
        Territory tmp = null;
        if (this.territories.containsKey(cmd.getCountry())) {
            tmp = (Territory)this.territories.get(cmd.getCountry());
        } else {
            tmp = new Territory(cmd.getCountry());
        }
        tmp.setCapital( new Point(cmd.getX(), cmd.getY()) );
        this.territories.put(cmd.getCountry(), tmp);
    }
}
