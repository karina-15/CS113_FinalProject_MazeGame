package edu.miracosta.cs113;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Public World class will hold Player, Enemy, & Obstacle objects.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 * Problem: Create a class that holds and uses all other Character derived objects to run a game.
 * Solution:
 *
 *
 * NOTE: CLASS DESIGNED TO WORK WITH THE GUI  CLASS
 */
public class World {
    // Unsure if we an get rid of these Grid objects...
    public static final Grid OBSTACLE = new Grid(Color.GREEN);
    private Grid[][] grids;

    /** ArrayLists to hold enemies and obstacles of World Objects. */
    private ArrayList<Enemy> enemyList;
    private ArrayList<Obstacle> obstacleList;

    // The Player character to be manipulated by the user.
    private Player player;

    // Graph stuff
    private MatrixGraph graph;

    // Default conditions for enemies.
    private static final int DEFAULT_AMOUNT_OF_MOVES = 3;
    private static final int DEFAULT_ENEMIES = 3;


    // Bounds of the game field.
    private int rows;
    private int columns;

    // 2D array representation of the map used for the enemies to know where they are .
    private int[][] coordinates;

    private final int WINXPOSITION = 18 ;
    private final int WINYPOSITION = 1 ;

    /** Default constructor uses the rows and columns constructor to create World Objects. */
    public World() {
        this(20,20);
    }

    /**
     * Rows and columns constructor makes the bounds of the game field based on input.
     * @param rows - Size of the X-axis.
     * @param columns - Size of the Y-axis.
     */
    public World(int rows, int columns) {
        /** Sets the rows and columns of the World and initializes the Grid[][] using them. */
        this.rows = rows;
        this.columns = columns;
        grids = new Grid[rows][columns];

        /** Initializes the int[][] coordinates and increments the value inserted as it runs. */
        coordinates = new int[rows][columns];
        this.fillCoordinates();

        /** Initializes the Enemy ArrayList and adds a few default enemies. */
        enemyList = new ArrayList<Enemy>();
        this.addEnemies();
        System.out.println( " SIZE" + enemyList.size());

        /** Initializes the Obstacle ArrayList and adds default Obstacles. */
        obstacleList = new ArrayList<>();
        this.addDefaultObstacles();

        // Initializes the Player.
        player = new Player("Player one", 1,18);
        // Initializes the MatrixGraph using the given World.
        graph = new MatrixGraph(this);
    }
    /**
     * Enemies constructor used for testing purposes.
     * @param enemies - Number of desired enemies.
     */
    public World(int enemies) {
        /** Initializes the Enemy ArrayList. */
        enemyList = new ArrayList<>();
        /** Initializes the Obstacle ArrayList. */
        obstacleList = new ArrayList<>();

        // Initializes the Player.
        player = new Player("Player one", 6,6);
        // Initializes the MatrixGraph using the given World.

        graph = new MatrixGraph(this);

    }

    /**
     * Allows game to reset
     */
    @Override
    public void finalize() {

    }

    /**
     * Created a 2d array representing the map. Used by the moveEnemy() method to know in which position the player is located
     */
    private void fillCoordinates() {
        /** Single value of the current */
        int counter = 0;
        /** Fills the coordinates with incremental values. */
        for (int i = 0; i < this.rows; i++) {
            for (int j =0; j < this.columns; j++) {
                coordinates[j][i] = counter;
                counter++;

            }
        }
    }
    //METHOD ONLY USED FOR THE TESTS
    public void addEnemy(int x, int y) {
        enemyList.add(new Enemy(""+ enemyList.size(), x, y));
    }

    /**
     * METHOD USED TO ADD OBSTAClES IN A SPECIFIC SECTION (NOTE THIS IS ONLY USED FOR TESTING)
     * @param x the X position
     * @param y the Y Position
     */
    public void addObstacles(int x, int y) {
        obstacleList.add(new Obstacle(x,y));
    }

    /**
     * Adds default enemies to the Enemy ArrayList.
     */
    private void addEnemies() {
        this.addEnemy(10,1);
        this.addEnemy(3,6);
        this.addEnemy(18, 11);
    }

