package Entitys.Player;

import Background.TileUtills;
import Display.Layer;
import Entitys.EntityPlacer;

import javax.vecmath.Vector2d;
import java.awt.*;

public class PlayerPlacer implements EntityPlacer {
    static boolean isPlaceing;
    //boolean ;

    @Override
    public void place(Layer base, Vector2d position, Image image) {
        if (isPlaceing) {
            base.drawColorImage(position, TileUtills.iceColor, image);
        }
    }
}
