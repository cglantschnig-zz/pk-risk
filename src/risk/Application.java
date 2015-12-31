package risk;

import risk.components.Map;
import risk.data.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame implements ActionListener {
    private Map map;
    private JButton start = new JButton("Begin game");

    private Game game;

    public Application() {
        super("Josette's Window");

        // initialize data
        this.game = new Game();
        this.map = new Map(this.game.getTerritories(), this.game);
        this.game.setMap(map);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Application.this.setVisible(false);
                Application.this.dispose();
            }
        });

        this.setLayout(new BorderLayout());

        start.addActionListener(this);
        add(start, BorderLayout.PAGE_END);

        setSize(1300, 700);
        add(map);
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.start){
            System.out.println("CHANGED");
            this.game.selectMap();
        }
    }
}
