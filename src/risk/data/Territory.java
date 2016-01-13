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

    public void setUnits(int units){
        this.units = units;
    }

    public boolean isSelected() {
        return selected;
    }

    public void addUnit() {
        this.units += 1;
    }

    public boolean isNeighbor(String territory_name){

        for (Territory territory : getNeighbors()){
            if (territory_name.equals(territory.getName())){
                return true;
            }
        }
        return false;
    }

    public void attack(Territory enemy){
        System.out.println("Start attackt:" + this.getName()+"(" +this.getUnits()+ ")" + "->" + enemy.getName() + "(" + enemy.getUnits()+ ")");

        int attack_count = 0, enemy_count = 0;

        while (this.getUnits() > 1 && enemy.getUnits() > 0){
            attack_count = this.getUnits()-1>3 ? 3 : this.getUnits()-1;
            enemy_count = enemy.getUnits() > 2 ? 2 : enemy.getUnits();

            this.setUnits(this.getUnits()-attack_count);
            enemy.setUnits(enemy.getUnits() - enemy_count);

            int[] attack_dices = getDices(attack_count);
            int[] enemy_dices = getDices(enemy_count);

            for (int i = 0; i < Math.min(attack_dices.length, enemy_dices.length); i++){
                if (attack_dices[i] > enemy_dices[i]){
                    enemy_count--;
                } else {
                    attack_count--;
                }
            }

            this.setUnits(this.getUnits() + attack_count);
            enemy.setUnits(enemy.getUnits() + enemy_count);

        }


        if (enemy.getUnits() == 0){
            enemy.setPlayer(this.getPlayer(), attack_count);
            this.setUnits(this.getUnits() - attack_count);
        }
        System.out.println("End attackt:" + this.getName()+"(" +this.getUnits()+ ")" + "->" + enemy.getName() + "(" + enemy.getUnits()+ ")");
    }

    public int[] getDices(int count){
        int[] dices = new int[count];
        for(int i = 0; i < count; i++){
            dices[i] = (int)((Math.random()*5)+1);
        }
        return dices;
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
