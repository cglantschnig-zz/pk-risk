package risk.components;

import risk.data.Game;
import risk.data.PatchPolygon;
import risk.data.Territory;
import risk.utils.listeners.MapChangeListener;
import risk.utils.states.GameState;
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
        this.currentTerritories = new ArrayList<>();
        for (Territory tmp : this.territories) {
            this.areas.addAll(tmp.getPolygons());

            TerritoryComponent territoryComponent = new TerritoryComponent(tmp);
            this.add(territoryComponent);
            this.currentTerritories.add(territoryComponent);
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
        else if (this.game.getState() instanceof GameState) {
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
