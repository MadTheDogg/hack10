package org.hack10.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Monster extends Entity {
    private BufferedImage image;
    private Rectangle hitbox;

    public Monster(BufferedImage image) {
        this.image = image;
    }        
}