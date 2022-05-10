package main;

public class Camera {
    
    public int x = 0;
    public int y = 0;

    public void update() {
        x = Window.WIDTH / 2 - (Handler.player.x + (Handler.player.width / 2));
        y = Window.HEIGHT / 2 - (Handler.player.y + (Handler.player.height / 2));
    }
}
