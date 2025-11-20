package interpreter;

import java.util.ArrayList;
import java.util.Iterator;

public class CommandListExpression implements Expression{
    private ArrayList<CommandExpression> commands
            = new ArrayList<>();
    @Override
    public boolean parse(Context context) {
        while (true) {
            String currentKeyword = context.getCurrentKeyword();

            if(currentKeyword == null){
                return false;
            } else if(currentKeyword.equals("END")) {
                context.readNextKeyWord();
                break;
            } else {
                CommandExpression command = null;

                if(LoopCommandExpression.checkValidKeyword(currentKeyword)){
                    command = new LoopCommandExpression(currentKeyword);
                } else if (ActionCommandExpression.chechValidKeyword(currentKeyword)) {
                    command = new ActionCommandExpression(currentKeyword);
                }

                if(command != null){
                    if(command.parse(context)){
                        commands.add(command);
                    } else  {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean run() {
        Iterator<CommandExpression> iterator = commands.iterator();

        while (iterator.hasNext()){
            boolean bOK = iterator.next().run();
            if(!bOK) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return commands.toString();
    }
}
