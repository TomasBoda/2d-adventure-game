package inventory;

import environment.Block;

public class Item {
    
    public Block block;
    public int amount;

    public Item(Block block, int amount) {
        this.block = block;
        this.amount = amount;
    }
}
