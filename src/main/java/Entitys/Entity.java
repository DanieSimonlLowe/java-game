package Entitys;

import java.awt.*;
import javax.vecmath.*;
import Background.Tile;
import Background.TileUtills;
import Display.Drawable;
import Display.Layer;

public class Entity {
    private Tile[] weaknesses;

    private static final int testPointCount = 5;
    private double speed;
    private Vector2d position;
    private EntityController controller;
    private Drawable drawable;
    private EntityPlacer placer;

    private Tuple2d oldDir;

    private int health;

    public EntityController getController() {
        return controller;
    }

    public Entity(Tile[] weaknesses, int health, double speed, Vector2d position, Drawable drawable) {
        initEntity(weaknesses,health,speed,position,null,null,drawable);
    }

    public Entity(Tile[] weaknesses, int health, double speed, Vector2d position, EntityController controller, Drawable drawable) {
        initEntity(weaknesses,health,speed,position,controller,null,drawable);
    }

    public Entity(Tile[] weaknesses, int health, double speed, Vector2d position, EntityController controller, EntityPlacer placer, Drawable drawable) {
        initEntity(weaknesses,health,speed,position,controller,placer,drawable);
    }

    private void initEntity(Tile[] weaknesses, int health, double speed, Vector2d position, EntityController controller, EntityPlacer placer, Drawable drawable) {
        this.health = health;
        this.weaknesses = weaknesses;
        this.speed = speed;
        this.position = position;
        this.controller = controller;
        this.drawable = drawable;
        this.placer = placer;
        this.oldDir = new Vector2d(0,0);
    }

    public Image getImage(double deltaTime) {
        return drawable.draw(deltaTime);
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
        points[0][0] = getPosX()+drawable.getWidth()/2;
        points[0][1] = getPosY()+drawable.getHeight()/2;
        //top
        points[1][0] = getPosX()+drawable.getWidth()/2;
        points[1][1] = getPosY();
        //left
        points[2][0] = getPosX();
        points[2][1] = getPosY()+drawable.getHeight()/2;
        //bottom
        points[3][0] = getPosX()+drawable.getWidth()/2;
        points[3][1] = getPosY()+drawable.getHeight();
        //right
        points[4][0] = getPosX()+drawable.getWidth();
        points[4][1] = getPosY()+drawable.getHeight()/2;
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
            } else if (position.x >= base.getWidth() - drawable.getWidth()) {
                position.x = base.getWidth() - drawable.getWidth() - 1;
                dir.x = 0;
            }
            if (position.y < 0) {
                position.y = 0;
                dir.y = 0;
            } else if (position.y >= base.getHeight() - drawable.getHeight()) {
                position.y = base.getHeight() - drawable.getHeight() - 1;
                dir.y = 0;
            }
        }

        oldDir = dir;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void place(Layer base, double deltaTime) {
        if (placer != null) {
            placer.place(base, position, drawable.getPlaceImage(), deltaTime);
        }
    }
}
