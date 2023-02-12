package entities;

import javax.swing.*;
import javax.vecmath.Vector2d;

import background.Base;
import background.Tile;
import entities.ai.*;
import entities.player.Inventory;
import entities.player.PlayerController;
import entities.player.PlayerDrawable;
import entities.player.PlayerPlacer;

public class EntityFactory {

    static public Entity makePlayer(Vector2d position) {
        Tile[] weaknesses = {Tile.fire, Tile.toxic};
        Inventory inventory = new Inventory();

        return new Entity(weaknesses,8,100,position,new PlayerController(inventory),new PlayerPlacer(inventory),new PlayerDrawable(inventory));
    }

    static public Entity makeChaser(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/chaser.png");
        Tile[] weaknesses = {Tile.toxic};
        return new Entity(weaknesses,1,75,position,new ChaseAi(player,45,new WonderAi(200),8),new ConstantPlacer(Tile.fire),new SimpleDrawable(icon.getImage()));
    }

    static public Entity makeGhost(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/ghost.png");
        Tile[] weaknesses = {Tile.fire};
        Entity entity = new Entity(weaknesses,1,50,position, new ChaseAi(player,45,new WonderAi(400),8),new ConstantPlacer(Tile.toxic),new SimpleDrawable(icon.getImage()));
        entity.setNotAffectedByWall();
        return entity;
    }


    static public Entity makeFire(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/fire.png");
        Tile[] weaknesses = {Tile.toxic};
        Entity entity = new Entity(weaknesses,1,65,position, new ChaseAi(player,45,new WonderAi(400),8),new ConstantPlacer(Tile.fire),new SimpleDrawable(icon.getImage()));
        entity.setNotAffectedByWall();
        return entity;
    }


    static public Entity makeRamer(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/ramer.png");
        Tile[] weaknesses = {Tile.toxic};
        RamAi ramAi = new RamAi(0.5,0.75,0.15,player);
        Entity entity = new Entity(weaknesses,1,300,position, ramAi,new ConstantPlacer(Tile.fire),new SimpleDrawable(icon.getImage()));
        ramAi.addSelf(entity);
        return entity;
    }

    static public Entity makePointyFire(Vector2d position, Entity player) {
        ImageIcon icon = new ImageIcon("src/main/resources/Images/entitys/ramer.png");
        Tile[] weaknesses = {Tile.toxic};
        RamAi ramAi = new RamAi(1,0.5,0.15,player);
        CollisionPlacer placer = new CollisionPlacer(Tile.fire,200);
        Entity entity = new Entity(weaknesses,1,300,position, ramAi,placer,new SimpleDrawable(icon.getImage()));
        ramAi.addSelf(entity);
        placer.addSelf(entity);
        entity.setNotAffectedByWall();
        return entity;
    }

    static public Entity makeSimpleFire(Vector2d position, Entity player) {
        int rand = Base.random.nextInt(2);
        if (rand == 0) {
            return makeRamer(position,player);
        } else {
            return makeChaser(position,player);
        }
    }

    static public Entity makeSimpleFireThoughWall(Vector2d position, Entity player) {
        int rand = Base.random.nextInt(2);
        if (rand == 0) {
            return makePointyFire(position,player);
        } else {
            return makeFire(position,player);
        }
    }
}
