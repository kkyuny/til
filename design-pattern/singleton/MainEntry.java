package singleton;

public class MainEntry {
    public static void main(String[] args) {
        King king = King.getInstance();

        King king2 = King.getInstance();

        if(king == king2)
            System.out.println("King is same");
        else
            System.out.println("King is not same");
    }
}
