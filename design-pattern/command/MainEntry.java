package command;

public class MainEntry {
    public static void main(String[] args) {
        CommandGroup commandGroup = new CommandGroup();

        Command clearCmd = new ClearCommand();
        commandGroup.add(clearCmd);

        Command printCmd = new PrintCommand("안녕하세요");
        commandGroup.add(printCmd);

        Command printCmd2 = new PrintCommand("2안녕하세요");
        commandGroup.add(printCmd2);

        commandGroup.run();
    }
}
