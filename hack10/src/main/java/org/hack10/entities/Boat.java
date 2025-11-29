package org.hack10.entities;

import javax.imageio.ImageIO;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Boat extends Entity {
    private Position pos;

    //Constructor
    public Boat(Position position)
    {
        pos = position;
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("../../../resources/TopDown.png"));
            this.hitbox = new Rectangle(position.x, position.y, image.getWidth(), image.getHeight());
        }catch(Exception e){
            System.err.println("Error loading boat image: " + e.getMessage());
        }
    }

    //Getters
    public Position getPos() { return pos; }

    //Setters
    public void move() { pos = new Position(pos.x, pos.y - 1); } 
}