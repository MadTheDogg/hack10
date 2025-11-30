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
        this.hitbox = new Rectangle((int) position.x, (int) position.y, 100, 100);

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("../../../resources/TopDown.png"));
        } catch(Exception e){
            System.err.println("Error loading boat image: " + e.getMessage());
        }
    }

    //Getters
    public Position getPos() { return pos; }

    //movers
    public void move(Position position) { 
        pos = new Position(position.x, position.y); 
        this.hitbox.setLocation((int)position.x, (int)position.y);
    } 
    
    public void angleMove(double angle){
        this.angle = angle;
    }
}