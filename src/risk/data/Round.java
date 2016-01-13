package risk.data;

import java.util.HashMap;

public class Round {
    private Player[] players;

    HashMap<Player, Territory[]> move_history = new HashMap<>();
    HashMap<Player, Boolean> attack_history = new HashMap<>();

    public Round(Player[] players){
        for(Player player : players){
            move_history.put(player, new Territory[2]);
            attack_history.put(player, false);
        }
    }

    public boolean isAttacked(Player player){
        return attack_history.get(player);
    }

    public void setAttackEnd(Player player){
        attack_history.put(player, true);
    }

    public boolean isAllPlayersAttacked(){
        System.out.println("\n\n\nisAllPlayersAttacked;");
        for(Player player : attack_history.keySet()){
            System.out.println(player + "->" + attack_history.get(player));
        }
        for (boolean isAttack : attack_history.values()){
            if (!isAttack){
                return false;
            }
        }
        return true;
    }

    public boolean isMoveable(Player player, Territory territory){
        for(Territory territory1 : move_history.get(player)){
            if(territory1==null || territory1==territory){
                return true;
            }
        }
        return false;
    }

    public void setMove(Player player, Territory from, Territory to){
        move_history.put(player, new Territory[]{from, to});
    }
}
