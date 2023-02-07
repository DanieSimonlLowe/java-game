package background.tasks;

import background.Base;

import java.awt.image.WritableRaster;

public class PixelTask extends QueuedTask {

    public PixelTask(int x, int y, int proirty) {
        super(x, y, proirty);
    }

    @Override
    public boolean run(Base base, WritableRaster raster) {
        base.tickPixel(x,y,raster);
        return true;
    }
}
