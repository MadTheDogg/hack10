package org.hack10.entities;
<<<<<<< HEAD

public class Tile {
    
=======
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

import javax.imageio.ImageIO;


public class Tile{
    private BufferedImage image;
    private BufferedImage hitbox;//white and black image for hitbox - white is allowed, black is not - no racial motivations
    private Entity[][] interactables;

    //constructor
    public Tile(){
        this.image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        this.hitbox = new BufferedImage(32, 32, BufferedImage.TYPE_BYTE_BINARY);
        this.interactables = new Entity[32][32];
    }

    //getters
    public BufferedImage getImage() {
        return image;
    }
    public BufferedImage getHitbox() {
        return hitbox;
    }

    //setters
    public void setImage(String filePath){//turns an image into a buffered image
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        }catch(Exception e){
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    public void setHitbox(String filePath){//turns an image into a buffered image
        try{
        BufferedImage hitbox = ImageIO.read(new File(filePath));
        this.hitbox = hitbox;
        }catch(Exception e){
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    //add interactables to the array
    public void addInteractable(Entity entity, int x, int y){
        this.interactables[x][y] = entity;
    }
>>>>>>> d84631d (added tile and entity)
}
