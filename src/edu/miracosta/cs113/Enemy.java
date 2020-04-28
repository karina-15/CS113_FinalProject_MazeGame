package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Public Enemy class extends Character class to create an object to be controlled by the user.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 * Problem: Create an enemy object that follows a predetermined routine of movement or uses the graph algorithm
 * to find the shortest path to reach the Player object.
 * Solution:
 */
public class Enemy extends Character
{
    // The finite amount of moves a player can do during an instance of a turn.
    private final int numMoves = 2 ;

    // Holds the Enemy object's sprite sheet.

    BufferedImage buffImage = null ;

    /**
     * Constructor of enemy objects.
     * @param name   Given name.
     * @param xCoord Starting x-coordinate.
     * @param yCoord Starting y-coordinate.
     */
    public Enemy(String name, int xCoord, int yCoord) {
        /** Creates an Enemy object using the Character constructor. */
        super(name, xCoord, yCoord);

        /** Opens and holds the full sprite sheet of an Enemy object. */

        try
        {
            buffImage = ImageIO.read(new File("resources/megaGengarSprite.png")) ;
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
