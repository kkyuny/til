package composite;

import java.util.Iterator;
import java.util.LinkedList;

public class Folder extends Unit {
    private final LinkedList<Unit> units = new LinkedList<>();

    public Folder(String name) {
        super(name);
    }

    @Override
    public int getSize() {
        int size = 0;

        for (Unit unit : units) {
            size += unit.getSize();
        }

        return size;
    }

    public void add(Unit unit){
        units.add(unit);
    }

    private void list(String indent, Unit unit){
        if(unit instanceof File){
            System.out.println(indent +unit);
        } else {
            Folder dir = (Folder)unit;
            Iterator<Unit> it = dir.units.iterator();;
            System.out.println(indent + "+ " +unit);;
            while (it.hasNext()){
                list(indent+"   ", it.next());
            }
        }
    }

    public void list(){
        list("", this);
    }
}
