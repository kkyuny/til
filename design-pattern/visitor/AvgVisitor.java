package visitor;

public class AvgVisitor implements Visitor {
    private int sum = 0;
    private int count = 0;

    public double getAvg() {
        return sum / count;
    }

    @Override
    public void visit(Unit unit) {
        if(unit instanceof Item){
            sum += ((Item)unit).value();
            count++;
        } else {
            unit.accept(this);
        }
    }
}
