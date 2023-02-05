package entitys.player;

import entitys.EntityController;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;
import java.awt.event.KeyEvent;

public class PlayerController implements EntityController {

    private final boolean[] directions;
    private final Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public PlayerController(Inventory inventory) {
        this.inventory = inventory;
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_Q) {
            inventory.selectDown();
        }

        if (key == KeyEvent.VK_E) {
            inventory.selectUp();
        }

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            directions[0] = true;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            directions[1] = true;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            directions[2] = true;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            directions[3] = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            inventory.inUse = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();


        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            directions[0] = false;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            directions[1] = false;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            directions[2] = false;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            directions[3] = false;
        }

        if (key == KeyEvent.VK_SPACE) {
            inventory.inUse = false;
        }
    }
}
