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
        map = new ArrayList<>();
        generator = new Random();

        calcNextTile(context);
    }

    private void calcNextTile(Context context) {
        //Randomly generate a number to select from the tile calculation array
        int tileNum = generator.nextInt(context.getPossibleTiles().size());
        currentTile = context.getPossibleTiles().get(tileNum);
        map.add(currentTile);
        context.setMap(this);

        if (context.getBoat() != null) {
            Boat newBoat = context.getBoat();
            newBoat.move(new Position(50, 415));
            context.setBoat(newBoat);
        }

        //Will need to add entity, resource, building spawning
    }

    //Getters
    public Tile getCurrentTile() {
        return currentTile;
    }

    //Public methods
    public boolean isValidMove(Context context) {
        if (currentTile.isEnd(context)) {
            calcNextTile(context);
        }
        switch (currentTile.canBeTravelled(context)) {
            //-1 is a border, 0 means normal, 1 is a monster, 2 is resource, 3 is trading outpost
            case -1 : return false;
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            default : break;
        }
        return true;
    }
}
