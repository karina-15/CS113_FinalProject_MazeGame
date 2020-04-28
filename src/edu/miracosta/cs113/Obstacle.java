package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Public Obstacle class extends Character class to create an object to be controlled by the user.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 * Problem: Create an Obstacle object that will be inserted into a World object, it should have some sort of sprite to
 * depict it.
 * Solution:
 */
public class Obstacle extends Character {
    // An obstacle should never move.
    private final int numMoves = 0 ;

    // Holds the Obstacle object's sprite sheet.
    BufferedImage buffImage = null ;

    /**
     * Constructor of Obstacle objects.
     * @param xCoord Starting x-coordinate.
     * @param yCoord Starting y-coordinate.
     */
    public Obstacle(int xCoord, int yCoord) {
        /** Creates an Obstacle object using the Character constructor. */
        super("Obstacle", xCoord, yCoord) ;

        /** Opens and holds the full sprite sheet of an Obstacle object. */

        try
        {
            buffImage = ImageIO.read(new File("resources/treeSprite.png")) ;
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
     * Gets the sprite of the obstacle from the sprite sheet.
     * @return A bufferedImage of obstacle.
     */
    public BufferedImage displayImage()
    {
        return buffImage.getSubimage(0,0,48,64) ;
    }

}
