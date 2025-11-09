package chainofresponsibility;

public abstract class Handler {
    protected Handler nextHandler = null;

    public Handler setNext(Handler handler){
        this.nextHandler = handler;
        return handler;
    }

    protected abstract void process(String url);

    public void run(String url) {
        process(url);
        if(nextHandler != null) nextHandler.run(url);
    }
}
