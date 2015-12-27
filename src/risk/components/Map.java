package risk.components;

import risk.data.Patch;
import risk.data.Territory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Map extends JComponent {

    private Collection<Territory> territories;
    public Map(Collection<Territory> territories) {
        super();
        this.territories = territories;
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        graphics.setColor(new Color(8, 114, 200));
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics.setColor(new Color(110, 104, 111));
        for (Territory tmp : this.territories) {
            for (Patch patch : tmp.getPatches()) {
                ArrayList<Point> points = patch.getPoints();
                int x[] = new int[points.size() + 1];
                int y[] = new int[points.size() + 1];
                for (int i = 0; i < points.size(); i++) {
                    x[i] = (int) points.get(i).getX();
                    y[i] = (int) points.get(i).getY();
                }
                x[points.size()] = (int) points.get(0).getX();
                y[points.size()] = (int) points.get(0).getY();

                graphics.setColor(new Color(255, 255, 255));
                graphics.fillPolygon(x, y, points.size() + 1);

                graphics.setColor(new Color(52, 52, 52));
                graphics.drawPolyline(x, y, points.size() + 1);
            }
        }
    }

}
