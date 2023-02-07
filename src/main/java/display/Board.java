package display;

import background.Base;
import background.Tile;
import entitys.Entity;
import entitys.EntityFactory;
import entitys.player.PlayerController;
import entitys.player.TAdapter;
import items.Item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.vecmath.Vector2d;

public class Board extends JPanel implements ActionListener {

    private final Base baseLayer;
    private final Layer entityLayer;

    private final List<Entity> entities;
    private final List<Item> items;

    private final static int DELAY = 20;

    private long oldTime;

    private final Entity player;
    private double deltaTime;


    public Board() {
        super();
        baseDeltaTime = 0;

        setBackground(Color.black);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        baseLayer = new Base(width,height);
        entityLayer = new Layer(width,height);

        entities = new ArrayList<>();

        player = EntityFactory.makePlayer(new Vector2d(0,0));
        entities.add(player);

        items = new ArrayList<>();


        addKeyListener(new TAdapter((PlayerController) player.getController()));

        Timer timer = new Timer(DELAY, this);
        timer.start();


        setFocusable(true);

        oldTime = System.nanoTime();


        baseLayer.drawRect(Tile.wall, 100,100,100,100);
        entities.add(EntityFactory.makeChaser(new Vector2d(400,400),player));
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        entityLayer.clear();
        for (Entity entity: entities) {
            entityLayer.drawEntity(entity, deltaTime);
        }

        for (Item item: items) {
            entityLayer.drawItem(item);
        }



        baseLayer.paint(g);
        entityLayer.paint(g);

    }

    double baseDeltaTime;
    @Override
    public void actionPerformed(ActionEvent e) {
        long newTime = System.nanoTime();
        deltaTime = (newTime - oldTime)/1e+9;
        oldTime = newTime;

        ArrayList<Entity> toBeRemoved0 = new ArrayList<>();
        for (Entity entity: entities) {
            entity.move(deltaTime,baseLayer);
            entity.place(baseLayer, deltaTime);
            if (entity.shouldBeDestroyed()) {
                toBeRemoved0.add(entity);
            }
        }
        entities.removeAll(toBeRemoved0);

        ((PlayerController)player.getController()).getInventory().collect(items,player.getPosition());


        ArrayList<Item> toBeRemoved = new ArrayList<>();
        for (Item item: items) {
            if (item.shouldBeDestroyed()) {
                toBeRemoved.add(item);
            }
        }
        items.removeAll(toBeRemoved);


        if (baseDeltaTime > 0.25) {
            baseLayer.tickBoard();
            baseDeltaTime = 0;
        } else {
            baseDeltaTime += deltaTime;
        }

        repaint();
    }
}