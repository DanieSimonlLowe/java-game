package items;

import entities.Entity;
import entities.player.Inventory;

import javax.vecmath.Vector2d;
import java.awt.*;

public abstract class Item {


    private final Vector2d position;

    private boolean shouldBeDestroyed;


    private final Image image;

    public Item( Image image, Vector2d position) {
        this.position = position;
        this.shouldBeDestroyed = false;
        this.image = image;
    }

    static private final double pickUpDistSquare = 2500;

    public boolean shouldPickUp(Entity player) {
        double distX = player.getCenterX() - (image.getWidth(null)/2.0 + position.getX());
        double distY = player.getCenterY() - (image.getHeight(null)/2.0 + position.getY());
        double distSquare = distX*distX + distY*distY;
        return distSquare <= (pickUpDistSquare);
    }


    public int getPosX() {
        return (int) position.getX();
    }

    public int getPosY() {
        return (int) position.getY();
    }

    public Image getImage() {
        return image;
    }

    public void destroy() {
        shouldBeDestroyed = true;
    }

    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

    public abstract void addItem(Entity player, Inventory inventory);

}
