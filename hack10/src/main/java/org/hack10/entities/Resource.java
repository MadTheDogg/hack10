package org.hack10.entities;

import java.io.File;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Resource extends Entity {
    private String resourceType;
    private int quantity;
    private Position position;

    public Resource(String resourceType, int quantity,String filePath){ 
        this.resourceType = resourceType;
        this.quantity = quantity;
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        this.hitbox = new Rectangle(100,100,image.getWidth(),image.getHeight());
        }catch(Exception e){
            System.err.println("Error loading Resource image: " + e.getMessage());
        }
    }
    
    public void setPosition(Position position){
        this.position=position;
    }
    
    public String getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
