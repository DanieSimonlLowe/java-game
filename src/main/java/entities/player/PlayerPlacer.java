package entities.player;

import background.Base;
import entities.EntityPlacer;

import javax.vecmath.Vector2d;
import java.awt.*;

public class PlayerPlacer implements EntityPlacer {
    //boolean
    private final Inventory inventory;

    public PlayerPlacer(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void place(Base base, Vector2d position, Image image, double deltaTime) {
        if (inventory.isPlacing()) {
            base.drawTile(position, inventory.getCurrentTile(), image);
            inventory.useCurrent(deltaTime);
        }
    }
}
