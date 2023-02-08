package entities;

import background.Base;

import javax.vecmath.Vector2d;
import java.awt.*;

public interface EntityPlacer {

    void place(Base base, Vector2d position, Image image, double deltaTime);
}
