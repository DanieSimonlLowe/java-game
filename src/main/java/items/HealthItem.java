package items;

import entities.Entity;
import entities.player.Inventory;

import javax.swing.*;
import javax.vecmath.Vector2d;

public class HealthItem extends Item {
    static private final ImageIcon imageIcon = new ImageIcon("src/main/resources/Images/items/health.png");

    private final int amount;

    public HealthItem(Vector2d position, int amount) {
        super(imageIcon.getImage(), position);
        this.amount = amount;
    }

    @Override
    public void addItem(Entity player, Inventory inventory) {
        player.addHealth(amount);
        destroy();
    }
}
