package org.hack10.entities;

import javax.imageio.ImageIO;
import java.awt.Rectangle;



public class Boat extends Entity {
    private Position pos;
    private double angle;
    private double windDirection;
    private double sailDirection;
    private int health;
    private int ballistaLevel;
    private int sailLevel;

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
        this.windDirection = 0.0;
        health = 100;
        this.ballistaLevel=1;
        this.sailLevel=1;
    }

    //Getters
    public Position getPos() { return pos; }
    public int getBallistaLevel(){ return ballistaLevel; }
    public int getSailLevel(){ return sailLevel; }
    public double getWindDirection() 
    { 
        return windDirection; 
    }

    public double getSailDirection() 
    { 
        return sailDirection; 
    }

    //Setters

    public void setWindDirection(double windDirection) 
    { 
        this.windDirection = windDirection; 
    }

    public void setSailDirection(double sailDirection) 
    { 
        this.sailDirection = sailDirection; 
    }

    //movers
    public void move(Position position) { 
        pos = new Position(position.x, position.y); 
        this.hitbox.setLocation((int)position.x, (int)position.y);
    } 
    
    public void angleMove(double angle){
        this.angle = angle;
    }
    public void loseHealth(int damage){//add a method for when boat dies
        this.health -= damage;
        if(this.health < 0){
            this.health = 0;
        }
    }
}