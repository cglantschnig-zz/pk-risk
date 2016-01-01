package risk;

import risk.components.Map;
import risk.components.ToolBox;
import risk.data.Game;
import risk.utils.listeners.MapChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame implements MapChangeListener {
    private Map map;
    private ToolBox toolBox;

    private Game game;

    public Application() {
        super("Risiko");

        // initialize data
        this.game = new Game();
        this.map = new Map(this.game);
        this.game.setMap(map);
        this.toolBox = new ToolBox(this.game);

        this.toolBox.addToolboxListener(this);
        this.toolBox.addToolboxListener(this.map);

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
