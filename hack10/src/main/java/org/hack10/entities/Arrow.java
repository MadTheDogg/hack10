package org.hack10.entities;

import org.hack10.gamestate.*;
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
    public boolean move(Context context){//returns false if arrow runs out of speed or hits something
        if(this.speed<=0){
            return false;
        }
        else if (!(this.checkCollision(context))){
            return false;
        }else{
            this.speed--;
            this.position.x+=speed;
            this.hitbox.setLocation((int)this.position.x,(int)this.position.y);
            return true;
        }
        
        //this only runs if the arrow should still fly
        
    }
    public boolean checkCollision(Context context){
        BufferedImage tileHitbox = context.getMap().getCurrentTile().getHitbox();
        int [] hitboxColours = tileHitbox.getRGB(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height, null, 0, this.hitbox.width);
        for(int colour : hitboxColours){
            if(colour == -16777216){//black ??? - does it go that high?
                return false;
            }
        }   
        Entity[][] interactables = context.getMap().getCurrentTile().getInteractables();
        for (int i=0;i<interactables.length;i++){//check if boat hitbox hits an interactable hitbox
            for(int j=0;j<interactables[i].length;j++){
                Entity entity = interactables[i][j];
                if(entity != null){
                    Rectangle entityHitbox = entity.getHitbox();
                    if(this.hitbox.intersects(entityHitbox)){
                        return false;
                    }
                }
            }
        }return true;
    }
    public int calculateDMG(){return this.speed*10;}
}
