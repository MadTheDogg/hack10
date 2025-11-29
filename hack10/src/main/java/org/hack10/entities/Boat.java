package org.hack10.entities;

import javax.imageio.ImageIO;
import java.awt.Rectangle;



public class Boat extends Entity {
    private Position pos;
    private double angle;
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

    //movers
    public void move() { 
        pos = new Position(pos.x, pos.y - 1); 
        this.hitbox.setLocation(pos.x, pos.y-1);
    } 
    
    public void angleMove(){
        int xChange= (int) Math.cos(angle);
        int yChange= (int) Math.sin(angle);
        pos = new Position(pos.x + xChange, pos.y + yChange);
        this.hitbox.setLocation(pos.x + xChange, pos.y + yChange);
    }
}