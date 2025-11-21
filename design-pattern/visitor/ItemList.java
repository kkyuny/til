package visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemList implements Unit {
    private final List<Unit> list = new ArrayList<>();

    public void add(Unit unit) {
        list.add(unit);
    }

    @Override
    public void accept(Visitor visitor) {
        for (Unit unit : list) {
            visitor.visit(unit);
        }
    }
}
