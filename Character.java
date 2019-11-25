import java.util.HashSet;
import java.util.Iterator;

/**
 * A class for the player. Used for storing basic info
 */

public class Character
{
    private HashSet<Item> itemsPicked;     // store the items picked up by the player

    public Character()
    {
        itemsPicked = new HashSet<>();
    }

    /**
     * Pick up an item from inside the room
     * @param item The item to pick
     */
    public void pickUpItem(Item item)
    {
        int totalWeight = 0;
        for (Item itemInInventory : itemsPicked) {
            totalWeight += itemInInventory.getWeight();
        }

        if (totalWeight <= 40) {
            itemsPicked.add(item);
        }
        else {
            System.out.println("You can't carry any more weight, bro.");
        }
    }

    /**
     * Drop an item currently in the player's inventory
     * @param item The item to remove from the inventory
     */
    public void dropItem(Item item)
    {
        itemsPicked.remove(item);
    }
}
