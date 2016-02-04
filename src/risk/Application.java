package risk;

import risk.components.Map;
import risk.components.ToolBox;
import risk.data.Game;
import risk.utils.listeners.MapChangeListener;
import risk.utils.listeners.StateChangeListener;
import risk.utils.states.GameState;
import risk.utils.states.IState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame implements MapChangeListener {
    private Map map;
    private ToolBox toolBox;

    private Game game;

    /**
     * Frame of the Game. It just holds the game and the UI Components on the highest level
     */
    public Application() {
        super("Risiko");

        // initialize data
        this.game = new Game();
        this.map = new Map(this.game);
        this.game.setMap(map);
        this.toolBox = new ToolBox(this.game);

        this.toolBox.addToolboxListener(this);
        this.toolBox.addToolboxListener(this.map);
        this.game.addStateChangeListener(this.toolBox);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Application.this.setVisible(false);
                Application.this.dispose();
            }
        });

        this.setLayout(new BorderLayout());

        add(toolBox, BorderLayout.PAGE_END);

        setSize(1300, 700);
        add(map);
    }

    @Override
    public void changeMap(Game game) {
        this.game = game;
    }

}
