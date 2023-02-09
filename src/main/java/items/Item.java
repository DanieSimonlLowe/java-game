package items;

import background.Tile;
import entities.Entity;

import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;

public class Item {

    private final Vector2d position;
    private static final ImageIcon gooIcon = new ImageIcon("src/main/resources/Images/items/goo.png");
    private static final ImageIcon iceIcon = new ImageIcon("src/main/resources/Images/items/ice.png");
    private static final ImageIcon oilIcon = new ImageIcon("src/main/resources/Images/items/oil.png");

    private boolean shouldBeDestroyed;

    private final Tile tile;
    private final double amount;

    private final Image image;

    public Item(double amount, Tile tile, Vector2d position) {
        this.amount = amount;

        this.tile = tile;
        this.position = position;
        this.shouldBeDestroyed = false;
        image = makeImage();
    }

    static private final double pickUpDistSquare = 800;

    public boolean shouldPickUp(Entity player) {
        double distX = player.getCenterX() - position.getX() - image.getWidth(null)/2.0;
        double distY = player.getCenterY() - position.getY() - image.getHeight(null)/2.0;
        double distSquare = distX*distX + distY*distY;
        return distSquare <= (pickUpDistSquare);
    }

    private Image makeImage() {
        switch (tile) {
            case oil:
                return oilIcon.getImage();
            case goo:
                return gooIcon.getImage();
            case ice:
                return iceIcon.getImage();
            default:
                throw new RuntimeException();
        }
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

    public Tile getTile() {
        return tile;
    }

    public double getAmount() {
        return amount;
    }

    public void destroy() {
        shouldBeDestroyed = true;
    }

    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

}
