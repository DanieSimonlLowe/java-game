package entities.ai;

import background.Base;
import background.Tile;
import entities.EntityPlacer;

import javax.vecmath.Vector2d;
import java.awt.*;

public class ConstantPlacer implements EntityPlacer {

    final private Tile tile;

    public ConstantPlacer(Tile tile) {
        this.tile = tile;
    }

    @Override
    public void place(Base base, Vector2d position, Image image, double deltaTime) {
        base.drawTile(position, tile, image);
    }
}
