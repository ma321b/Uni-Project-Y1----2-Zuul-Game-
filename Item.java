public class Item
{
    // item's weight and description
    private String name;
    private String description;
    private int weight;
    private boolean canPickUp;  // true if the player can pick up this item, false otherwise

    public Item(String name, String description, int weight, boolean canPickUp)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.canPickUp = canPickUp;
    }

    /**
     * @return The name field of the item
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The description of item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return The weight of item
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * @return True if the item can be picked by the player. False otherwise
     */
    public boolean isCanPickUp()
    {
        return canPickUp;
    }
}

