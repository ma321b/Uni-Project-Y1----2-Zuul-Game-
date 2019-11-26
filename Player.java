import java.util.HashSet;

/**
 * A class for the player. Used for storing basic info
 */

public class Player
{
    private HashSet<Item> itemsPicked;     // store the items picked up by the player

    public Player()
    {
        itemsPicked = new HashSet<>();
    }

    /**
     * Pick up an item from inside the room
     * @param item The item to pick
     */
    public void pickUpItem(Item item)
    {
        int totalWeight = getTotalWeight();
        if (item.isCanPickUp()) {
            if (totalWeight <= 50) {
                itemsPicked.add(item);
            }
            else {
                System.out.println("You can't carry any more weight, bro.");
            }
        }
        else {
            System.out.println("Do you really think you can pick it up, mate? NO!");
        }
    }

    /**
     * Gets the total weight of the objects the player is carrying
     * @return The total weight of carried objects
     */
    private int getTotalWeight()
    {
        int totalWeight = 0;
        for (Item itemInInventory : itemsPicked) {
            totalWeight += itemInInventory.getWeight();
        }
        return totalWeight;
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
