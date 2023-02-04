package Entitys;

import java.awt.*;
import javax.swing.*;
import javax.vecmath.*;
import Background.Tiles;
import Display.Layer;

public class Entity {
    private Tiles[] weaknesses;
    private double speed;
    private Vector2d position;
    private EntityController controller;
    private Image image;
    private EntityPlacer placer;

    public Entity(Tiles[] weaknesses, double speed, Vector2d position, EntityController controller, ImageIcon image) {
        this.weaknesses = weaknesses;
        this.speed = speed;
        this.position = position;
        this.controller = controller;
        this.image = image.getImage();
        this.placer = null;
    }

    public Entity(Tiles[] weaknesses, double speed, Vector2d position, EntityController controller, EntityPlacer placer, ImageIcon image) {
        this.weaknesses = weaknesses;
        this.speed = speed;
        this.position = position;
        this.controller = controller;
        this.image = image.getImage();
        this.placer = placer;
    }

    public Image getImage() {
        return image;
    }

    public int getPosX() {
        return (int)position.x;
    }
    public int getPosY() {
        return (int)position.y;
    }

    public void move(double deltaTime) {
        Tuple2d dir = controller.getDirection();
        if (dir.x != 0 || dir.y != 0) {
            double malt = speed*deltaTime;
            position.x += dir.x * malt;
            position.y += dir.y * malt;
        }

    }

    public void place(Layer base) {
        if (placer != null) {
            placer.place(base, position, image);
        }
    }
}
