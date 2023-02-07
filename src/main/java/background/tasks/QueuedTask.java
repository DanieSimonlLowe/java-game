package background.tasks;

import background.Base;

import java.awt.image.WritableRaster;

public abstract class QueuedTask implements Comparable<QueuedTask> {
    final protected int x;
    final protected int y;

    final protected int proirty;




    protected QueuedTask(int x, int y, int proirty) {
        this.x = x;
        this.y = y;
        this.proirty = proirty;
    }



    abstract public boolean run(Base base, WritableRaster raster);

    @Override
    public int compareTo(QueuedTask o) {
        return this.proirty - o.proirty;
    }
}
