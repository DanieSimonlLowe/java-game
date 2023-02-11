package entities.ai;

import entities.Entity;
import entities.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

public class RamAi implements EntityController {

    private final double ramDelay;
    private final double backUpTime;
    private final Entity ramming;
    private final double backUpSpeed;
    static private final double minRamTime = 1;

    private double time;


    public RamAi(double ramDelay, double backUpTime, double backUpSpeed, Entity ramming) {
        this.ramDelay = ramDelay;
        this.backUpTime = ramDelay+backUpTime;
        this.ramming = ramming;
        this.backUpSpeed = backUpSpeed;
        time = 0;
    }

    private Entity entity;

    public void addSelf(Entity entity) {
        this.entity = entity;
    }

    private Vector2d rammingDir;

    @Override
    public Tuple2d getDirection(Vector2d position, double deltaTime) {
        if (time < ramDelay) {
            time += deltaTime;
            //return controller.getDirection(position,deltaTime);
            return new Vector2d(0,0);
        } else if (time < backUpTime) {
            time += deltaTime;
            Vector2d dir = (Vector2d)position.clone();
            dir.scaleAdd(-1,ramming.getCenter());
            dir.normalize();
            rammingDir = (Vector2d) dir.clone();

            dir.scale(-backUpSpeed);

            return dir;
        } else {

            time += deltaTime;
            if (entity.touchingWall && backUpTime+minRamTime < time) {
                time = 0;
            }

            return rammingDir;
        }
    }
}
