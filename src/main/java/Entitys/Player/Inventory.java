package Entitys.Player;

import Background.Tile;
import Background.TileUtills;
import Items.Item;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;

public class Inventory {
    static final double maxSpace = 30;
    static private final Tile[] tilePos = {Tile.ice,Tile.goo,Tile.oil};
    private int selected;
    private final double[] tileSpace;
    private double spaceUsed;

    protected boolean inUse;

    public Inventory() {
        tileSpace = new double[tilePos.length];
        spaceUsed = 0;
        inUse = false;
    }

    public void addTile(Tile tile, double amount) {
        if (spaceUsed + amount > maxSpace) {
            tileSpace[getTilePos(tile)] += maxSpace - spaceUsed;
            spaceUsed = maxSpace;
        } else {
            tileSpace[getTilePos(tile)] += amount;
            spaceUsed += amount;
        }
    }

    public boolean isPlacing() {
        return inUse && tileSpace[selected] != 0;
    }

    public void selectUp() {
        if (spaceUsed == 0) {
            return;
        }
        do {
            selected += 1;
            if (selected >= tilePos.length) {
                selected = 0;
            }
        } while (tileSpace[selected] == 0);
    }

    public void selectDown() {
        if (spaceUsed == 0) {
            return;
        }
        do {
            selected -= 1;
            if (selected < 0) {
                selected = tilePos.length-1;
            }
        } while (tileSpace[selected] == 0);
    }

    private int getTilePos(Tile tile) {
        for (int i = 0; i < tilePos.length; i++) {
            if (tile == tilePos[i]) {
                return i;
            }
        }
        throw new RuntimeException("could not find that tile.");
    }

    public Tile getCurrentTile() {
        return tilePos[selected];
    }

    public void useCurrent(double deltaTime) {
        spaceUsed -= deltaTime;
        tileSpace[selected] -= deltaTime;
        if (spaceUsed < 0) {
            spaceUsed = 0;
        }
        if (tileSpace[selected] < 0) {
            tileSpace[selected] = 0;
        }
    }

    static final private double circleConst = 1/Math.sqrt(2);
    private int getCircleX(int currHeight, int size) {
        return (int)Math.floor(circleConst*Math.sqrt( 2*currHeight*(size-currHeight) ));
    }

    static private final int circleRectNum = 20;
    protected void drawTo(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Graphics2D g2d = (Graphics2D)image.getGraphics();

        int sumY = height;
        int i = selected;
        do {
            if (tileSpace[i] <= maxSpace/circleRectNum) {
                i += 1;
                if (i >= tilePos.length) {
                    i = 0;
                }
                continue;
            }

            g2d.setColor(TileUtills.getTileColor(tilePos[i]));

            int amount = (int) Math.round(tileSpace[i]/maxSpace*height);
            while (amount > 0) {
                int h;
                if (amount > maxSpace/circleRectNum) {
                    h = height/circleRectNum;
                } else {
                    h = amount;
                }
                int valX = getCircleX(sumY,height);
                g2d.fillRect(width/2-valX,sumY,valX*2,h);
                amount -= h;
                sumY -= h;
            }

            if (sumY <= 1) {
                break;
            }

            i += 1;
            if (i >= tilePos.length) {
                i = 0;
            }
        } while (i != selected);
        g2d.dispose();
    }

    public void addItem(Item item) {
        addTile(item.getTile(),item.getAmount());
        item.destroy();
    }

    public void collect(List<Item> items, Vector2d position) {
        if (spaceUsed >= maxSpace) {
            return;
        }
        for (Item item: items) {
            if (item.shouldPickUp(position)) {
                addItem(item);
            }
        }
    }
}
