package org.hack10.entities;

import java.awt.image.BufferedImage;
import java.io.File;

import org.hack10.gamestate.*;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class Tile{
    private Image image;
    private BufferedImage hitbox;//white and black image for hitbox - white is allowed, black is not - no racial motivations
    private Entity[][] interactables;
    private ImageView background;

    //constructor
    public Tile(){
        //this.hitbox = new BufferedImage(32, 32, BufferedImage.TYPE_BYTE_BINARY);
        this.interactables = new Entity[32][32];
    }

    //getters
    public Image getImage() {
        return image;
    }
    public BufferedImage getHitbox() {
        return hitbox;
    }

    //setters
    public void setImage(String filePath){//turns an image into a buffered image
        try {
            Image image = new Image(getClass().getResourceAsStream(filePath));
            this.image = image;
        } catch(Exception e){
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    public void setHitbox(String filePath){//turns an image into a buffered image
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
    public void addInteractable(Entity entity, int x, int y){
        this.interactables[x][y] = entity;
    }
    public Integer canBeTravelled(Context context){//-1 is a border, 0 means normal, 1 is a monster, 2 is resource, 3 is trading outpost
        if (context == null || context.getBoat() == null) {
            System.err.println("canBeTravelled: context or boat is null");
            return 0;
        }
        Rectangle playerCollision = context.getBoat().getHitbox();
        if (playerCollision == null) {
            System.err.println("canBeTravelled: boat hitbox is null");
            return 0;
        }
        if (hitbox == null) {
            System.err.println("canBeTravelled: tile hitbox image is null");
            return 0;
        }
        int [] hitboxColours = hitbox.getRGB(playerCollision.x, playerCollision.y, playerCollision.width, playerCollision.height, null, 0, playerCollision.width);
        for(int colour : hitboxColours){
            if(colour == -16777216){//black ??? - does it go that high?
                return -1;
            }
        }   
        for (int i=0;i<interactables.length;i++){//check if boat hitbox hits an interactable hitbox
            for(int j=0;j<interactables[i].length;j++){
                Entity entity = interactables[i][j];
                if(entity != null){
                    Rectangle entityHitbox = entity.getHitbox();
                    if(playerCollision.intersects(entityHitbox)){
                        //change based on entity type;;
                        if(entity instanceof Monster){
                            return 1;
                        }else if(entity instanceof Resource){
                            return 2;
                        }else if(entity instanceof TradingOutpost){
                            return 3; 
                        }
                    }
                }
            }
        }return 0;
    }
    public Boolean isEnd(Context context){//checks if it is the end of the tile aka move to next one
        Rectangle playerCollision = context.getBoat().getHitbox();
        if (playerCollision.x >= image.getWidth() - (2 * playerCollision.width) - 100 ) return true;
        else return false;
    }

    public ImageView getBackground() {
        return background;
    }
    public void setBackground(ImageView background) {
        this.background = background;
    }
}
