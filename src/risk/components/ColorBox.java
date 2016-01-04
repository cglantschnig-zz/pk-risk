package risk.components;

import javax.swing.*;
import java.awt.*;

public class ColorBox extends JPanel {

    private Color color = null;

    public ColorBox() {
        super();
        this.setSize(this.getMaximumSize());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (this.color != null) {
            graphics.setColor(this.color);
            graphics.fillOval(0, 0, this.getHeight(), this.getHeight());
        }
    }

}
