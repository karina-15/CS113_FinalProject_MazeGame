package edu.miracosta.cs113;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObstacleTest {

    private Obstacle testObstacle = new Obstacle( -123,18) ;

    @Test
    public void testNumMoves() {
        assertEquals(0, testObstacle.getNumMoves());
    }
    @Test
    public void testXCoordinate()
    {
        assertEquals(0, testObstacle.getxCoord());
    }
    @Test
    public void testYCoordinate()
    {
        assertEquals(18, testObstacle.getyCoord()) ;
    }
}