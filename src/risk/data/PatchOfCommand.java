package risk.data;

import risk.utils.InvalidCommandException;

import java.awt.*;
import java.util.ArrayList;

public class PatchOfCommand extends Command {
    protected String country;
    protected ArrayList<Point> points = new ArrayList<>();

    public PatchOfCommand(Command cmd) throws InvalidCommandException {
        super(cmd.original);
        try {
            this.country = "";
            int start = 0;
            boolean noException = true;
            while (noException) {
                try {
                    Integer.parseInt(this.parameters[start]);
                    noException = false;
                } catch (NumberFormatException e) {
                    this.country += " " + this.parameters[start];
                    start++;
                }
            }
            this.country = this.country.trim();
            for (int i = start; (i + 1) < this.parameters.length; i += 2) {
                int x = Integer.parseInt(this.parameters[i]);
                int y = Integer.parseInt(this.parameters[i + 1]);
                this.points.add(new Point(x, y));
            }
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public String getCountry() {
        return this.country;
    }
}
