package risk.components;

import risk.data.Patch;
import risk.data.PatchPolygon;
import risk.data.Player;
import risk.data.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class TerritoryComponent extends JComponent {

    private Territory territory;

    private final Color noPlayerFillColor = new Color(255, 255, 255);
    private final Color borderColor = new Color(52, 52, 52);
    private final Color selectedBorderColor = new Color(255, 0, 0);

    public TerritoryComponent(Territory tmp) {
        super();
        this.setSize(this.getMaximumSize());
        this.territory = tmp;
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Player player = this.territory.getPlayer();
        Color fillColor = player == null ? this.noPlayerFillColor : player.getColor();
        if (this.territory.isSelected()) {
            fillColor = new Color(0,0,0);
        }
        Color borderColor = this.territory.isSelected() ? this.selectedBorderColor : this.borderColor;

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setStroke(new BasicStroke(2));

        for (Patch patch : this.territory.getPatches()) {
            PatchPolygon polygon = patch.getPolygon();

            graphics.setColor(fillColor);
            graphics.fillPolygon(polygon.getX(), polygon.getY(), polygon.getLength());

            graphics.setColor(borderColor);
            graphics.drawPolyline(polygon.getX(), polygon.getY(), polygon.getLength());
        }

    }
}
