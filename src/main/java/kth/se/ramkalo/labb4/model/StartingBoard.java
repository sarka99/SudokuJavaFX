package kth.se.ramkalo.labb4.model;

import java.io.Serializable;

public class StartingBoard extends SudokuUtilities implements Serializable {
    private int[][][] board;
    private SudokuLevel level;

    /**
     * constructor to initialize the startingboard
     * @param level
     */
    public StartingBoard(SudokuLevel level) {
        this.level = level;
        generateBoard();
    }

    /**
     * @return returns clone of the board that has been generated
     */
    public int[][][] getBoard() {
        return board.clone();
    }

    /**
     * generates a new  random board
     * also generates the solution to the random board
     */
    public void generateBoard() {
        int[][][] tempBoard = generateSudokuMatrix(level);
        this.board = generateSudokuMatrix(level);

        int number1 = (int) ((Math.random() * (9- 1)) + 1);
        int number2 = (int) ((Math.random() * (9- 1)) + 1);
        while (number1==number2){
             number1 = (int) ((Math.random() * (9- 1)) + 1);
             number2 = (int) ((Math.random() * (9- 1)) + 1);

        }

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++){
                if (board[i][j][0] == number1) {
                    board[i][j][0] = number2;
                    tempBoard[i][j][0] = number1;
                }

                if(tempBoard[i][j][0] == number2){
                    tempBoard[i][j][0] = number1;
                    board[i][j][0] = tempBoard[i][j][0];
                }
            }
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++){
                if (board[i][j][1] == number1) {
                board[i][j][1] = number2;
                tempBoard[i][j][1] = number1;
            }
                if(tempBoard[i][j][1] == number2){
                    tempBoard[i][j][1] = number1;
                    board[i][j][1] = tempBoard[i][j][1];
                }
            }
        }

    }
}
