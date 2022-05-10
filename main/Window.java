package main;

import java.awt.*;
import javax.swing.JFrame;

public class Window {
    
    private JFrame window;

    private static final String TITLE = "Sandbox";

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public Window() {
        init();
    }

    private void init() {
        window = new JFrame();
        window.setTitle(TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(new Game());

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static Rectangle getBounds() {
        return new Rectangle(-Handler.camera.x, -Handler.camera.y, Window.WIDTH, Window.HEIGHT);
    }

    public static void main(String[] args) {
        new Window();
    }
}
