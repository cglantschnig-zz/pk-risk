package risk.data;

import java.awt.*;
import java.util.ArrayList;

public class Patch {
    private ArrayList<Point> points;

    public Patch() {
        this.points = new ArrayList<Point>();
    }

    public Patch(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
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
