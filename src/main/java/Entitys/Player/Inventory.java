package Entitys.Player;

import Background.Tile;

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
        System.out.println("up2");
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
                selected = tilePos.length;
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
    


}
