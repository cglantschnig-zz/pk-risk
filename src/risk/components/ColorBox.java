package risk.components;

import javax.swing.*;
import java.awt.*;

public class ColorBox extends JComponent {

    public void paintComponent(Graphics graphics)
    {
        int r= (int)Math.round((Math.random()*255));
        int g= (int)Math.round((Math.random()*255));
        int b= (int)Math.round((Math.random()*255));

        graphics.setColor(new Color(r,g,b));
        graphics.fillOval(100, 100, 40, 40);
    }

}
