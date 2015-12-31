package risk.components;

import risk.data.Patch;
import risk.data.PatchPolygon;
import risk.data.Player;
import risk.data.Territory;

import javax.swing.*;
import java.awt.*;

public class TerritoryComponent extends JComponent {

    private Territory territory;

    public TerritoryComponent(Territory tmp) {
        super();
        this.setSize(this.getMaximumSize());
        this.territory = tmp;
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        Player player = this.territory.getPlayer();
        Color fillColor = player == null ? new Color(255, 255, 255) : player.getColor();

        for (Patch patch : this.territory.getPatches()) {
            PatchPolygon polygon = patch.getPolygon();

            graphics.setColor(fillColor);
            graphics.fillPolygon(polygon.getX(), polygon.getY(), polygon.getLength());

            graphics.setColor(new Color(52, 52, 52));
            graphics.drawPolyline(polygon.getX(), polygon.getY(), polygon.getLength());
        }

    }
}
