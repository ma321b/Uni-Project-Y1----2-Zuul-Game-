import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 *                                          MAP:
 *
 *
 *                              Great Hall
 *                                   |
 *          Bed Room  --------- Courtyard ----------- Kitchen ------ Solar Room  ----- Garden
 *             |                     |
 *          Bathroom             Guard Room
 *                                   \
 *                              Long Passage  ------- Random Magic Room
 *                                   |
 *                                Dungeon
 *
 * @author  Michael Kölling and David J. Barnes + Muhammad Athar Abdullah
 * @version 2016.02.29
 */

public class Game 
{
    public static void main(String[] args) {
        Game g = new Game();
        g.play();
    }
    private Random random;
    private Parser parser;
    private ArrayList<Room> allGameRooms;     // stores all the Rooms in the game
    private HashSet<Characters> allGameCharacters;  // stores all the Characters in game
    private Room currentRoom;
    private Stack<Room> roomsStack;
    private Player player;
    private HashMap<String, Item> itemsMap;      // map each item's name to it's object
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        allGameCharacters = new HashSet<>();
        random = new Random();
        allGameRooms = new ArrayList<>();
        itemsMap = new HashMap<>();
        player = new Player();
        roomsStack = new Stack<>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room greatHall, bedRoom, courtyard, kitchen, dungeon, solarRoom,
                bathroom, garden, longPassage, guardRoom, magicRoom;

        // create the rooms
        greatHall = new Room("in the great hall, surrounded by weapons");
        bedRoom = new Room("in a strange bedroom, having no bed");
        courtyard = new Room("in a spooky-looking courtyard");
        kitchen = new Room("in a kitchen having an odd smell");
        dungeon = new Room("in a dark, scary dungeon");
        solarRoom = new Room("in an eerily quiet and dark 'solar' room, with " +
                "disordered trails of footsteps leading nowhere");
        garden = new Room("a 'garden', with no greenery");
        bathroom = new Room("a medieval style bathroom");
        longPassage = new Room("a seemingly never-ending passage");
        guardRoom = new Room("in a guardroom with armoured guard statues");
        magicRoom = new Room("a magic transporter room, which will transport you to a random room in 3..2..1");

        // store all the rooms in the allGameRooms ArrayList
        Room[] roomsArray = new Room[] {
                greatHall, bedRoom, courtyard, kitchen, dungeon, magicRoom,
                solarRoom, garden, bathroom, longPassage, guardRoom
        };
        allGameRooms.addAll(Arrays.asList(roomsArray));

        // initialise room exits and items and characters in the rooms
        greatHall.setExit("south", courtyard);
        greatHall.addItems(new Item("spear",
                "a bloody spear",
                20,
                true));
        greatHall.addItems(new Item("portrait",
                "a huge portrait of Mona Lisa looking angry",
                100,
                false));
        greatHall.addItems(new Item("statue-piece",
                "a broken piece of some statue",
                250,
                false));
        greatHall.addItems(new Item("round-table",
                "a huge round table, turned upside-down",
                400,
                false));
        greatHall.addItems(new Item("sharp-sword",
                "a sharp-edged sword",
                15,
                true));
        greatHall.addCharacter(new Characters("The Ghost of King Edward"));
        greatHall.addCharacter(new Characters("The demon of Prince Caesar"));
        allGameCharactersInitializer(greatHall.getCharactersInRoom());
        itemsMapInitializer(greatHall);

        bedRoom.setExit("east", courtyard);
        bedRoom.setExit("south", bathroom);
        bedRoom.addItems(new Item("bed",
                "a surprisingly intact queen-sized bed in the corner",
                500,
                false));
        bedRoom.addItems(new Item("big-mirror",
                "a broken big mirror",
                100,
                true));
        bedRoom.addItems(new Item("bookshelf",
                "a bookshelf containing books of spells",
                50,
                false));
        bedRoom.addItems(new Item("broken-arrow",
                "a piece of broken arrow on the floor",
                2,
                true));
        bedRoom.addCharacter(new Characters("A cat of an impossibly-black color"));
        bedRoom.addCharacter(new Characters("The Demon of King Paul"));
        allGameCharactersInitializer(bedRoom.getCharactersInRoom());
        itemsMapInitializer(bedRoom);

