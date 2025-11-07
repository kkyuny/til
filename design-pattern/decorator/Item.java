package decorator;

public abstract class Item {
    public abstract int getLinesCount();
    public abstract int getMaxCount();
    public abstract int getLength(int index);
    public abstract String getString(int index);

    public void print(){
        int cntLines = getLinesCount();
        for(int i=0; i<cntLines; i++){
            String s = getString(i);
            System.out.println(s);
        }
    }
}
