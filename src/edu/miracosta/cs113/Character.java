package edu.miracosta.cs113;

/**
 * Public abstract class Character will be the parent class for all objects being used in the World class.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 */
public abstract class Character
{
    // Character's name.
    private String name ;

    // Used to determine the position of this Character.
    private int xCoord ;
    private int yCoord ;

    /**
     * Constructor of Character objects.
     * @param name Given name.
     * @param xCoord Starting x-coordinate.
     * @param yCoord Starting y-coordinate.
     */
    public Character(String name, int xCoord, int yCoord)
    {
        // Any entered name is valid.
        this.name = name ;

        /** Makes sure coordinate trying to escape the boundaries of the x and y bounds of JFrames is never allowed. */
        this.xCoord = validCoordinate(xCoord) ;
        this.yCoord = validCoordinate(yCoord) ;

    }

    /**
     * Returns the given Character's name.
     * @return Name of Character.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for a given Character.
     * @param name to be changed to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the x-coordinate of a given Character.
     * @return X-coordinate of Character.
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     * Sets a new x-coordinate for a given Character.
     * @param xCoord to be changed to.
     */
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * Gets the y-coordinate of a given Character.
     * @return Y-coordinate of Character.
     */
    public int getyCoord() {
        return yCoord;
    }

    /**
     * Sets a new y-coordinate for a given Character.
     * @param yCoord to be changed to.
     */
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }


    /**
     * To be implemented in derivative classes. Unfortunately was never used in the current game.
     * @return Number of moves allotted to a child of Character.
     */
    public abstract int getNumMoves() ;

    /**
     * Ensures that only in-bounds coordinates are given.
     * @param coordinate
     * @return Either the entered coordinate or 0.
     */
    private int validCoordinate(int coordinate)

    {
        // If the coordinate is less than the 0 then it cannot be used.
        if(coordinate < 0)
        {
            return 0 ;
        }
        else
        {
            return coordinate ;

        }
    }

}
