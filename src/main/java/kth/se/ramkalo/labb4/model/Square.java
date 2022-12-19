package kth.se.ramkalo.labb4.model;

import java.io.Serializable;
import java.util.Objects;

public class Square implements Comparable<Square> , Serializable {
    private int number;
    private boolean visible;
    private boolean correctSquare;
    private boolean isSquareChangeable;
    /**
     * constructor to initialize a square object
     * @param number the number to be placed in the square
     */
    public Square(int number) {
        this.number = number;
        this.correctSquare = true;
        this.isSquareChangeable = true;

    }
    /**
     *
     * @param number number to be placed in square
     * @param visible whether the square to create is visible or not
     */
    public Square(int number, boolean visible){
        this.number = number;
        this.visible = visible;
        this.correctSquare = false;
    }
    /**
     *
     * @return returns true if square is changeable
     */
    public boolean isSquareChangeable() {
        return isSquareChangeable;
    }
    /**
     * sets if the square is changeable or not
     * @param squareChangeable
     */
    public void setSquareChangeable(boolean squareChangeable) {
        isSquareChangeable = squareChangeable;
    }
    /**
     * get number in the square
     * @return
     */
    public int getNumber() {
        return number;
    }
    /**
     * @return returns true if the square is placed correctly
     */
    public boolean getCorrectSquare() {
        return correctSquare;
    }
    /**
     * sets if the square is correctly placed or not
     * @param correctSquare
     */
    public void setCorrectSquare(boolean correctSquare) {
        this.correctSquare = correctSquare;
    }
    /**
     * sets number
     * @param number number that we want to set
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * @return returns true if the square is visible, that is it's not a 0
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * sets whether the square is visible or not
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    /**
     *
     * @param o the other square object we are comparing with
     * @return returns 0 if the same number
     */
    @Override
    public int compareTo(Square o) {
        if(this.number == o.number){
            return 0;
        }else if(this.number > o.number){
            return 1;
        }else {
            return -1;
        }
    }
    /**
     *
     * @param o the other object we are comparing to
     * @return returns true if objects have the same content
     */
    @Override
    public boolean equals(Object o) {
       if (compareTo((Square) o) == 0){
           return true;
       }else {
           return false;
       }
    }
    /**
     *
     * @return returns everything contained in the square object as a string
     */
    @Override
    public String toString() {
        return "Square{" +
                "number=" + number +
                ", visible=" + visible +
                ", correctSquare=" + correctSquare +
                '}';
    }
}


