package inventory;

import java.awt.*;
import java.util.ArrayList;

import environment.Block;
import main.Handler;
import main.Window;

public class Inventory {
    
    public ArrayList<Item> items;
    public ArrayList<Slot> slots;

    private int width = 445;
    private int height = 60;
    private int x = (Window.WIDTH / 2) - (width / 2);
    private int y = Window.HEIGHT - 30 - height;

    public Inventory() {
        this.items = new ArrayList<Item>();
        this.slots = new ArrayList<Slot>();

        this.initSlots();
    }

    private void initSlots() {
        for (int i = 0; i < 8; i++) {
            int slotX = this.x + 5 + (i * ((this.height - 10) + 5));
            int slotY = this.y + 5;
            int slotWidth = this.height - 10;
            int slotHeight = this.height - 10;

            this.slots.add(new Slot(slotX, slotY, slotWidth, slotHeight));
        }

        this.slots.get(0).selected = true;
    }

    public void render(Graphics g) {
        g.translate(-Handler.camera.x, -Handler.camera.y);

        for (Slot slot : this.slots) {
            slot.render(g);
        }

        g.translate(Handler.camera.x, Handler.camera.y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void add(Block block, int amount) {
        for (Slot slot : this.slots) {
            if (slot.item != null) {
                if (slot.item.block.type == block.type) {
                    slot.item.amount += amount;
                    break;
                }
            } else {
                slot.changeItem(new Item(block, amount));
                break;
            }
        }
    }

    public Slot getSelected() {
        for (Slot slot : this.slots) {
            if (slot.selected) {
                return slot;
            }
        }

        return null;
    }
}
