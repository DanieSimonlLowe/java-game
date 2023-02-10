package background;

import entities.Entity;
import entities.EntityFactory;
import items.HealthItem;
import items.Item;
import items.TileItem;

import javax.vecmath.Vector2d;
import java.util.*;

public class RoomFactory {
    private static final int borderSize = 20;
    private static final int extraBorderSize = 30;

    static private void drawBorder(Base base) {
        base.drawRect(Tile.wall,0,0,base.getWidth()-1,borderSize);
        base.drawRect(Tile.wall,0,base.getHeight()-borderSize-1,base.getWidth()-1,borderSize);
        base.drawRect(Tile.wall,0,0,borderSize,base.getHeight()-1);
        base.drawRect(Tile.wall,base.getWidth()-borderSize-1,0,borderSize,base.getHeight()-1);
    }

    static private void addItems(List<Item> items, Base base, int count) {
        final int itemWidth = 20;
        final int itemHeight = 30;
        for (int i = 0; i < count; i++) {
            int x, y;
            do {
                x = borderSize + extraBorderSize + Base.random.nextInt(base.getWidth() - 2 * (borderSize + extraBorderSize));
                y = borderSize + extraBorderSize + Base.random.nextInt(base.getHeight() - 2 * (borderSize + extraBorderSize));
            } while (!base.isClear(x,y,itemWidth,itemHeight));
            int type = Base.random.nextInt(5);
            if (type == 0) {
                double amount = Base.random.nextDouble()*10+5;
                items.add(new TileItem(amount,Tile.oil,new Vector2d(x,y)));
            } else if (type == 1) {
                double amount = Base.random.nextDouble()*8+4;
                items.add(new TileItem(amount,Tile.goo,new Vector2d(x,y)));
            } else if (type == 2) {
                double amount = Base.random.nextDouble()*6+2;
                items.add(new TileItem(amount,Tile.ice,new Vector2d(x,y)));
            } else {
                int amount = Base.random.nextInt(5)+2;
                items.add(new HealthItem(new Vector2d(x,y),amount));
            }
        }
    }

