package Entitys.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {

    PlayerController controller;

    public TAdapter(PlayerController controller) {
        this.controller = controller;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        controller.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        controller.keyPressed(e);
    }
}

