package template;

public class SimpleDisplayArticle extends DisplayArticleTemplate {
    public SimpleDisplayArticle(Article article) {
        super(article);
    }

    @Override
    protected void title() {
        System.out.println(article.getTitle());
    }

    @Override
    protected void content() {
        System.out.println(article.getContent());
    }

    @Override
    protected void footer() {
        System.out.println(article.getFooter());
    }


}