        courtyard.setExit("north", greatHall);
        courtyard.setExit("east", kitchen);
        courtyard.setExit("south", guardRoom);
        courtyard.setExit("west", bedRoom);
        courtyard.addItems(new Item("broken-vase",
                "a broken glass vase",
                4,
                true));
        courtyard.addItems(new Item("knight-statue1",
                "a statue of knight on the right hand side",
                500,
                false));
        courtyard.addItems(new Item("knight-statue2",
                "a bigger statue of a knight on the left hand side",
                800,
                false));
        courtyard.addItems(new Item("wand",
                "a small wand-like object",
                1,
                true));
        courtyard.addCharacter(new Characters("a scary witch"));
        courtyard.addCharacter(new Characters("a levitating old woman"));
        allGameCharactersInitializer(courtyard.getCharactersInRoom());
        itemsMapInitializer(courtyard);

        kitchen.setExit("east", solarRoom);
        kitchen.setExit("west", courtyard);
        kitchen.addItems(new Item("knives",
                "a pair of dagger-like knives",
                2,
                true));
        kitchen.addItems(new Item("stick",
                "a big stick",
                5,
                true));
        kitchen.addItems(new Item("cutlery",
                "a set of broken cutlery levitating in air",
                500,
                false));
        kitchen.addItems(new Item("stone",
                "a big stone on the floor from which strange " +
                "noises can be heard",
                20,
                true));
        kitchen.addCharacter(new Characters("a moving statue"));
        kitchen.addCharacter(new Characters("a moving skeleton"));
        allGameCharactersInitializer(kitchen.getCharactersInRoom());
        itemsMapInitializer(kitchen);

        dungeon.setExit("north", longPassage);
        dungeon.addItems(new Item("steel-bar",
                "a steel bar on floor",
                30,
                true));
        dungeon.addItems(new Item("dungeon-spear",
                "a long sharp spear inside a cell",
                20,
                true));
        dungeon.addItems(new Item("broken-door",
                "a broken wooden door on the floor",
                40,
                false));
        dungeon.addItems(new Item("big-book",
                "a rather big book with light-rays coming out of it",
                5,
                true));
        dungeon.addCharacter(new Characters("a moving doll"));
        dungeon.addCharacter(new Characters("The Ghost of Prince Leonard"));
        allGameCharactersInitializer(dungeon.getCharactersInRoom());
        itemsMapInitializer(dungeon);

        solarRoom.setExit("east", garden);
        solarRoom.setExit("west", kitchen);
        solarRoom.addItems(new Item("chair-mysterious",
                "a chair with one leg missing, facing towards you",
                30,
                true));
        solarRoom.addItems(new Item("curtains",
                "black curtains covering the window in front",
                5,
                true));
        solarRoom.addItems(new Item("broken-cup",
                "a broken cup, placed upside down on floor",
                2,
                true));
        solarRoom.addItems(new Item("sharp-broken-mirror",
                "a sharp piece of broken mirror placed on the chair",
                6,
                true));
        solarRoom.addCharacter(new Characters("a cat watching you endlessly"));
        solarRoom.addCharacter(new Characters("a hobbit"));
        allGameCharactersInitializer(solarRoom.getCharactersInRoom());
        itemsMapInitializer(solarRoom);

        garden.setExit("west", solarRoom);
        garden.addItems(new Item("kennel",
                "an empty kennel in dark shadows",
                100,
                false));
        garden.addItems(new Item("swing",
                "a big swing, swinging for no apparent reason since the last 20 years",
                100,
                false));
        garden.addItems(new Item("bats",
                "thousands of bats flying here everywhere",
                25,
                false));
        garden.addCharacter(new Characters("a dog staring at you"));
        garden.addCharacter(new Characters("a badly-wounded soldier, whose cries u can't hear"));
        allGameCharactersInitializer(garden.getCharactersInRoom());
        itemsMapInitializer(garden);

        bathroom.setExit("north", bedRoom);
        bathroom.addItems(new Item("pages",
                "pages of some kind levitating in the air",
                1,
                true));
        bathroom.addItems(new Item("glowing-wood",
                "a glowing piece of wood",
                2,
                true));
        bathroom.addCharacter(new Characters("a crazy scientist"));
        bathroom.addCharacter(new Characters("Elon Musk's moving statue"));
        allGameCharactersInitializer(bathroom.getCharactersInRoom());
        itemsMapInitializer(bathroom);

