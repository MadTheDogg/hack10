package org.hack10.entities;

import org.hack10.gamestate.*;
import java.util.*;
import java.io.*;

public class Map {

    private Context context;
    private List<Tile> map;
    private Random generator;
    private Tile currentTile;
    private boolean reloading;

    public Map(Context context) {
        this.context = context;
        map = new ArrayList<>();
        generator = new Random();

        reloading = false;

        calcFirstTile(context);
    }

    private void calcFirstTile(Context context) {
        int tileNum = generator.nextInt(context.getPossibleTiles().size());
        currentTile = context.getPossibleTiles().get(tileNum);
        map.add(currentTile);
        context.setMap(this);
    }

    private void calcNextTile(Context context) {
        //Randomly generate a number to select from the tile calculation array
        context.getBoat().move(new Position(50, 450));

        int tileNum = generator.nextInt(context.getPossibleTiles().size());
        currentTile = context.getPossibleTiles().get(tileNum);
        map.add(currentTile);
        context.setMap(this);
        System.out.println("Moving boat to start of new tile");

        for (Tile t: context.getPossibleTiles()) {
            t.moved = false;
        }
        //Will need to add entity, resource, building spawning
    }

    //Getters
    public Tile getCurrentTile() {
        return currentTile;
    }

    //Public methods
    public boolean isValidMove(Context context) {
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

    public boolean isEnd(Context context) {
        if (!currentTile.moved && currentTile.isEnd(context)) {
            currentTile.moved = true;
            
            System.out.println("Map.java: Tile end reached, calculating next tile.");
            calcNextTile(context);
            return true;
        } return false;
    }
}
