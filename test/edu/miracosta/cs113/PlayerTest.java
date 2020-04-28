package edu.miracosta.cs113;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player testPlayer = new Player("test", -1,23) ;

    @Test
    public void testNumMoves() {
        assertEquals(1, testPlayer.getNumMoves());
    }
    @Test
    public void testXCoordinate()
    {
        assertEquals(0, testPlayer.getxCoord());
    }
    @Test
    public void testYCoordinate()
    {
        assertEquals(23, testPlayer.getyCoord()) ;
    }
}