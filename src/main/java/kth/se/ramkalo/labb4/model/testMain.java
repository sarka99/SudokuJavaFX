package kth.se.ramkalo.labb4.model;

import java.util.Scanner;

public class testMain {
    public static void main(String[] args) {

        Board board = new Board();

        while (!board.isGameOver()) {
            System.out.println(board.toString());
            Scanner scanner = new Scanner(System.in);
            System.out.print("colloum: ");
            int colloum = scanner.nextInt();
            System.out.print("row: ");
            int row = scanner.nextInt();
            System.out.println("number: ");
            int number = scanner.nextInt();
          // System.out.println("The square: " + board.getSquareByRnC(row,colloum));

            board.makeMove(new Square(number),row, colloum);
            System.out.println("The square: " + board.getSquareByRnC(row,colloum));

            //testing out the removeSquare method
          /*  Scanner removeScan = new Scanner(System.in);
            System.out.print("colloum: ");
            int colRem = removeScan.nextInt();
            System.out.print("row: ");
            int rowRem = removeScan.nextInt();
            board.removeSquareFromBoard(rowRem,colRem);
            System.out.println(board);*/

        }


}


}