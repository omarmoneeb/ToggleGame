package ToggleGame.backend;
import ToggleGame.frontend.ToggleGameInteraction;

import java.util.*;
/**
 * We used openAI in the following methods:
 * 1- minNumberOfMoves
 * 2- buttonClicked
 * 3- movesToSolve
 * for movesToSolve it was a small mistake in how to calculate the moves.
 * and it added the movesArray.
 */


public class ToggleGameEngine implements ToggleGameInteraction {

    /**
     * Initialize and return the game board for a ToggleGame (9x "1")
     *
     * @return the String "111111111" to start a game with all white squares
     */
    @Override
    public String initializeGame() {
        return "111111111";
    }
    /**

     Toggles a character from 0 to 1 or the opposite
     @param c char to toggle
     @return toggled character
     */
    private static char toggle(char c) {
        return c == '0' ? '1' : '0';
    }
    /**

     Updates the representation of the board by toggling the button at an index and its adjacents if they exist.
     @param current current string representation of the board
     @param button index of button to toggle
     @return the updated string representation of the board
     */
    private static String updateButton(String current, int button) {
        StringBuilder sb = new StringBuilder(current);
        sb.setCharAt(button, toggle(sb.charAt(button)));
        if (button % 3 != 0) {
            sb.setCharAt(button - 1, toggle(sb.charAt(button - 1)));
        }
        if  (button % 3 != 2) {
            sb.setCharAt(button + 1, toggle(sb.charAt(button + 1)));
        }
        if (button / 3 != 0) {
            sb.setCharAt(button - 3, toggle(sb.charAt(button - 3)));
        }
        if (button / 3 != 2) {
            sb.setCharAt(button + 3, toggle(sb.charAt(button + 3)));
        }
        return sb.toString();
    }
    /**
     Calculates minimum number of moves needed to change current game board to target game board using movesToSolve.
     @param current the current game board state as string
     @param target the target game board state as string
     @return the minimum number of moves required to transform the current game board state to the target game board state.
     */
    @Override
    public int minNumberOfMoves(String current, String target) {
        int[] moves = movesToSolve(current, target);
        return moves.length;
    }


    /**
     * Update the game board for the given button that was clicked.
     * Squares marked as 0 are black and 1 is white
     *
     * @param button the game board square button that was clicked (between 0 and 8)
     * @return the updated game board as a String giving the button colors in order
     * with "0" for black and "1" for white.
     * @throws IllegalArgumentException when button is outside 0-8
     */
    @Override
    public String buttonClicked(String current, int button) {
        if (button < 0 || button > 8) {
            throw new IllegalArgumentException("Button index out of range");
        }
        return updateButton(current, button);
    }

    /**
     * Return a sequence of moves that leads in the minimum number of moves
     * from the current board state to the target state
     *
     * @param current the current board state given as a String of 1's (white square)
     *                and 0's (black square)
     * @param target  the target board state given as a String of 1's (white square)
     *                and 0's (black square)
     * @return the sequence of moves to advance the board from current to target.
     * Each move is the number associated with a button on the board. If no moves are
     * required to advance the currentBoard to the target an empty array is returned.
     */
    @Override
    public int[] movesToSolve(String current, String target) {
        if (current.equals(target)) {
            return new int[0];
        }

        Map<String, Integer> dist = new HashMap<>();
        Map<String, Integer> move = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(current);
        dist.put(current, 0);
        move.put(current, -1);
        parent.put(current, null);

        while (!queue.isEmpty()) {
            String currentBoard = queue.poll();
            for (int i = 0; i < 9; i++) {
                String newBoard = updateButton(currentBoard, i);
                if (!dist.containsKey(newBoard)) {
                    dist.put(newBoard, dist.get(currentBoard) + 1);
                    move.put(newBoard, i);
                    parent.put(newBoard, currentBoard);
                    queue.offer(newBoard);
                    if (newBoard.equals(target)) {
                        List<Integer> movesList = new ArrayList<>();
                        String board = newBoard;
                        while (!board.equals(current)) {
                            int prevMove = move.get(board);
                            movesList.add(prevMove);
                            board = parent.get(board);
                        }
                        Collections.reverse(movesList);
                        int[] movesArray = new int[movesList.size()];
                        for (int j = 0; j < movesList.size(); j++) {
                            movesArray[j] = movesList.get(j);
                        }
                        return movesArray;
                    }
                }
            }
        }

        return new int[0];
    }
}