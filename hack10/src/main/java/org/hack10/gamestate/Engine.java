package org.hack10.gamestate;

import org.hack10.*;

public class Engine {
    private Context currentContext;
    private GameState currentGameState;

    public Engine() {
        currentGameState = new GameState();
        currentContext = new Context(this, currentGameState);
    }
}
