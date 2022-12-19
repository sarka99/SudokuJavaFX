package kth.se.ramkalo.labb4.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import kth.se.ramkalo.labb4.io.BoardFileIO;
import kth.se.ramkalo.labb4.model.Board;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static kth.se.ramkalo.labb4.model.SudokuUtilities.*;

public class GridView extends BorderPane {
    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private TilePane numberPane;

    private SudokuController controller;
    private BorderPane root1;
    private Board board;
    private MenuBar menuBar;

    private Button[] numButtons;

    private Button check;
    private Button hint;

    private Menu[] menus;
    private Insets INSETS = new Insets(10, 10, 10, 10);
    Light.Point point;
    private String numberClicked;
    private File projectsFile;
    private FileChooser fileChooser;
    private static final String FILE_NAME = "projects.sudoku";

    public GridView(Board board) {
        super();
        projectsFile = new File(FILE_NAME);
        fileChooser = new FileChooser();
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        this.board = board;
        this.point = new Light.Point();
        this.numberClicked = "";
        this.controller= new SudokuController(this,board);
        initNumberTiles();
        // ...
        numberPane = makeNumberPane();
        menus= new Menu[3];
        root1 = new BorderPane();
        menuBar = new MenuBar();
        numButtons = new Button[10];
        check = createButton("Check");
        hint = createButton("Hint");
        getMenuBar();
    }

    // ...

