package adapter;

import java.util.ArrayList;

public class User {
    public static void main(String[] args) {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(new Dog("강아지"));
        animals.add(new Cat("고양이"));
        animals.add(new TigerAdapter("호랑이"));

        animals.forEach(Animal::sound);
    }
}
