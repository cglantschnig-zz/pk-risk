package risk.data;

import java.util.ArrayList;
import java.util.Collections;

public class Unit implements Comparable<Unit> {

    private final int STRENGTH = 6;

    private int currentRoll = 0;

    public Unit() {

    }

    private void roll() {
        currentRoll = (int) Math.floor(Math.random() * (this.STRENGTH + 1));
    }

    public static void attack(ArrayList<Unit> attacker, ArrayList<Unit> defender) {
        /*
        for (Unit u : attacker) {
            u.roll();
        }
        for (Unit u : defender) {
            u.roll();
        }

        Unit maxAttacker = Collections.max(attacker);
        Unit maxDefender = Collections.max(defender);

        if (maxAttacker.compareTo(maxDefender) > 0) {
            attacker.remove(maxAttacker);
        } else {
            defender.remove(maxDefender);
        }
        */

    }

    @Override
    public int compareTo(Unit o) {
        return this.currentRoll - o.currentRoll;
    }
}
