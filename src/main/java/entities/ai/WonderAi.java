package entities.ai;

import background.Base;
import entities.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

public class WonderAi implements EntityController {
    private Vector2d point;

    final private double distWander;


    public WonderAi(double distWander, Vector2d wanderNear) {
        this.distWander = distWander;
        generatePoint(wanderNear);
    }

    private void generatePoint(Vector2d position) {
        double x = position.getX() - distWander + Base.random.nextDouble() * distWander * 2;
        double y = position.getY() - distWander + Base.random.nextDouble() * distWander * 2;
        System.out.println("x:" + x +"," + y);
        point = new Vector2d(x,y);
    }

    @Override
    public Tuple2d getDirection(Vector2d position) {

        Vector2d dir = (Vector2d)position.clone();
        dir.scaleAdd(-1,point);
        if (dir.lengthSquared() < 50) {
            generatePoint(position);
        }
        dir.normalize();
        return dir;
    }
}
