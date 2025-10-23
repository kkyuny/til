package adapter;

public class TigerAdapter extends Animal{
    private Tiger tiger;

    public TigerAdapter(String name) {
        super(name);
        tiger = new Tiger(name);
    }

    @Override
    public void sound() {
        tiger.roar();
    }
}
