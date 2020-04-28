package edu.miracosta.cs113;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

    private World testWorld = new World();


    /**
     * UP 38
     * LEFT 37
     * RIGHT 39'
     * DOWN 40
     */

    @Test
    public void playerMove() {
        String oldPosition = testWorld.getPlayerPosition();
        // manually moving the player
        testWorld.playerMove("38");
        testWorld.playerMove("37");
        System.out.println(oldPosition);
        System.out.println(testWorld.getPlayerPosition());
        assertFalse("THIS IS A TEST", oldPosition.equalsIgnoreCase(testWorld.getPlayerPosition()));
    }

    @Test
    public void enemyMoving() {
        Enemy[] temp = testWorld.getAllEnemies();
        int x = temp[0].getxCoord();
        int y = temp[0].getyCoord();
        assertTrue(testWorld.moveEnemies());
        // CHecking only if the first enemy moved. If it moved everything else also moved
        assertTrue(temp[0].getxCoord() != x | temp[0].getyCoord() != y );
    }

    /**
     * UP 38
     * LEFT 37
     * RIGHT 39'
     * DOWN 40
     */

    @Test
    public void  testHit() {
        // No enemies in the same position as the player
        assertFalse(testWorld.checkForHit());
        // MOVING THE PLAYER TO THE ENEMY POSITION
        testWorld.addEnemy(testWorld.getPlayerXPosition(),testWorld.getPlayerYPosition());
        System.out.println(testWorld.getPlayerPosition());

        assertTrue(testWorld.checkForHit());
    }
    
    @Test
    public void testObstacle() {
        // Player starting position is 1,18
        // Placing an obstacle in front of the player
        testWorld.addObstacles(2,18);
        int oldX = testWorld.getPlayerXPosition();
        int oldY = testWorld.getPlayerYPosition();
        testWorld.playerMove("39");

        // player position will not change due to an obstacle being in front of him
        assertEquals(oldX , testWorld.getPlayerXPosition());
        assertEquals(oldY, testWorld.getPlayerYPosition());
    }
}
