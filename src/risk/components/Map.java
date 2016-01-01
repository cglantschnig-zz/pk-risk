package risk.components;

import risk.data.Game;
import risk.data.PatchPolygon;
import risk.data.Territory;
import risk.utils.states.SelectionState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

public class Map extends JComponent implements MouseListener {

    private Collection<Territory> territories;
    private ArrayList<PatchPolygon> areas;
    private Game game = null;
    public Map(Collection<Territory> territories, Game game) {
        super();
        this.addMouseListener(this);
        this.game = game;
        this.territories = territories;
        this.areas = new ArrayList<>();
        for (Territory tmp : this.territories) {
            this.areas.addAll(tmp.getPolygons());
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        graphics.setColor(new Color(8, 114, 200));
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Territory tmp : this.territories) {
            TerritoryComponent territoryComponent = new TerritoryComponent(tmp);
            this.add(territoryComponent);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.game.getState() instanceof SelectionState) {
            String clicked = null;
            for (PatchPolygon area : this.areas) {
                if (area.contains(e.getX(), e.getY())) {
                    clicked = area.getTerritory();
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
}
