import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Article;
import models.Parser;

import java.io.IOException;

public class Controller {


    Parser parser = new Parser();
    ObservableList<Article> articles;


    @FXML
    private TextField search;

    @FXML
    private Button updateBt;

    @FXML
    private TableView<Article> newsTable;

    @FXML
    private TableColumn<Article, String> title;


    @FXML
    public void update(ActionEvent actionEvent) {
        UpdateThread updateThread = new UpdateThread();
    }

    @FXML
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            //Parent root = FXMLLoader.load(getClass().getResource("/META-INF/article.fxml"));
            //System.out.println(newsTable.getSelectionModel().getSelectedItem().getText());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Статья");
            stage.setResizable(false);
            stage.setMaxHeight(400);
            Text text = new Text(newsTable.getSelectionModel().getSelectedItem().getText());
            Text date = new Text(newsTable.getSelectionModel().getSelectedItem().getDate());
            ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            text.setWrappingWidth(400);
            vBox.getChildren().addAll(date,text);
            scrollPane.setContent(vBox);
            Scene scene = new Scene(scrollPane);
            stage.setScene(scene);
            stage.show();
        }
    }

    private class UpdateThread extends Thread {

        public UpdateThread() {
            start();
        }

        @Override
        public void run() {
            try {
                articles = parser.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }

            title.setCellValueFactory(new PropertyValueFactory<Article, String>("title"));
            newsTable.setItems(articles);
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
