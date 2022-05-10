package main;

import java.awt.*;

import entity.Player;
import environment.World;

public class Handler {

    public static Camera camera;
    public Images images;

    public static Player player;
    public static World world;
    
    public Handler() {
        init();
    }

    private void init() {
        images = new Images();
        world = new World();
        camera = new Camera();
    }

    public void render(Graphics g) {
        g.translate(camera.x, camera.y);

        world.render(g);
        player.render(g);
        player.inventory.render(g);

        g.translate(-camera.x, -camera.y);

        // game version
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Sandbox v.1.0.0", 18, 30);
    }

    public void update() {
        world.update();
        player.update();
        camera.update();
    }
}
