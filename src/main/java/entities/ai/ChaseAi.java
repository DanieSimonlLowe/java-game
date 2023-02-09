package entities.ai;

import background.Base;
import entities.Entity;
import entities.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

public class ChaseAi implements EntityController {

    private final Entity chasing;

    Vector2d oldPosition;

    private final double distSqare;
    private double timeSinceOld;

    private final double wonderTime;

    private double timeWonder;

    private final EntityController otherController;

    public ChaseAi(Entity chasing, double distGotoOther, EntityController otherController, double wonderTime) {
        this.chasing = chasing;
        distSqare = distGotoOther*distGotoOther;
        this.otherController = otherController;
        this.wonderTime = wonderTime;
        timeWonder = 0;
    }

    @Override
    public Tuple2d getDirection(Vector2d position, double deltaTime) {
        if (oldPosition == null) {
            oldPosition = (Vector2d) position.clone();
            timeSinceOld = 0;
        }
        timeSinceOld += deltaTime;

        Vector2d dir = (Vector2d)position.clone();
        dir.scaleAdd(-1,chasing.getCenter());
        dir.normalize();

        if (timeWonder > 0) {
            timeWonder -= deltaTime;
            return otherController.getDirection(position,deltaTime);
        } else if (timeSinceOld > 2) {
            oldPosition.scaleAdd(-1,position);
            if (oldPosition.lengthSquared() <= distSqare) {
                timeWonder = wonderTime * Base.random.nextDouble();
            }
            oldPosition = (Vector2d) position.clone();
            timeSinceOld = 0;
        }



        return dir;
    }
}
