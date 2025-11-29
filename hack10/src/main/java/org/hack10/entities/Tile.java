package org.hack10.entities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import org.hack10.gamestate.*;
import java.awt.Rectangle;
import javax.imageio.ImageIO;


public class Tile{
    private BufferedImage image;
    private BufferedImage hitbox;//white and black image for hitbox - white is allowed, black is not - no racial motivations
    private Entity[][] interactables;
    private Context context;

    //constructor
    public Tile(Context context){
        this.image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        this.hitbox = new BufferedImage(32, 32, BufferedImage.TYPE_BYTE_BINARY);
        this.interactables = new Entity[32][32];
        this.context = context;
    }

    //getters
    public BufferedImage getImage() {
        return image;
    }
    public BufferedImage getHitbox() {
        return hitbox;
    }

    //setters
    public void setImage(String filePath){//turns an image into a buffered image
        try{
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;
        }catch(Exception e){
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    public void setHitbox(String filePath){//turns an image into a buffered image
        try{
        BufferedImage hitbox = ImageIO.read(new File(filePath));
        this.hitbox = hitbox;
        }catch(Exception e){
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    //add interactables to the array
    public void addInteractable(Entity entity, int x, int y){
        this.interactables[x][y] = entity;
    }
    public Boolean canBeTravelled(){
        Position playerCollision = context.getGameState().getBoat().getHitbox();
        int [] hitboxColours = hitbox.getRGB(playerCollision.x, playerCollision.y, playerCollision.width, playerCollision.height, null, 0, playerCollision.width);
        for(int colour : hitboxColours){
            if(colour == -16777216){//black ????????????????????????????
                return false;
            }
        }   
        for (int i=0;i<interactables.length;i++){//check if boat hitbox hits an interactable hitbox
            for(int j=0;j<interactables[i].length;j++){
                Entity entity = interactables[i][j];
                if(entity != null){
                    Rectangle entityHitbox = entity.getHitbox();
                    if(playerCollision.intersects(entityHitbox)){
                        return false;
                    }
                }
            }
        }return true;
    }
}
