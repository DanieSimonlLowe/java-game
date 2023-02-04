package Entitys;

import java.awt.*;
import javax.swing.*;
import javax.vecmath.*;
import Background.Tile;
import Background.TileUtills;
import Display.Layer;

public class Entity {
    private Tile[] weaknesses;

    private static int testPointCount = 5;
    private double speed;
    private Vector2d position;
    private EntityController controller;
    private Image image;
    private EntityPlacer placer;

    private Tuple2d oldDir;

    private int health;

    public Entity(Tile[] weaknesses, int health, double speed, Vector2d position, ImageIcon image) {
        initEntity(weaknesses,health,speed,position,null,null,image);
    }

    public Entity(Tile[] weaknesses, int health, double speed, Vector2d position, EntityController controller, ImageIcon image) {
        initEntity(weaknesses,health,speed,position,controller,null,image);
    }

    public Entity(Tile[] weaknesses, int health, double speed, Vector2d position, EntityController controller, EntityPlacer placer, ImageIcon image) {
        initEntity(weaknesses,health,speed,position,controller,placer,image);
    }

    private void initEntity(Tile[] weaknesses, int health, double speed, Vector2d position, EntityController controller, EntityPlacer placer, ImageIcon image) {
        this.health = health;
        this.weaknesses = weaknesses;
        this.speed = speed;
        this.position = position;
        this.controller = controller;
        this.image = image.getImage();
        this.placer = placer;
        this.oldDir = new Vector2d(0,0);
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

    private int[][] getTestPoints() {
        int[][] points = new int[testPointCount][2];
        //center
        points[0][0] = getPosX()+image.getWidth(null)/2;
        points[0][1] = getPosY()+image.getHeight(null)/2;
        //top
        points[1][0] = getPosX()+image.getWidth(null)/2;
        points[1][1] = getPosY();
        //left
        points[2][0] = getPosX();
        points[2][1] = getPosY()+image.getHeight(null)/2;
        //bottom
        points[3][0] = getPosX()+image.getWidth(null)/2;
        points[3][1] = getPosY()+image.getHeight(null);
        //right
        points[4][0] = getPosX()+image.getWidth(null);
        points[4][1] = getPosY()+image.getHeight(null)/2;
        return points;
    }

    private boolean isWeakness(Tile test) {
        for (Tile tile: weaknesses) {
            if (test == tile) {
                return true;
            }
        }
        return false;
    }

    public void move(double deltaTime, Layer base) {
        if (controller == null) {
            return;
        }
        Tuple2d dir = controller.getDirection();

        //center top left bottom right
        int[][] testPoints = getTestPoints();
        Tile[] testTile = new Tile[testPointCount];
        boolean onGoo = false;
        boolean onIce = false;
        for (int i = 0; i<testPointCount;i++) {
            Color pixColor = base.getColor(testPoints[i][0],testPoints[i][1]);
            testTile[i] = TileUtills.getTileFromColor(pixColor);

            if (testTile[i] == Tile.goo) {
                onGoo = true;
            } else if (testTile[i] == Tile.ice) {
                onIce = true;
            }
        }

        if (isWeakness(testTile[0])) {
            health -= 2;
        } else if (isWeakness(testTile[1])) {
            health -= 1;
            dir = new Vector2d(0,-1);
        } else if (isWeakness(testTile[2])) {
            health -= 1;
            dir = new Vector2d(-1,0);
        } else if (isWeakness(testTile[3])) {
            health -= 1;
            dir = new Vector2d(0,1);
        } else if (isWeakness(testTile[4])) {
            health -= 1;
            dir = new Vector2d(1,0);
        }
        if ((oldDir.x != 0 || oldDir.y != 0) && onIce) {
            dir = oldDir;
        }
        if (testTile[1] == Tile.wall && dir.y > 0) {
            dir.y = 0;
        }
        if (testTile[2] == Tile.wall && dir.x > 0) {
            dir.x = 0;
        }
        if (testTile[3] == Tile.wall && dir.y < 0) {
            dir.y = 0;
        }
        if (testTile[4] == Tile.wall) {
            if (dir.x < 0) {
                dir.x = 0;
            }
        }


        if (dir.x != 0 || dir.y != 0) {
            double malt = speed*deltaTime;
            if (onGoo) {
                malt *= TileUtills.gooMalt;
            }
            if (onIce) {
                malt *= TileUtills.iceMalt;
            }
            position.x += dir.x * malt;
            position.y += dir.y * malt;

            if (position.x < 0) {
                position.x = 0;
                dir.x = 0;
            } else if (position.x >= base.getWidth() - image.getWidth(null)) {
                position.x = base.getWidth() - image.getWidth(null) - 1;
                dir.x = 0;
            }
            if (position.y < 0) {
                position.y = 0;
                dir.y = 0;
            } else if (position.y >= base.getHeight() - image.getHeight(null)) {
                position.y = base.getHeight() - image.getHeight(null) - 1;
                dir.y = 0;
            }
        }

        oldDir = dir;
    }

    public void place(Layer base) {
        if (placer != null) {
            placer.place(base, position, image);
        }
    }
}
