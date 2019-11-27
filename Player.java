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
        itemsPicked.add(item);
    }

    /**
     * Gets the total weight of the items the player is carrying
     * @return Total weight of the objects
     */
    public int getTotalWeight()
    {
        int totalWeight = 0;
        for (Item itemInInventory : itemsPicked) {
            totalWeight += itemInInventory.getWeight();
        }
        return totalWeight;
    }

    /**
     * OVERLOADED METHOD:
     * Gets the total weight of the items the player is carrying
     * plus the weight of the item for which an attempt to pick
     * up is made
     * @param weight Weight of the object which is to be picked up
     * @return The total weight of carried objects + weight of object
     * attempted to pick up
     */
    public int getTotalWeight(int weight)
    {
        for (Item itemInInventory : itemsPicked) {
            weight += itemInInventory.getWeight();
        }
        return weight;
    }

    /**
     * Drop an item currently in the player's inventory
     * @param item The item to remove from the inventory
     */
    public void dropItem(Item item)
    {
        itemsPicked.remove(item);
    }

    /**
     * Retrieve the HashSet containing the items picked
     * up by the player
     * @return The HashSet containing items
     */
    public HashSet<Item> getItemsPicked()
    {
        return itemsPicked;
    }
}
