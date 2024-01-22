package ToggleGame.backend;
import java.util.Random;

/**
 * A few helper methods, including two methods that might be used to
 * convert between String representation of a game board
 * (set of 9 ordered black or white buttons)
 * and a binary version contained in an integer and vice versa.
 */
public class GameHelper {
    public static final String TARGET_BOARD = generateRandomBoard();
    private static boolean BOARD_GENERATED = false;

    /**
     * Generate a random game board with 9 integers: 0 for black
     * and 1 for white
     * @return a random game board of 0's and 1's
     */
    public static String generateRandomBoard() {
        Random r = new Random();
        StringBuilder target = new StringBuilder();
        for(int i = 0; i < 9; i++) {
            target.append(r.nextInt(2));
        }
        return target.toString();
    }

    /**
     * Convert the integer (binary) representation of the board
     * to a String
     * @param binaryBoard the board formatted as a binary number
     *                    (e.g, 111111111 for all white squares)
     * @return the game board as a string of "0"'s and "1"'s
     */
    public static String binaryToString(int binaryBoard) {
        String updatedBoard = Integer.toBinaryString(binaryBoard);
        //pad back to 9 characters long with leading zeros
        return String.format("%1$" + 9 + "s", updatedBoard).replace(' ', '0');
    }

    /**
     * Convert a String representing a game board into an int with binary digits in the least significant
     * spots representing the game board
     * @param stringBoard the board formatting as "1"'s and "0"'s
     * @return an int giving the binary representation of hte board
     */
    public static int stringToBinary(String stringBoard) {
        return Integer.parseUnsignedInt(stringBoard,2);
    }
}
