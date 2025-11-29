package org.hack10.entities;

import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class TradingOutpost extends Entity {
    private Position position;

    public TradingOutpost(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
    public void setImageAndHitbox(String filePath) {
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        this.hitbox = new Rectangle(position.x,position.y,image.getWidth(),image.getHeight());
        }catch(Exception e){
            System.err.println("Error loading Resource image: " + e.getMessage());
        }
    }
}
