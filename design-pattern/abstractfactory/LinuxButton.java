package abstractfactory;

public class LinuxButton extends Button{
    public LinuxButton(String caption) {
        super(caption);
    }

    @Override
    public void render() {
        System.out.println("linux button:" +caption);
    }
}
