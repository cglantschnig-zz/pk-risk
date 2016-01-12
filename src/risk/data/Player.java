package risk.data;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {

    public String name;
    public Color color;
    public boolean isAttack = false;

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
        return  Math.floorDiv(territoryCount, 3) + bonus;
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

    public int getLeftReinforcement(){
        return this.leftReinforcement;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
