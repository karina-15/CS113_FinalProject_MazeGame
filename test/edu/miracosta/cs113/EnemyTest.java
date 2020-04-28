package edu.miracosta.cs113;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyTest {

    private Enemy testEnemy = new Enemy("test", -98,19) ;

    @Test
    public void testNumMoves() {
        assertEquals(2, testEnemy.getNumMoves());
    }
    @Test
    public void testXCoordinate()
    {
        assertEquals(0, testEnemy.getxCoord());
    }
    @Test
    public void testYCoordinate()
    {
        assertEquals(19, testEnemy.getyCoord()) ;
    }

}