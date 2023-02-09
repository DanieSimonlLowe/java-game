package entities.ai;

import entities.Entity;
import entities.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

public class RunAi implements EntityController {

    private final Entity from;
    private final double runDistSquare;
    EntityController outOfRange;

    public RunAi(Entity from, double runDist, EntityController outOfRange) {
        this.from = from;
        this.runDistSquare = runDist * runDist;
        this.outOfRange = outOfRange;
    }

    @Override
    public Tuple2d getDirection(Vector2d position, double deltaTime) {
        Vector2d dir = from.getCenter();
        dir.scaleAdd(-1, position);

        if (dir.lengthSquared() <= runDistSquare) {
            dir.normalize();
            return dir;
        } else {
            return outOfRange.getDirection(position, deltaTime);
        }

    }
}