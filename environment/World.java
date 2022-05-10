package environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import entity.Player;
import environment.Block.TYPE;
import main.Window;
import main.Handler;

public class World {

    public ArrayList<Block> blocks;
    public Background background;

    public static int SIZE = 1000;
    
    public World() {
        init();
    }

    private void init() {
        blocks = generate();
        background = new Background();
    }

    public void render(Graphics g) {
        background.render(g);

        for (Block block : blocks) {
            if (block.getBounds().intersects(Window.getBounds())) {
                block.render(g);
            }
        }
    }

    public void update() {
        background.update();

        for (Block block : blocks) {
            block.update();

            // destroy a block
            if (block.destroying) {
                block.health -= block.strength;

                if (block.health <= 0) {
                    Handler.player.inventory.add(block, 1);
                    blocks.remove(block);
                    break;
                }
            }
        }
    }

    private ArrayList<Block> generate() {
        ArrayList<Block> b = new ArrayList<Block>();

        int currentHeight = 100;

        int smoothing = 4;
        int currentSmoothingPosition = 0;

        for (int x = 0; x < World.SIZE; x++) {
            for (int y = 0; y < currentHeight; y++) {
                int xBlock = x * Block.SIZE;
                int yBlock = Window.HEIGHT - (y * Block.SIZE);
                int durability;
                TYPE type;

                if (y == currentHeight - 1) {
                    type = TYPE.GRASS;
                    durability = 600;
                } else if (y == currentHeight - 2) {
                    type = TYPE.DIRT;
                    durability = 800;
                } else {
                    type = TYPE.STONE;
                    durability = 1000;
                }

                b.add(new Block(xBlock, yBlock, Block.SIZE, Block.SIZE, false, durability, type));
                b.add(new Block(xBlock, yBlock, Block.SIZE, Block.SIZE, true, durability, type));
            }

            if (currentSmoothingPosition == smoothing) {
                int direction = new Random().nextInt(3);

                if (direction == 0) currentHeight--;
                if (direction == 2) currentHeight++;

                currentSmoothingPosition = 0;
            }

            currentSmoothingPosition++;

            // spawn player
            if (x == World.SIZE / 2) {
                int xPlayer = x * Block.SIZE;
                int yPlayer = Window.HEIGHT - ((currentHeight + 3) * Block.SIZE);

                Handler.player = new Player(xPlayer, yPlayer, (int) (Block.SIZE * 1.3), (int) (Block.SIZE * 2.5));
            }
        }

        return b;
    }
}
