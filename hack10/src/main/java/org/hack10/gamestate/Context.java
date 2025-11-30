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

import javafx.scene.control.skin.ContextMenuSkin;

public class Context {
    //Stores the current state of all objects in the game

    //private Boat _boat
    private Map map;
    private Boat boat;
    private List<Tile> possibleTiles;
    private List<Monster> possibleMonsters;
    private Arrow arrow;
    
    public Context() {
        possibleTiles = new ArrayList<>();
        possibleMonsters = new ArrayList<>();
        readJSON(Context.class.getResourceAsStream("/org/hack10/config/Tiles.json"));

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

                JSONArray nodes = (JSONArray) tileObject.get("nodes");
                List<Node> nodeList = new ArrayList<>();
                for (Object n : nodes) {
                    JSONObject nodeObject = (JSONObject) n;
                    Long x = (Long) nodeObject.get("x");
                    Long y = (Long) nodeObject.get("y");
                    nodeList.add(new Node(new Position(x.intValue(), y.intValue())));
                }

                Tile tile = new Tile(nodeList);
                tile.setImage(imagePath);
                tile.setHitbox(hitboxPath);

                possibleTiles.add(tile);
            }

            //Reading each monster
            JSONArray monsterArray = (JSONArray) array.get(1);
            for (Object m : monsterArray) {
                JSONObject monsterObject = (JSONObject) m;
                String imagePath = (String) monsterObject.get("imagePath");
                int monsterHealth = ((Long) monsterObject.get("health")).intValue();
                int monsterDamage = ((Long) monsterObject.get("damage")).intValue();

                //Creating monster object
                try {
                    Monster monster = new Monster(monsterHealth, monsterDamage,imagePath);
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
    public List<Monster> getPossibleMonsters() { return possibleMonsters; }
    public Boat getBoat() { return boat; }
    public Map getMap() { return map; }
    public Arrow getArrow() { return arrow; }

    //Setters
    public void setMap(Map map) { this.map = map; }
    public void setBoat(Boat boat) { this.boat = boat; } 
    public void setArrow(Arrow arrow) { this.arrow = arrow; }
}