    // called by constructor (only)
    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        Label tile;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board.getBoard()[row][col].isVisible())
                    tile = new Label("" + board.getBoard()[row][col].getNumber()); // data from model
                else tile = new Label("");
                tile.setPrefWidth(40);
                tile.setPrefHeight(40);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.3px;");

                // css style
                tile.setOnMouseClicked(tileCLickHandler); // add your custom event handler
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }

    public final TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        FlowPane pane = new FlowPane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 0.7px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        TilePane[][] sections = new TilePane[SECTIONS_PER_ROW][SECTIONS_PER_ROW];
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.6px;");

                // add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.getChildren().add(
                                numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }

                // add the section to the root tile pane
                root.getChildren().add(section);
            }
        }
        return root;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMinWidth(150);
        BorderPane.setMargin(button, INSETS);
        button.setMinSize(10, 10);
        button.setPrefSize(40, 40);
        return button;
    }

    private VBox initNumButton() {

        VBox numbers = new VBox();
        for (int i = 0; i < 9; i++) {
            numButtons[i] = createButton(String.valueOf(i + 1));
            numbers.getChildren().add(numButtons[i]);
        }
        numButtons[9] = createButton("C");
        numbers.getChildren().add(numButtons[9]);
        numbers.setSpacing(5);
        numbers.setAlignment(Pos.CENTER_RIGHT);
        for(int i  = 0; i < 10; i++){
            numButtons[i].setOnAction(numEvent);
        }

        return numbers;
    }

    private VBox initHintAndCheck() {
        VBox checkAndHint = new VBox();

        checkAndHint.getChildren().addAll(hint, check);
        checkAndHint.setSpacing(20);
        checkAndHint.setAlignment(Pos.CENTER_LEFT);
        checkAndHint.setMinWidth(50);
        check.setOnAction(checkButtonEvent);
        hint.setOnAction(hintButtonEvent);
        return checkAndHint;
    }

    private void getMenuBar(){
        menus[0]= new Menu("File");
        menus[1]= new Menu("Game");
        menus[2]= new Menu("Help");
        MenuItem menuItemNewGame = new MenuItem("Save Game");
        MenuItem menuItemLoadGame = new MenuItem("Load Game");
        MenuItem menuItemExit = new MenuItem("Exit");
        menus[0].getItems().addAll(menuItemNewGame,menuItemLoadGame,menuItemExit);
        menuItemNewGame.setOnAction(menuItemSaveEvHandler);
        menuItemLoadGame.setOnAction(menuItemLoadEvHandler);
        menuItemExit.setOnAction(menuItemExitEvHandler);

        MenuItem menuItemRestart = new MenuItem("Restart with same difficulty");
        MenuItem menuItemEasy = new MenuItem("Easy");
        MenuItem menuItemMedium = new MenuItem("Medium");
        MenuItem menuItemHard = new MenuItem("Hard");
        menus[1].getItems().addAll(menuItemRestart,menuItemEasy,menuItemMedium,menuItemHard);
        menuItemRestart.setOnAction(menutItemRestartEvHandler);
        menuItemEasy.setOnAction(menutItemEasyGameEvHandler);
        menuItemMedium.setOnAction(menutItemMediumGameEvHandler);
        menuItemHard.setOnAction(menutItemHardGameEvHandler);

        MenuItem menuItemClearAll = new MenuItem("Clear all");
        MenuItem menuItemCheck = new MenuItem("Check");
        MenuItem menuItemInfo = new MenuItem("Info");
        menus[2].getItems().addAll(menuItemClearAll,menuItemCheck,menuItemInfo);
        menuItemClearAll.setOnAction(menutItemResetEvHandler);
        menuItemCheck.setOnAction(menutItemCheckEvHandler);
        menuItemInfo.setOnAction(menuItemInfoEvHandler);

        menuBar.getMenus().addAll(menus[0], menus[1], menus[2]);
    }
    public void updateView(){
        initNumberTiles();
        makeNumberPane();
        view();

    }
    public void gameIsOverAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText("You have won");
        alert.showAndWait();
        Platform.exit();
    }

    public BorderPane view() {

        root1.setTop(menuBar);
        root1.setCenter(makeNumberPane());
        root1.setRight(initNumButton());
        root1.setLeft(initHintAndCheck());
        return root1;
    }


    private EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (event.getSource() == numberTiles[row][col]) {
                        System.out.println(row+ "." +col);
                        point.setX(col);
                        point.setY(row);
                        if (numberClicked.equals("C")){
                            controller.handleRemoveSquare(point);
                            System.out.println(board);
                            System.out.println(board.getSquareByRnC((int) point.getY(), (int) point.getX()));
                            return;
                        }else if (numberClicked.equals("")){
                            return;
                        }else {
                            controller.handleTileClick(point, Integer.parseInt(numberClicked));
                            numberClicked = "";
                            System.out.println(board);
                            System.out.println(board.getSquareByRnC((int) point.getY(), (int) point.getX()));

                            return;
                        }

                    }

                }
            }
        }
    };

    private EventHandler<ActionEvent> numEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Object node = actionEvent.getSource();
            Button buttonText = (Button) node;
            numberClicked = buttonText.getText();
            System.out.println(buttonText);
            System.out.println("board from num event" + board);

            }
        {

        }
    };
    private EventHandler<ActionEvent> checkButtonEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String info = "";
            if (controller.handleCheckClick()){
                info  = "Everything is right so far";
            }else {
                info = "At least one square is wrong";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check");
            alert.setHeaderText(info);
            alert.showAndWait();

        }
    };
    private EventHandler<ActionEvent> hintButtonEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.handleHintClick();

        }
    };
    private EventHandler<ActionEvent> menutItemRestartEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.handleRestartMenuItem();

        }
    };
    private EventHandler<ActionEvent> menutItemEasyGameEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.handleEasyGameMenuItem();

        }
    };
    private EventHandler<ActionEvent> menutItemMediumGameEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.handleMediumGameMenuItem();

        }
    };
    private EventHandler<ActionEvent> menutItemHardGameEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.handleHardGameMenuItem();

        }
    };
    private EventHandler<ActionEvent> menutItemResetEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.handleResetGameMenuItem();

        }
    };
    private EventHandler<ActionEvent> menutItemCheckEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String info = "";
            if (controller.handleCheckGameMenuItem()){
                info  = "Everything is right so far";
            }else {
                info = "At least one square is wrong";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check");
            alert.setHeaderText(info);
            alert.showAndWait();

        }
    };
    private EventHandler<ActionEvent> menuItemInfoEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game rules");
            alert.setHeaderText("Sudoku is played on a grid of 9 x 9 spaces. Within the rows and columns are 9 “squares” (made up of 3 x 3 spaces).+\n" +
                    "Each row, column and square (9 spaces each) needs to be filled out with the numbers 1-9,\n" +
                    "without repeating any numbers within the row, column or square.");
            alert.showAndWait();


        }
    };
    private EventHandler<ActionEvent> menuItemLoadEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            projectsFile  = fileChooser.showOpenDialog(new Stage());
            System.out.println("the board called from gridview is: " + board);

            controller.handleLoadGame(projectsFile);
        }
    };
    private EventHandler<ActionEvent> menuItemSaveEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Save");
            Window stage = root1.getScene().getWindow();
            FileChooser fileChooser1 = new FileChooser();
            fileChooser1.setTitle("Save game");
            fileChooser1.setInitialFileName("sodukoSaved");
            fileChooser1.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("soduku file", "*.sudoku"));
            File pickedFile = fileChooser1.showSaveDialog(stage);
            controller.handleSaveGame(pickedFile);


        }
    };
    private EventHandler<ActionEvent> menuItemExitEvHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            Platform.exit();
        }
    };



}