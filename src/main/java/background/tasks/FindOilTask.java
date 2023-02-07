package background.tasks;

import background.Base;
import background.TileUtils;

import java.awt.image.WritableRaster;

public class FindOilTask extends QueuedTask {

    public enum Direction {
        up,
        down,
        left,
        right
    }

    private final Direction direction;

    public FindOilTask(int x, int y, int proirty, Direction direction) {
        super(x, y, proirty);
        this.direction = direction;
    }

    @Override
    public boolean run(Base base, WritableRaster outRaster) {
        int[] pixel = outRaster.getPixel(x, y, (int[]) null);

        if (pixel[0] == TileUtils.oilColor.getRed() && pixel[1] == TileUtils.oilColor.getGreen() && pixel[2] == TileUtils.oilColor.getBlue()) {
            base.oilTick(x,y,outRaster);
            if (direction == Direction.up && y+Base.oilLightSize*2 < base.getHeight()) {
                base.watching.add(new FindOilTask(x,y+Base.oilLightSize*2,800,direction));
            } else if (direction == Direction.down && y-Base.oilLightSize*2 >= 0) {
                base.watching.add(new FindOilTask(x,y-Base.oilLightSize*2,800,direction));
            } else if (direction == Direction.left && x-Base.oilLightSize*2 >= 0) {
                base.watching.add(new FindOilTask(x-Base.oilLightSize*2,y,800,direction));
            } else if (x-Base.oilLightSize*2 <= base.getWidth()) {
                base.watching.add(new FindOilTask(x+Base.oilLightSize*2,y,800,direction));
            }

            return true;
        } else {
            return false;
        }
    }


}
