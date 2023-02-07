package background;

import background.tasks.OilTask;
import background.tasks.PixelTask;
import background.tasks.QueuedTask;
import display.Layer;

import java.awt.image.WritableRaster;
import java.util.*;

public class Base extends Layer {

    public Base(int width, int height) {
        super(width,height);
        watching = new PriorityQueue<>();
    }

    static final int pixelsChecked = 50;
    static final int maxWatchTick = 100;
    PriorityQueue<QueuedTask> watching;

    private void addToWatching(int x, int y, int proirty) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            return;
        }
        watching.add(new PixelTask(x,y,proirty));
    }


    private void addNextToWatching(int x, int y,int proirty) {
        int temp = hash((x ^ y)) & 3;
        if (temp == 0) {
            addToWatching(x+1,y,proirty);
            addToWatching(x-1,y,proirty);
            addToWatching(x,y+1,proirty);
            addToWatching(x,y-1,proirty);
        } else if (temp == 1) {
            addToWatching(x-1,y,proirty);
            addToWatching(x,y+1,proirty);
            addToWatching(x,y-1,proirty);
            addToWatching(x+1,y,proirty);
        } else if (temp == 2) {
            addToWatching(x,y+1,proirty);
            addToWatching(x,y-1,proirty);
            addToWatching(x+1,y,proirty);
            addToWatching(x-1,y,proirty);
        } else {
            addToWatching(x,y-1,proirty);
            addToWatching(x+1,y,proirty);
            addToWatching(x-1,y,proirty);
            addToWatching(x,y+1,proirty);
        }

    }

    private void lightOilInit(int x, int y, WritableRaster outRaster) {
        lightOil(x,y,outRaster,0);
    }

    public boolean lightOilTask(int x, int y, WritableRaster outRaster) {

        int[] pixel = outRaster.getPixel(x, y, (int[]) null);
        if (pixel[0] == TileUtils.oilColor.getRed() && pixel[1] == TileUtils.oilColor.getGreen() && pixel[2] == TileUtils.oilColor.getBlue()) {
            lightOil(x,y,outRaster,0);
            return true;
        }
        return false;
    }

    private void lightOil(int x, int y, WritableRaster outRaster, int deapth) {
        final int[] fire = {TileUtils.fireColor.getRed(),TileUtils.fireColor.getGreen(),TileUtils.fireColor.getBlue(),255};

        outRaster.setPixel(x,y,fire);
        for (int i = -1; i <= 1; i++) {
            if ( i + x < 0 | i + x >= getWidth()) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {
                if ( j + y < 0 | j + y >= getHeight() || (i == 0 && j == 0)) {
                    continue;
                }
                int[] testPixel = outRaster.getPixel(i+x, j+y, (int[]) null);
                if (testPixel[0] == TileUtils.oilColor.getRed() && testPixel[1] == TileUtils.oilColor.getGreen() && testPixel[2] == TileUtils.oilColor.getBlue()) {

                    int maxOilDeapth = 5;
                    if (deapth == maxOilDeapth) {
                        //addToWatching(i+x,j+y,10);
                        watching.add(new OilTask(i+x,j+y,10));
                    } else {


                        lightOil(x+i,y+j,outRaster, deapth+1);
                    }

                }
            }
        }
    }

    public void tickPixel(int x, int y, WritableRaster outRaster) {
        int[] pixel = outRaster.getPixel(x, y, (int[]) null);

        if (pixel[0] == TileUtils.fireColor.getRed() && pixel[1] == TileUtils.fireColor.getGreen() && pixel[2] == TileUtils.fireColor.getBlue()) {
            int[] out = {0,0,0,0};
            outRaster.setPixel(x,y,out);
            //addNextToWatching(x,y,100);



        } else if (pixel[0] == TileUtils.oilColor.getRed() && pixel[1] == TileUtils.oilColor.getGreen() && pixel[2] == TileUtils.oilColor.getBlue()) {

            for (int i = -1; i <= 1; i++) {
                if (i+x < 0 | i+x >= getWidth()) {
                    continue;
                }
                for (int j = -1; j <= 1; j++) {
                    if (j+y < 0 | j+y >= getHeight()) {
                        continue;
                    }
                    int[] testPixel = outRaster.getPixel(i+x, j+y, (int[]) null);
                    if (testPixel[0] == TileUtils.fireColor.getRed() && testPixel[1] == TileUtils.fireColor.getGreen() && testPixel[2] == TileUtils.fireColor.getBlue()) {
                        lightOilInit(x+i,y+j,outRaster);
                        return;
                    }
                }
            }


        }
    }

    private int hash(int a) {
        a = (a ^ 61) ^ (a >> 16);
        a = a + (a << 3);
        a = a ^ (a >> 4);
        a = a * 0x27d4eb2d;
        a = a ^ (a >> 15);
        return a;
    }


    int tickBoardPos = 0;
    public void tickBoard() {

        WritableRaster outRaster = image.getRaster();

        for (int i = 0; i<maxWatchTick; ) {

            QueuedTask task = watching.poll();
            if (task == null) {
                break;
            }

            if (task.run(this,outRaster)) {
                i++;
            }

        }

        for (int i = 0; i<pixelsChecked; i++) {
            int pos = tickBoardPos + i;
            int x = hash(pos) % image.getWidth();

            int y = hash(~pos) % image.getHeight();


            if (x < 0) {
                x += image.getWidth();
            }
            if (y < 0) {
                y += image.getHeight();
            }
            tickPixel(x,y,outRaster );
        }
        tickBoardPos += pixelsChecked;


    }
}
