package kth.se.ramkalo.labb4.view;

import javafx.scene.effect.Light;
import javafx.stage.FileChooser;
import kth.se.ramkalo.labb4.io.BoardFileIO;
import kth.se.ramkalo.labb4.model.Board;
import kth.se.ramkalo.labb4.model.Square;

import java.io.File;
import java.io.IOException;

public class SudokuController {
    private GridView view;
    private Board model;
    /**
     * initializes the controller object
     * @param view the view that is connected to this controll
     * @param model the model that is connected to this controller
     */
    public SudokuController(GridView view, Board model) {
        this.view = view;
        this.model = model;
    }
    /**
     * handles a tile click by the user and updates and view and model accordingly
     * @param point x and y (row and col)
     * @param numberToAdd the number the user wants to add to a tile/square
     */
    public void handleTileClick(Light.Point point, int numberToAdd){
        model.makeMove(new Square(numberToAdd), (int) point.getY(), (int) point.getX());
        if (model.isGameOver()){
           view.gameIsOverAlert();
        }
        view.updateView();
    }
    /**
     *  updates view and model accordingly when the user wants to remove a square
     * @param point x and y (row and col)
     */
    public void handleRemoveSquare(Light.Point point){
        model.removeSquareFromBoard((int) point.getY(), (int) point.getX());
        view.updateView();
    }
    /**
     * updates model and view when the user wants to get a hint
     */
    public void handleHintClick(){
        model.placeHint();
        view.updateView();
    }
    /**
     *  the view will use the return value from this method to update correctly
     *  model is used to find if everything is correct so far
     * @return returns true if everything in board is correct so far
     */
    public boolean handleCheckClick(){
        if (model.isBoardCorrect()){
            return true;
        }else {
            return false;
        }
    }
    /**
     * updates model and view when the user wants to restart game
     */
    public void handleRestartMenuItem(){
        model.initGame(model.getLevel().toString());
        view.updateView();
    }
    /**
     * start game on easy difficulty
     * updates model and view accordingly
     */
    public void handleEasyGameMenuItem(){
        model.initGame("Easy");
        view.updateView();
    }
    /**
     * start game on medium difficulty
     * updates model and view accordingly
     */
    public void handleMediumGameMenuItem(){
        model.initGame("Medium");
        view.updateView();
    }
    /**
     * start game on hard difficulty
     * updates model and view accordingly
     */
    public void handleHardGameMenuItem(){
        model.initGame("Hard");
        view.updateView();
    }
    /**
     * handles when the user want to reset the game
     * updates model and view accordingly
     */
    public void handleResetGameMenuItem(){
        model.resetBoard();
        view.updateView();
    }
    /**
     * checks if everything in  the board is placed correctly so far
     * @return
     */
    public boolean handleCheckGameMenuItem(){
        return model.isBoardCorrect();
    }
    /**
     * updates model and view accordingly after receiving the data from file
     * @param file where it is saved
     */
    public void handleLoadGame(File file){
        try {
            model.setBoard(BoardFileIO.getGameData(file));
            view.updateView();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * save the data to file, get the data from the model
     * @param file where to save the file
     */
    public void handleSaveGame(File file){
        try {
            BoardFileIO.updateGameData(file,model.getBoard());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
