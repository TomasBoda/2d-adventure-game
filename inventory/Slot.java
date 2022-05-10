package inventory;

import java.awt.*;
import java.awt.Color;

public class Slot {
    
    public int x;
    public int y;
    public int width;
    public int height;

    public Item item;

    public boolean selected = false;

    public Slot(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g) {
        if (selected) {
            g.setColor(new Color(255, 255, 255, 120));
        } else {
            g.setColor(new Color(255, 255, 255, 60));
        }
        
        g.fillRoundRect(x, y, width, height, 10, 10);

        if (item != null) {
            g.drawImage(item.block.image, x + 8, y + 8, width - 16, height - 16, null);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString(item.amount + "", x + width - 12, y + height - 5);
        }
    }

    public void changeItem(Item item) {
        this.item = item;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
