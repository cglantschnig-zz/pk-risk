package risk;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Application mainWindow = new Application();
                mainWindow.setVisible(true);
            }
        });

    }
}
