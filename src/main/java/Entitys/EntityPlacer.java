package Entitys;

import Display.Layer;

import javax.vecmath.Vector2d;
import java.awt.*;

public interface EntityPlacer { ;

    void place(Layer base, Vector2d position, Image image);
}
