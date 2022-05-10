package environment;

import java.awt.*;

import main.Handler;
import main.Images;
import main.Window;
import entity.Player.MOVEMENT;

public class Background {

    public int[][] xCoordinates = {
        { 0, Window.WIDTH },
        { 0, Window.WIDTH },
        { 0, Window.WIDTH },
        { 0, Window.WIDTH }
    };

    public int[] speeds = { 1, 2, 3, 4 };
    
    public void render(Graphics g) {
        g.translate(-Handler.camera.x, -Handler.camera.y);

        g.drawImage(Images.background[0], 0, 0, Window.WIDTH, Window.HEIGHT, null);

        g.drawImage(Images.background[1], xCoordinates[0][0], 0, Window.WIDTH, Window.HEIGHT, null);
        g.drawImage(Images.background[1], xCoordinates[0][1], 0, Window.WIDTH, Window.HEIGHT, null);

        g.drawImage(Images.background[2], xCoordinates[1][0], 0, Window.WIDTH, Window.HEIGHT, null);
        g.drawImage(Images.background[2], xCoordinates[1][1], 0, Window.WIDTH, Window.HEIGHT, null);

        g.drawImage(Images.background[3], xCoordinates[2][0], 0, Window.WIDTH, Window.HEIGHT, null);
        g.drawImage(Images.background[3], xCoordinates[2][1], 0, Window.WIDTH, Window.HEIGHT, null);

        g.drawImage(Images.background[4], xCoordinates[3][0], 0, Window.WIDTH, Window.HEIGHT, null);
        g.drawImage(Images.background[4], xCoordinates[3][1], 0, Window.WIDTH, Window.HEIGHT, null);

        g.translate(Handler.camera.x, Handler.camera.y);
    }

    public void update() {
        if (Handler.player.xSpeed != 0) {
            if (Handler.player.movement == MOVEMENT.RIGHT) {
                for (int i = 0; i < xCoordinates.length; i++) {
                    xCoordinates[i][0] -= speeds[i];
                    xCoordinates[i][1] -= speeds[i];
                }
            } else if (Handler.player.movement == MOVEMENT.LEFT) {
                for (int i = 0; i < xCoordinates.length; i++) {
                    xCoordinates[i][0] += speeds[i];
                    xCoordinates[i][1] += speeds[i];
                }
            }

            loop();
        }
    }

    public void loop() {
        for (int i = 0; i < xCoordinates.length; i++) {
            if (xCoordinates[i][0] < -Window.WIDTH) {
                xCoordinates[i][0] = xCoordinates[i][1] + Window.WIDTH;
            } else if (xCoordinates[i][0] > Window.WIDTH) {
                xCoordinates[i][0] = xCoordinates[i][1] - Window.WIDTH;
            }

            if (xCoordinates[i][1] < -Window.WIDTH) {
                xCoordinates[i][1] = xCoordinates[i][0] + Window.WIDTH;
            } else if (xCoordinates[i][1] > Window.WIDTH) {
                xCoordinates[i][1] = xCoordinates[i][0] - Window.WIDTH;
            }
        }
    }

    public void fixCollision(String direction) {
        if (direction == "right") {
            for (int i = 0; i < Handler.world.background.xCoordinates.length; i++) {
                Handler.world.background.xCoordinates[i][0] += Handler.world.background.speeds[i];
                Handler.world.background.xCoordinates[i][1] += Handler.world.background.speeds[i];
            }
        } else if (direction == "left") {
            for (int i = 0; i < Handler.world.background.xCoordinates.length; i++) {
                Handler.world.background.xCoordinates[i][0] -= Handler.world.background.speeds[i];
                Handler.world.background.xCoordinates[i][1] -= Handler.world.background.speeds[i];
            }
        }
    }
}
