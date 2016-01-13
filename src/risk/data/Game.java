package risk.data;

import risk.components.Map;
import risk.utils.command.*;
import risk.utils.listeners.PlayerChangedListener;
import risk.utils.listeners.ReinforcementChangedListener;
import risk.utils.listeners.StateChangeListener;
import risk.utils.states.*;

import javax.swing.*;
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

    public Territory attack_territory = null;
    public Round round;

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
        this.players[0] = new Person("Person", new Color(44, 255, 144));
        this.players[1] = new Computer("Computer 1", new Color(255, 99, 72));
        this.players[2] = new Computer("Computer 3", new Color(179, 77, 255));
        this.players[3] = new Computer("Computer 4", new Color(255, 210, 90));
        this.players[4] = new Computer("Computer 5", new Color(90, 119, 121));

        this.round = new Round(players);
    }

    /**
     * performs the current move
     */
    public void next() {
        this.state.next();
        this.currentSelector = this.players[this.turn % this.players.length];
        this.turn += 1;

        if (round.isAllPlayersAttacked()){
            for (Player player : players){
                player.setLeftReinforcement(player.getReinforcementCount(this));
            }
            shareReinforcement();
            round = new Round(players);
        }

        if (checkEnd()){
            JOptionPane.showMessageDialog(null, "" + getCurrentPlayer().getName(),"Gewinner" , JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (currentSelector instanceof Computer) {
            // do nothing so far
            // simulate his fights


            attackSimulate();

            this.state.next();
            this.next();

        } else {
            int availableReinforcement = this.currentSelector.getReinforcementCount(this);
            this.currentSelector.setLeftReinforcement(availableReinforcement);
            this.changeReinforcement(availableReinforcement);
            this.updatePlayer(getCurrentPlayer());
            round.setAttackEnd(getCurrentPlayer());
        }

    }


    public boolean checkEnd(){
        ArrayList<Territory> allTerritory = new ArrayList<Territory>(territories.values());
        Player player = allTerritory.get(0).getPlayer();
        for (Territory ter : allTerritory){
            if (ter.getPlayer() != player){
                return false;
            }
        }
        return true;
    }

    public void attackSimulate(){
        System.out.println("11111");
        if (round.isAttacked( getCurrentPlayer() )) {
            System.out.println("22222");
            if(!round.isAllPlayersAttacked()) {
                System.out.println("33333");
                turn++;
                this.currentSelector = this.players[this.turn % this.players.length];
                attackSimulate();
            }
            return;
        }

        ArrayList<Territory> territorysByPlayer_tmp = this.findTerritorysByPlayer(this.getCurrentPlayer());
        ArrayList<Territory> territorysByPlayer = new ArrayList<>();

        for (Territory ter : territorysByPlayer_tmp){
            if (ter.getUnits() >= 2){
                territorysByPlayer.add(ter);
            }
        }

        if (territorysByPlayer.size() == 0){
            round.setAttackEnd(getCurrentPlayer());
            return;
        }
        Territory selected;
        ArrayList<Territory> neighborbySelected = new ArrayList<>();
        boolean true_selected = false;

        do {
            selected = territorysByPlayer.get((int) Math.random() * territorysByPlayer.size());
            ArrayList<Territory> neighborbySelected_tmp = new ArrayList<Territory>(selected.getNeighbors());
            for (Territory ter : neighborbySelected_tmp) {
                if (ter.getPlayer() != getCurrentPlayer()) {
                    neighborbySelected.add(ter);
                }
            }

            if(neighborbySelected.size() == 0) {
                territorysByPlayer.remove(selected);
            }
            else{
                true_selected = true;
            }

        }while (!true_selected && territorysByPlayer.size() > 0);

        if(true_selected) {
            Territory enemy = neighborbySelected.get((int) Math.random() * neighborbySelected.size());
            selected.attack(enemy);

            this.map.repaint();
            attackSimulate();
        }
        else{
            round.setAttackEnd(getCurrentPlayer());
            turn++;
        }

    }

    public void addReinforcementChangedListener(ReinforcementChangedListener listener) {
        this.reinforceListeners.add(listener);
    }

    public void changeReinforcement(int count) {

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

    public void nextState() {
        this.state.next();
    }

    public void setNextPerson() {
        if (this.leftTerritories.isEmpty()) {
            this.state.next();
            for (Player player : players){
                player.setLeftReinforcement(player.getReinforcementCount(this));
            }
            shareReinforcement();
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

    public void shareReinforcement(){
        boolean checkPlayers = false;

        for (Player player : this.players){
            if (player.getLeftReinforcement() > 0){
                checkPlayers = true;
                break;
            }
        }
        if (!checkPlayers){
            //this.round = new Round(players);
            return;
        }
        this.currentSelector = this.players[this.turn++ % this.players.length];
        if (this.currentSelector instanceof Computer) {

            ArrayList<Territory> player_territorys = findTerritorysByPlayer(getCurrentPlayer());
            int player_reinforcement = getCurrentPlayer().getLeftReinforcement();
            while (player_reinforcement > 0){

                player_territorys.get((int)(Math.random()*player_territorys.size())).addUnit();
                player_reinforcement--;
            }
            getCurrentPlayer().setLeftReinforcement(0);
            this.map.repaint();
            shareReinforcement();
        }
    }

    public void updatePlayer(Player changedPlayer) {
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
    public ArrayList<Territory> findTerritorysByPlayer(Player player) {
        ArrayList<Territory> result = new ArrayList<>();
        for (Territory ter : territories.values()){
            if (ter.getPlayer() == player){
                result.add(ter);
            }
        }

        return result;
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
