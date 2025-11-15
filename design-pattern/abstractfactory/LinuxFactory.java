package abstractfactory;

public class LinuxFactory extends ComponentFactory{
    @Override
    public Button createButton(String caption) {
        return new LinuxButton(caption);
    }

    @Override
    public CheckBox createCheckBox(boolean bCheck) {
        return null;
    }

    @Override
    public TextEdit createTextEdit(String value) {
        return null;
    }
}
