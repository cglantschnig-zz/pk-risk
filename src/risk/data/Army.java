package risk.data;

public class Army {
    private int armycount;

    public Army(){
        this.armycount = 0;
    }

    public void plusOne(){
        this.armycount++;
    }

    public void minusOne(){
        this.armycount--;
    }

    public int getArmycount(){
        return this.armycount;
    }

    public void attack(Army enemy){

        System.out.println("1");
        System.out.println("attack: "+this.getArmycount());
        System.out.println("eney :"+enemy.getArmycount());

        while (this.getArmycount() > 1 && enemy.getArmycount() > 0){

            System.out.println("2");
            int attack_count = this.getArmycount()-1>3 ? 3 : this.getArmycount()-1;
            int enemy_count = enemy.getArmycount() > 2 ? 2 : enemy.getArmycount();

            int[] attack_dices = getDices(attack_count);
            int[] enemy_dices = getDices(enemy_count);

            System.out.println("attackt dices :" + attack_dices);
            System.out.println("enemy dices :" + enemy_dices);
            for (int i = 0; i < Math.min(attack_dices.length, enemy_dices.length); i++){
                if (attack_dices[i] > enemy_dices[i]){
                    enemy.minusOne();
                } else {
                    this.minusOne();
                }
            }

        }
    }

    public int[] getDices(int count){
        int[] dices = new int[count];
        for(int i = 0; i < count; i++){
            dices[i] = (int)((Math.random()*5)+1);
        }
        return dices;
    }
}
