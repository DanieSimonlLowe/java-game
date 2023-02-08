package entities;

import display.Drawable;

import java.awt.*;

public class SimpleDrawable implements Drawable {
    private final Image image;
    private boolean drawen;

    SimpleDrawable(Image image) {
        this.image = image;
        drawen = true;
    }

    @Override
    public Image draw(double deltaTime) {
        return image;
    }

    @Override
    public Image getPlaceImage() {
        return image;
    }

    @Override
    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image.getHeight(null);
    }

    @Override
    public boolean needsRedraw() {
        if (drawen) {
            drawen = false;
            return true;
        } else {
            return false;
        }

    }
}
