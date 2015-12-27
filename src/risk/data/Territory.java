package risk.data;

import java.awt.*;
import java.util.ArrayList;

public class Territory {
    private String name;
    private ArrayList<Patch> patches;
    private Point capital;
    public Territory(String name) {
        this.patches = new ArrayList<>();
        this.name = name;
    }

    public void addPatch(Patch p) {
        this.patches.add(p);
    }

    public void setCapital(Point p) {
        this.capital = p;
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
