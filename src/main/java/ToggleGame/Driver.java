package ToggleGame;

import ToggleGame.backend.ToggleGameEngine;
import ToggleGame.frontend.ToggleGameDisplay;

/**
 * Run the main method to Play a game of Toggle Click to Match a given target on a 3x3 grid
 *
 * This file is complete and should not be altered
 */
public class Driver {
    public static void main(String[] args) {
        ToggleGameEngine engine = new ToggleGameEngine();
        ToggleGameDisplay display = new ToggleGameDisplay(engine);

    }
}
