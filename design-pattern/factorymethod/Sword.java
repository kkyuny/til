package factorymethod;

public class Sword implements Item{
    @Override
    public void use() {
        System.out.println("칼로 베었다.");
    }
}
