package background.tasks;

import background.Base;

import java.awt.image.WritableRaster;

public class OilTask extends QueuedTask {


    public OilTask(int x, int y, int proirty) {
        super(x, y, proirty);
    }

    @Override
    public boolean run(Base base, WritableRaster raster) {
        return base.lightOilTask(x,y,raster);
    }
}