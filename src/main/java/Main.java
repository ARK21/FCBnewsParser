import models.Article;
import models.Parser;

import java.io.IOException;
import java.util.List;

/**
 * Class start application
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        List<Article> articleList = (List<Article>) parser.getData();
        for (Article article : articleList) {
            System.out.println(article);
            System.out.println("");
        }
    }
}






