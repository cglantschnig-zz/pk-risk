package risk;

import risk.components.ColorBox;
import risk.components.Map;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame {
    private Map map = new Map(Main.data.getTerritories());
    private JLabel label = new JLabel();
    public Application() {
        super("Josette's Window");

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Application.this.setVisible(false);
                Application.this.dispose();
            }
        });

        setSize(1300, 700);

        add(map);
    }
}
