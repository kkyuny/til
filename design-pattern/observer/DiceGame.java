package observer;

import java.util.LinkedList;

public abstract class DiceGame {
    protected LinkedList<Player> players = new LinkedList<>();

    public void addPlayer(Player player){
        players.add(player);
    }

    public abstract void play();
}
