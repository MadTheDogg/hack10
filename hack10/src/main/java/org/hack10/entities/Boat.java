package org.hack10.entities;

public class Boat {
    private Position pos;

    //Constructor
    public Boat(Position position)
    {
        pos = position;
    }

    //Getters
    public Position getPos() { return pos; }

    //Setters
    public void move() { pos = new Position(pos.x, pos.y - 1); } 
}