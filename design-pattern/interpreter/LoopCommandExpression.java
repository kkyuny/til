package interpreter;

public class LoopCommandExpression extends CommandExpression {
    private int count;
    private CommandListExpression expressions;
    public LoopCommandExpression(String keyword) {
        super(keyword);
    }

    public static boolean checkValidKeyword(String keyword) {
        return keyword.equals("LOOP");
    }

    @Override
    public boolean parse(Context context) {
        if(!checkValidKeyword(keyword)) return false;

        String countKeyword = context.readNextKeyWord();
        if(countKeyword == null) return false;

        try {
            count = Integer.parseInt(countKeyword);

            expressions = new CommandListExpression();
            if(context.readNextKeyWord() == null) return false;
            return expressions.parse(context);
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public boolean run() {
        for(int i=0; i<count; i++){
            if(!expressions.run()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "LOOP(" + count + ")" +expressions;
    }
}
