package proxy;

public class MainEntry {
    public static void main(String[] args) {
        Display display = new ScreenDisplay();

        display.print("1");
        display.print("2");
        display.print("3");
        display.print("4");
        display.print("5");

        BufferDisplay display2 = new BufferDisplay(10);

        display2.print("11");
        display2.print("22");
        display2.print("33");
        display2.print("44");
        display2.print("55");

        display2.flush();
    }
}