        longPassage.setExit("upstairs", guardRoom);
        longPassage.setExit("south", dungeon);
        longPassage.setExit("east", magicRoom);
        longPassage.addItems(new Item("medieval-lamp",
                "a medieval era lamp",
                2,
                true));
        longPassage.addItems(new Item("scribbled-portrait",
                "a portrait on the left with ancient scribblings",
                20,
                true));
        longPassage.addCharacter(new Characters("a giant which can teleport anywhere"));
        allGameCharactersInitializer(longPassage.getCharactersInRoom());
        itemsMapInitializer(longPassage);

        guardRoom.setExit("downstairs", longPassage);
        guardRoom.setExit("north", courtyard);
        guardRoom.addItems(new Item("skeleton",
                "a skeleton on a chair with sword in it's hand",
                30,
                true));
        guardRoom.addItems(new Item("pike",
                "a pike on the left wall",
                12,
                true));
        guardRoom.addItems(new Item("guardRoom-statue",
                "a statue of a knight",
                300,
                false));
        guardRoom.addCharacter(new Characters("The Ghost of Java"));
        guardRoom.addCharacter(new Characters("a small group of bats"));
        allGameCharactersInitializer(guardRoom.getCharactersInRoom());
        itemsMapInitializer(guardRoom);

        currentRoom = courtyard;  // start the game in courtyard
        roomsStack.push(currentRoom);
        moveCharacters();
    }

    /**
     * Stores the items in a HashMap, mapping the names of the items to
     * the corresponding Item objects.
     * @param room The room, items of which are to be added in the itemsMap
     */
    private void itemsMapInitializer(Room room)
    {
        ArrayList<Item> itemsInRoom = room.getItems();
        for (Item item : itemsInRoom) {
            itemsMap.put(item.getName(), item);
        }
    }

    /**
     * Add Characters in the room to allGameCharacters HashSet
     * @param charactersInRoom The HashSet containing all characters in room
     */
    private void allGameCharactersInitializer(HashSet<Characters> charactersInRoom)
    {
        allGameCharacters.addAll(charactersInRoom);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("The game ends here. See you again!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Ghost-busters: Castle Edition!");
        System.out.println("Ghost-busters: Castle Edition is a new and interesting" );
        System.out.println("text-based adventure game! Let's dive in!");
        System.out.println("Type 'help' if you need help and all the available commands.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription() + "\n" + getInventory());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("pick")) {
            pickItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("exorcise")) {
            exorcise();
            wantToQuit = true;
        }
        else if (commandWord.equals("move")) {
            commandMoveRandom(command);
        }
        else if (commandWord.equals("provide")) {
            roomHint(command);
        }
        else if (commandWord.equals("i")) {
            iAmAfraid(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a list of the command words and how to
     * use them.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around in some old castle amidst ghosts. ");
        System.out.println("This is the life you have chosen. It's no easy task");
        System.out.println("ridding places of evil spirits. But Good Luck hero! - ");
        System.out.println("it's not impossible either");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("The \"back\" command takes you back to the previous room, ");
        System.out.println("ending up in the room you started with.");
        System.out.println();
        System.out.println("The \"pick\" command lets you pick up items. To pick up ");
        System.out.println("a specific item, type \"pick <item name>\" without the <>. Item names");
        System.out.println("are printed after their description, in square brackets.");
        System.out.println();
        System.out.println("The \"drop\" command lets your drop an item from your ");
        System.out.println("inventory. It is of the same form as the pick command i.e., ");
        System.out.println("\"drop <item name>.\"");
        System.out.println();
        System.out.println("The \"exorcise\" command lets you perform exorcism in the ");
        System.out.println("current room. Be sure to use it wisely though - it is a matter of ");
        System.out.println("life and death for you!");
        System.out.println();
        System.out.println("The \"move random room\" command transports you to a random room.");
    }

    /** 
     * Try to move in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        //int randomIndex = random.nextInt(allGameRooms.size());
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Can't go that way, human!");
        }
        else if (nextRoom.getShortDescription().equals(
                "a magic transporter room, which will transport you to a random room in 3..2..1")) {
            // if the room is magic transporter room
            moveRandomRoom(nextRoom);
            moveCharacters();
        }
        else {
            currentRoom = nextRoom;
            roomsStack.push(currentRoom);
            moveCharacters();
            System.out.println(currentRoom.getLongDescription() + "\n" + getInventory());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Go to previous room the user was in
     */
    private void goBack()
    {
        if (roomsStack.toArray().length > 1) {
            // if we have visited at least two rooms
            roomsStack.pop();   // remove the top most Room from the stack, i.e., the one in which player is currently
            currentRoom = roomsStack.peek();
            System.out.println(currentRoom.getLongDescription());
            System.out.println(getInventory());
            moveCharacters();
        }
        else {
            System.out.println("No Room to go back to!!!!");
        }
    }

    /**
     * Pick up the items for the player and store them in his inventory
     * @param command The command to specify which item in room to pick
     */
    private void pickItem(Command command)
    {
        String itemName = command.getSecondWord();
        // The item itself with it's name given in command:
        Item item = itemsMap.get(itemName);

        if (!command.hasSecondWord()) {
            // if the player only enters "pick" as the command (without a second word)
            System.out.println("Pick what, mate?");
            return;
        }
        else if (player.getItemsPicked().contains(item)) {
            // if the player already has this item in inventory
            System.out.println("You've picked this item up already!");
            return;
        }
        else {
            if (currentRoom.isItemInRoom(itemName)) {
                // if the item is in the current room
                if (player.getTotalWeight(item.getWeight()) <= 50 && item.isCanPickUp()) {
                    // if the total weight carried by the player does not exceed
                    // a maximum of 50 (kg) WITH the weight of the object which we are trying to
                    // pick up combined, and the item is declared it is able to
                    // be picked up
                    player.pickUpItem(item);
                    currentRoom.getItems().remove(item);   // remove the item from current room since it has been picked up
                    System.out.println("Picked up " + itemName);
                    System.out.println(getInventory());
                    System.out.println(currentRoom.getLongDescription());

                    return;
                }
                else if (!item.isCanPickUp()) {
                    // if the canPickUp field of item is false. i.e.,
                    // the item can not be picked up
                    System.out.println("This item can not be picked up");
                    return;
                }
                else if (player.getTotalWeight(item.getWeight()) > 50) {
                    System.out.println("Too heavy to pick up!");
                }
            }
            else {
                // if the item is not in the current room
                System.out.println("There is no such item in the room, bro!");
            }
        }
    }

    /**
     * @return A string containing all the items in the player's inventory
     */
    private String getInventory()
    {
        String inventory = "Item(s) in inventory: " + "\n";
        for (Item item : player.getItemsPicked()) {
            inventory += item.getName() + "\n";
        }
        return inventory;
    }

    /**
     * Drop an item from the player's inventory
     * @param command The command, specifying the object
     *                to drop
     */
    private void dropItem(Command command)
    {
        String itemName = command.getSecondWord();
        Item item = itemsMap.get(itemName);

        if (!command.hasSecondWord()) {
            // if the player only enters "drop" as the command (i.e., without a second word)
            System.out.println("Drop what?");
        }
        else if (!player.getItemsPicked().contains(item)) {
            // if no such item exists in the player's inventory
            System.out.println("There is no such item in the inventory, bro.");
        }
        else {
            player.dropItem(item);
            currentRoom.addItems(item);   // add the item dropped in the current room
            System.out.println(getInventory());
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Perform exorcism in the current room the player is in
     */
    private void exorcise()
    {
        Item itemWand = itemsMap.get("wand");
        Item itemStone = itemsMap.get("stone");
        Item itemBook = itemsMap.get("big-book");

        // check if the user has picked up the required items to successfully
        // perform exorcism:

        boolean correctItemsPicked = player.getItemsPicked().contains(itemBook) &&
                player.getItemsPicked().contains(itemStone) &&
                player.getItemsPicked().contains(itemWand);
        // check if the user is in the correct room to perform exorcism, i.e., dungeon:
        boolean correctRoom = currentRoom.getShortDescription().equals("in a dark, scary dungeon");

        if (correctItemsPicked && correctRoom) {
            // If we're currently in dungeon and our player is carrying correct items
            System.out.println("GAME WON!");
            System.out.println();
            System.out.println("CONGRATULATIONS! You have successfully performed exorcism!");
            System.out.println("The castle can finally be restored now!");
            System.out.println("The castle owners await you at dinner <3");
        }
        else if (!(correctItemsPicked || correctRoom)) {
            // if both the player is in the wrong room and he isn't carrying
            // correct items:
            System.out.println("GAME OVER!");
            System.out.println();
            System.out.println("You could've done better!");
            System.out.println("You weren't in the correct room to perform ");
            System.out.println("exorcism and neither were you carrying the ");
            System.out.println("items required for the exorcism to be successful.");
        }
        else if (!correctItemsPicked) {
            // The user does not have correct items to perform a successful exorcism
            System.out.println("GAME OVER!");
            System.out.println();
            System.out.println("The ghosts in castle have killed you since you did ");
            System.out.println("not have correct items for exorcism");
        }
        else if (!correctRoom) {
            System.out.println("GAME OVER!");
            System.out.println();
            System.out.println("You have been toasted by the demons here. You ");
            System.out.println("shouldn't have attempted exorcism in the wrong room.");
        }
    }

    /**
     * Simulate the movement of the characters in rooms. Used as
     * a supplement in other methods above. Called each time the player
     * moves across rooms.
     */
    private void moveCharacters()
    {
        for (Characters character : allGameCharacters) {
            int randomIndex = random.nextInt(allGameRooms.size());
            allGameRooms.get(randomIndex).addCharacter(character);
        }
    }

    /**
     * Implements the magic transporter room - moves the player to a random
     * room when the user enters this room.
     * @param room The transporter room
     */
    private void moveRandomRoom(Room room)
    {
        int randomIndex = random.nextInt(allGameRooms.size());
        System.out.println("You are in " + room.getShortDescription());
        currentRoom = allGameRooms.get(randomIndex);
        moveCharacters();
        System.out.println(currentRoom.getLongDescription());
        System.out.println(getInventory());
    }

    /**
     * Moves the player to a random room in the game when a three-word command
     * "move random room" is entered by the user
     * @param command The command provided by the user
     */
    private void commandMoveRandom(Command command)
    {
        int randomIndex = random.nextInt(allGameRooms.size());
        if (!(command.hasSecondWord() || command.hasThirdWord())) {
            System.out.println("No such three-word command");
        }
        else if (command.getSecondWord().equals("random") &&
                command.getThirdWord().equals("room")) {
            System.out.println("Transporting you to a random room in 3...2...1...");
            currentRoom = allGameRooms.get(randomIndex);
            System.out.println(currentRoom.getLongDescription());
            System.out.println(getInventory());
            moveCharacters();
        }
        else {
            System.out.println("Didn't quite get that, m8!");
        }
    }

    /**
     * A secret code to teleport the user to a room containing one of the items
     * required for successful exorcism
     * @param command The command itself
     */
    private void roomHint(Command command)
    {
        boolean correctCommand = command.getSecondWord().equals("room") &&
                command.getThirdWord().equals("hint");
        if (correctCommand) {
            ArrayList<Room> winningRooms = new ArrayList<>();
            for (Room room : allGameRooms) {
                if (room.getShortDescription().equals("in a spooky-looking courtyard") ||
                        room.getShortDescription().equals("in a kitchen having an odd smell") ||
                        room.getShortDescription().equals("in a dark, scary dungeon")) {
                    // if the room is either courtyard or dungeon or kitchen
                    winningRooms.add(room);
                }
            }
            int randomIndex = random.nextInt(winningRooms.size());
            System.out.println("You have used a hint!");
            moveCharacters();
            currentRoom = winningRooms.get(randomIndex);
            System.out.println(currentRoom.getLongDescription());
            System.out.println(getInventory());
        }
        else {
            System.out.println("Whoops! What did you mean?");
        }
    }

    /**
     * Teleport the user straight to the dungeon when the user
     * types "i am afraid" as the command.
     * @param command The command entered by the user
     */
    private void iAmAfraid(Command command)
    {
        boolean isAfraid = command.getSecondWord().equals("am") &&
                command.getThirdWord().equals("afraid");
        if (isAfraid) {
            for (Room room : allGameRooms) {
                if (room.getShortDescription().equals("in a dark, scary dungeon")) {
                    System.out.println("Don't be afraid next time, hero!");
                    currentRoom = room;
                    System.out.println(currentRoom.getLongDescription());
                    System.out.println(getInventory());
                    return;
                }
            }
        }
        else {
            System.out.println("Not sure what u mean :/");
        }
    }
}
