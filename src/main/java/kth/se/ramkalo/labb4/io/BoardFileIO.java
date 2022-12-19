package kth.se.ramkalo.labb4.io;

import kth.se.ramkalo.labb4.model.Board;
import kth.se.ramkalo.labb4.model.Square;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BoardFileIO {
    public BoardFileIO() {
    }
    /**
     * this method is used to serialize the data which is our board
     * @param file where we want to save the file
     * @param data the board, 2d array of square objects
     * @throws IOException
     */
    public static void updateGameData(File file, Square[][] data) throws IOException {
        // ...
        // and then, make sure the file always get closed
        FileOutputStream fout = null;
        // Serialize to file
        try {
            fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(data);
            fout.close();
        }
        catch (Exception e) {
        }
        finally {
            try {
                if(fout != null) fout.close();
            }
            catch(IOException e) {

            }
        }
    }
    /**
     * Call this method to load the board data this deserializes the board and
     * from file the specified file.
     */
    @SuppressWarnings("unchecked")
    public static Square[][] getGameData(File file) throws IOException, ClassNotFoundException {
        // ...
        // and then, make sure the file always get closed
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Square[][] board = (Square[][]) ois.readObject(); // Downcast from Object
            ois.close();
            fin.close();
            return board;
        }
        catch (IOException e) {
        }
        catch (ClassNotFoundException e) {
        }
        finally {
            try {
                if(fin != null) {
                    fin.close();
                }
            }
            catch(IOException e) {

            }
        }
        return null;
    }
}
