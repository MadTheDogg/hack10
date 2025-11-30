package org.hack10.entities;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javafx.scene.image.Image;

public class Entity {
    protected BufferedImage image;
    protected Image drawImage;
    protected Rectangle hitbox;
    protected Position position;

    //constructor will be done within subclasses for their values
    public BufferedImage getImage() {
        return image;
    }

    public Image getViewImage() {
        return drawImage;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Position getPosition() {
        return position;
    }   
}
