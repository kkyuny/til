package adapter;

public class Tiger {
    private String name;

    public Tiger(String name) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void roar() {
        System.out.println("growl");
    }
}
