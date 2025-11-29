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
        angle = 0;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("../../../resources/TopDown.png"));
            this.hitbox = new Rectangle(position.x, position.y, 100, 100);
        } catch(Exception e){
            System.err.println("Error loading boat image: " + e.getMessage());
        }
    }

    //Getters
    public Position getPos() { return pos; }

    //movers
    public void move(Position position) { 
        pos = new Position(position.x, position.y); 
        this.hitbox.setLocation(position.x, position.y);
    } 
    
    public void angleMove(double angle){
        this.angle = angle;
    }
}