package org.hack10.entities;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Entity {
    protected BufferedImage image;
    protected Rectangle hitbox;

    //constructor will be done within subclasses for their values
    public BufferedImage getImage() {
        return image;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
