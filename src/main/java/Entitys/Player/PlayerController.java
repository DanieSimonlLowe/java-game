package Entitys.Player;

import Entitys.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;
import java.awt.event.KeyEvent;

public class PlayerController implements EntityController {

    static private boolean[] directions;

    public PlayerController() {
        directions = new boolean[]{false, false, false, false};
    }
    @Override
    public Tuple2d getDirection() {
        Vector2d dir = new Vector2d(0,0);
        if (directions[0] && !directions[1]) {
            dir.x = -1;
        } else if (!directions[0] && directions[1]) {
            dir.x = 1;
        }
        if (directions[2] && !directions[3]) {
            dir.y = -1;
        } else if (!directions[2] && directions[3]) {
            dir.y = 1;
        }

        if (dir.x != 0 && dir.y != 0) {
            dir.normalize();
        }
        //
        return dir;
    }

    static public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            directions[0] = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            directions[1] = true;
        }

        if (key == KeyEvent.VK_UP) {
            directions[2] = true;
        }

        if (key == KeyEvent.VK_DOWN) {
            directions[3] = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            PlayerPlacer.isPlaceing = true;
        }
    }

    static public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            directions[0] = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            directions[1] = false;
        }

        if (key == KeyEvent.VK_UP) {
            directions[2] = false;
        }

        if (key == KeyEvent.VK_DOWN) {
            directions[3] = false;
        }

        if (key == KeyEvent.VK_SPACE) {
            PlayerPlacer.isPlaceing = false;
        }
    }
}
