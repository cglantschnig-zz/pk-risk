package risk.data;

import risk.components.Map;
import risk.utils.command.*;
import risk.utils.states.NewState;
import risk.utils.states.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Game {

    private HashMap<String, Territory> territories;
    private HashMap<String, Continent> continents;
    private Player[] players;
    private Map map; // Map reference
    private State state = new NewState();

    private ArrayList<Territory> leftTerritories;
    private int turn = 0;
    private Player currentSelector = null;

    public Game() {
        this("world.map");
    }
    public Game(String mapFile, Game initiator) {
        this(mapFile);
        this.map = initiator.map;
    }

    /**
     * 1. load map file and create Map data
     */
    public Game(String mapFile) {
        territories = new HashMap<>();
        continents = new HashMap<>();

        // loading the map file
        CommandParser parser = new CommandParser("src/assets/" + mapFile);

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
                        System.out.println("invalid command: `" + cmd.original + "`");
                }
            } catch (InvalidCommandException e) {
                System.out.println(e);
            }
        }

        // set Players
        this.players = new Player[5];
        this.players[0] = new Computer("Computer 1", new Color(255, 99, 72));
        this.players[1] = new Person("Computer 2", new Color(44, 255, 144));
        this.players[2] = new Computer("Computer 3", new Color(179, 77, 255));
        this.players[3] = new Computer("Computer 4", new Color(255, 210, 90));
        this.players[4] = new Computer("Computer 5", new Color(63, 231, 255));

    }

    public void selectMap() {
        this.leftTerritories = new ArrayList<>(this.territories.values());
        this.turn = 0;
        this.state = this.state.next();
        this.setNextPerson();
    }

    public void setNextPerson() {
        if (this.leftTerritories.isEmpty()) {
            System.out.println("FINISHED SELECTION");
            this.state = this.state.next();
            return;
        }
        this.currentSelector = this.players[this.turn % this.players.length];
        if (this.currentSelector instanceof Computer) {
            Territory territory = this.findTerritory(this.currentSelector.chooseCountry(leftTerritories));
            territory.setPlayer(this.currentSelector, 1);
            this.turn += 1;
            this.map.repaint();
            this.setNextPerson();
        } else {
            this.turn += 1;
        }
    }

    public Player getCurrentPlayer() {
        return this.currentSelector;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Collection<Territory> getTerritories() {
        return this.territories.values();
    }

    private void patchOf(PatchOfCommand cmd) {
        Territory tmp = this.findTerritory(cmd.getCountry());
        tmp.addPatch( new Patch(cmd.getCountry(), cmd.getPoints()) );
        this.territories.put(cmd.getCountry(), tmp);
    }

    private void capitalOf(CapitalOfCommand cmd) {
        Territory tmp = this.findTerritory(cmd.getCountry());
        tmp.setCapital(new Point(cmd.getX(), cmd.getY()));
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

    public State getState() {
        return this.state;
    }

    public Territory findTerritory(String country) {
        if (this.territories.containsKey(country)) {
            return this.territories.get(country);
        } else {
            return new Territory(country);
        }
    }

    public ArrayList<Territory> getLeftTerritories() {
        return this.leftTerritories;
    }

    public void setLeftTerritories(ArrayList<Territory> leftTerritories) {
        this.leftTerritories = leftTerritories;
    }

    public void updateTerritory(Territory tmp) {
        this.territories.put(tmp.getName(), tmp);
    }

}
