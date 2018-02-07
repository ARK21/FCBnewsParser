package models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class contains article data from site
 */
public class Article {

    // article url
    private SimpleStringProperty url;
    // title
    private SimpleStringProperty title;
    // article text
    private SimpleStringProperty text;
    // publication date
    private SimpleStringProperty date;

    /**
     * Constructor
     * @param url
     * @param title
     * @param text
     * @param date
     */
    public Article(String url, String title, String text, String date) {
        this.url = new SimpleStringProperty(url);
        this.title = new SimpleStringProperty(title);
        this.text = new SimpleStringProperty(text);
        this.date = new SimpleStringProperty(date);
    }

    // getters and setters
    public String getUrl() {
        return this.url.get();
    }

    public void setUrl(String url) {
        this.url = new SimpleStringProperty(url);
    }

    public String getTitle() {
        return this.title.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public String getText() {
        return this.text.get();
    }

    public void setText(String text) {
        this.text = new SimpleStringProperty(text);
    }

    public String getDate() {
        return this.date.get();
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }

    @Override
    public String toString() {
        return title.get();
    }
}
