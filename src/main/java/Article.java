/**
 * Class contains article data from site
 */
public class Article {

    // article url
    private String url;
    // title
    private String title;
    // article text
    private String text;
    // publication date
    private String date;

    /**
     * Constructor
     * @param url
     * @param title
     * @param text
     * @param date
     */
    public Article(String url, String title, String text, String date) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    // getters and setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + "\n" +
                title + "\n" +
                text;
    }
}
