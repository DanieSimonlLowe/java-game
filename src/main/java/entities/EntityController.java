package entities;

import javax.vecmath.*;
public interface EntityController {
    Tuple2d getDirection(Vector2d position, double deltaTime);
}