    static private RoomTick createPillarRoom(Base base, Entity player, List<Entity> entities, List<Item> items, int difficulty) {
        player.resetOnExit();
        base.clear();

        drawBorder(base);

        final int pilerWidth = 200;
        base.drawRect(Tile.wall,(base.getWidth()-pilerWidth)/2,(base.getHeight()-pilerWidth)/2,pilerWidth,pilerWidth);

        player.getPosition().set(borderSize+10,borderSize+10);

        final int wallWidth = 40;
        final int holeWidthMin = 55;
        final int holeWidthVar = 60;
        int filledWall = Base.random.nextInt(5);
        if (Base.random.nextBoolean()) {
            int holeWidth = holeWidthMin + Base.random.nextInt(holeWidthVar);
            base.drawRect(Tile.wall,(base.getWidth())/2-wallWidth/2,(base.getHeight())/2,wallWidth,(base.getHeight())/2);
            if (filledWall != 0) {
                int pos = Base.random.nextInt(base.getHeight()/2-borderSize-holeWidth-pilerWidth) + pilerWidth + (base.getHeight()/2);
                base.drawRect(Tile.none,(base.getWidth()-wallWidth)/2,pos,wallWidth,holeWidth);
            }

        }
        if (Base.random.nextBoolean()) {
            int holeWidth = holeWidthMin + Base.random.nextInt(holeWidthVar);
            base.drawRect(Tile.wall,(base.getWidth())/2-wallWidth/2,0,wallWidth,(base.getHeight())/2);
            if (filledWall != 1) {
                int pos = Base.random.nextInt(base.getHeight() / 2 - holeWidth - pilerWidth) + borderSize;
                base.drawRect(Tile.none, (base.getWidth() - wallWidth) / 2, pos, wallWidth, holeWidth);
            }
        }
        if (Base.random.nextBoolean()) {
            int holeWidth = holeWidthMin + Base.random.nextInt(holeWidthVar);
            base.drawRect(Tile.wall,(base.getWidth())/2,(base.getHeight()-wallWidth)/2,(base.getWidth())/2,wallWidth);
            if (filledWall != 2) {
                int pos = Base.random.nextInt(base.getWidth() / 2 -borderSize-holeWidth-pilerWidth) + pilerWidth + (base.getWidth()/2);
                base.drawRect(Tile.none, pos, (base.getHeight() - wallWidth) / 2, holeWidth, wallWidth);
            }
        }
        if (Base.random.nextBoolean()) {
            int holeWidth = 55 + Base.random.nextInt(holeWidthVar);
            base.drawRect(Tile.wall,0,(base.getHeight()-wallWidth)/2,(base.getWidth())/2,wallWidth);
            if (filledWall != 3) {
                int pos = Base.random.nextInt(base.getWidth() / 2 - pilerWidth - holeWidth) + borderSize;
                base.drawRect(Tile.none, pos, (base.getHeight() - wallWidth) / 2, holeWidth, wallWidth);
            }
        }


        final int spawnArea = 280;
        int side = Base.random.nextInt(3);
        int count =  Base.random.nextInt(difficulty) + 1;
        for (int i = 0; i < count; i++) {
            int x = base.getWidth() - borderSize -extraBorderSize - Base.random.nextInt(spawnArea);
            int y = Base.random.nextInt(spawnArea) + borderSize + extraBorderSize;
            if (side == 0) {
                entities.add(EntityFactory.makeGhost(new Vector2d(x,y),player));
            } else {
                entities.add(EntityFactory.makeChaser(new Vector2d(x,y),player));
            }

        }
        count =  Base.random.nextInt(difficulty) + 1;
        for (int i = 0; i < count; i++) {
            int x = Base.random.nextInt(spawnArea) + borderSize+ extraBorderSize;
            int y =  base.getHeight()-borderSize- extraBorderSize - Base.random.nextInt(spawnArea);
            if (side == 1) {
                entities.add(EntityFactory.makeGhost(new Vector2d(x,y),player));
            } else {
                entities.add(EntityFactory.makeChaser(new Vector2d(x,y),player));
            }
        }

        count =  Base.random.nextInt(difficulty) + 1;
        for (int i = 0; i < count; i++) {
            int x = base.getWidth()  - borderSize - extraBorderSize - Base.random.nextInt(spawnArea);
            int y =  base.getHeight()- borderSize - extraBorderSize - Base.random.nextInt(spawnArea);
            if (side == 2) {
                entities.add(EntityFactory.makeGhost(new Vector2d(x,y),player));
            } else {
                entities.add(EntityFactory.makeChaser(new Vector2d(x,y),player));
            }
        }



        return () -> {
            if (entities.size() <= 1) {
                base.drawRect(Tile.exit,(base.getWidth()-pilerWidth)/2,(base.getHeight()-pilerWidth)/2,pilerWidth,pilerWidth);
            }
        };
    }

    private static boolean mazeHasNabors(boolean[][] grid,int blockCountW, int blockCountH, int x, int y) {
        if (x > 1) {
            if (!grid[x-1][y]) {
                return true;
            }
        }
        if (x+1 < blockCountW) {
            if (!grid[x+1][y]) {
                return true;
            }
        }
        if (y > 1) {
            if (!grid[x][y-1]) {
                return true;
            }
        }
        if (y+1 < blockCountH) {
            return !grid[x][y + 1];
        }
        return false;
    }

