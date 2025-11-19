package state;

public class RunState extends State {
    public RunState(Player player) {
        super(player);
    }

    @Override
    public void standUp() {
        player.setSpeed(0);
        player.talk("멈추자");
        player.setState(new StandUpState(player));
    }

    @Override
    public void run() {
        player.setSpeed(player.getSpeed()+10);
        player.talk("더 빨리 뛰자.");
    }

    @Override
    public String getDescription() {
        return "뛰고 있는 중";
    }
}
