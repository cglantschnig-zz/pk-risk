package risk.components;

import risk.data.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBox extends JPanel implements ActionListener {
    private JButton start = new JButton("Begin game");
    private JComboBox selectMap = new JComboBox();
    private Game game = null;

    public ToolBox(Game game) {
        super();
        this.setLayout(new FlowLayout());
        this.game = game;

        String[] mapOptions = { "africa.map", "squares.map", "three-continents.map", "world.map" };
        for (String item : mapOptions) {
            this.selectMap.addItem(item);
        }

        this.add(selectMap);
        this.add(start);
        start.addActionListener(this);
        selectMap.addActionListener(this);
        start.requestFocusInWindow();
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.start){
            this.game.selectMap();
        } else if (ae.getSource() == this.selectMap) {
            this.game = new Game(this.selectMap.getSelectedItem().toString());
        }
    }

}
