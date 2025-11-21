package visitor;

public record Item(int value) implements Unit {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
