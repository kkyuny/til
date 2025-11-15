package abstractfactory;

public abstract class Button {
    protected String caption;

    public Button(String caption) {
        this.caption = caption;
    }

    public void clickEvent() {
        System.out.printf(caption+" 버튼을 클릭했습니다. 결과-> ");
        render();
    }

    abstract void render();
}
