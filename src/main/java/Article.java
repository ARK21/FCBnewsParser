import java.util.Calendar;

public class Article {

    private String url;
    private String title;
    private String text;
    private String data;


    public Article(String url, String title, String text, String data) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.data = data;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data + "\n" +
                title + "\n" +
                text;
    }
}
