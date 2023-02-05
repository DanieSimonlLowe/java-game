package display;

import java.awt.*;

public interface Drawable {
    Image draw(double deltaTime);

    Image getPlaceImage();
    int getWidth();
    int getHeight();
}
