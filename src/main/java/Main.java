import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        List<Article> articleList = (List<Article>) parser.getData();

        for (Article article : articleList)
        {
            System.out.println(article);
            System.out.println("");
        }

        }



    }






