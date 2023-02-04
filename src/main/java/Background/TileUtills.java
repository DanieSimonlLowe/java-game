package Background;

import java.awt.*;

public class TileUtills {
    static final public Color iceColor = new Color(160,160,255);
    static final public Color gooColor = new Color(100,255,50);
    static final public Color wallColor = new Color(200,200,200);

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
        } else {
            return Tile.none;
        }
    }
}
