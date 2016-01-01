package risk.data;

public class Army {
    private int armycount;

    public Army(){
        this.armycount = 0;
    }

    public void plusOne(){
        this.armycount++;
    }

    public int getArmycount(){
        return this.armycount;
    }
}
