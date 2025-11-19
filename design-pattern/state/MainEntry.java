package state;

import java.util.Scanner;

public class MainEntry {
    public static void main(String[] args) {
        Player player = new Player();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("플레이어의 상태: "
            + player.getState().getDescription()
            + " / 속도: " + player.getSpeed() + "km/h");
            System.out.println("[1]서기 [2]뛰기 [3] 종료");

            String input = scanner.next();
            System.out.println();

            if(input.equals("1")) player.getState().standUp();
            else if(input.equals("2")) player.getState().run();
            else break;
        }
    }
}
