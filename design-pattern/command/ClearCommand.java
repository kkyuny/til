package command;

public class ClearCommand implements Command{
    @Override
    public void run() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
}
