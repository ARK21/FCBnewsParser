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
        boolean isInsert = false;
        ObservableList<Article> willBeAdd = FXCollections.observableArrayList();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM data WHERE url=(?)")) {
            // check is article already exist in database
            for (int i = 0; i < articles.size(); i++) {
                statement.setString(1, articles.get(i).getUrl());
                ResultSet resultSet = statement.executeQuery();
                // if don't exist
                if (!(resultSet.isBeforeFirst())) {
                    System.out.println("Один есть");
                    willBeAdd.add(articles.get(i));
                    if (i == articles.size() - 1) {
                        insert(willBeAdd);
                        isInsert = true;
                    }
                }
                // if exists
                else {
                    if (willBeAdd.isEmpty()) {
                    } else {
                        insert(willBeAdd);
                        isInsert = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    private void insert(ObservableList<Article> articles) throws SQLException {
        try (PreparedStatement addSt = connection.prepareStatement("INSERT INTO data (date, title, text, status, url) VALUES (?,?,?,?,?)")) {
            for (Article insertArticle : articles) {
                addSt.setString(1, insertArticle.getDate());
                addSt.setString(2, insertArticle.getTitle());
                addSt.setString(3, insertArticle.getText());
                addSt.setString(4, "new");
                addSt.setString(5, insertArticle.getUrl());
                addSt.executeUpdate();
                System.out.println("Добавил: " + insertArticle);
            }
        }

    }
}
