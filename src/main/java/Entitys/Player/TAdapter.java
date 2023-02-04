package Entitys.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {


    @Override
    public void keyReleased(KeyEvent e) {
        PlayerController.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PlayerController.keyPressed(e);
    }
}

