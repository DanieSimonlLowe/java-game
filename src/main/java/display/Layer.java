package display;

import background.TileUtils;
import entitys.Entity;
import items.Item;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class Layer {
    protected BufferedImage image;
    private final int width;
    private final int height;

    private final Graphics2D g2d;


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Layer(int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height,TYPE_INT_ARGB);

        
        g2d = (Graphics2D)image.getGraphics();
        clear();


    }

    void clear() {
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0,0,width,height);
        g2d.setComposite(AlphaComposite.SrcOver);
    }

    public void drawImage(Image image, int x, int y) {
        g2d.drawImage(image,x,y,null);
    }

    void drawEntity(Entity entity, double deltaTime) {
        drawImage(entity.getImage(deltaTime),entity.getPosX(),entity.getPosY());
    }


    void drawItem(Item item) {
        drawImage(item.getImage(),item.getPosX(),item.getPosY());
    }

    public void drawColorImage(Vector2d position, Color color, Image image) {

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage colorImage = new BufferedImage(width,height, TYPE_INT_ARGB);
        Graphics2D g2 = colorImage.createGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();

        WritableRaster outRaster = this.image.getRaster();
        Raster inRaster = colorImage.getRaster();

        int screenX = (int) position.getX();
        int screenY = (int) position.getY();

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

    public void drawRect(background.Tile tile, int x, int y, int width, int height) {
        g2d.setColor(TileUtils.getTileColor(tile));
        g2d.fillRect(x,y,width,height);
    }

    public Color getColor(int x, int y) {

        int clr = image.getRGB(x,y);
        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        return new Color(red,green,blue);
    }



    protected void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image,0,0,null);
    }



}
