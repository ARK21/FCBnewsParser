import db.SqlManager;

import java.io.IOException;

/**
 * Class start application
 */
public class Main {

    public static void main(String[] args) throws IOException {
        /*BarcaSiteParser parser = new BarcaSiteParser();
        List<Article> articleList = (List<Article>) parser.getData();
        for (Article article : articleList) {
            System.out.println(article);
            System.out.println("");
        }*/

        SqlManager sqlManager = new SqlManager();
        System.out.println(sqlManager.getLastUrl());
    }
}






