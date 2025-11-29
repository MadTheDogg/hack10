package org.hack10.entities;

import org.hack10.gamestate.*;
import java.util.*;
import java.io.*;

public class Map {

    private Context context;
    private List<Tile> map;
    private Random generator;
    private Tile currentTile;

    public Map(Context context) {
        this.context = context;
        calcNextTile(context);

        map = new ArrayList<>();
        generator = new Random();
    }

    private void calcNextTile(Context context) {
        //Randomly generate a number to select from the tile calculation array
        int tileNum = generator.nextInt(1);
        currentTile = context.getGameState().getPossibleTiles().get(tileNum);
        map.add(currentTile);

        //Will need to add entity, resource, building spawning
    }

    //Getters
    public Tile getCurrentTile() {
        return currentTile;
    }

    //Public methods
    public void isValidMove(Context context) {
        if (true) {
            //Using the tiles methods to check collision
        }
        else calcNextTile(context);
    }
}
