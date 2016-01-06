package risk.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Territory {
    private String name;
    private ArrayList<Patch> patches;
    private Point capital;
    private HashMap<String, Territory> neighbors;
    private Player player = null;
    private int units = 0;
    private boolean selected = false;
    private Army army = new Army();

    public Territory(String name) {
        this.patches = new ArrayList<>();
        this.neighbors = new HashMap<>();
        this.name = name;
    }

    public void setPlayer(Player player, int units) {
        this.player = player;
        this.units = units;
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

    public Army getArmy() {
        return this.army;
    }

    public Collection<Territory> getNeighbors() {
        return this.neighbors.values();
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

    public int getUnits() {
        return units;
    }

    public boolean isSelected() {
        return selected;
    }

    public void addUnit() {
        this.units += 1;
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
