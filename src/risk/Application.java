package risk;

import risk.components.Map;
import risk.components.ToolBox;
import risk.data.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame {
    private Map map;
    private ToolBox toolBox;

    private Game game;

    public Application() {
        super("Josette's Window");

        // initialize data
        this.game = new Game();
        this.map = new Map(this.game.getTerritories(), this.game);
        this.game.setMap(map);
        this.toolBox = new ToolBox(this.game);

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
}
