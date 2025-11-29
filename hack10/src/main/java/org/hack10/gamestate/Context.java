package org.hack10.gamestate;

public class Context {
    //Stores both the engine and the game state
    private Engine engine;
    private GameState gameState;

    public Context(Engine engine, GameState gameState) {
        this.engine = engine;
        this.gameState = gameState;
    }

    //Getters
    public Engine getEngine() { return engine;}
    public GameState getGameState() { return gameState; }
}
