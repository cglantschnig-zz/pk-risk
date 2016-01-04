package risk.components;

import risk.data.Player;
import risk.utils.listeners.PlayerChangedListener;

import javax.swing.*;
import java.awt.Color;

public class PlayerBox extends JPanel implements PlayerChangedListener {

    private ColorBox color = new ColorBox();
    private JLabel text = new JLabel();

    public PlayerBox() {
        super();
        this.setSize(this.getMaximumSize());
        this.add(color);
        this.add(text);
    }

    @Override
    public void playerChanged(Player player) {
        this.color.setColor(player.getColor());
        this.text.setText(player.getName());
        this.repaint();
    }
}
