package org.hack10.gamestate;

import org.hack10.entities.*;

public class Engine {
    private Context currentContext;
    private GameState currentGameState;

    public Engine() {
        currentContext = new Context(this, currentGameState);
        //Initialising game state with initial entities
        currentGameState = new GameState(new Map(currentContext),
            new Boat(new Position(0, 0)));        
    }
}
