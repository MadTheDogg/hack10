package org.hack10.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Monster extends Entity {
    private int health;
    private int damage;
    private Position position;

    public Monster(int health, int damage, String filePath) {
        this.health=health;
        this.damage=damage;
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        this.hitbox = new Rectangle(-100,-100,image.getWidth(),image.getHeight());
        }catch(Exception e){
            System.err.println("Error loading Resource image: " + e.getMessage());
        }
    }        
    public void setPosition(Position position){
        this.position=position;
        this.hitbox.setLocation((int) position.x, (int) position.y);
    }

}