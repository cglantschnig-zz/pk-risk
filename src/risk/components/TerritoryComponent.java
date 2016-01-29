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

        Graphics2D g2 = (Graphics2D) graphics;
        if (this.territory.isSelected()) {
            g2.setStroke(new BasicStroke(2));
            fillColor = new Color(
                    (int) ((fillColor.getRed() * (1 - 0.2) / 255 + 0.2) * 255),
                    (int) ((fillColor.getGreen() * (1 - 0.2) / 255 + 0.2) * 255),
                    (int) ((fillColor.getBlue() * (1 - 0.2) / 255 + 0.2) * 255)
            );
        }
        Color borderColor = this.territory.isSelected() ? this.selectedBorderColor : this.borderColor;

        for (Patch patch : this.territory.getPatches()) {
            PatchPolygon polygon = patch.getPolygon();

            graphics.setColor(fillColor);
            graphics.fillPolygon(polygon.getX(), polygon.getY(), polygon.getLength());

            graphics.setColor(borderColor);
            graphics.drawPolyline(polygon.getX(), polygon.getY(), polygon.getLength());
        }

        if (territory.getUnits() > 0) {
            graphics.setColor(new Color(196, 196, 196));
            graphics.fillRect(this.territory.getCapital().x - 5, this.territory.getCapital().y - 10, 20, 20);
            graphics.setColor(new Color(55, 55, 55));
            graphics.drawRect(this.territory.getCapital().x - 5, this.territory.getCapital().y - 10, 20, 20);

            if (territory.getUnits() > 9) {
                graphics.drawString(territory.getUnits() + "", this.territory.getCapital().x - 3, this.territory.getCapital().y + 5);
            } else {
                graphics.drawString(territory.getUnits() + "", this.territory.getCapital().x + 1, this.territory.getCapital().y + 5);
            }
        }



    }
}
