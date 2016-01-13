package risk.data;

import java.awt.*;
import java.util.ArrayList;

public class Patch {
    private ArrayList<Point> points;
    private String territory;

    public Patch(String territory, ArrayList<Point> points) {
        this.territory = territory;
        this.points = points;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public PatchPolygon getPolygon() {
        ArrayList<Point> points = this.points;
        int x[] = new int[points.size() + 1];
        int y[] = new int[points.size() + 1];
        for (int i = 0; i < points.size(); i++) {
            x[i] = (int) points.get(i).getX();
            y[i] = (int) points.get(i).getY();
        }
        x[points.size()] = (int) points.get(0).getX();
        y[points.size()] = (int) points.get(0).getY();

        return new PatchPolygon(territory, x, y, points.size());
    }

    @Override
    public String toString() {
        String tmp = "{ ";
        for (Point p : this.points) {
            tmp += "(" + p.getX() + "," + p.getY() + "), ";
        }
        tmp += "}";
        return tmp;
    }

}
