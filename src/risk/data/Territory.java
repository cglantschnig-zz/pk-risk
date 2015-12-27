package risk.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Territory {
    private String name;
    private ArrayList<Patch> patches;
    private Point capital;
    private HashMap<String, String> neighbors;
    public Territory(String name) {
        this.patches = new ArrayList<>();
        this.neighbors = new HashMap<>();
        this.name = name;
    }

    public void addPatch(Patch p) {
        this.patches.add(p);
    }

    public void setCapital(Point p) {
        this.capital = p;
    }

    public void addNeighbor(String neighbor) {
        this.neighbors.put(neighbor, neighbor);
    }

    public Collection<String> getNeighbors() {
        return this.neighbors.values();
    }

    public Point getCapital() {
        return this.capital;
    }

    public ArrayList<Patch> getPatches() {
        return this.patches;
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
