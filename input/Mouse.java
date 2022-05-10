package input;

import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import environment.Block;
import inventory.Slot;
import main.Handler;

public class Mouse implements MouseListener, MouseMotionListener {

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        int xMouse = e.getX();
        int yMouse = e.getY();

        boolean rightClicked = SwingUtilities.isRightMouseButton(e);
        boolean leftClicked = SwingUtilities.isLeftMouseButton(e);

        //Rectangle mouse = new Rectangle(xMouse, yMouse, 1, 1);
        Rectangle translatedMouse = new Rectangle(xMouse - Handler.camera.x, yMouse - Handler.camera.y, 1, 1);
        Rectangle staticMouse = new Rectangle(xMouse, yMouse, 1, 1);

        if (rightClicked && !staticMouse.intersects(Handler.player.inventory.getBounds())) {
            int xOffset = (xMouse + Handler.camera.x) % Block.SIZE;
            int yOffset = (yMouse + Handler.camera.y) % Block.SIZE;

            int xBlock = xMouse + Handler.camera.x - xOffset;
		    int yBlock = yMouse + Handler.camera.y - yOffset - Block.SIZE;

            Block block = Handler.player.inventory.getSelected().item.block;
            block.x = xBlock;
            block.y = yBlock;

            Handler.world.blocks.add(block);
        }

        if (leftClicked && staticMouse.intersects(Handler.player.inventory.getBounds())) {
            Slot previousSlot = null;
            boolean hit = false;

            for (Slot slot : Handler.player.inventory.slots) {
                if (slot.selected) previousSlot = slot;

                slot.selected = false;

                if (staticMouse.intersects(slot.getBounds())) {
                    slot.selected = true;
                    hit = true;
                }
            }

            if (!hit) previousSlot.selected = true;
            return;
        }

        // destroy a block
        if (leftClicked && translatedMouse.intersects(Handler.player.getDestroyRange())) {
            for (Block block : Handler.world.blocks) {
                if (!block.solid) continue;
                
                if (translatedMouse.intersects(block.getBounds())) {
                    block.destroying = true;
                    block.strength = Handler.player.strength;
                }
            }

            return;
        }
    }

    public void mouseReleased(MouseEvent e) {
        int xMouse = e.getX();
        int yMouse = e.getY();

        //Rectangle mouse = new Rectangle(xMouse, yMouse, 1, 1);
        //Rectangle gridMouse = new Rectangle(xMouse - Handler.camera.x, yMouse - Handler.camera.y, 1, 1);

        for (Block block : Handler.world.blocks) {
            if (!block.solid) continue;
            
            block.destroying = false;
            block.health = block.durability;
        }
    }

    public void mouseDragged(MouseEvent e) {
        int xMouse = e.getX();
        int yMouse = e.getY();

        //Rectangle mouse = new Rectangle(xMouse, yMouse, 1, 1);
        Rectangle gridMouse = new Rectangle(xMouse - Handler.camera.x, yMouse - Handler.camera.y, 1, 1);

        // destroy a block
        if (gridMouse.intersects(Handler.player.getDestroyRange())) {
            for (Block block : Handler.world.blocks) {
                if (!block.solid) continue;
                
                if (gridMouse.intersects(block.getBounds())) {
                    block.destroying = true;
                    block.strength = Handler.player.strength;
                } else {
                    block.destroying = false;
                }
            }
        } else {
            for (Block block : Handler.world.blocks) {
                block.destroying = false;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        
    }
}
