package entities.ai;

import entities.Entity;
import entities.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

public class ChaseAi implements EntityController {

    private final Entity chasing;

    public ChaseAi(Entity chasing) {
        this.chasing = chasing;
    }

    @Override
    public Tuple2d getDirection(Vector2d position) {
        Vector2d dir = (Vector2d)position.clone();
        dir.scaleAdd(-1,chasing.getPosition());
        dir.normalize();
        return dir;
    }
}
