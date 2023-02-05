package Display;

import Background.Tile;
import Entitys.Entity;
import Entitys.EntityFactory;
import Entitys.Player.PlayerController;
import Entitys.Player.TAdapter;
import Items.Item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.vecmath.Vector2d;

public class Board extends JPanel implements ActionListener {

    private final Layer baseLayer;
    private final Layer entityLayer;

    private final List<Entity> entities;
    private final List<Item> items;

    private final Timer timer;
    private final static int DELAY = 20;

    private long oldTime;

    private final Entity player;
    private double deltaTime;

    public Board() {
        super();
        setBackground(Color.black);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        baseLayer = new Layer(width,height);
        entityLayer = new Layer(width,height);
        entities = new ArrayList<>();

        player = EntityFactory.makePlayer(new Vector2d(0,0));
        entities.add(player);

        items = new ArrayList<>();
        items.add(new Item(10, Tile.ice, new Vector2d(50,50)));

        addKeyListener(new TAdapter((PlayerController) player.getController()));

        timer = new Timer(DELAY, this);
        timer.start();


        setFocusable(true);

        oldTime = System.nanoTime();
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


    @Override
    public void actionPerformed(ActionEvent e) {
        long newTime = System.nanoTime();
        deltaTime = (newTime - oldTime)/1e+9;
        oldTime = newTime;
        for (Entity entity: entities) {
            entity.move(deltaTime,baseLayer);
            entity.place(baseLayer, deltaTime);
        }

        ((PlayerController)player.getController()).getInventory().collect(items,player.getPosition());

        ArrayList<Item> toBeRemoved = new ArrayList<>();
        for (Item item: items) {
            if (item.shouldBeDestroyed()) {
                toBeRemoved.add(item);
            }
        }
        items.removeAll(toBeRemoved);

        repaint();
    }
}