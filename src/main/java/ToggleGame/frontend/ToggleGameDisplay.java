package ToggleGame.frontend;

import ToggleGame.backend.GameHelper;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The GUI for the Black and White Toggle Game
 *
 * This file is complete and should not be altered
 */
public class ToggleGameDisplay {

    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final String TARGET_PATTERN = GameHelper.TARGET_BOARD;

    /**
     *   0 1 2
     *   3 4 5
     *   6 7 8
     */

    //handling of clicks
    private final ToggleGameInteraction mGameInteraction;

    //labels and buttons that need to be updated as the game progresses
    private final JButton[] mButtonGrid;
    private JLabel mMinTurnsLabel;
    private JLabel mTurnCount;

    //counters
    private int mTurn;

    //game board state
    private String mCurrentBoard;

    /**
     * Constructor to create the GUI
     * @param clickHandler This represents the backend of the game and is
     *                     where actions are sent and responses are retrieved from
     */
    public ToggleGameDisplay(ToggleGameInteraction clickHandler) {
        mButtonGrid = new JButton[9];
        mTurn=0;
        mGameInteraction = clickHandler;
        mCurrentBoard = mGameInteraction.initializeGame();

        JFrame frame = new JFrame("Match the Target Pattern");

        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);

        frame.add(makeButtonGrid(), BorderLayout.CENTER);
        frame.add(makeTargetPanel(TARGET_PATTERN), BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 710);
        frame.setResizable(false);

        setMinTurns(mGameInteraction.minNumberOfMoves(mCurrentBoard, TARGET_PATTERN));
    }

    /**
     *
     * @return
     */
    public JPanel makeHintPanel() {
        JPanel pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        JButton hint = new JButton("Hint");
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        hint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] hintMoves = mGameInteraction.movesToSolve(mCurrentBoard, TARGET_PATTERN);
                if(hintMoves != null) {
                    setHint(hintMoves[0]);
                }
            }
        });
        pan.add(hint);

        JPanel turns = new JPanel();
        JLabel minTurns = new JLabel("Minimum required turns");
        minTurns.setAlignmentX(Component.CENTER_ALIGNMENT);
        turns.add(minTurns);
        mMinTurnsLabel = new JLabel();
        mMinTurnsLabel.setBorder(new EtchedBorder());
        mMinTurnsLabel.setText(String.valueOf(mGameInteraction.minNumberOfMoves(mCurrentBoard, TARGET_PATTERN)));
        turns.add(mMinTurnsLabel);
        pan.add(turns);

        JPanel cPanel = new JPanel();
        JLabel numTurns = new JLabel("Turns Taken");
        numTurns.setAlignmentX(Component.CENTER_ALIGNMENT);
        cPanel.add(numTurns);
        mTurnCount = new JLabel(String.valueOf(mTurn));
        mTurnCount.setBorder(new EtchedBorder());
        cPanel.add(mTurnCount);
        pan.add(cPanel);

        return pan;
    }

    /**
     * Return a panel containing the grid of buttons
     * @return the panel with the 9 toggle buttons
     */
    public JPanel makeButtonGrid() {
        JPanel panel = new JPanel(new GridLayout(3,3));
        panel.setPreferredSize(new Dimension(600,600));
        for(int i = 0; i< 9; i++) {
            mButtonGrid[i] = new JButton();
            mButtonGrid[i].setText(""+i);
            mButtonGrid[i].setOpaque(true);
            mButtonGrid[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            int buttonValue = i;
            mButtonGrid[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    incrementAndDisplayTurns();
                    mCurrentBoard = mGameInteraction.buttonClicked(mCurrentBoard, buttonValue);
                    if(mCurrentBoard != null)
                        updateButtonGrid(mCurrentBoard);

                    int minMoves = mGameInteraction.minNumberOfMoves(mCurrentBoard, TARGET_PATTERN);
                    setMinTurns(minMoves);
                }
            });
            panel.add(mButtonGrid[i]);
        }

        //color the buttons for a new game
        updateButtonGrid(mCurrentBoard);
        return panel;
    }

    /**
     * The bottom panel of the board contains the target and hints sections
     * @param targetBoard A string representation of the target pattern
     * @return a panel with the target
     */
    public JPanel makeTargetPanel(String targetBoard) {
        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(600,110));
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

        JPanel east = makeHintPanel();

        southPanel.add(east);

        JPanel west = new JPanel();
        JLabel label = new JLabel("Target Pattern");
        west.add(label);

        GameGrid gg = new GameGrid(targetBoard);
        west.add(gg);

        southPanel.add(west);

        return southPanel;
    }

    /**
     * Set the minimum number of moves to the finish
     * @param movesToFinish the minimum number of moves
     */
    private void setMinTurns(int movesToFinish) {
        mMinTurnsLabel.setText(String.valueOf(movesToFinish));

        if (movesToFinish == 0) {
            showMessageDialog(null, "Congratulations you've won!");
        }
    }

    private void setHint(int nextMove) {
        mButtonGrid[nextMove].setForeground(Color.RED);
    }

    /**
     * Given a game board color the buttons appropriately
     * 0 for Black and 1 for white
     * @param gameBoard a string of "1"s and "0"s representing the game board
     */
    private void updateButtonGrid(String gameBoard) {
        for(int i = 0; i < gameBoard.length(); i++) {
            mButtonGrid[i].setBackground(gameBoard.charAt(i) == '0'?Color.BLACK:Color.WHITE);
            mButtonGrid[i].setForeground(gameBoard.charAt(i) == '0'?Color.WHITE:Color.BLACK);
        }
    }

    private void incrementAndDisplayTurns() {
        mTurn++;
        mTurnCount.setText(String.valueOf(mTurn));
    }
}
