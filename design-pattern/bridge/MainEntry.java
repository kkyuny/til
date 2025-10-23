package bridge;

public class MainEntry {
    public static void main(String[] args) {
        var title = "복원된 지구";
        var author = "김형준";
        String[] contents = {
                "내용1",
                "내용2",
                "내용3"
        };

        Draft draft = new Draft(title, author, contents);

        Display display1 = new SimpleDisplay();
        draft.print(display1);

        Display display2 = new CaptionDisplay();
        draft.print(display2);

        var publisher = "호호출판";
        var cost = 100;

        Publication publication = new Publication(title, author, contents, publisher, cost);

        publication.print(display1);
    }
}
