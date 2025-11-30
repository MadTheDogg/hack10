package org.hack10.entities;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Arrow extends Entity{
    int speed;
    Position position;

    public Arrow(int speed,String filePath){
        this.speed=speed;
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        this.hitbox = new Rectangle(-100,-100,image.getWidth(),image.getHeight());//-100 so can not be hit before being generated
        }catch(Exception e){
            System.err.println("Error loading Resource image: " + e.getMessage());
        }
    }
    public void setPosition(Position position){
        this.position=position;
        this.hitbox.setLocation((int) position.x,(int) position.y);
    }
    public boolean move(){//returns false if arrow runs out of speed of hits something
        if(this.speed<=0){
            return false;
        }
        this.hitbox.setLocation((int) this.position.x, (int) this.position.y);
        this.speed--;
        this.position.x+=speed;
        return true;
    }
    public int calculateDMG(){
        return this.speed*10;
    }
}
