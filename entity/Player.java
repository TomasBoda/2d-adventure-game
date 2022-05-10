package entity;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import environment.Block;
import inventory.Inventory;
import main.Handler;
import main.Images;

public class Player {

    public int x;
    public int y;
    public int width;
    public int height;

    private BufferedImage image;

    private final int speed = 7;
    public int xSpeed = 0;

    private double gravity = 0;

    public enum MOVEMENT { NONE, RIGHT, LEFT };
    private enum DIRECTION { RIGHT, LEFT };

    public boolean jumping = false;

    private int animationTick = 0;
    private int animationFrequency = 4;
    private int animationStage = 0;
    
    public MOVEMENT movement = MOVEMENT.NONE;
    public DIRECTION direction = DIRECTION.RIGHT;

    public int strength = 10;

    public Inventory inventory;
    
    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.image = Images.playerIdle[0];
        this.movement = MOVEMENT.NONE;

        this.inventory = new Inventory();
    }

    public void render(Graphics g) {
        g.drawImage(image, direction == DIRECTION.RIGHT ? x : x + width, y, direction == DIRECTION.RIGHT ? width : -width, height, null);
    }

    public void update() {
        move();
        applyGravity();
        checkCollision();
        animate();
    }

    public void animate() {
        if (animationTick == animationFrequency) {
            if (gravity == 0) {
                if (movement == MOVEMENT.NONE) {
                    // IDLE
                    if (animationStage > Images.playerIdle.length - 1) animationStage = 0;

                    image = Images.playerIdle[animationStage];
                } else {
                    // RUNNING
                    if (animationStage > Images.playerRunning.length - 1) animationStage = 0;

                    image = Images.playerRunning[animationStage];
                }
            } else {
                if (gravity < -2) {
                    image = Images.playerAir[0];
                } else if (gravity >= -2 && gravity < 0) {
                    image = Images.playerAir[1];
                } else if (gravity > 0 && gravity <= 2) {
                    image = Images.playerAir[2];
                } else {
                    image = Images.playerAir[3];
                }
            }

            animationStage++;
            animationTick = 0;
        }

        animationTick++;
    }

    public void applyGravity() {
        gravity += 0.5;

        if (gravity > 20) {
            gravity = 20;
        }

        y += gravity;
    }

    public void move() {
        x += xSpeed;
    }

    public void handleMovement(String type, int key) {
        if (key == KeyEvent.VK_D) {
            if (type == "press") {
                movement = MOVEMENT.RIGHT;
                direction = DIRECTION.RIGHT;
                xSpeed = speed;

                for (Block block : Handler.world.blocks) {
                    if (!block.solid) continue;
                    
                    block.destroying = false;
                }
            }

            if (type == "release") {
                movement = MOVEMENT.NONE;
                xSpeed = 0;
            }
        }

        if (key == KeyEvent.VK_A) {
            if (type == "press") {
                movement = MOVEMENT.LEFT;
                direction = DIRECTION.LEFT;
                xSpeed = -speed;

                for (Block block : Handler.world.blocks) {
                    if (!block.solid) continue;
                    
                    block.destroying = false;
                }
            }

            if (type == "release") {
                movement = MOVEMENT.NONE;
                xSpeed = 0;
            }
        }

        if (key == KeyEvent.VK_SPACE) {
            if (!jumping) {
                jump();

                for (Block block : Handler.world.blocks) {
                    if (!block.solid) continue;
                    
                    block.destroying = false;
                }
            }
        }
    }

    private void jump() {
        gravity = -10;
        jumping = true;
    }

    private void checkCollision() {
        for (Block block : Handler.world.blocks) {
            if (!block.solid) continue;

            if (getBoundsBottom().intersects(block.getBounds())) {
                if (gravity > 0) {
                    y = block.y - height;
                    gravity = 0;

                    jumping = false;
                }
            }

            if (getBoundsTop().intersects(block.getBounds())) {
                y = block.y + block.height;
                gravity = 0;
            }

            if (getBoundsRight().intersects(block.getBounds())) {
                x = block.x - width;
                xSpeed = 0;

                // fix background moving when a block collision occurs
                Handler.world.background.fixCollision("right");
            } else {
                if (movement == MOVEMENT.RIGHT) xSpeed = speed;
            }

            if (getBoundsLeft().intersects(block.getBounds())) {
                x = block.x + block.width;
                xSpeed = 0;

                // fix background moving when a block collision occurs
                Handler.world.background.fixCollision("left");
            } else {
                if (movement == MOVEMENT.LEFT) xSpeed = -speed;
            }
        }
    }

    public Rectangle getDestroyRange() {
        return new Rectangle(x + (width / 2) - (Block.SIZE * 5), y + (height / 2) - (Block.SIZE * 4), Block.SIZE * 10, Block.SIZE * 8);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(x + 10, y, width - 20, 10);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(x + 10, y + height - 10, width - 20, 10);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(x + width - 10, y + 10, 10, height - 20);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(x, y + 10, 10, height - 20);
    }
}
