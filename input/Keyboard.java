package input;

import java.awt.event.*;

import main.Handler;

public class Keyboard implements KeyListener {

    public void keyTyped(KeyEvent e) {
       
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        Handler.player.handleMovement("press", key);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        Handler.player.handleMovement("release", key);
    }
}
