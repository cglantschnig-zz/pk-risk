package risk.data;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {

    public String name;
    public Color color;

    private boolean dead = false;
    private int leftReinforcement = 0;

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

    public boolean isDead() {
        return dead;
    }
    public void checkIfDead(Game game) {
        int territoryCount = 0;
        for (Territory territory : game.getTerritories()) {
            if (territory.getPlayer() == this) {
                territoryCount += 1;
            }
        }
        // if the player has no territories. set him as dead
        if (territoryCount == 0) {
            this.dead = true;
        }
    }

    /**
     * calculates the reinforcement for the current player on the game map
     */
    public int getReinforcementCount(Game game) {
        int territoryCount = 0;
        for (Territory territory : game.getTerritories()) {
            if (territory.getPlayer() == this) {
                territoryCount += 1;
            }
        }
        int bonus = 0;
        for (Continent continent : game.getContinents()) {
            boolean getsBonus = true;
            for (Territory country : continent.getCountries() ) {
                if (game.findTerritory(country.getName()).getPlayer() != this) {
                    getsBonus = false;
                    break;
                }
            }
            if (getsBonus == true) {
                bonus += continent.getBonus();
            }
        }

        int calculatedReinforcement = Math.floorDiv(territoryCount, 3) + bonus;

        // set at least 2 Reinforcement for each player
        return Math.max(calculatedReinforcement, 2);
    }

    public void takeReinforcement(Game game) {
        this.leftReinforcement -= 1;
        if (this.leftReinforcement == 0) {
            game.nextState();
        }
        game.changeReinforcement(this.leftReinforcement);
    }

    public void setLeftReinforcement(int leftReinforcement) {
        this.leftReinforcement = leftReinforcement;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
