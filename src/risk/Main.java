package risk;

import risk.data.Game;

import javax.swing.*;

public class Main {

    public static Game data;

    public static void main(String[] args) {

        Main.data = new Game();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Application mainWindow = new Application();
                mainWindow.setVisible(true);
            }
        });

    }
}
