package org.hack10.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class Monster extends Entity {
    private int health;
    private int damage;
    private Position position;
    private Image drawImage;
    private String filePath;

    public Monster(int health, int damage, String filePath) {
        this.health = health;
        this.damage = damage;
        this.filePath = filePath;
        try {
            drawImage = new Image(getClass().getResourceAsStream(filePath));
            this.hitbox = new Rectangle(0, 0, 100, 100);
        } catch (Exception e){
            System.err.println("Error loading Resource image: " + e.getMessage());
        }
    }      
      
    public void setPosition(Position position){
        this.position = position;
        this.hitbox.setLocation((int) position.x, (int) position.y);
    }

    public Position getPosition(){
        return this.position;
    }

    public Image getViewImage() {
        return drawImage;
    }

    public Monster copy() {
        return new Monster(this.health, this.damage, this.filePath);
    }

    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public String getFilePath() { return filePath; }
}