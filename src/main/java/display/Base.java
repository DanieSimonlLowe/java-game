package display;

import background.TileUtils;

import java.awt.image.WritableRaster;
import java.util.*;

public class Base extends Layer{

    Base (int width, int height) {
        super(width,height);
        watching = new PriorityQueue<>();
    }

    static final int pixelsChecked = 50;
    static final int maxWatch = 100;
    PriorityQueue<queuedPoint> watching;

    private void addToWatching(int x, int y, int proirty) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            return;
        }
        watching.add(new queuedPoint(x,y,proirty));
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

    private void lightOil(int x, int y, WritableRaster outRaster) {
        lightOilSub(x,y,outRaster,0,-1,1,-1,1);
    }

    private void lightOilSub(int x, int y, WritableRaster outRaster, int deapth, int minx, int maxx, int miny, int maxy) {
        final int[] fire = {TileUtils.fireColor.getRed(),TileUtils.fireColor.getGreen(),TileUtils.fireColor.getBlue(),255};
        outRaster.setPixel(x,y,fire);
        System.out.println("X:" + x + "Y:" + y + " ,D:" + deapth);
        for (int i = minx; i <= maxx; i++) {
            if ( i + x < 0 | i + x >= getWidth()) {
                continue;
            }
            for (int j = miny; j <= maxy; j++) {
                if ( j + y < 0 | j + y >= getHeight() || (i == 0 && j == 0)) {
                    continue;
                }
                System.out.println("i:" + i + "j:" + j);
                int[] testPixel = outRaster.getPixel(i+x, j+y, (int[]) null);
                if (testPixel[0] == TileUtils.oilColor.getRed() && testPixel[1] == TileUtils.oilColor.getGreen() && testPixel[2] == TileUtils.oilColor.getBlue()) {
                    int maxOilDeapth = 10;
                    if (deapth == maxOilDeapth) {
                        addToWatching(i+x,j+y,10);
                        return;
                    } else {
                        int ominx = minx, omaxx = maxx, ominy = miny, omaxy = maxy;
                        if (i > 0) {
                            ominx = 1;
                        } else if (i < 0) {
                            omaxx = -1;
                        }
                        if (j > 0) {
                            ominy = 1;
                        } else if (j < 0) {
                            omaxy = -1;
                        }
                        lightOilSub(i+x,j+y,outRaster, deapth+1,ominx,omaxx,ominy,omaxy);
                    }

                }
            }
        }
    }

    private void tickPixel(int x, int y, WritableRaster outRaster) {
        int[] pixel = outRaster.getPixel(x, y, (int[]) null);

        if (pixel[0] == TileUtils.fireColor.getRed() && pixel[1] == TileUtils.fireColor.getGreen() && pixel[2] == TileUtils.fireColor.getBlue()) {
            int[] out = {0,0,0,0};
            outRaster.setPixel(x,y,out);
            addNextToWatching(x,y,100);



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
                        lightOil(x+i,y+j,outRaster);
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

        for (int i = 0; i<maxWatch; i++) {

            queuedPoint point = watching.poll();
            if (point == null) {
                break;
            }
            int x = point.x;
            int y = point.y;

            tickPixel(x,y,outRaster);

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