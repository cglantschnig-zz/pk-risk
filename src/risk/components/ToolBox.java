package risk.components;

import risk.data.Game;
import risk.utils.listeners.MapChangeListener;
import risk.utils.listeners.StateChangeListener;
import risk.utils.states.GameState;
import risk.utils.states.IState;
import risk.utils.states.SelectionState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolBox extends JPanel implements ActionListener, StateChangeListener {
    private JButton start = new JButton("Spiel starten");
    private JComboBox selectMap = new JComboBox();
    private JLabel info = new JLabel();
    private PlayerBox playerInfo = new PlayerBox();
    private Game game = null;
    private JButton next = new JButton("weiter");

    private ArrayList<MapChangeListener> listeners = new ArrayList<MapChangeListener>();

    public ToolBox(Game game) {
        super();
        this.setSize(this.getWidth(), 100);
        this.game = game;

        String[] mapOptions = { "africa.map", "squares.map", "three-continents.map", "world.map" };
        for (String item : mapOptions) {
            this.selectMap.addItem(item);
        }
        this.selectMap.setSelectedItem("world.map");

        playerInfo.setVisible(false);
        info.setVisible(false);
        next.setVisible(false);

        this.add(playerInfo);
        this.add(info);
        this.add(selectMap);
        this.add(start);
        this.add(next);

        next.addActionListener(this);
        start.addActionListener(this);
        selectMap.addActionListener(this);

        this.game.addPlayerChangedListener(this.playerInfo);
    }

    public void addToolboxListener(MapChangeListener listener) {
        this.listeners.add(listener);
    }

    public void actionPerformed (ActionEvent ae) {
        if(ae.getSource() == this.start){
            this.game.selectMap();
        } else if (ae.getSource() == this.selectMap) {
            this.game = new Game(this.selectMap.getSelectedItem().toString(), this.game);
            this.game.addPlayerChangedListener(this.playerInfo);

            for (MapChangeListener listener : this.listeners) {
                listener.changeMap(this.game);
            }
        }
    }

    @Override
    public void stateChanged(IState newState) {
        if (newState instanceof SelectionState) {
            this.selectMap.setVisible(false);
            this.start.setVisible(false);
            this.playerInfo.setVisible(true);
            this.info.setVisible(true);
            this.info.setText("wähle ein Land aus!");
            this.repaint();
        }
        else if (newState instanceof GameState) {
            this.next.setVisible(true);
            this.info.setText("0 Verstärkung");
        }
    }
}
