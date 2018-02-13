package models;

import javafx.collections.ObservableList;

import java.io.IOException;

public interface Parser {

    ObservableList getData() throws IOException;
}
