package builder1;

public class Car {
    private String engine;
    private boolean airbag;
    private String color;
    private boolean cameraSensor;
    private boolean AEB;

    public Car(String engine, boolean airbag, String color, boolean cameraSensor, boolean AEB) {
        this.engine = engine;
        this.airbag = airbag;
        this.color = color;
        this.cameraSensor = cameraSensor;
        this.AEB = AEB;
    }

    @Override
    public String toString() {
        return "Car{" +
                "engine='" + engine + '\'' +
                ", airbag='" + airbag + '\'' +
                ", color='" + color + '\'' +
                ", cameraSensor='" + cameraSensor + '\'' +
                ", AEB='" + AEB + '\'' +
                '}';
    }
}
