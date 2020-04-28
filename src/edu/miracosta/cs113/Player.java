package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Public Player class extends Character class to create an object to be controlled by the user.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 * Problem: Create a Player object that allows movement on a World object.
 * Solution:
 */
public class Player extends Character
{
    // The finite amount of moves a player can do during an instance of a turn.
    private final int numMoves = 1 ;

    // Holds the Player object's sprite sheet.

    BufferedImage buffImage = null ;

    /**
     * Constructor of player objects.
     * @param name   Given name.
     * @param xCoord Starting x-coordinate.
     * @param yCoord Starting y-coordinate.
     */
    public Player(String name, int xCoord, int yCoord) {
        /** Creates a Player object using the Character constructor. */
        super(name, xCoord, yCoord) ;


        /** Opens and holds the full sprite sheet of a Player object. */
        try
        {
            buffImage = ImageIO.read(new File("resources/redSprite.png")) ;
        } catch(IOException e)
        {
            System.exit(0) ;
        }
    }

    @Override
    public int getNumMoves() {
        return numMoves ;
    }

    /**
     * Gets the sprite of the player facing left from the full sprite sheet.
     * @return A bufferedImage of the player facing left.
     */
    public BufferedImage displayLeftImage()
    {
        return buffImage.getSubimage(0,32,32,32) ;
    }
    /**
     * Gets the sprite of the player facing right from the full sprite sheet.
     * @return A bufferedImage of the player facing right.
     */
    public BufferedImage displayRightImage()
    {
        return buffImage.getSubimage(0,64,32,32) ;
    }
    /**
     * Gets the sprite of the player facing up from the full sprite sheet.
     * @return A bufferedImage of the player facing up.
     */
    public BufferedImage displayUpImage()
    {
        return buffImage.getSubimage(0,96,32,32) ;
    }
    /**
     * Gets the sprite of the player facing down from the full sprite sheet.
     * @return A bufferedImage of the player facing down.
     */
    public BufferedImage displayDownImage()
    {
        return buffImage.getSubimage(0,0,32,32) ;
    }
}
