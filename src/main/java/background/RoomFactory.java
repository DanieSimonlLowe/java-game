package background;

import entities.Entity;
import entities.EntityFactory;
import items.Item;

import javax.vecmath.Vector2d;
import java.util.List;

public class RoomFactory {
    private static final int borderSize = 20;

    static public void createBasicRoom(Base base, Entity player, List<Entity> entities, List<Item> items) {
        base.clear();

        base.drawRect(Tile.wall,0,0,base.getWidth()-1,borderSize);
        base.drawRect(Tile.wall,0,base.getHeight()-borderSize-1,base.getWidth()-1,borderSize);
        base.drawRect(Tile.wall,0,0,borderSize,base.getHeight()-1);
        base.drawRect(Tile.wall,base.getWidth()-borderSize-1,0,borderSize,base.getHeight()-1);

        final int pilerWidth = 240;
        base.drawRect(Tile.wall,(base.getWidth()-pilerWidth)/2,(base.getHeight()-pilerWidth)/2,pilerWidth,pilerWidth);

        player.getPosition().set(borderSize+10,borderSize+10);

        entities.add(EntityFactory.makeChaser(new Vector2d(base.getWidth()-borderSize-50,borderSize+50),player));
        entities.add(EntityFactory.makeChaser(new Vector2d(borderSize+50,base.getHeight()-borderSize-50),player));
        entities.add(EntityFactory.makeGhost(new Vector2d(base.getWidth()-borderSize-50,base.getHeight()-borderSize-50),player,base.getWidth(),base.getHeight()));
    }
}
