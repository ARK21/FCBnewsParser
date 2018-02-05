import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Article;
import models.Parser;

import java.io.IOException;

/**
 * Class controller fot GUI
 */
public class Controller {

    // parser instance for access for site
    Parser parser = new Parser();
    // container containing list of article
    ObservableList<Article> articles;

    /**
     * Field for search
     */
    @FXML
    private TextField search;

    /**
     * Button for get new article from site
     */
    @FXML
    private Button updateBt;

    /**
     * Table for view list of article
     */
    @FXML
    private TableView<Article> newsTable;

    /**
     * Column contain article's titles
     */
    @FXML
    private TableColumn<Article, String> title;


    /**
     * Method start when you press updateBt. Thread which get data from site run
     * @param actionEvent
     */
    @FXML
    public void update(ActionEvent actionEvent) {
        UpdateThread updateThread = new UpdateThread();
    }

    /**
     * Method start when you 2 click on article's title. Open new stage with article's text
     * @param event MouseEvent
     */
    @FXML
    public void clickItem(MouseEvent event) {
        // if double click
        if (event.getClickCount() == 2) {

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Статья");
            stage.setResizable(false);
            stage.setMaxHeight(400);
            // Get article text and publication date
            Text text = new Text(newsTable.getSelectionModel().getSelectedItem().getText());
            Text date = new Text(newsTable.getSelectionModel().getSelectedItem().getDate());
            // Set wrapping
            text.setWrappingWidth(400);
            // Set label and font for date
            Label label = new Label("Дата публикации: " + date.getText());
            label.setFont(Font.font(null, FontWeight.BOLD, 12));
                        // Initialize scroll pane for article text. It's usefully for big article
            ScrollPane scrollPane = new ScrollPane();
            // Layout
            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            layout.getChildren().addAll(label, text);
            // Set layout on scroll pane
            scrollPane.setContent(layout);
            // Create scene add scroll pane on it. Stand on stage and show
            Scene scene = new Scene(scrollPane);
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * Class that get data fro article
     */
    private class UpdateThread extends Thread {

        /**
         * Constructor start thread when initialize
         */
        UpdateThread() {
            start();
        }

        /**
         * Override method which get data from site
         */
        @Override
        public void run() {

            try {
                // Get data or catch exception get it
                articles = parser.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Set data from table column
            title.setCellValueFactory(new PropertyValueFactory<Article, String>("title"));
            newsTable.setItems(articles);
            // Set tooltip for each cell. You can see full title if you point at title.
            title.setCellFactory(new Callback<TableColumn<Article, String>, TableCell<Article, String>>() {
                @Override
                public TableCell<Article, String> call(TableColumn<Article, String> param) {
                    return new TableCell<Article, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null) {
                                setTooltip(null);
                                setText(null);
                            } else {
                                Tooltip tooltip = new Tooltip();
                                Article article = getTableView().getItems().get(getTableRow().getIndex());
                                tooltip.setText(article.getTitle());
                                setTooltip(tooltip);
                                setText(item);
                            }

                        }
                    };
                }
            });

        }
    }

}
