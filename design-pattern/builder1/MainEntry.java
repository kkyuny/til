package builder1;

public class MainEntry {
    public static void main(String[] args) {
        Car car1 = new Car("V7", true, "Black", true, false);
        Car car2 = new CarBuilder()
                .setAEB(false)
                .setAirbag(false)
                .setCameraSensor(true)
                .setColor("white")
                .setEngine("V9")
                .build(); // 메서드 체이닝 방식

    }
}
