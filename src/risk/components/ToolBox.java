package risk.components;

import risk.data.Game;
import risk.utils.listeners.MapChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolBox extends JPanel implements ActionListener {
    private JButton start = new JButton("Begin game");
    private JComboBox selectMap = new JComboBox();
    private Game game = null;

    private ArrayList<MapChangeListener> listeners = new ArrayList<MapChangeListener>();

    public ToolBox(Game game) {
        super();
        this.setLayout(new FlowLayout());
        this.game = game;

        String[] mapOptions = { "africa.map", "squares.map", "three-continents.map", "world.map" };
        for (String item : mapOptions) {
            this.selectMap.addItem(item);
        }
        this.selectMap.setSelectedItem("world.map");

        this.add(selectMap);
        this.add(start);
        start.addActionListener(this);
        selectMap.addActionListener(this);
    }

    public void addToolboxListener(MapChangeListener listener) {
        this.listeners.add(listener);
    }

    public void actionPerformed (ActionEvent ae) {
        if(ae.getSource() == this.start){
            this.game.selectMap();
        } else if (ae.getSource() == this.selectMap) {
            System.out.println(this.selectMap.getSelectedItem().toString());
            this.game = new Game(this.selectMap.getSelectedItem().toString());

            for (MapChangeListener listener : this.listeners) {
                listener.changeMap(this.game);
            }
        }
    }

}
