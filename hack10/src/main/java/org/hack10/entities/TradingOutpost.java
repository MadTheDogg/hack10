package org.hack10.entities;

import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class TradingOutpost extends Entity {
    private Position position;
    //resources and whatnot

    public TradingOutpost() {
        //will set resources and shit later
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
        this.hitbox.setLocation((int) position.x, (int) position.y);
    }

    public void setImageAndHitbox(String filePath) {
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        this.hitbox = new Rectangle(-100,-100,image.getWidth(),image.getHeight());//0,0 used for now
        }catch(Exception e){
            System.err.println("Error loading Resource image: " + e.getMessage());
        }
    }
}
