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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    public static void main(String[] args) {
        Game g = new Game();
        g.play();
    }

    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room greatHall, bedRoom, courtyard, kitchen, dungeon, solarRoom,
                bathroom, garden, longPassage, guardRoom;

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

        // initialise room exits and items in the rooms
        greatHall.setExit("south", courtyard);
        greatHall.addItems(new Item("a bloody spear",
                20,
                true));
        greatHall.addItems(new Item("a huge portrait of Mona Lisa looking angry",
                100,
                false));
        greatHall.addItems(new Item("a broken piece of some statue",
                250,
                false));
        greatHall.addItems(new Item("a huge round table, turned upside-down",
                400,
                false));
        greatHall.addItems(new Item("a sharp-edged sword",
                15,
                true));

        bedRoom.setExit("east", courtyard);
        bedRoom.setExit("south", bathroom);
        bedRoom.addItems(new Item("a surprisingly intact queen-sized bed in the corner",
                500,
                false));
        bedRoom.addItems(new Item("a broken big mirror",
                100,
                true));
        bedRoom.addItems(new Item("a bookshelf containing books of spells",
                50,
                false));
        bedRoom.addItems(new Item("a piece of broken arrow on the floor",
                2,
                true));

        courtyard.setExit("north", greatHall);
        courtyard.setExit("east", kitchen);
        courtyard.setExit("south", guardRoom);
        courtyard.setExit("west", bedRoom);
        courtyard.addItems(new Item("a broken glass vase",
                4,
                true));
        courtyard.addItems(new Item("a statue of knight on the right hand side",
                500,
                false));
        courtyard.addItems(new Item("a bigger statue of a knight on the left hand side",
                800,
                false));
        courtyard.addItems(new Item("a small wand-like object",
                1,
                true));

        kitchen.setExit("east", solarRoom);
        kitchen.setExit("west", courtyard);
        kitchen.addItems(new Item("a pair of dagger-like knives",
                2,
                true));
        kitchen.addItems(new Item("a big stick",
                5,
                true));
        kitchen.addItems(new Item("a set of broken cutlery levitating in air",
                500,
                false));
        kitchen.addItems(new Item("a big stone on the floor from which strange " +
                "noises can be heard",
                20,
                true));

        dungeon.setExit("north", longPassage);
        dungeon.addItems(new Item("a steel bar on floor",
                30,
                true));
        dungeon.addItems(new Item("a long sharp spear inside a cell",
                20,
                true));
        dungeon.addItems(new Item("a broken wooden door on the floor",
                40,
                false));
        dungeon.addItems(new Item("a rather big book with light-rays coming out of it",
                5,
                true));

        solarRoom.setExit("east", garden);
        solarRoom.setExit("west", kitchen);
        solarRoom.addItems(new Item("a chair with one leg missing, facing towards you",
                30,
                true));
        solarRoom.addItems(new Item("black curtains covering the window in front",
                5,
                true));
        solarRoom.addItems(new Item("a broken cup, placed upside down on floor",
                2,
                true));
        solarRoom.addItems(new Item("a sharp piece of broken mirror placed on the chair",
                6,
                true));

        garden.setExit("west", solarRoom);
        garden.addItems(new Item("an empty kennel in dark shadows",
                100,
                false));
        garden.addItems(new Item("a big swing, swinging for no apparent reason since the last 20 years",
                100,
                false));
        garden.addItems(new Item("thousands of bats flying here everywhere",
                25,
                false));

        bathroom.setExit("north", bedRoom);
        bathroom.addItems(new Item("pages of some kind levitating in the air",
                1,
                true));
        bathroom.addItems(new Item("a glowing piece of wood",
                2,
                true));

        longPassage.setExit("upstairs", guardRoom);
        longPassage.setExit("south", dungeon);
        longPassage.addItems(new Item("a medieval era lamp",
                2,
                true));
        longPassage.addItems(new Item("a portrait on the left with ancient scribblings",
                20,
                true));

        guardRoom.setExit("downstairs", longPassage);
        guardRoom.setExit("north", courtyard);
        guardRoom.addItems(new Item("a skeleton on a chair with sword in it's hand",
                30,
                true));
        guardRoom.addItems(new Item("a pike on the left wall",
                12,
                true));
        guardRoom.addItems(new Item("a statue of a knight",
                300,
                false));

        currentRoom = courtyard;  // start game outside
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
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
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
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
}
