package models;

import db.SqlManager;
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
public class BarcaSiteParser implements Parser {

    /**
     * site URl, where gets article
     */
    private final String URL = "http://barca.ru/usernews/page=";
    private int pageNumber;
    private SqlManager manager = new SqlManager();
    private ObservableList<Article> articleList = FXCollections.observableArrayList();

    public BarcaSiteParser(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Get article from site
     *
     * @return list of article
     * @throws IOException if can't connect to URl
     */
    public ObservableList<Article> getData() throws IOException {
        String lastUrlInDatabase = manager.getLastUrl();
        int index = -1;
        // connect to site and get Elements by attributeValue
        Document document = Jsoup.connect(URL + pageNumber).get();
        Elements elements = document.getElementsByAttributeValue("class", "center_c news");
        // get element from elements
        for (Element element : elements) {
            //get elements contains publication date
            Elements dateElements = element.getElementsByAttributeValue("class", "date_news_list_blue");
            // get elements contains article titles
            Elements textElements = element.getElementsByAttributeValue("class", "text_news_list");
            // take all articles from pageNumber
            for (int i = dateElements.size() - 1; i >= 0; i--) {
                // article's url
                String articleUrl = getArticleUrl(textElements.get(i));
                if (lastUrlInDatabase.equals(articleUrl)) {
                    index = i;
                    break;
                }
            }
            add(dateElements, textElements, index);
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
     *
     * @param element which contain date
     * @return date in string format
     */
    private String getDate(Element element) {
        return element.ownText();
    }

    /**
     * get title
     *
     * @param element which contain title
     * @return title
     */
    private String getTitle(Element element) {
        return element.text();
    }

    /**
     * get url where article text is
     *
     * @param element which contain url
     * @return url
     */
    private String getArticleUrl(Element element) {
        return element.child(0).attr("href");
    }

    private void add(Elements dateElements, Elements textElements, int index) throws IOException {
        if (index == -1) {
            pageNumber++;
            getData();
            for (int i = dateElements.size() - 1; i >= 0; i--) {
                String articleUrl = getArticleUrl(textElements.get(i));
                String date = getDate(dateElements.get(i));
                String title = getTitle(textElements.get(i));
                Document documentInside = Jsoup.connect("http://barca.ru" + articleUrl).get();
                Elements elementsInside = documentInside.getElementsByAttributeValue("id", "new");
                Elements children = elementsInside.get(0).children();
                String text = getText(children);
                Article article = new Article(articleUrl, title, text, date);
                articleList.add(article);
            }
        } else {
            for (int i = index - 1; i >= 0; i--) {
                String articleUrl = getArticleUrl(textElements.get(i));
                String date = getDate(dateElements.get(i));
                String title = getTitle(textElements.get(i));
                Document documentInside = Jsoup.connect("http://barca.ru" + articleUrl).get();
                Elements elementsInside = documentInside.getElementsByAttributeValue("id", "new");
                Elements children = elementsInside.get(0).children();
                String text = getText(children);
                Article article = new Article(articleUrl, title, text, date);
                articleList.add(article);
            }
        }
    }

    public void clear() {
        articleList.clear();
    }
}
