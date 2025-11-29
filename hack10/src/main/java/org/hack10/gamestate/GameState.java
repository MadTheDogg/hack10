package org.hack10.gamestate;

import org.hack10.entities.*;
import org.hack10.entities.Map;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;

public class GameState {
    //Stores the current state of all objects in the game

    //private Boat _boat
    private Map map;
    private Boat boat;
    private List<Tile> possibleTiles;
    private List<Monster> possibleMonsters;
    
    public GameState(Map map, Boat boat) {
        possibleTiles = new ArrayList<>();
        possibleMonsters = new ArrayList<>();
        readJSON(GameState.class.getResourceAsStream("/org/hack10/config/Tiles.txt"));

        this.map = map;
        this.boat = boat;
    }

    private void readJSON(InputStream stream) {
        //Reader for json
        JSONParser parser = new JSONParser();
        try {
            //Taking in the overall json array that has sub arrays for tiles, entities etc
            JSONArray array = (JSONArray) parser.parse(new InputStreamReader(stream));

            //Reading each tile object and adding them to the possible tiles
            JSONArray tileArray = (JSONArray) array.get(0);
            for (Object t : tileArray) {
                JSONObject tileObject = (JSONObject) t;
                String imagePath = (String) tileObject.get("imagePath");
                String hitboxPath = (String) tileObject.get("hitboxPath");

                Tile tile = new Tile();
                tile.setImage(imagePath);
                tile.setHitbox(hitboxPath);

                possibleTiles.add(tile);
            }

            //Reading each monster
            JSONArray monsterArray = (JSONArray) array.get(1);
            for (Object m : monsterArray) {
                JSONObject monsterObject = (JSONObject) m;
                String imagePath = (String) monsterObject.get("imagePath");

                //Creating monster object
                try {
                    BufferedImage image = ImageIO.read(new File(imagePath));
                    Monster monster = new Monster(image);
                    possibleMonsters.add(monster);
                } catch (Exception e) {
                    //Error handliong
                }
            }
        } catch (Exception e) {
            //Error handling
        }
    }

    //Getters
    public List<Tile> getPossibleTiles() { return possibleTiles; }
    public Boat getBoat() { return boat; }
    public Map getMap() { return map; }
}
