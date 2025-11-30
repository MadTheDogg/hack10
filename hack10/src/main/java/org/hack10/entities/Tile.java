package org.hack10.entities;

import java.awt.image.BufferedImage;
import java.io.File;

import org.hack10.gamestate.*;
import org.hack10.entities.*;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.spec.ECField;

import javax.imageio.ImageIO;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.*;


public class Tile{
    private Image image;
    private BufferedImage hitbox;//white and black image for hitbox - white is allowed, black is not - no racial motivations
    private List<Entity> interactables;
    private ImageView background;

    private String imagePath, hitPath;
    public boolean moved = false;
    private List<Node> nodes;
    private int nIndex;

    //constructor
    public Tile(List<Node> nodes){
        //this.hitbox = new BufferedImage(32, 32, BufferedImage.TYPE_BYTE_BINARY);
        this.interactables = new ArrayList<>();
        this.nodes = nodes;     
        nIndex = 0;
    }

    //getters
    public Image getImage() {
        return image;
    }
    public BufferedImage getHitbox() {
        return hitbox;
    }
    public List<Entity> getInteractables() {
        return interactables;
    }
    //setters
    public void setImage(String filePath){//turns an image into a buffered image
        imagePath = filePath;
        try {
            Image image = new Image(getClass().getResourceAsStream(filePath));
            this.image = image;
        } catch(Exception e){
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    public void setHitbox(String filePath){//turns an image into a buffered image
        hitPath = filePath;
        try(InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                is.close();
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            BufferedImage hitbox = ImageIO.read(is);
            if (hitbox == null) {
                throw new IllegalArgumentException("Failed to read image from file: " + filePath);
            }
            this.hitbox = hitbox;
        } catch(Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    //add interactables to the array
    public void addInteractable(Entity entity){
        interactables.add(entity);
    }

    public Integer canBeTravelled(Context context){//-1 is a border, 0 means normal, 1 is a monster, 2 is resource, 3 is trading outpost
        if (context == null || context.getBoat() == null) {
            System.err.println("canBeTravelled: context or boat is null");
            return -2;
        }
        Rectangle playerCollision = context.getBoat().getHitbox();
        if (playerCollision == null) {
            System.err.println("canBeTravelled: boat hitbox is null");
            return -2;
        }
        if (hitbox == null) {
            System.err.println("canBeTravelled: tile hitbox image is null");
            return -2;
        }
        int [] hitboxColours = hitbox.getRGB(playerCollision.x, playerCollision.y, playerCollision.width, playerCollision.height, null, 0, playerCollision.width);
        for(int colour : hitboxColours){
            if(colour == -16777216){//black ??? - does it go that high?
                return -1;
            }
        } 
        for (Entity e : interactables) {
            if(e != null) {
                Rectangle entityHitbox = e.getHitbox();
                if(playerCollision.intersects(entityHitbox)){
                    //change based on entity type;;
                    if(e instanceof Monster){
                        context.getBoat().loseHealth(((Monster) e).getDamage());//boat loses health on monster collision
                        return 1;
                    }else if(e instanceof Resource){
                        return 2;
                    }else if(e instanceof TradingOutpost){
                        return 3; 
                    }
                }
            }
        }  return 0;
    }
    public Boolean isEnd(Context context){//checks if it is the end of the tile aka move to next one
        Rectangle playerCollision = context.getBoat().getHitbox();
        //System.out.print(playerCollision.x + " | " + (image.getWidth() - (2 * playerCollision.width)) + ", ");
        if (playerCollision.x >= image.getWidth() - (2 * playerCollision.width)) {
            //System.out.println("Tile.java: Reached end of tile");
            return true;
        }
        else return false;
    }

    public ImageView getBackground() {
        return background;
    }
    public void setBackground(ImageView background) {
        this.background = background;
    }
    public String toString() {
        return imagePath + " | " + hitPath;
    }

    public static int[][] toGrid(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int[] pixels = img.getRGB(0, 0, w, h, null, 0, w);
        int[][] grid = new int[h][w];
        for (int y=0;y<h;y++) {
            int off = y*w;
            for (int x=0;x<w;x++) {
                int argb = pixels[off + x];
                int r = (argb>>16)&0xFF;
                int g = (argb>>8)&0xFF;
                int b = argb & 0xFF;
                grid[y][x] = (r==255 && g==255 && b==255) ? 1 : 0;
            }
        }
        return grid;
    }

    public List<Position> findWhitePixels() {
        BufferedImage img = hitbox;
        int tolerance = 10;
        boolean requireOpaque = false;

        List<Position> points = new ArrayList<>();
        if (img == null) return points;

        final int w = img.getWidth();
        final int h = img.getHeight();
        // Read all pixels in one call (row-major)
        int[] pixels = img.getRGB(0, 0, w, h, null, 0, w);

        for (int y = 0; y < h; y++) {
            int rowOff = y * w;
            for (int x = 0; x < w; x++) {
                int argb = pixels[rowOff + x];
                int a = (argb >> 24) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8)  & 0xFF;
                int b =  argb        & 0xFF;

                if (requireOpaque && a < 255) continue; // skip transparent pixels

                // treat as white if all channels are within tolerance of 255
                if ((255 - r) <= tolerance && (255 - g) <= tolerance && (255 - b) <= tolerance) {
                    points.add(new Position(x, y));
                }
            }
        }
        return points;
    }

    public Tile copy() {
        Tile newTile = new Tile(this.nodes);
        newTile.setImage(this.imagePath);
        newTile.setHitbox(this.hitPath);
        return newTile;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
