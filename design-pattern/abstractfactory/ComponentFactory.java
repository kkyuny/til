package abstractfactory;

public abstract class ComponentFactory {
    public abstract Button createButton(String caption);
    public abstract CheckBox createCheckBox(boolean bCheck);
    public abstract TextEdit createTextEdit(String value);

}