    static private RoomTick createMazeRoom(Base base, Entity player, List<Entity> entities, List<Item> items, int difficulty) {
        player.resetOnExit();

        player.getPosition().set(borderSize+10,borderSize+10);

        base.drawRect(Tile.wall,0,0,base.getWidth(),base.getHeight());


        final int sectionSize = 70;
        final int wallSize = 30;
        final int sumSize = wallSize + sectionSize;
        final int blockCountH = (base.getHeight()-2*borderSize)/sumSize;
        final int blockCountW = (base.getWidth()-2*borderSize)/sumSize;

        boolean[][] grid = new boolean[blockCountW][blockCountH];
        for (int i = 0; i<blockCountW; i++) {
            for (int j = 0; j<blockCountH; j++) {
                grid[i][j] = false;
            }
        }

        int x = Base.random.nextInt(blockCountW-1)+1;
        int y = Base.random.nextInt(blockCountH-1)+1;
        int num = 0;
        grid[x][y] = true;
        while (true) {
            num++;
            base.drawRect(Tile.none, sumSize*x+borderSize, sumSize*y+borderSize, sectionSize, sectionSize);
            while (mazeHasNabors(grid,blockCountW,blockCountH,x,y)) {
                int dir, oldX = x, oldY = y;
                do {
                    dir = Base.random.nextInt(4);
                    if (dir == 0) {
                        x = oldX + 1;
                        y = oldY;
                    } else if (dir == 1) {
                        x = oldX;
                        y = oldY + 1;
                    } else if (dir == 2) {
                        x = oldX - 1;
                        y = oldY;
                    } else {
                        x = oldX;
                        y = oldY - 1;
                    }
                } while (( x >= blockCountW)
                        || ( y >= blockCountH)
                        || ( x < 0)
                        || ( y < 0));
                if (grid[x][y]) {
                    x = oldX;
                    y = oldY;
                    continue;
                }
                if (dir == 0) {
                    base.drawRect(Tile.none, sumSize * oldX + borderSize, sumSize * oldY + borderSize, sumSize, sectionSize);
                } else if (dir == 1) {
                    base.drawRect(Tile.none, sumSize * oldX + borderSize, sumSize * oldY + borderSize, sectionSize, sumSize);
                } else if (dir == 2) {
                    base.drawRect(Tile.none, sumSize * x + borderSize, sumSize * oldY + borderSize, sumSize, sectionSize);
                } else {
                    base.drawRect(Tile.none, sumSize * oldX + borderSize, sumSize * y + borderSize, sectionSize, sumSize);
                }
                base.drawRect(Tile.none, sumSize*x+borderSize, sumSize*y+borderSize, sectionSize, sectionSize);
                grid[x][y] = true;
            }


            boolean noEmpty = true;
            for (int i = 0; i<blockCountW; i++) {
                for (int j = 0; j<blockCountH; j++) {
                    if (!grid[i][j]) {
                        noEmpty = false;
                        break;
                    }
                }
                if (!noEmpty) {
                    break;
                }
            }
            if (noEmpty || num > 1000) {
                break;
            }

            do {
                x = Base.random.nextInt(blockCountW);
                y = Base.random.nextInt(blockCountH);
            } while (!grid[x][y]);

        }

        for (int i = 0; i < blockCountW; i++) {
            for (int j = 0; j < blockCountH; j++) {
                if (!grid[i][j]) {
                    base.drawRect(Tile.none, sumSize * i + borderSize, sumSize * j + borderSize, sumSize, sumSize);
                }
            }
        }
        int count = Base.random.nextInt(10-difficulty);
        for (int i = 0; i < count; i++) {
            x = Base.random.nextInt(blockCountW-1)+1;
            y = Base.random.nextInt(blockCountH-1)+1;
            base.drawRect(Tile.none, sumSize * x + borderSize - wallSize, sumSize * y + borderSize - wallSize, sumSize+wallSize, sumSize+wallSize);
        }

        base.drawRect(Tile.exit, sumSize * (blockCountW-1) + borderSize, sumSize * (blockCountH-1) + borderSize , sumSize, sumSize);

        entities.add(EntityFactory.makeFire(new Vector2d(borderSize,base.getHeight() - borderSize),player));
        entities.add(EntityFactory.makeFire(new Vector2d(base.getWidth() - borderSize, borderSize),player));


        return () -> {};
    }

    static public RoomTick createRoom(Base base, Entity player, List<Entity> entities, List<Item> items, int difficulty) {


        RoomTick rect;
        int type = Base.random.nextInt(2);
        if (type == 0) {
            rect = createPillarRoom(base,player,entities,items,difficulty);
        } else {
            rect = createMazeRoom(base,player,entities,items,difficulty);
        }
        items.clear();
        int count = (2 + Base.random.nextInt(3));
        addItems(items,base,count);

        return rect;

    }

}
