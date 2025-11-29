package org.hack10.entities;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Boat extends Entity {
    private Position pos;

    //Constructor
    public Boat(Position position)
    {
        pos = position;
        this.image = image;
    }

    //Getters
    public Position getPos() { return pos; }

    //Setters
    public void move() { pos = new Position(pos.x, pos.y - 1); } 
}