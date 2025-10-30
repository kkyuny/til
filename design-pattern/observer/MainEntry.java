package observer;

public class MainEntry {
    public static void main(String[] args) {
        FairDiceGame diceGame = new FairDiceGame();

        Player player1 = new EvenBettingPlayer("짝수1");
        Player player2 = new OddBettingPlayer("홀수1");
        Player player3 = new OddBettingPlayer("홀수2");

        diceGame.addPlayer(player1);
        diceGame.addPlayer(player2);
        diceGame.addPlayer(player3);

        for (int i = 0; i < 5; i++){
            diceGame.play();
        }
    }
}
