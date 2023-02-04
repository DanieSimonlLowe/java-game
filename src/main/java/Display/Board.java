package Display;

import Entitys.Entity;
import Entitys.EntityFactory;
import Entitys.Player.TAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.vecmath.Vector2d;

public class Board extends JPanel implements ActionListener {

    Layer baseLayer;
    Layer entityLayer;

    List<Entity> entities;
    private Timer timer;
    private final int DELAY = 20;

    long oldTime;
    public Board() {
        super();
        setBackground(Color.black);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        baseLayer = new Layer(width,height);
        entityLayer = new Layer(width,height);
        entities = new ArrayList<Entity>();

        addKeyListener(new TAdapter());

        timer = new Timer(DELAY, this);
        timer.start();

        entities.add(EntityFactory.makePlayer(new Vector2d(0,0)));

        setFocusable(true);

        oldTime = System.nanoTime();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        entityLayer.clear();
        for (Entity entity: entities) {
            entityLayer.drawEntity(entity);
        }


        baseLayer.paint(g);
        entityLayer.paint(g);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        long newTime = System.nanoTime();
        double deltaTime = (newTime - oldTime)/1e+9;
        oldTime = newTime;
        for (Entity entity: entities) {
            entity.move(deltaTime);
            entity.place(baseLayer);
        }

        repaint();
    }
}