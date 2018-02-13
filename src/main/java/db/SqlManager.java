package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Article;
import service.Settings;

import java.sql.*;

/**
 * Class saves articles to database
 */
public class SqlManager {

    /**
     * Connection to database
     */
    private Connection connection;

    /**
     * Constructor
     */
    public SqlManager() {
        try {
            Settings settings = Settings.getInstance();
            Class.forName(settings.getDriver());
            connection = DriverManager.getConnection(settings.getUrl(), settings.getName(), settings.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list of article from database
     *
     * @return
     */
    public ObservableList<Article> values() {
        ObservableList<Article> articles = FXCollections.observableArrayList();
        try (Statement statement = connection.createStatement();
             // I sort by descending, so that the newest news will be at the top of the list
             ResultSet resultSet = statement.executeQuery("SELECT * FROM data ORDER BY id DESC ")) {
            while (resultSet.next()) {
                String date = resultSet.getString(2);
                String title = resultSet.getString(3);
                String text = resultSet.getString(4);
                String url = resultSet.getString(6);
                articles.add(new Article(url, title, text, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    /**
     * Method insert in database if there is no article, if exist donn;t insert
     * @param articles list of article
     */
    public boolean add(ObservableList<Article> articles) {
        boolean isAdded = false;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO data (date, title, text, status, url) VALUES (?,?,?,?,?)")) {
            for (Article insertArticle : articles) {
                statement.setString(1, insertArticle.getDate());
                statement.setString(2, insertArticle.getTitle());
                statement.setString(3, insertArticle.getText());
                statement.setString(4, "new");
                statement.setString(5, insertArticle.getUrl());
                try {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Не смог добавить: " + insertArticle.getTitle() + ", " + e.getMessage() );
                    continue;
                }
                System.out.println("Добавил: " + insertArticle);
                isAdded  = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }



    public String getLastUrl() {
        String lastUrl = "not";
        try (PreparedStatement statement = connection.prepareStatement("SELECT url from data WHERE id=(SELECT LAST_INSERT_ID(data.id))")) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                lastUrl = set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastUrl;
    }
}
