package template;

public class CaptionDisplayArticle extends DisplayArticleTemplate{
    public CaptionDisplayArticle(Article article) {
        super(article);
    }
    @Override
    protected void title() {
        System.out.println("caption display:" + article.getTitle());
    }

    @Override
    protected void content() {
        System.out.println("caption display:" + article.getContent());
    }

    @Override
    protected void footer() {
        System.out.println("caption display:" + article.getFooter());
    }
}
