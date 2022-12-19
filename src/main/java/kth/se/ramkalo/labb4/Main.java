package kth.se.ramkalo.labb4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import kth.se.ramkalo.labb4.io.BoardFileIO;
import kth.se.ramkalo.labb4.model.Board;
import kth.se.ramkalo.labb4.model.SudokuUtilities;
import kth.se.ramkalo.labb4.view.GridView;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board();
        GridView gridView = new GridView(board);
        Scene scene = new Scene(gridView.view(), 569, 569);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Soduku");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
