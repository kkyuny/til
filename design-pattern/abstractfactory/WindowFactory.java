package abstractfactory;

public class WindowFactory extends ComponentFactory{
    @Override
    public Button createButton(String caption) {
        return new WindowsButton(caption);
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
