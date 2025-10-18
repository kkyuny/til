package template;

import java.util.ArrayList;

public class MainEntry {
    public static void main(String[] args) {
        String title = "디자인패턴";

        ArrayList<String> content = new ArrayList<>();
        content.add("content1");
        content.add("content2");
        content.add("content3");

        String footer = "footer";

        Article article = new Article(title, content, footer);

        DisplayArticleTemplate template1 = new CaptionDisplayArticle(article);
        template1.display();

        DisplayArticleTemplate template2 = new SimpleDisplayArticle(article);
        template2.display();
    }
}
