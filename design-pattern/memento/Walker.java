package memento;

import java.util.ArrayList;

public class Walker {
    private int currentX, currentY;
    private final int targetX;
    private final int targetY;
    private ArrayList<String> actionList = new ArrayList<String>();

    public Walker(int currentX, int currentY, int targetX, int targetY) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public double walk(String action) {
        actionList.add(action);

        switch (action) {
            case "UP" -> currentY += 1;
            case "RIGHT" -> currentX += 1;
            case "DOWN" -> currentY -= 1;
            case "LEFT" -> currentX -= 1;
        }

        return Math.sqrt(Math.pow(currentX-targetX,2)+Math.pow(currentY-targetY,2));
    }

    public static class Memento {
        private int x,y;
        private ArrayList<String> actionList;
        private Memento (){

        }
    }

    public Memento createMemento() {
        Memento memento = new Memento();

        memento.x = this.currentX;
        memento.y = this.currentY;
        memento.actionList = (ArrayList<String>)this.actionList.clone();

        return  memento;
    }

    public void restoreMemento(Memento memento) {
        this.currentX = memento.x;
        this.currentY = memento.y;
        this.actionList = (ArrayList<String>)this.actionList.clone();
    }

    @Override
    public String toString() {
        return actionList.toString();
    }
}
