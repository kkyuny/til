package interpreter;

public class ActionCommandExpression extends CommandExpression {
    public ActionCommandExpression(String keyword) {
        super(keyword);
    }

    @Override
    public boolean parse(Context context) {
        if(!chechValidKeyword(keyword)) return false;

        if(context.readNextKeyWord() == null) return false;

        return true;
    }

    public static boolean chechValidKeyword(String keyword) {
        boolean bKeywordOk = keyword.equals("FRONT") ||
                keyword.equals("BACK") || keyword.equals("LEFT") ||
                keyword.equals("RIGHT");

        return bKeywordOk;
    }

    @Override
    public boolean run() {
        System.out.println("cmd: " + keyword);
        return true;
    }

    @Override
    public String toString() {
        return keyword;
    }
}
