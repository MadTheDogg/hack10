package org.hack10.gamestate;

import org.hack10.entities.*;
import org.hack10.entities.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GameState {
    //Stores the current state of all objects in the game

    //private Boat _boat
    private Map map;
    private List<Tile> possibleTiles;
    
    public GameState(Map map) {
        possibleTiles = new ArrayList<>();
        readPossibleTiles(GameState.class.getResourceAsStream("/org/hack10/config/Tiles.txt"));

        this.map = map;
    }
    
    //Setup
    private void readPossibleTiles(InputStream stream) {
        try {
            //Reading the intial tile path file, taking all subsequent tile paths and putting them into a list
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            List<String> paths = new ArrayList<>();
            String tilePath = "";
            while ((tilePath = reader.readLine()) != null) {
                paths.add(tilePath);
            }

            //Reading all tile paths
            reader.close();
            for (String line : paths) {
                try {
                    reader = new BufferedReader(new FileReader(line));
                    String contents[] = line.split(",");
                    //possibleTiles.add(new Tile(contents[0], contents[1], ...));
                    reader.close();
                } catch (Exception e) {
                    //Error handling I cba doing
                }
            }
        }
        catch (Exception e) {
            //Some sort of error handling that I can't be bothered to do rn
        }
    }

    //Getters
    public List<Tile> getPossibleTiles() { return possibleTiles; }
}
