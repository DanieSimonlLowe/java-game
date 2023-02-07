package entitys;

import javax.swing.*;
import javax.vecmath.Vector2d;
import background.Tile;
import entitys.ai.ChaseAi;
import entitys.ai.ConstantPlacer;
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

    static public Entity makeChaser(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/chaser.png");
        Tile[] weaknesses = {Tile.wall};
        return new Entity(weaknesses,1,50,position,new ChaseAi(player),new ConstantPlacer(Tile.fire),new SimpleDrawable(icon.getImage()));
    }

}
