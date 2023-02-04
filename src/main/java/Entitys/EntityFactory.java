package Entitys;

import javax.swing.*;
import javax.vecmath.Vector2d;
import Background.Tiles;
import Entitys.Player.PlayerController;
import Entitys.Player.PlayerPlacer;

public class EntityFactory {

    static public Entity makePlayer(Vector2d position) {
        Tiles[] weaknesses = {};
        ImageIcon imageIcon = new ImageIcon("src/main/resources/player.png");
        Entity entity = new Entity(weaknesses,100,position,new PlayerController(),new PlayerPlacer(),imageIcon);
        return entity;
    }
}
