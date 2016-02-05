package risk.data;

import java.util.ArrayList;
import java.util.Collections;

public class Unit implements Comparable<Unit> {

    private final int STRENGTH = 6;

    private int currentRoll = 0;
    private boolean attacked = false;
    private Territory originTerritory = null;

    public Unit() {

    }

    /**
     * roll some dices
     */
    private void roll() {
        currentRoll = (int) Math.floor(Math.random() * (this.STRENGTH + 1));
        attacked = true;
    }

    /**
     * attacking units are fighting with the defending units and the deads units will be removed on one site
     */
    public static void attack(ArrayList<Unit> attacker, ArrayList<Unit> defender) {

        // roll the dice for all the units
        for (Unit u : attacker) {
            u.roll();
        }
        for (Unit u : defender) {
            u.roll();
        }

        boolean defenderWon = removeLosingOpponent(attacker, defender);

        if (defenderWon == true && defender.size() > 1 && attacker.size() > 0 || defenderWon == false && defender.size() > 0 && attacker.size() > 1) {
            removeLosingOpponent(attacker, defender);
        }

    }

    /**
     * helper function which will get the strongest unit from attacker and defender
     * then these 2 units will get compared and the weaker unit will get destroyed
     * it will return true if the defender won, if the attacker won false will be returned
     */
    private static boolean removeLosingOpponent(ArrayList<Unit> attacker, ArrayList<Unit> defender) {
        Unit maxAttacker = Collections.max(attacker);
        Unit maxDefender = Collections.max(defender);

        if (maxAttacker.compareTo(maxDefender) <= 0) {
            attacker.remove(maxAttacker);
            maxDefender.currentRoll = -1;
            return true;
        } else {
            defender.remove(maxDefender);
            maxAttacker.currentRoll = -1;
            return false;
        }
    }

    public boolean isAttacked() {
        return attacked;
    }

    public Territory getOriginTerritory() {
        return this.originTerritory;
    }

    public void reset(Territory territory) {
        this.originTerritory = territory;
        this.currentRoll = 0;
        this.attacked = false;
    }

    @Override
    public int compareTo(Unit o) {
        return this.currentRoll - o.currentRoll;
    }

    @Override
    public String toString() {
        return "origin: " +  this.originTerritory;
    }
}
