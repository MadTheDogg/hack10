package org.hack10.entities;



public class Boat {
    /* attributes */
    private int x;
    private int y;

    /* constructor method */
    public Boat(int x, int y)
    {
        this.x = x;
        this.y = y;

    }

    /* get and set methods */

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public Position getPosition() 
    {
        return new Position(this.x, this.y);
    }

    /*movement methods */

    public void moveRight()
    {
        this.x = (this.x+1);
    }

    public void moveLeft()
    {
        this.x = (this.x-1);
    }

    public void moveUp()
    {
        this.y = (this.y-1);
    }
    
    public void moveDown()
    {
        this.y = (this.y+1);
    }

    
}
