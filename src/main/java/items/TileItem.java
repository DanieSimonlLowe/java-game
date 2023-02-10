package items;

import background.Tile;
import entities.Entity;
import entities.player.Inventory;

import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;

public class TileItem extends Item {

    private static final ImageIcon gooIcon = new ImageIcon("src/main/resources/Images/items/goo.png");
    private static final ImageIcon iceIcon = new ImageIcon("src/main/resources/Images/items/ice.png");
    private static final ImageIcon oilIcon = new ImageIcon("src/main/resources/Images/items/oil.png");


    private final Tile tile;
    private final double amount;


    public TileItem(double amount, Tile tile, Vector2d position) {
        super(makeImage(tile),position);
        this.amount = amount;

        this.tile = tile;
    }




    private static Image makeImage(Tile tile) {
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


    public Tile getTile() {
        return tile;
    }

    public double getAmount() {
        return amount;
    }


    @Override
    public void addItem(Entity player, Inventory inventory) {
        inventory.addTile(getTile(),getAmount());
        destroy();
    }
}
