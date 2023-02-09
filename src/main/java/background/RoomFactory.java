package background;

import entities.Entity;
import entities.EntityFactory;
import items.Item;

import javax.vecmath.Vector2d;
import java.util.List;

public class RoomFactory {
    private static final int borderSize = 20;
    private static final int extraBorderSize = 30;

    static public RoomEnd createBasicRoom(Base base, Entity player, List<Entity> entities, List<Item> items) {
        player.resetOnExit();
        base.clear();

        base.drawRect(Tile.wall,0,0,base.getWidth()-1,borderSize);
        base.drawRect(Tile.wall,0,base.getHeight()-borderSize-1,base.getWidth()-1,borderSize);
        base.drawRect(Tile.wall,0,0,borderSize,base.getHeight()-1);
        base.drawRect(Tile.wall,base.getWidth()-borderSize-1,0,borderSize,base.getHeight()-1);

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
        int count =  Base.random.nextInt(10) + 1;
        for (int i = 0; i < count; i++) {
            int x = base.getWidth() - borderSize -extraBorderSize - Base.random.nextInt(spawnArea);
            int y = Base.random.nextInt(spawnArea) + borderSize + extraBorderSize;
            if (side == 0) {
                entities.add(EntityFactory.makeGhost(new Vector2d(x,y),player));
            } else {
                entities.add(EntityFactory.makeChaser(new Vector2d(x,y),player));
            }

        }
        count =  Base.random.nextInt(10) + 1;
        for (int i = 0; i < count; i++) {
            int x = Base.random.nextInt(spawnArea) + borderSize+ extraBorderSize;
            int y =  base.getHeight()-borderSize- extraBorderSize - Base.random.nextInt(spawnArea);
            if (side == 1) {
                entities.add(EntityFactory.makeGhost(new Vector2d(x,y),player));
            } else {
                entities.add(EntityFactory.makeChaser(new Vector2d(x,y),player));
            }
        }

        count =  Base.random.nextInt(10) + 1;
        for (int i = 0; i < count; i++) {
            int x = base.getWidth()  - borderSize - extraBorderSize - Base.random.nextInt(spawnArea);
            int y =  base.getHeight()- borderSize - extraBorderSize - Base.random.nextInt(spawnArea);
            if (side == 2) {
                entities.add(EntityFactory.makeGhost(new Vector2d(x,y),player));
            } else {
                entities.add(EntityFactory.makeChaser(new Vector2d(x,y),player));
            }
        }


        count = 2 + Base.random.nextInt(3);
        for (int i = 0; i < count; i++) {
            int x, y;
            do {
                x = borderSize + extraBorderSize + Base.random.nextInt(base.getWidth() - 2 * (borderSize + extraBorderSize));
                y = borderSize + extraBorderSize + Base.random.nextInt(base.getHeight() - 2 * (borderSize + extraBorderSize));
            } while (base.getTile(x,y) != Tile.none || base.getTile(x+extraBorderSize,x+extraBorderSize) != Tile.none);
            int type = Base.random.nextInt(3);
            if (type == 0) {
                double amount = Base.random.nextDouble()*10+5;
                items.add(new Item(amount,Tile.oil,new Vector2d(x,y)));
            } else if (type == 1) {
                double amount = Base.random.nextDouble()*8+4;
                items.add(new Item(amount,Tile.goo,new Vector2d(x,y)));
            } else {
                double amount = Base.random.nextDouble()*6+2;
                items.add(new Item(amount,Tile.ice,new Vector2d(x,y)));
            }
        }

        return () -> {
            if (entities.size() <= 1) {
                base.drawRect(Tile.exit,(base.getWidth()-pilerWidth)/2,(base.getHeight()-pilerWidth)/2,pilerWidth,pilerWidth);
            }
        };
    }
}
