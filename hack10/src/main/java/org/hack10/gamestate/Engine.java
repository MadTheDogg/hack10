package org.hack10.gamestate;

import org.hack10.entities.*;

public class Engine {
    private Context currentContext;
    private GameState currentGameState;

    public Engine() {
        currentContext = new Context(this, currentGameState);
        currentGameState = new GameState(new Map(currentContext));        
    }
}
