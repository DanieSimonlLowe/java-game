package background;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TileUtilsTest extends TestCase {


    public TileUtilsTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( TileUtilsTest.class );
    }

    public void testGetTileColor() {
        assertEquals(TileUtils.getTileColor(Tile.goo),TileUtils.gooColor);
        assertEquals(TileUtils.getTileColor(Tile.ice),TileUtils.iceColor);
        assertEquals(TileUtils.getTileColor(Tile.fire),TileUtils.fireColor);
        assertEquals(TileUtils.getTileColor(Tile.wall),TileUtils.wallColor);
        assertEquals(TileUtils.getTileColor(Tile.oil),TileUtils.oilColor);
    }

    public void testGetTileFromColor() {
        assertEquals(TileUtils.getTileFromColor(TileUtils.gooColor),Tile.goo);
        assertEquals(TileUtils.getTileFromColor(TileUtils.iceColor),Tile.ice);
        assertEquals(TileUtils.getTileFromColor(TileUtils.fireColor),Tile.fire);
        assertEquals(TileUtils.getTileFromColor(TileUtils.wallColor),Tile.wall);
        assertEquals(TileUtils.getTileFromColor(TileUtils.oilColor),Tile.oil);
    }
}
