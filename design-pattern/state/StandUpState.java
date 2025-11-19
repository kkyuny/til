package state;

public class StandUpState extends State{

    public StandUpState(Player player) {
        super(player);
    }

    @Override
    public void standUp() {
        player.talk("언제 움직일까?");
    }

    @Override
    public void run() {
        player.setSpeed(10);
        player.setState(new RunState(player));
        player.talk("달리기 시작!");
    }

    @Override
    public String getDescription() {
        return "제자리에 서 있음.";
    }
}
