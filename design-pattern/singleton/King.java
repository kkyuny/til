package singleton;

public class King {
    private King() {}

    private static King self = null;

    public synchronized static King getInstance(){
        // synchronized: 멀티스레드환경에서 동기화 하기 위해서 사용
        if (self == null){
            self = new King();
        }
        return self;
    }
}
