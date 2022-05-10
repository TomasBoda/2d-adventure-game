package environment;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.Handler;
import main.Images;
import main.Window;

public class Block {

    public static int SIZE = 56;

    public int x;
    public int y;
    public int width;
    public int height;

    public boolean solid;

    public static enum TYPE { STONE, DIRT, GRASS };

    public TYPE type;
    public BufferedImage image;

    public int alpha = 0;

    public double durability;
    public double health;

    public int strength = 1;
    public boolean destroying = false;
    
    public Block(int x, int y, int width, int height, boolean solid, int durability, TYPE type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.durability = durability;
        this.health = durability;
        this.type = type;

        this.image = getImage();
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, width, height, null);

        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(x, y, width, height);

        if (!solid) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(x, y, width, height);
        }

        // draw destroy progress
        if (destroying) {
            g.setColor(new Color(255, 255, 255, 60));
            g.fillRoundRect(x + 7, y + height - 18, width - 14, 6, 3, 3);

            double healthWidth = (width - 14) * (health / durability);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRoundRect(x + 7, y + height - 18, (int) healthWidth, 6, 3, 3);
        }
    }

    public void update() {
        alpha = getAlpha();
    }
    
    private BufferedImage getImage() {
        BufferedImage image = null;

        if (type == TYPE.GRASS) image = Images.grass;
        if (type == TYPE.DIRT) image = Images.dirt;
        if (type == TYPE.STONE) image = Images.stone;

        return image;
    }

    private int getAlpha() {
        int xDistance = Math.abs((Handler.player.x + (Handler.player.width / 2)) - (x + (width / 2)));
        int yDistance = Math.abs((Handler.player.y + (Handler.player.height / 2)) - (y + (height / 2)));
        int distance = (int) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        double ratio = 1.2;
        int maxDistance = (int) (Math.sqrt((Window.WIDTH * Window.WIDTH) + (Window.HEIGHT * Window.HEIGHT)) / ratio);

        if (distance > maxDistance) return 0;

        if (distance == 0) return 255;

        int alpha = (int) (255 / (maxDistance / distance));

        return alpha;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
