package org.hack10.entities;

import org.hack10.gamestate.*;

import java.util.*;
import java.io.*;

import javafx.geometry.Pos;

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
        currentTile = context.getPossibleTiles().get(tileNum).copy();
        setupMonster(context);
        map.add(currentTile);
        context.setMap(this);
    }

    private void calcNextTile(Context context) {
        //Randomly generate a number to select from the tile calculation array
        context.getBoat().move(new Position(50, 450));

        int tileNum = generator.nextInt(context.getPossibleTiles().size());
        currentTile = context.getPossibleTiles().get(tileNum).copy();
        map.add(currentTile);
        context.setMap(this);
        context.getBoat().setWindDirection(generator.nextDouble() * Math.PI);

        for (Tile t : context.getPossibleTiles()){
            t.moved = false;
        }
        setupMonster(context);
        //Will need to add entity, resource, building spawning
    }

    private void setupMonster(Context context) {
        List<Node> currentNodes = currentTile.getNodes();
        for (Node n : currentNodes) {
            Monster prototype = context.getPossibleMonsters().get(0); //temporary prototype
            Monster monster = prototype.copy();
            Position pos = n.getPosition();
            monster.setPosition(pos);
            currentTile.addInteractable(monster);
        }
    }

    //Getters
    public Tile getCurrentTile() {
        return currentTile;
    }

    //Public methods
    public boolean isValidMove(Context context) {
        switch (currentTile.canBeTravelled(context)) {
            //-2 is an error , -1 is a border, 0 means normal, 1 is a monster, 2 is resource, 3 is trading outpost
            case -1 : return false;
            case -2: return false;
            case 1: {
                return false;
            }
            case 2: {
                return false;
            }
            case 3: {
                return false;
            }
            default : break;
        }
        return true;
    }
    public boolean isBorder(Context context,Position newPosition) {//checks if next move will be illegal
        int [] hitboxColours = currentTile.getHitbox().getRGB((int)newPosition.x, (int)newPosition.y, context.getBoat().getHitbox().width, context.getBoat().getHitbox().height, null, 0, context.getBoat().getHitbox().width);
        for(int colour : hitboxColours){
            if(colour == -16777216){//black
                return true;
            }
        }
        return false;
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
