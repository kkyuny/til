package decorator;

public class SideDecorator extends Decorator{
    private Character ch;

    public SideDecorator(Item item, Character ch) {
        super(item);
        this.ch = ch;
    }

    @Override
    public int getLinesCount() {
        return item.getLinesCount();
    }

    @Override
    public int getMaxCount() {
        return 0;
    }

    @Override
    public int getLength(int index) {
        return item.getLength(index) + 2;
    }

    @Override
    public String getString(int index) {
        return ch + item.getString(index) + ch;
    }
}
