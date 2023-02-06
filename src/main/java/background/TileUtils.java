package background;

import java.awt.*;

public class TileUtils {
    static final public Color iceColor = new Color(160,160,255);
    static final public Color gooColor = new Color(100,255,50);
    static final public Color wallColor = new Color(200,200,200);
    static final public Color fireColor = new Color(255,110,40);
    static final public Color oilColor = new Color(74,60,60);


    static final public double gooMalt = 0.2;
    static final public double iceMalt = 2.5;

    static public Color getTileColor(Tile tile) {
        switch (tile) {
            case goo:
                return gooColor;
            case ice:
                return iceColor;
            case wall:
                return wallColor;
            case fire:
                return fireColor;
            case oil:
                return oilColor;
            default:
                throw new RuntimeException("invalid tile color.");
        }
    }

    static public Tile getTileFromColor(Color color) {
        if (gooColor.equals(color)) {
            return Tile.goo;
        } else if (iceColor.equals(color)) {
            return Tile.ice;
        } else if (wallColor.equals(color)) {
            return Tile.wall;
        } else if (fireColor.equals(color)) {
            return Tile.fire;
        } else if (oilColor.equals(color)) {
            return Tile.oil;
        } else {
            return Tile.none;
        }
    }
}
