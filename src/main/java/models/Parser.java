package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Class get data from URL. Use JSOUP library.
 */
public class Parser {

    /**
     * site URl, where gets article
     */
    private final String URL = "http://barca.ru/usernews";


    /**
     * Get article from site
     *
     * @return list of article
     * @throws IOException if can't connect to URl
     */
    public ObservableList<Article> getData() throws IOException {
        // list contains articles
        ObservableList<Article> articleList = FXCollections.observableArrayList();
        // connect to site and get Elements by attributeValue
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttributeValue("class", "center_c news");
        // get element from elements
        for (Element element : elements) {
            //get elements contains publication date
            Elements dateElements = element.getElementsByAttributeValue("class", "date_news_list_blue");
            // get elements contains article titles
            Elements textElements = element.getElementsByAttributeValue("class", "text_news_list");
            // take last 10 article
            for (int i = 0; i < 10; i++) {
                // article's url
                String articleUrl = getArticleUrl(textElements.get(i));
                // publication date
                String date = getDate(dateElements.get(i));
                // article's title
                String title = getTitle(textElements.get(i));
                // connect to article Url and get article's text
                Document documentInside = Jsoup.connect("http://barca.ru" + articleUrl).get();
                Elements elementsInside = documentInside.getElementsByAttributeValue("id", "new");
                Elements children = elementsInside.get(0).children();
                // article text;
                String text = getText(children);
                // create article
                Article article = new Article(articleUrl, title, text, date);
                // add to articleList
                articleList.add(article);
            }

        }
        return articleList;
    }


    /**
     * Get article text
     *
     * @param elements which need to check
     * @return
     */
    private String getText(Elements elements) {
        // to concat text
        StringBuilder builder = new StringBuilder();
        for (Element element : elements) {
            // else element contain text
            if (element.hasText()) {
                builder.append(element.text()).append("\n");
            }
            //If article text is over.
            else if (!element.hasText() && element.attributes().size() == 0) {
                break;
            }
        }
        // to string
        return builder.toString();
    }

    /**
     * Get date. Element contains date in format "date time".
     * Method split string to " " and get string from array with index 0.
     * @param element which contain date
     * @return date in string format
     */
    private String getDate(Element element) {
        String date = element.ownText();
        return date;
    }

    /**
     * get title
     * @param element which contain title
     * @return title
     */
    private String getTitle(Element element) {
        return element.text();
    }

    /**
     * get url where article text is
     * @param element which contain url
     * @return url
     */
    private String getArticleUrl(Element element) {
        return element.child(0).attr("href");
    }
}
