package risk.components;

import risk.data.Territory;

import javax.swing.*;
import java.awt.*;

public class NeighborLineComponent extends JComponent {
    private Point start;
    private Point end;
    public NeighborLineComponent(Point start, Point end) {
        super();
        this.setSize(this.getMaximumSize());
        this.start = start;
        this.end = end;
        if (start.x > end.x) {
            this.end = start;
            this.start = end;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (this.isOverBorderShorter()) {
            graphics.drawLine(end.x, end.y, this.getWidth(), end.y);
            graphics.drawLine(0, start.y, start.x, start.y);
        } else {
            graphics.drawLine(start.x, start.y, end.x, end.y);
        }
    }

    private boolean isOverBorderShorter() {
        double oneWay = this.getDistance(this.start.x, this.start.y, this.end.x, this.end.y);

        double twoWay = this.getDistance(this.end.x, this.end.y, 1300, this.start.y) +
                        this.getDistance(0, this.end.y, this.start.x, this.start.y);

        return twoWay < oneWay;
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
    }
}
