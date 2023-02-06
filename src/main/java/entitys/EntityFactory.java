package entitys;

import javax.vecmath.Vector2d;
import background.Tile;
import entitys.player.Inventory;
import entitys.player.PlayerController;
import entitys.player.PlayerDrawable;
import entitys.player.PlayerPlacer;

public class EntityFactory {

    static public Entity makePlayer(Vector2d position) {
        Tile[] weaknesses = {Tile.fire};
        Inventory inventory = new Inventory();
        inventory.addTile(Tile.ice, 10);
        inventory.addTile(Tile.goo, 10);
        inventory.addTile(Tile.oil, 20);

        return new Entity(weaknesses,8,100,position,new PlayerController(inventory),new PlayerPlacer(inventory),new PlayerDrawable(inventory));
    }

    //TODO create
}
