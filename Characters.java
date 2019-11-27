/**
 * This class stores information about all the characters in the game, excluding the player
 */

public class Characters
{
    private String name;

    public Characters(String name)
    {
        this.name = name;
    }

    /**
     * @return The short name of the character
     */
    public String getName()
    {
        return name;
    }
}