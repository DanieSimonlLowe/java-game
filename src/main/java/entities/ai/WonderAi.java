package entities.ai;

import background.Base;
import entities.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

public class WonderAi implements EntityController {
    private Vector2d point;

    final private double distWander;

    final private static double maxTime = 5;
    private double timeSinceGen;


    public WonderAi(double distWander) {
        this.distWander = distWander;
    }

    private void generatePoint(Vector2d position) {
        double x = position.getX() - distWander + Base.random.nextDouble() * distWander * 2;
        double y = position.getY() - distWander + Base.random.nextDouble() * distWander * 2;
        point = new Vector2d(x,y);
        timeSinceGen = 0;
    }

    @Override
    public Tuple2d getDirection(Vector2d position, double deltaTime) {
        if (point == null || timeSinceGen > maxTime) {
            generatePoint(position);
        }
        timeSinceGen += deltaTime;
        Vector2d dir = (Vector2d)position.clone();
        dir.scaleAdd(-1,point);
        if (dir.lengthSquared() < 50) {
            generatePoint(position);
        }
        dir.normalize();
        return dir;
    }
}
