package entities.player;

import display.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class PlayerDrawable implements Drawable {

    private static final ImageIcon topIcon = new ImageIcon("src/main/resources/Images/playerTop.png");
    private static final ImageIcon bottomIcon = new ImageIcon("src/main/resources/Images/playerBottom.png");
    private final Inventory inventory;

    public PlayerDrawable(Inventory inventory) {
        this.inventory = inventory;
    }

    private BufferedImage bufferedImage;


    @Override
    public Image draw(double deltaTime) {
        if (!inventory.getHasChanged()) {
            return bufferedImage;
        }

        bufferedImage = new BufferedImage(getWidth(),getHeight(),TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(bottomIcon.getImage(),0,0,null);
        //g2d.dispose();
        inventory.drawTo(bufferedImage);
        g2d.drawImage(topIcon.getImage(),0,0,null);


        return bufferedImage;
    }

    @Override
    public boolean needsRedraw() {
        return inventory.getHasChanged();
    }

    @Override
    public Image getPlaceImage() {
        return bottomIcon.getImage();
    }

    @Override
    public int getWidth() {
        return bottomIcon.getIconWidth();
    }

    @Override
    public int getHeight() {
        return bottomIcon.getIconHeight();
    }
}
