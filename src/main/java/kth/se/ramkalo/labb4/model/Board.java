package kth.se.ramkalo.labb4.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Board implements Serializable {
    private static final int ROWS = 9, COLUMNS=9;
    private Square [][] board;
    private SudokuUtilities.SudokuLevel level;
    private StartingBoard startingBoard;
    private int nrOfCorrect;
    /**
     * Constructor for a board object
     * initialize nrOfCorrect to 0 from the start
     * initialize the level to easy from the start
     * initGame easy starts game on easy mode
     */
    public Board() {
        this.nrOfCorrect = 0;
        this.level = SudokuUtilities.SudokuLevel.EASY;
        this.board = new Square[ROWS][COLUMNS];
        initGame("Easy");
    }

    /**
     * getter for level
     * @return
     */
    public SudokuUtilities.SudokuLevel getLevel() {
        return level;
    }
    /**
     *
     * @param level a string which assigns difficulty
     * checks which difficulty to start the game on
     * FillBoard is called to fill the board with the given difficulty
     */
    public void initGame(String level){
        if (level.equals("Easy")){
            startingBoard = new StartingBoard(SudokuUtilities.SudokuLevel.EASY);
            this.level = SudokuUtilities.SudokuLevel.EASY;
        }else if(level.equals("Medium")){
            startingBoard = new StartingBoard(SudokuUtilities.SudokuLevel.MEDIUM);
            this.level = SudokuUtilities.SudokuLevel.MEDIUM;
        }else if (level.equals("Hard")){
            startingBoard = new StartingBoard(SudokuUtilities.SudokuLevel.HARD);
            this.level = SudokuUtilities.SudokuLevel.HARD;
        }else {
            startingBoard = new StartingBoard(this.level);
        }
        fillBoard();
    }
    /**
     * fills the board with squares and gets the values the starting board has from the starting board generated
     * sets each Squares attributes depending on if the square is included in the starting board or not
     */
    private void fillBoard(){
        for(int i = 0; i < ROWS; i ++){
            for(int j =0 ; j < COLUMNS; j++){
                board[i][j] = new Square(startingBoard.getBoard()[i][j][0]);
                if(board[i][j].getNumber() != 0 && board[i][j].getNumber() == startingBoard.getBoard()[i][j][1]){
                    board[i][j].setVisible(true);
                    board[i][j].setCorrectSquare(true);
                    board[i][j].setSquareChangeable(false);
                }else {
                    board[i][j].setCorrectSquare(false);
                    board[i][j].setVisible(false);
                    board[i][j].setSquareChangeable(true);
                }
            }
        }
    }
    /**
     * Checks if the game is over, game is over if the amount of correct squares is 81
     * @return
     */
    public boolean isGameOver() {
        int correctCounter = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j].getCorrectSquare()){
                    correctCounter++;
                }
            }
        }
        return (correctCounter == 81);
    }
    /**
     *
     * @param square a square is given as a parameter, it is needed for the isValidMove method
     * @param rowNr the row which we want to insert a number at
     * @param colNr the col which we want to insert a number at
     * This method allows us to place  a square on the board
     */
    public void makeMove(Square square, int rowNr, int colNr){
        if(isValidMove(square,colNr,rowNr) && board[rowNr][colNr].isSquareChangeable()){
            board[rowNr][colNr] = square;
            board[rowNr][colNr].setCorrectSquare(true);
            board[rowNr][colNr].setVisible(true);
        }else if (!isValidMove(square,colNr,rowNr) && board[rowNr][colNr].isSquareChangeable()){
            board[rowNr][colNr] = square;
            board[rowNr][colNr].setCorrectSquare(false);
            board[rowNr][colNr].setVisible(true);
        }
        if(board[rowNr][colNr].getNumber() == 0){
            board[rowNr][colNr].setVisible(false);
        }
    }
    /**
     *
     * @param square Needed to check the attributes within a square for example number
     * @param rowNr the row that we want to check if the number is in
     * @return returns true if the number is in row
     */
    private boolean isNumberInRow(Square square, int rowNr){
        for(int i = 0 ; i < ROWS; i++ ){
            if (board[rowNr][i].getNumber() == square.getNumber()){
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param square Needed to check the attributes within a square for example number
     * @param colNr the col that we want to check if the number is in
     * @return returns true if the number is in col
     */
    private boolean isNumberInCol(Square square, int colNr){
        for (int i = 0; i < COLUMNS; i++){
            if(board[i][colNr].getNumber() == square.getNumber()){
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param square Needed to check the attributes within a square for example number
     * @param colNr the col which we want to insert a number at
     * @param rowNr the row that we want to check if the number is in
     * @return returns true if a number is in the 3x3 box
     */
    private boolean isNumberInBox(Square square, int colNr, int rowNr){
          int boxRow =  rowNr - rowNr % 3;
          int boxCol = colNr - colNr % 3;
          for(int i = boxRow; i < boxRow + 3; i++){
              for (int j = boxCol; j  < boxCol + 3; j++){
                  if (board[i][j].getNumber() == square.getNumber()){
                      return true;
                  }
              }
          }
          return false;
    }
    /**
     *
     * @param square needed for the other methods to check with the square
     * @param colNr the col which we want to make the move on
     * @param rowNr the row which we want to make the move on
     * @return returns true if the move is valid
     */
    public boolean isValidMove(Square square,int colNr, int rowNr){
        return !isNumberInCol(square,colNr) &&
                !isNumberInRow(square,rowNr) &&
                !isNumberInBox(square,colNr,rowNr);
    }
    /**
     * returns a square object
     * @param row where the square we want to return is at
     * @param col here the square we want to return is at
     * @return returns a square object
     */
    public Square getSquareByRnC(int row,int col) {
        return board[row][col];
    }
    /**
     * removes a square from the board, given the row and col where the square is placed at
     * @param row row where the square we want to remove is at
     * @param col col where the square we want to remove is at
     */
    public void removeSquareFromBoard(int row, int col){
        if (board[row][col].isSquareChangeable() && board[row][col].getNumber() != 0){
            board[row][col].setNumber(0);
            board[row][col].setVisible(false);
            board[row][col].setCorrectSquare(false);
        }
    }
    /**
     * resets the board to its original starting state
     */
    public void resetBoard(){
        for(int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                if (board[i][j].isSquareChangeable() && board[i][j].getNumber() != 0){
                    board[i][j].setNumber(0);
                    board[i][j].setVisible(false);
                    board[i][j].setCorrectSquare(false);
                }
            }
        }
    }
    /**
     * returns the board, a 2d array of square objects
     * @return shallow copy of 2d array of square objects
     */
    public Square[][] getBoard() {
        return board.clone();
    }
    /**
     * sets the board to be the same as board given as a parameter
     * @param board the board which we want to set our board to
     */
    public void setBoard(Square [][] board){
        this.board = board;
    }
    /**
     * method to find a valid hint and also place it on the board
     */
    public void placeHint(){
        int randRow,randCol;
        do {
            randRow = (int) ((Math.random() * (8)));
            randCol = (int) ((Math.random() * (8)));
        }while (board[randRow][randCol].getNumber() != 0);

        for (int i = 1; i <= 9; i++){
            Square square = new Square(i);
            if (isValidMove(square, randCol,randRow)){
                makeMove(square, randRow,randCol);
                break;
            }
        }
    }
    /**
     * @return returns true if everything is correct in the board at any given point
     */
    public boolean isBoardCorrect(){
        int counterOfNonZeros = 0;
        this.nrOfCorrect = 0;
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                if (board[i][j].getCorrectSquare()){
                    nrOfCorrect++;
                }
                if (board[i][j].getNumber() != 0){
                    counterOfNonZeros++;
                }
            }
        }
        return (nrOfCorrect==counterOfNonZeros);
    }
    /**
     *
     * @return returns a string which is formatted as a board
     */
    public String toString() {
        String info = new String();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                info +=board[i][j].getNumber()+ " ";
            }
            info += "\n";
        }
        return info;
    }
}
