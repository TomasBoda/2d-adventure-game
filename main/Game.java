package main;

import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import input.Keyboard;
import input.Mouse;

public class Game extends JPanel implements ActionListener {

    private Timer timer;

    public static final int FPS = 60;

    private Handler handler;
    
    public Game() {
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT));
        addKeyListener(new Keyboard());
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        setFocusable(true);

        handler = new Handler();

        timer = new Timer(1000 / FPS, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        handler.render(g);
    }

    public void actionPerformed(ActionEvent e) {
        handler.update();
        repaint();
    }
}
