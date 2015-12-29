package risk.components;

import risk.data.Territory;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class Map extends JComponent {

    private Collection<Territory> territories;
    public Map(Collection<Territory> territories) {
        super();
        this.territories = territories;
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        graphics.setColor(new Color(8, 114, 200));
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Territory tmp : this.territories) {
            TerritoryComponent territoryComponent = new TerritoryComponent(tmp);
            this.add(territoryComponent);
        }
        this.updateUI();
    }

}
