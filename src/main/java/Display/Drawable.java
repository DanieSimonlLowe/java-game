package Display;

import java.awt.*;

public interface Drawable {
    public Image draw(double deltaTime);

    public Image getPlaceImage();
    public int getWidth();
    public int getHeight();
}
