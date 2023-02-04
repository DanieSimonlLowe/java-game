package Display;

import Entitys.Entity;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class Layer {
    private final BufferedImage image;
    private final int width;
    private final int height;

    private final Graphics2D g2d;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    Layer(int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height,TYPE_INT_ARGB);

        
        g2d = (Graphics2D)image.getGraphics();
        clear();


        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }

    void clear() {
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0,0,width,height);
        g2d.setComposite(AlphaComposite.Src);
    }

    void drawEntity(Entity entity) {
        g2d.drawImage(entity.getImage(),entity.getPosX(),entity.getPosY(),null);
    }

    public void drawColorImage(Vector2d postion, Color color, Image image) {
        //g2d.setColor(color);
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage colorImage = new BufferedImage(width,height, TYPE_INT_ARGB);
        Graphics2D g2 = colorImage.createGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();

        WritableRaster outRaster = this.image.getRaster();
        Raster inRaster = colorImage.getRaster();

        int screenX = (int) postion.getX();
        int screenY = (int) postion.getY();

        for (int ix = 0; ix < width; ix++) {
            for (int iy = 0; iy < height; iy++) {
                int[] pixels = inRaster.getPixel(ix, iy, (int[]) null);
                if (pixels[3] != 0) {
                    int[] out = {color.getRed()
                            ,color.getGreen()
                            ,color.getBlue()
                            ,255};
                    outRaster.setPixel(screenX+ix,screenY+iy,out);
                }
            }
        }

    }

    public Color getColor(int x, int y) {
        int clr = image.getRGB(x,y);
        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        return new Color(red,green,blue);
    }

    void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image,0,0,null);

    }
}
