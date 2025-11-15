package abstractfactory;

public class MainEntry {
    public static void main(String[] args) {
        ComponentFactory factory = new WindowFactory();
        //ComponentFactory factory = new LinuxFactory();

        Button button = factory.createButton("확인");
        CheckBox checkBox = factory.createCheckBox(false);
        TextEdit textEdit = factory.createTextEdit("디자인패턴");

        button.render();
        //checkBox.render();
        //textEdit.render();

        button.clickEvent();
        //checkBox.setChecked(true);
        //textEdit.setValue("GoF의 디지안패턴");
    }
}
