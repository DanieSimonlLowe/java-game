package Entitys;

import javax.vecmath.Vector2d;
import Background.Tile;
import Entitys.Player.Inventory;
import Entitys.Player.PlayerController;
import Entitys.Player.PlayerDrawable;
import Entitys.Player.PlayerPlacer;

public class EntityFactory {

    static public Entity makePlayer(Vector2d position) {
        Tile[] weaknesses = {Tile.fire};
        Inventory inventory = new Inventory();
        inventory.addTile(Tile.ice, 10);
        inventory.addTile(Tile.goo, 10);
        inventory.addTile(Tile.oil, 20);

        return new Entity(weaknesses,5,100,position,new PlayerController(inventory),new PlayerPlacer(inventory),new PlayerDrawable(inventory));
    }
}
