package risk.data;


import java.awt.*;

public class PatchPolygon {

    private int x[];
    private int y[];
    private int length;
    private String territory;

    public PatchPolygon(String territory, int x[], int y[], int length) {
        this.x = x;
        this.y = y;
        this.length = length + 1;
        this.territory = territory;
    }

    public boolean contains(int x, int y) {
        Polygon p = new Polygon(this.x, this.y, length);
        return p.contains(x, y);
    }

    public int getLength() {
        return length;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public String getTerritory() {
        return territory;
    }
}
