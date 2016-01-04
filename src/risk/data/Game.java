package risk.data;

import risk.components.Map;
import risk.utils.command.*;
import risk.utils.listeners.PlayerChangedListener;
import risk.utils.listeners.ReinforcementChangedListener;
import risk.utils.listeners.StateChangeListener;
import risk.utils.states.GameState;
import risk.utils.states.IState;
import risk.utils.states.NewState;
import risk.utils.states.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Game implements StateChangeListener{

    private HashMap<String, Territory> territories;
    private HashMap<String, Continent> continents;
    private Player[] players;
    private Map map; // Map reference
    private State state = new State(new NewState());

    private ArrayList<PlayerChangedListener> listeners = new ArrayList<>();
    private ArrayList<ReinforcementChangedListener> reinforceListeners = new ArrayList<>();

    private ArrayList<Territory> leftTerritories;
    private int turn = 0;
    private Player currentSelector = null;

    public Game() {
        this("world.map");
    }
    public Game(String mapFile, Game initiator) {
        this(mapFile);
        this.map = initiator.map;
        this.state = new State(new NewState());
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

        this.addStateChangeListener(this);

        // set Players
        this.players = new Player[5];
        this.players[0] = new Computer("Computer 1", new Color(255, 99, 72));
        this.players[1] = new Person("Computer 2", new Color(44, 255, 144));
        this.players[2] = new Computer("Computer 3", new Color(179, 77, 255));
        this.players[3] = new Computer("Computer 4", new Color(255, 210, 90));
        this.players[4] = new Computer("Computer 5", new Color(90, 119, 121));

    }

    /**
     * performs the current move
     */
    public void next() {
        this.currentSelector = this.players[this.turn % this.players.length];
        this.turn += 1;
        System.out.println(this.currentSelector + ": " + this.currentSelector.getReinforcementCount(this));
        if (currentSelector instanceof Computer) {
            // do nothing so far
            this.next();
        } else {
            this.changeReinforcement(this.currentSelector.getReinforcementCount(this));
            this.updatePlayer(this.currentSelector);
        }
    }

    public void addReinforcementChangedListener(ReinforcementChangedListener listener) {
        this.reinforceListeners.add(listener);
    }

    private void changeReinforcement(int count) {

        for (ReinforcementChangedListener listener : this.reinforceListeners) {
            listener.reinforcementChanged(count);
        }
    }

    public void addPlayerChangedListener(PlayerChangedListener listener) {
        this.listeners.add(listener);
    }

    public void selectMap() {
        this.leftTerritories = new ArrayList<>(this.territories.values());
        this.turn = 0;
        this.state.next();
        this.setNextPerson();
    }

    public void setNextPerson() {
        if (this.leftTerritories.isEmpty()) {
            this.state.next();
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
            this.updatePlayer(this.currentSelector);
        }
    }

    private void updatePlayer(Player changedPlayer) {
        for (PlayerChangedListener listener : this.listeners) {
            listener.playerChanged(changedPlayer);
        }
    }

    public void addStateChangeListener(StateChangeListener listener) {
        this.state.addStateChangeListener(listener);
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
            Territory other = this.findTerritory(neighbor);
            home.addNeighbor(other);
            other.addNeighbor(home);
            this.territories.put(neighbor, other);
        }
        this.territories.put(cmd.getCountry(), home);
    }

    private void continent(ContinentCommand cmd) {
        ArrayList<Territory> territories = new ArrayList<>();
        for (String country : cmd.getCountries()) {
            territories.add(this.findTerritory(country));
        }
        Continent tmp = new Continent(cmd.getContinent(), territories, cmd.getBonus());
        this.continents.put(cmd.getContinent(), tmp);
    }

    public IState getState() {
        return this.state.getState();
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

    public Collection<Continent> getContinents() {
        return this.continents.values();
    }

    @Override
    public void stateChanged(IState newState) {
        if (newState instanceof GameState) {
            this.currentSelector = null;
            this.turn = 0;
            this.next();
        }
    }

}
