package chainofresponsibility;

public class DomainHandler extends Handler{
    @Override
    protected void process(String url) {
        int sIndex = url.indexOf("://");
        int lIndex = url.indexOf(":", sIndex + 3);

        System.out.println("DOMAIN: ");
        if (sIndex != -1) {
            if (lIndex == -1) {
                System.out.println(url.substring(sIndex + 3));
            } else {
                System.out.println(url.substring(sIndex + 3, lIndex));
            }
        } else {
            System.out.println("NONE");
        }
    }
}
