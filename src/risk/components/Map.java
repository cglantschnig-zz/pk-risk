package risk.components;

import risk.data.Game;
import risk.data.PatchPolygon;
import risk.data.Player;
import risk.data.Territory;
import risk.utils.listeners.MapChangeListener;
import risk.utils.states.FightState;
import risk.utils.states.GameState;
import risk.utils.states.ReinforcementState;
import risk.utils.states.SelectionState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

public class Map extends JComponent implements MouseListener, MapChangeListener {

    private Collection<Territory> territories;
    private ArrayList<PatchPolygon> areas;
    private Game game = null;

    private ArrayList<TerritoryComponent> currentTerritories = new ArrayList<>();
    private ArrayList<NeighborLineComponent> currentNeighbors = new ArrayList<>();

    public Map(Game game) {
        super();
        this.addMouseListener(this);
        this.setMap(game);
    }

    private void setMap(Game game) {
        this.game = game;
        this.territories = game.getTerritories();
        this.areas = new ArrayList<>();
        for (TerritoryComponent terr : this.currentTerritories) {
            this.remove(terr);
        }
        for (NeighborLineComponent comp : this.currentNeighbors) {
            this.remove(comp);
        }
        int depthCounter = 0;

        this.currentTerritories = new ArrayList<>();
        for (Territory tmp : this.territories) {
            this.areas.addAll(tmp.getPolygons());

            TerritoryComponent territoryComponent = new TerritoryComponent(tmp);
            this.add(territoryComponent);
            this.setComponentZOrder(territoryComponent, depthCounter++);
            this.currentTerritories.add(territoryComponent);
        }

        this.currentNeighbors = new ArrayList<>();
        for (Territory tmp : this.territories) {
            for (Territory neighbor : tmp.getNeighbors()) {
                NeighborLineComponent neighborLineComponent = new NeighborLineComponent(tmp.getCapital(), neighbor.getCapital());
                this.add(neighborLineComponent);
                this.setComponentZOrder(neighborLineComponent, depthCounter++);
                this.currentNeighbors.add(neighborLineComponent);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        graphics.setColor(new Color(8, 114, 200));
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.game.getState() instanceof SelectionState) {
            String clicked = null;
            for (PatchPolygon area : this.areas) {
                if (area.contains(e.getX(), e.getY())) {
                    clicked = area.getTerritory();
                    break;
                }
            }
            ArrayList<Territory> leftOnes = this.game.getLeftTerritories();
            boolean changed = false;
            for (Territory tmp : leftOnes) {
                if (tmp.getName().equals(clicked)) {
                    leftOnes.remove(tmp);
                    changed = true;
                    break;
                }
            }
            if (changed) {
                this.game.findTerritory(clicked).setPlayer(this.game.getCurrentPlayer(), 1);
                this.game.setLeftTerritories(leftOnes);
                this.repaint();
                this.game.setNextPerson();
            }

        }
        else if (this.game.getState() instanceof ReinforcementState) {
            String clicked = null;
            for (PatchPolygon area : this.areas) {
                if (area.contains(e.getX(), e.getY())) {
                    clicked = area.getTerritory();
                    break;
                }
            }

            if (clicked != null) {
                Territory territory = this.game.findTerritory(clicked);
                Player currentPlayer = this.game.getCurrentPlayer();
                if (territory.getPlayer() == this.game.getCurrentPlayer()) {
                    territory.addUnit();
                    currentPlayer.takeReinforcement(this.game);
                    this.game.updateTerritory(territory);
                    this.repaint();
                }
            }
        }
        else if (this.game.getState() instanceof FightState) {
            String clicked = null;
            for (PatchPolygon area : this.areas) {
                if (area.contains(e.getX(), e.getY())) {
                    clicked = area.getTerritory();
                    break;
                }
            }
            for (Territory territory : this.territories) {
                territory.setSelected(false);
                if (territory.getName().equals(clicked)) {
                    territory.setSelected(true);
                }
                this.game.updateTerritory(territory);
            }


            if (clicked != null) {

                if (this.game.attack_territory == null) {
                    Territory selected = this.game.findTerritory(clicked);
                    if (this.game.getCurrentPlayer() == selected.getPlayer() && selected.getUnits() > 1) {
                        this.game.attack_territory = selected;
                    }
                } else {
                    Territory enemy = this.game.findTerritory(clicked);

                    if (this.game.getCurrentPlayer() != enemy.getPlayer() && this.game.attack_territory.isNeighbor(enemy.getName())){
                        this.game.attack_territory.attack(enemy);

                        if (enemy.getUnits() == 0){
                            enemy.setPlayer(game.getCurrentPlayer(), this.game.attack_territory.getUnits()-1);
                            this.game.attack_territory.setUnits(1);
                        }
                        this.game.attack_territory = null;
                    }
                }
            }
            this.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void changeMap(Game game) {
        this.setMap(game);
        this.repaint();
    }
}
