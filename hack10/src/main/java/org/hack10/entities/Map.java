package org.hack10.entities;

import org.hack10.*;
import java.util.*;
import java.io.*;

public class Map {

    private List<Tile> map, possibleTiles;
    private Random generator;

    public Map() {
        map = new ArrayList<>();

        possibleTiles = new ArrayList<>();
        readPossibleTiles(Map.class.getResourceAsStream("/org/hack10/config/Tiles.txt"));

        generator = new Random();
    }

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

    private void calcNextTile() {
        int tileNum = generator.nextInt(1);

    }
}
