package abstractfactory;

public class WindowsButton extends Button{
    public WindowsButton(String caption) {
        super(caption);
    }

    @Override
    void render() {
        System.out.println("window button: " + caption);
    }
}
