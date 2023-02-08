package entities;

import background.Base;
import background.Tile;
import display.Drawable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.swing.*;
import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;
import java.awt.*;


public class EntityTest extends TestCase {
    public EntityTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( EntityTest.class );
    }

    public void testInit() {
        testController controller = new testController(new Vector2d(0,0));
        testDrawable drawable =  new testDrawable();
        testPlacer placer = new testPlacer();
        Tile[] weaknesses = {Tile.fire};
        Vector2d position = new Vector2d(1,0);

        Entity entity1 = new Entity(weaknesses,5,10,position,drawable);
        Entity entity2 = new Entity(weaknesses,6,20,position,controller,drawable);
        Entity entity3 = new Entity(weaknesses,7,30,position,controller,placer,drawable);


        assertSame(entity1.getPosition(), position);
        assertSame(entity2.getPosition(), position);
        assertSame(entity3.getPosition(), position);

        assertNull(entity1.getController());
        assertSame(entity2.getController(), controller);
        assertSame(entity3.getController(), controller);

        assertEquals(entity1.getImage(0), new ImageIcon("src/main/resources/Images/items/goo.png").getImage());
        assertEquals(entity2.getImage(0), new ImageIcon("src/main/resources/Images/items/goo.png").getImage());
        assertEquals(entity3.getImage(0), new ImageIcon("src/main/resources/Images/items/goo.png").getImage());

        entity1.place(null,1);
        assertEquals(entity1.getPosition(), new Vector2d(1,0));
        entity2.place(null,1);
        assertEquals(entity2.getPosition(), new Vector2d(1,0));
        entity3.place(null,1);
        assertEquals(entity3.getPosition(), new Vector2d(1,1));
    }

    public void testMoveNoTiles() {
        testController controller = new testController(new Vector2d(0,0));
        testDrawable drawable =  new testDrawable();
        Tile[] weaknesses = {Tile.fire};
        Entity entity = new Entity(weaknesses,7,2,new Vector2d(10,10),controller,drawable);
        Base base = new Base(100,100);

        entity.move(0.5,base);
        assertEquals(new Vector2d(10,10),entity.getPosition());

        controller.setDir(new Vector2d(1,0));
        entity.move(0.5,base);
        assertEquals(new Vector2d(11,10),entity.getPosition());
        entity.move(1.5,base);
        assertEquals(new Vector2d(14,10),entity.getPosition());

        controller.setDir(new Vector2d(0,1));
        entity.move(1,base);
        assertEquals(new Vector2d(14,12),entity.getPosition());

        controller.setDir(new Vector2d(0,-1));
        entity.move(2,base);
        assertEquals(new Vector2d(14,8),entity.getPosition());

        controller.setDir(new Vector2d(-1,0));
        entity.move(4,base);
        assertEquals(new Vector2d(6,8),entity.getPosition());
    }

    //TODO test tile collsion.
}

class testDrawable implements Drawable {

    protected testDrawable() {

    }
    @Override
    public Image draw(double deltaTime) {
        return new ImageIcon("src/main/resources/Images/items/goo.png").getImage();
    }

    @Override
    public Image getPlaceImage() {
        return new ImageIcon("src/main/resources/Images/items/goo.png").getImage();
    }

    @Override
    public int getWidth() {
        return 12;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public boolean needsRedraw() {
        return false;
    }
}

class testController implements EntityController {
    private Vector2d dir;

    protected testController(Vector2d dir) {
        this.dir = dir;
    }

    protected void setDir(Vector2d dir) {
        this.dir = dir;
    }
    @Override
    public Tuple2d getDirection(Vector2d position) {
        return dir;
    }
}

class testPlacer implements EntityPlacer {

    testPlacer() {

    }

    @Override
    public void place(Base base, Vector2d position, Image image, double deltaTime) {
        position.set(deltaTime,deltaTime);
    }
}