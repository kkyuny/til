package interpreter;

public class BeginExpression implements Expression{
    private CommandListExpression expression;

    @Override
    public boolean parse(Context context) {
        if(chechValidKeyword(context.getCurrentKeyword())){
            context.readNextKeyWord();
            expression = new CommandListExpression();
            return expression.parse(context);
        } else {
            return false;
        }
    }

    private boolean chechValidKeyword(String keyword) {
        return keyword.equals("BEGIN");
    }

    @Override
    public boolean run() {
        return expression.run();
    }

    @Override
    public String toString() {
        return "BEGIN" + expression;
    }
}
