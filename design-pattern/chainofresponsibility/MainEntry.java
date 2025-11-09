package chainofresponsibility;

public class MainEntry {
    public static void main(String[] args) {
        Handler handler1 = new ProtocolHandler();
        Handler handler2 = new DomainHandler();

        handler1.setNext(handler2);

        String url = "Http://www.youtube.com:1007";
        System.out.println("Input: " +url);

        handler1.run(url);
    }
}
