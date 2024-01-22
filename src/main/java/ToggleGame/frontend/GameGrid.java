package ToggleGame.frontend;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel to display a black and white target board
 * of squares
 *
 * This file is complete and should not be altered
 *
 */
public class GameGrid extends JPanel {

    public static final int SIZE = 30;
    String mGrid;

    /**
     * Construct a GameGrid Panel from the given String of 9: 1's and 0's
     * @param s a string representing the game board to draw
     */
    public GameGrid(String s) {
        mGrid = s;
        this.setPreferredSize(new Dimension(SIZE*3, SIZE*3));
    }

    @Override
    public void paintComponent(Graphics g) {
       for(int i = 0; i < 3; i++) {
           for(int j = 0; j < 3; j++) {
               g.setColor(mGrid.charAt(i*3+j)=='0'?Color.BLACK:Color.WHITE);
               g.fillRect(j*30, i*30, SIZE, SIZE);
               g.setColor(Color.GRAY);
               g.drawRect(j*30, i*30, SIZE, SIZE);
           }
       }
    }
}
