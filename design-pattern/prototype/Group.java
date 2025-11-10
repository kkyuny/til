package prototype;

import java.util.ArrayList;
import java.util.Iterator;

public class Group implements Shape, Prototype {
    private final ArrayList<Shape> shapes = new ArrayList<>();

    private final String name;

    public Group(String name) {
        this.name = name;
    }

    Group addShape(Shape shape){
        shapes.add(shape);
        return this;
    }

    @Override
    public Object copy() {
        Group newGroup = new Group(name);

        for (Shape value : shapes) {
            Prototype shape = (Prototype) value;
            newGroup.shapes.add((Shape) shape.copy());
        }

        return newGroup;
    }

    @Override
    public String draw() {
        StringBuilder result = new StringBuilder(name);
        result.append("(");

        Iterator<Shape> iterator = shapes.iterator();
        while (iterator.hasNext()){
            Shape shape = iterator.next();
            result.append(shape.draw());
            if(iterator.hasNext()){
                result.append(" ");
            }
        }

        result.append(")");
        return result.toString();
    }

    @Override
    public void moveOffset(int dx, int dy) {
        for (Shape shape : shapes) {
            shape.moveOffset(dx, dy);
        }
    }
}
