package entitys;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.vecmath.*;
import background.Tile;
import background.TileUtils;
import display.Drawable;
import display.Layer;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

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
        if (health > healthColors.length + 1) {
            this.health = healthColors.length;
        } else {
            this.health = health;
        }

        this.weaknesses = weaknesses;
        this.speed = speed;
        this.position = position;
        this.controller = controller;
        this.drawable = drawable;
        this.placer = placer;
        this.oldDir = new Vector2d(0,0);
        damageBuffer = 0;
    }

    private BufferedImage image;


    private Color getHealthColor() {
        return healthColors[health-2];
    }

    private final static Color[] healthColors = {
            new Color(255,20,20), // 2
            new Color(200,20,150), // 3
            new Color(100,20,200), // 4
            new Color(100,150,255), // 5
            new Color(50,255,120), // 6
            new Color(255,215,0), // 7
    };
    private final static int extraSize = 5;

    private int oldDrawHealth = -1;
    public Image getImage(double deltaTime) {
        if (!drawable.needsRedraw() && oldDrawHealth == health) {
            return image;
        }
        oldDrawHealth = health;
        Image tempImage = drawable.draw(deltaTime);

        image = new BufferedImage(tempImage.getWidth(null)+2*extraSize,tempImage.getHeight(null)+2*extraSize,TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(tempImage,extraSize,extraSize,null);
        if (health > 1) {
            Color color = getHealthColor();
            g2d.setColor(color);
            g2d.drawOval(0,0, image.getWidth(), image.getHeight());
            g2d.setPaint(new Color(color.getRed(),color.getGreen(),color.getBlue(),50));

            g2d.fillOval(0,0, image.getWidth(), image.getHeight());
        }
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
        points[0][0] = extraSize+getPosX()+drawable.getWidth()/2;
        points[0][1] = extraSize+getPosY()+drawable.getHeight()/2;
        //top
        points[1][0] = extraSize+getPosX()+drawable.getWidth()/2;
        points[1][1] = extraSize+getPosY();
        //left
        points[2][0] = extraSize+getPosX();
        points[2][1] = extraSize+getPosY()+drawable.getHeight()/2;
        //bottom
        points[3][0] = extraSize+getPosX()+drawable.getWidth()/2;
        points[3][1] = extraSize+getPosY()+drawable.getHeight();
        //right
        points[4][0] = extraSize+getPosX()+drawable.getWidth();
        points[4][1] = extraSize+getPosY()+drawable.getHeight()/2;
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

    private static final double damageBufferAmount = 0.75;
    private double damageBuffer;

    private void takeDamage(int amount) {
        if (damageBuffer <= 0) {
            damageBuffer = damageBufferAmount;
            health -= amount;
        }
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
            testTile[i] = TileUtils.getTileFromColor(pixColor);

            if (testTile[i] == Tile.goo) {
                onGoo = true;
            } else if (testTile[i] == Tile.ice) {
                onIce = true;
            }
        }

        if (isWeakness(testTile[0])) {
            takeDamage(2);
        } else if (isWeakness(testTile[1])) {
            takeDamage(1);
            dir = new Vector2d(0,1);
        } else if (isWeakness(testTile[2])) {
            takeDamage(1);
            dir = new Vector2d(1,0);
        } else if (isWeakness(testTile[3])) {
            takeDamage(1);
            dir = new Vector2d(0,-1);
        } else if (isWeakness(testTile[4])) {
            takeDamage(1);
            dir = new Vector2d(-1,0);
        }
        if ((oldDir.x != 0 || oldDir.y != 0) && onIce) {
            dir = oldDir;
        }
        if (testTile[1] == Tile.wall && dir.y < 0) { // top
            dir.y = 0;
        }
        if (testTile[2] == Tile.wall && dir.x < 0) { // left
            dir.x = 0;
        }
        if (testTile[3] == Tile.wall && dir.y > 0) { // bottom
            dir.y = 0;
        }
        if (testTile[4] == Tile.wall && dir.x > 0) { // right
            dir.x = 0;
        }


        if (dir.x != 0 || dir.y != 0) {
            double malt = speed*deltaTime;
            if (onGoo) {
                malt *= TileUtils.gooMalt;
            }
            if (onIce) {
                malt *= TileUtils.iceMalt;
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

        if (damageBuffer > 0 ) {
            damageBuffer -= deltaTime;
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public void place(Layer base, double deltaTime) {
        Vector2d vector = new Vector2d(getPosX()+extraSize,getPosY()+extraSize);

        if (placer != null) {
            placer.place(base, vector, drawable.getPlaceImage(), deltaTime);
        }
    }
}
