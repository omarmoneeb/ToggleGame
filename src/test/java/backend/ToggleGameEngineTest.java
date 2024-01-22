package backend;

import ToggleGame.backend.ToggleGameEngine;
import ToggleGame.frontend.ToggleGameInteraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToggleGameEngineTest {

    private ToggleGameInteraction simulatedGame;

    @BeforeEach
    void setUp() {
        simulatedGame = new ToggleGameEngine();
    }

    /**
     * Pass the First Deliverable Tests to
     * receive top marks on Part 1
     */
    @Nested
    class FirstDeliverableTests {

        @Test
        @DisplayName("Moves remaining when Target is Initial Board")
        void identityMovesRemainingTest() {
            //when the target is the initial board
            //the minimum number of moves required should be zero
            String target = "111111111";
            int moves = simulatedGame.minNumberOfMoves(target, target);

            assertEquals(0, moves, "expect 0 moves required when target == current");
        }


        @Test
        @DisplayName("Moves remaining when Target is Initial Board")
        void identityMovesArrayTest() {
            //when the target is the initial board
            //the moves
            String target = "111111111";
            int[] moves = simulatedGame.movesToSolve(target, target);

            assertEquals(0, moves.length, "expect empty array when target == current");
        }

        @DisplayName("Initial Board is \"111111111\" ")
        @Test
        void initialBoardTest() {
            String board = simulatedGame.initializeGame();
            String expected = "111111111";
            assertEquals(expected, board, "expecting all white game board (9x\"1\"");
        }
    }
///////////// END OF FIRST DELIVERABLE TESTS    ///////////////////

    /**
     * Tests that ensure button clicks result in the
     * board maintaining the correct board state
     */
    @Nested
    class BoardStateTests {

        @DisplayName("Single Clicks Leading to All Black Game Board")
        @ParameterizedTest
        @CsvSource({"110100000,0",
                    "111010000,1",
                    "011001000,2",
                    "100110100,3",
                    "010111010,4",
                    "001011001,5",
                    "000100110,6",
                    "000010111,7",
                    "000001011,8"})
        /**
         * Tests that result in an all black game board
         */
        void resultIsAllBlack(String board, String button) {
            int intButton = Integer.parseInt(button);
            String afterClick = simulatedGame.buttonClicked(board, intButton);
            String expected = "000000000";
            assertEquals(expected, afterClick, "expecting all black game board (9x\"0\"");
        }

        @DisplayName("Single Clicks Leading to All White Game Board")
        @ParameterizedTest
        @CsvSource({"001011111,0",
                "000101111,1",
                "100110111,2",
                "011001011,3",
                "101000101,4",
                "110100110,5",
                "111011001,6",
                "111101000,7",
                "111110100,8"})
        /**
         * Tests that result in an all white game board
         */
        void resultIsAllWhite(String board, String button) {
            int intButton = Integer.parseInt(button);
            String afterClick = simulatedGame.buttonClicked(board, intButton);
            String expected = "111111111";
            assertEquals(expected, afterClick, "expecting all white game board (9x\"1\"");
        }

        @DisplayName("Arbitrary button click sequence")
        @Test
        /**
         * Test an arbitrary button click sequence
         */
        void arbitraryBoardClick() {
            int[] buttonClicks = {2,4,5,6,7,8};
            String board = "111111111";

            for(int click : buttonClicks) {
                board = simulatedGame.buttonClicked(board, click);
            }
            String expected = "111101110";
            assertEquals(expected, board, "expecting a specific board configuration after 6 clicks");
        }

        @ParameterizedTest
        @ValueSource(ints = {0,1,2,3,4,5,6,7,8})
        /**
         * Clicking the same button twice should end up back at the original board
         */
        void doubleClickTests(int button) {

            //start with an arbitrary game board
            String board = "000111000";

            //click the same button twice
            String resultingBoard = simulatedGame.buttonClicked(board, button);
            resultingBoard = simulatedGame.buttonClicked(resultingBoard, button);

            //ensure we end up back with the same game board
            assertEquals(board, resultingBoard, "expect clicking the same button twice is identity operation");
        }
    }

    /**
     * Tests to ensure the correct minimum required moves
     * to go from one state to another is working
     */
    @Nested
    class MinimumRequiredMovesTests{

        @DisplayName("Arbitrary target and starting point")
        @Test
        /**
         * Test an arbitrary configuration
         */
        void arbitraryBoardConfiguration() {
            String board = "000000000";
            String target = "111111111";

            int minActual = simulatedGame.minNumberOfMoves(board, target);
            int manuallyComputedSolution = 5;
            assertEquals(manuallyComputedSolution, minActual, "expecting a certain minimum clicks");
        }

        @DisplayName("Tests requiring 1 move to reach target")
        @ParameterizedTest
        @ValueSource(strings = {"110100000",
                "111010000",
                "011001000",
                "100110100",
                "010111010",
                "001011001",
                "000100110",
                "000010111",
                "000001011"})
        /**
         * Tests that require 1 click to reach the target
         */
        void oneMoveToTargetTest(String board) {
            String target = "000000000";
            int movesRequired = simulatedGame.minNumberOfMoves(board, target);

            assertEquals(1, movesRequired, "expecting 1 required move");
        }
    }


    /**
     * Tests to ensure the correct sequence of button clicks
     * leading from current to target is returned (minimum amount of
     * button clicks)
     * Note: The tests should be invariant to the order of
     * button clicks
     */
    @Nested
    class PathToTargetTests{


        @ParameterizedTest
        @CsvSource({"110100000,0",
                "111010000,1",
                "011001000,2",
                "100110100,3",
                "010111010,4",
                "001011001,5",
                "000100110,6",
                "000010111,7",
                "000001011,8"})
        @DisplayName("Tests requiring 1 move to reach target")
        /**
         * Tests that require 1 move to reach the target
         */
        void oneMoveWithValueToTargetTest(String board, String expectedMove) {
            String target = "000000000";
            int[] moves = simulatedGame.movesToSolve(board, target);

            int mv = moves[0];

            assertEquals(Integer.parseInt(expectedMove), mv, "expecting the specified move");
        }

        @DisplayName("Arbitrary test requiring 2")
        @Test
        /**
         * Test an arbitrary configuration
         */
        void twoMovesRequiredTest() {
            String board = "000000000";
            String target = "110101011";

            int[] moves = simulatedGame.movesToSolve(board, target);
            int[] movesExpected = {0,8};

            assertEquals(2, moves.length);
            for(int m : movesExpected) {
                assertTrue(moves[0] == m || moves[1] == m);
            }
        }
    }
}