    /**
     * Method that loads the pbstacles into the map by reading their positions from the obstacle file (NOTE to add more obstacles
     *      edit the obstacles.txt file. FORMATTING: Each like is a new obstacle The first number in the like is the X position and the second number
     *      is the Y position. NOTE: X and Y position need to be separated by a space)
     */
    private void addDefaultObstacles() {
        try {
            Scanner file = new Scanner(new FileReader("obstacles.txt"));
            while (file.hasNext()) {
                obstacleList.add(new Obstacle(file.nextInt(), file.nextInt()));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        for (int i =0; i < obstacleList.size(); i++) {
            System.out.println(obstacleList.get(i).getxCoord() + " " + obstacleList.get(i).getyCoord());
        }

    }


    /**
     * Method to move the player to its new position it also checks for obstacles in the way when trying to move
     * @param keyPressed The key pressed as a String
     * @return True if the player has successfully moved
     */

    public boolean playerMove(String keyPressed) {
        String a = keyPressed;
        boolean playerMoved = false;
        /**
         * UP 38
         * LEFT 37
         * RIGHT 39'
         * DOWN 40
         */

        // Checking if there is an obstacle before moving. If an obstacle is in the position that the playter is trying to move to
        // The player will not move
        if (!checkForObstacle(keyPressed)) {
            // Moving the player based on the user input (arrow keys)
                switch (a) {
                    case "37":

                        if (getPlayerXPosition() >0) {
                            player.setxCoord(player.getxCoord() - 1);
                            System.out.println("Making sure: UP");

                            playerMoved = true;
                        }
                        break;
                    case "39":
                        if (getPlayerXPosition() < 19) {
                            player.setxCoord(player.getxCoord() + 1);
                            System.out.println("Making sure: DOWN");

                            playerMoved = true;
                        }
                        break;
                    case "38":

                        if (getPlayerYPosition() > 1) {

                            player.setyCoord(player.getyCoord() - 1);
                            System.out.println("Making sure: UP");
                            playerMoved = true;
                        }
                        break;

                    case "40":
                        if (getPlayerYPosition() < 19) {
                            player.setyCoord(player.getyCoord() + 1);


                            System.out.println("Making sure: RIGHT");
                            playerMoved = true;
                        }
                        break;

                }
            }
        System.out.println("PLAYER " + getPlayerXPosition() + " " + getPlayerYPosition());

        // Moving the enemies ONLY if the player moved
        if (playerMoved) {
            this.moveEnemies();
        }
        for (Enemy s: enemyList) {
            System.out.println( " ENEMY " +s.getxCoord()+" " + s.getyCoord());
        }
        return playerMoved;
    }

    /**
     * Method that will be used after the player has moved the allowed amount of times. This method will move every enemy
     *      to its next position in the map
     * @return True after all the enemy positions have been updated
     */

    public boolean moveEnemies() {
        int enemyX;
        int enemyY;
        int position;
        int playerX = this.getPlayerXPosition();
        int playerY = this.getPlayerYPosition();
        int[] path;
        // If the player is in the same position as the enemy the enemy will NOT move
        if (!checkForHit()) {
            // Looping through all the enemies
            for (int i = 0; i < enemyList.size(); i++) {
                enemyX = enemyList.get(i).getxCoord();
                enemyY = enemyList.get(i).getyCoord();
                // Getting the path from the enemy to the player position
                if (!(enemyX == 0 && enemyY == 0)) {
                    // Using the coordinates 2d array to calculate the position of the enemy
                    position = coordinates[enemyList.get(i).getxCoord()][enemyList.get(i).getyCoord()];
                    // Getting the path using the getPath method on the graph object
                    path = graph.getPath(coordinates[enemyX][enemyY], coordinates[playerX][playerY]);

                    // MOVING THE ENEMY DEPENDING ON THE GIVEN ARRAY CONTAINING THE PATH

                    if (path[0] > position + 1) {
                        enemyList.get(i).setyCoord(enemyY + 1);
                        if (checkForHit()) {
                            System.out.println("Enemy and player in the same position");
                        }
                    } else if (path[0] < position - 1) {
                        enemyList.get(i).setyCoord(enemyY - 1);
                        if (checkForHit()) {
                            System.out.println("Enemy and player in the same position");
                        }
                    } else if (path[0] == position + 1) {
                        enemyList.get(i).setxCoord(enemyX + 1);
                        if (checkForHit()) {
                            System.out.println("Enemy and player in the same position");
                        }
                    } else {
                        enemyList.get(i).setxCoord(enemyX - 1);
                        if (checkForHit()) {
                            System.out.println("Enemy and player in the same position");
                        }
                    }
                }
            }
        }
        return true;
    }



    /**
     * Method to check if the player is in the same spot as an enemy
     * @return True if the enemy and player are in the same spot
     */
    // TO BE USED IN THE GameGUI class
    public boolean checkForHit() {
        for (Enemy temp: enemyList) {
            if (player.getxCoord() == temp.getxCoord() && player.getyCoord() == temp.getyCoord()) {
                return true;
            }
        }
        return false;
    }



    /**
     * UP 38
     * LEFT 37
     * RIGHT 39'
     * DOWN 40
     */
    /**
     * Method called before the player can  move the check if the position to where they are trying to move is empty
     * @param keyPressed The direction that the player is trying to move
     * @return True if there is an obstacle in the way false if there isn't any objects in the next movement
     */
    // Used before a movement
    private boolean checkForObstacle(String keyPressed) {
        switch (keyPressed) {
            case "38": for (Obstacle temp: obstacleList) {
                if (player.getyCoord()-1 ==  temp.getyCoord() && player.getxCoord() == temp.getxCoord()){
                    return true;
                }
            }
                break;
            case "40": for (Obstacle temp: obstacleList) {
                if (player.getyCoord()+1 ==  temp.getyCoord() && player.getxCoord() == temp.getxCoord()){
                    return true;
                }
            }
                break;
            case "37": for (Obstacle temp: obstacleList) {
                if (player.getxCoord()-1 ==  temp.getxCoord() && player.getyCoord() == temp.getyCoord()){
                    return true;
                }
            }
                break;
            case "39": for (Obstacle temp: obstacleList) {
                if (player.getxCoord()+1 ==  temp.getxCoord() && player.getyCoord() == temp.getyCoord()){
                    return true;
                }
            }
                break;
        }
        System.out.println("FALSE");
        System.out.println(obstacleList.size());
        return false;
    }

    // FOr testing and movemtn in the GUI
    public String getPlayerPosition() {
        return player.getxCoord() + "." + player.getyCoord();
    }
    public int getPlayerXPosition() {
        return player.getxCoord();
    }
    public int getPlayerYPosition() {
        return player.getyCoord();
    }

    // accessor to get grid location
    public Grid getLocation(int x, int y) {
        return grids[x][y];
    }

    // accessor for 2D array
    public Grid[][] getGrids() {
        return grids;
    }

    // accessor for rows
    public int getRows() {
        return rows;
    }

    // accessor for columns
    public int getColumns() {
        return columns;
    }

    // accessor for graph
    public MatrixGraph getGraph() {
        return graph;
    }

    // mutator for graph
    public void setGraph(MatrixGraph graph) {
        this.graph = graph;
    }

    // create 1D index from 2D index
    public int getXIndex(int row, int column) {
        return column + row * columns;
    }

    // create 2D index from 1D index
    public int[] getYIndex(int index) {
        int column = index % columns;
        int row = index / columns;
        return new int[]{row, column};
    }

    /**
     * method to get the player
     * @return The player Object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method to get all of the enemies in the world
     * @return An array with all the enemy Objects in the world
     */
    public Enemy[] getAllEnemies() {
        Enemy[] enemyArray = new Enemy[enemyList.size()];
        int count = 0;
        for (Enemy temp: enemyList) {
            enemyArray[count] = temp;
            count++;
        }
        return enemyArray;
    }

    /**
     * Method to get an array containing all of the obstacles that there are in the map
     * @return An Array containing all of the obstacles objects
     */
    public Obstacle[] getAllObstacles() {
        Obstacle[] obstacleArray = new Obstacle[obstacleList.size()];
        for (int i =0; i < obstacleList.size(); i++) {
            obstacleArray[i] = obstacleList.get(i);
        }
        return obstacleArray;
    }


    /**
     * Method used on the MatrixGraph class to remove the vertex where the obstacles are located
      * @param currentIndex
     * @return
     */
    public boolean testForObstacle(int  currentIndex) {
        for (Obstacle temp: obstacleList) {
            int index = coordinates[temp.getxCoord()][temp.getyCoord()];
            if (index == currentIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if the player is in the position to give him a victory
     * @return True if the player is in the position false if the player isnt in the position
     */
    public boolean winCondition() {
        if (getPlayerXPosition() == WINXPOSITION && getPlayerYPosition() == WINYPOSITION) {
            return true;
        }
        return false;
    }

    /**
     * Returns the X-coordinate of the win position.
     * @return x-coordinate of win position.
     */
    public int getWINXPOSITION()
    {
        return WINXPOSITION ;
    }

    /**
     * Returns the Y-coordinate of the win position.
     * @return y-coordinate of win position.
     */
    public int getWINYPOSITION()
    {
        return WINYPOSITION ;
    }
}
