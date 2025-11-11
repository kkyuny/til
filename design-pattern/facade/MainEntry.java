package facade;

public class MainEntry {
    public static void main(String[] args) {
        Facade facade = new Facade();
        String name = "1";
        facade.run(name);
    }
}
