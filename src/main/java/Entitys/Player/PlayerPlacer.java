package Entitys.Player;

import Background.TileUtills;
import Display.Layer;
import Entitys.EntityPlacer;

import javax.vecmath.Vector2d;
import java.awt.*;

public class PlayerPlacer implements EntityPlacer {
    //boolean
    private final Inventory inventory;

    public PlayerPlacer(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void place(Layer base, Vector2d position, Image image, double deltaTime) {
        if (inventory.isPlacing()) {
            base.drawColorImage(position, TileUtills.getTileColor(inventory.getCurrentTile()), image);
            inventory.useCurrent(deltaTime);
        }
    }
}
