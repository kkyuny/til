package decorator;

public class MainEntry {
    public static void main(String[] args) {
        Strings strings = new Strings();

        strings.add("Hello~");
        strings.add("My name is Kyun");
        strings.add("I am a Developer");

        strings.print();

        Item item = new SideDecorator(strings, '!');
        item.print();
    }
}
