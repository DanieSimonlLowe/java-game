package entities.ai;

import background.Base;
import background.Tile;
import entities.Entity;
import entities.EntityPlacer;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class CollisionPlacer implements EntityPlacer {
    final private Tile tile;
    final private BufferedImage image;
    final private int size;

    private Entity entity;

    public CollisionPlacer(Tile tile, int size) {
        this.tile = tile;
        this.image = getImage(size);
        this.size = size;
    }

    public void addSelf(Entity entity) {
        this.entity = entity;
    }

    private BufferedImage getImage(int size) {
        BufferedImage image = new BufferedImage(size,size,TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D)image.getGraphics();
        graphics2D.fillOval(0,0,size,size);
        return image;
    }

    @Override
    public void place(Base base, Vector2d position, Image image, double deltaTime) {
        if (entity.touchingWall && entity.getLastDir().lengthSquared() < 0.01) {
            base.drawTile(new Vector2d(position.getX()-size/2.0,position.getY()-size/2.0), tile, this.image);
        }

    }
}
