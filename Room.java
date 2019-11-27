import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private HashSet<Characters> charactersInRoom;    // characters in current room
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> itemsInRoom;              // stores the items

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        charactersInRoom = new HashSet<>();
        this.description = description;
        exits = new HashMap<>();
        itemsInRoom = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString()
                + "\n" + getItemsString() + getCharactersInRoomString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Add items in the room
     * @param item The item object to be added to the room
     */
    public void addItems(Item item)
    {
        if (item != null) {
            itemsInRoom.add(item);
        }
    }

    /**
     * @return The ArrayList containing all items in the room
     */
    public ArrayList<Item> getItems()
    {
        return itemsInRoom;
    }

    /**
     * Creates a string of all the items in the room and returns it
     * @return The string containing all the items in a room
     */
    private String getItemsString()
    {
        String roomItems = "Item(s): ";
        for (Item item : itemsInRoom) {
            roomItems += "\n" + item.getDescription() + "[" + item.getName() + "]";
        }
        return roomItems;
    }

    /**
     * @param name Name of the item
     * @return True if item is present in room, otherwise false
     */
    public boolean isItemInRoom(String name)
    {
        HashSet<String> itemsNameSet = new HashSet<>();
        // add all items' names from the array to this set
        for (Item item : itemsInRoom) {
            itemsNameSet.add(item.getName());
        }

        return itemsNameSet.contains(name);   //return if the set contains an item with the same name as the parameter
    }

    /**
     * @return The String containing all items currently in the room
     */
    private String getCharactersInRoomString()
    {
        String charactersString = "Characters:";
        for (Characters character : charactersInRoom) {
            charactersString += " " + character.getName();
        }
        return charactersString;
    }

    /**
     * @return The HashSet containing all the characters in the Room
     */
    public HashSet<Characters> getCharactersInRoom()
    {
        return charactersInRoom;
    }
}

