import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Parser {

    private final String URL = "http://barca.ru/usernews";


    public Collection<Article> getData() throws IOException {
        List<Article> articleList = new ArrayList<Article>();
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttributeValue("class", "center_c news");
        for (Element element : elements) {
            Elements dateElements = element.getElementsByAttributeValue("class", "date_news_list_blue");
            Elements textElements = element.getElementsByAttributeValue("class", "text_news_list");
            for (int i = 0; i < dateElements.size(); i++) {
                String url = textElements.get(i).child(0).attr("href");
                String date = dateElements.get(i).ownText();
                String title = textElements.get(i).text();
                Document documentInside = Jsoup.connect("http://barca.ru" + url).get();
                Elements elementsInside = documentInside.getElementsByAttributeValue("id", "new");
                String text = elementsInside.text();
                articleList.add(new Article(url, title, text, date));
            }

        }
        return articleList;
    }
}
