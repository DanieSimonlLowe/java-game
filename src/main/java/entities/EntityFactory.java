package entities;

import javax.swing.*;
import javax.vecmath.Vector2d;

import background.Tile;
import entities.ai.ChaseAi;
import entities.ai.ConstantPlacer;
import entities.ai.RunAi;
import entities.ai.WonderAi;
import entities.player.Inventory;
import entities.player.PlayerController;
import entities.player.PlayerDrawable;
import entities.player.PlayerPlacer;

public class EntityFactory {

    static public Entity makePlayer(Vector2d position) {
        Tile[] weaknesses = {Tile.fire, Tile.toxic};
        Inventory inventory = new Inventory();
        inventory.addTile(Tile.ice, 10);
        inventory.addTile(Tile.goo, 10);
        inventory.addTile(Tile.oil, 20);

        return new Entity(weaknesses,8,100,position,new PlayerController(inventory),new PlayerPlacer(inventory),new PlayerDrawable(inventory));
    }

    static public Entity makeChaser(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/chaser.png");
        Tile[] weaknesses = {Tile.toxic};
        return new Entity(weaknesses,1,50,position,new ChaseAi(player),new ConstantPlacer(Tile.fire),new SimpleDrawable(icon.getImage()));
    }

    static public Entity makeGhost(Vector2d position, Entity player, double width, double height) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/ghost.png");
        Tile[] weaknesses = {Tile.fire};
        EntityController controller = new RunAi(player, 600,new WonderAi(200 , new Vector2d(width/2,height/2)));
        Entity entity = new Entity(weaknesses,1,50,position, controller,new ConstantPlacer(Tile.toxic),new SimpleDrawable(icon.getImage()));
        entity.setNotAffectedByWall();
        return entity;
    }

}
