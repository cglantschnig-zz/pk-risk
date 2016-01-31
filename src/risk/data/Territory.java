package risk.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Territory {
    private String name;
    private ArrayList<Patch> patches;
    private Point capital;
    private HashMap<String, Territory> neighbors;
    private Player player = null;
    private ArrayList<Unit> units = new ArrayList<>();
    private boolean selected = false;

    public Territory(String name) {
        this.patches = new ArrayList<>();
        this.neighbors = new HashMap<>();
        this.name = name;
    }

    public void setPlayer(Player player, Unit unit) {
        this.player = player;
        this.units = new ArrayList<>();
        this.units.add(unit);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void addPatch(Patch p) {
        this.patches.add(p);
    }

    public void setCapital(Point p) {
        this.capital = p;
    }

    public void addNeighbor(Territory neighbor) {
        this.neighbors.put(neighbor.getName(), neighbor);
    }

    public ArrayList<Territory> getNeighbors() {
        return new ArrayList<>(this.neighbors.values());
    }

    public Point getCapital() {
        return this.capital;
    }

    public ArrayList<Patch> getPatches() {
        return this.patches;
    }

    public ArrayList<PatchPolygon> getPolygons() {
        ArrayList<PatchPolygon> tmp = new ArrayList<>();
        for (Patch p : this.patches) {
            tmp.add(p.getPolygon());
        }
        return tmp;
    }

    public String getName() {
        return this.name;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    /**
     * moves units from the current territory to the destination. If the destination belongs to another player they are going to fight.
     * the method returns true if an occupation was successful
     */
    public boolean moveTo(Territory destination, Game game) {

        boolean found = false;
        for (Territory tmp : this.neighbors.values()) {
            if (tmp.name.equals(destination.name)) {
                found = true;
                break;
            }
        }
        // destination territory is no neightbor
        if (!found) {
            return false;
        }

        // check if the destination has the same owner as the source
        if (destination.getPlayer() == this.getPlayer()) {
            // make a move
            ArrayList<Unit> unitList = this.getUnitBlock(destination);

            for (Unit u : unitList) {
                destination.addUnit(u);
            }

        } else {
            // attack the other person
            ArrayList<Unit> sourceUnits = this.getUnitBlock();
            ArrayList<Unit> destinationUnits = destination.getUnitBlock(true);

            // if all sourceUnits are already used (not able to attack)
            if (sourceUnits.size() == 0) {
                for (Unit u : destinationUnits) {
                    destination.units.add(u);
                    return false;
                }
            }
            if (destinationUnits.size() == 0) {
                // error case give defender 1 unit and return attackers units
                for (Unit u : sourceUnits) {
                    this.units.add(u);
                }
                destination.units.add(new Unit());
                return false;
            }

            // dead units will be eliminated
            Unit.attack(sourceUnits, destinationUnits);

            // put the remaining destination units to the destination
            for (Unit u : destinationUnits) {
                destination.units.add(u);
            }

            // if there are still 0 units in the destination, then they are lost and the attacker takes it
            if (destination.units.size() == 0) {
                for (Unit u : sourceUnits) {
                    destination.units.add(u);
                }
                destination.player = this.player;
                game.checkForGameFinished();
                return true;
            } else {
                // if the occupation was not successful the left units go back
                for (Unit u : sourceUnits) {
                    this.units.add(u);
                }
            }

        }
        return false;

    }

    private ArrayList<Unit> getUnitBlock() {
        return this.getUnitBlock(false);
    }
    private ArrayList<Unit> getUnitBlock(boolean isDefender) {
        int minimum = isDefender ? 0 : 1;
        int maximum = isDefender ? 2 : 3;
        ArrayList<Unit> unitBlock = new ArrayList<>();
        for (Iterator<Unit> iterator = this.units.iterator(); iterator.hasNext();) {
            Unit u = iterator.next();
            if (isDefender || (!u.isAttacked() && u.getOriginTerritory().name.equals(this.name))) {
                unitBlock.add(u);
                iterator.remove();
                // if the have 3 units to undo, then we send these units as a block
                if (unitBlock.size() == maximum || this.units.size() == minimum) {
                    return unitBlock;
                }
            }
        }
        return unitBlock;
    }

    private ArrayList<Unit> getUnitBlock(Territory territory) {
        int minimum = 1;
        int maximum = 3;
        ArrayList<Unit> unitBlock = new ArrayList<>();
        for (Iterator<Unit> iterator = this.units.iterator(); iterator.hasNext();) {
            Unit u = iterator.next();
            // units to undo
            if (!u.isAttacked() && u.getOriginTerritory() != null && u.getOriginTerritory().name.equals(territory.name)) {
                unitBlock.add(u);
                iterator.remove();
                // if the have 3 units to undo, then we send these units as a block
                if (unitBlock.size() == maximum || this.units.size() == minimum) {
                    return unitBlock;
                }
            }
        }
        for (Iterator<Unit> iterator = this.units.iterator(); iterator.hasNext();) {
            Unit u = iterator.next();
            // units to undo
            if (!u.isAttacked() && u.getOriginTerritory().name.equals(this.name)) {
                unitBlock.add(u);
                iterator.remove();
                // if the have 3 units to undo, then we send these units as a block
                if (unitBlock.size() == maximum || this.units.size() == minimum) {
                    return unitBlock;
                }
            }
        }
        return unitBlock;
    }

    public void resetUnits() {
        for (Unit u : this.units) {
            u.reset(this);
        }
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {
        String params = "";
        for (Patch p : this.patches) {
            params += p + ", ";
        }
        return this.name + " - " + params;
    }

}
