package edu.miracosta.cs113;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * GameGUI class made to create the game field based on specifications made in the World class.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 */
public class GameGUI extends JFrame {

    /** Note: The Player and Enemy movement/redrawing is handled in the KeyPressed ActionListener. */

    /** JPanels for the Menu. */
    private JPanel menuPanel, treesPanel, gamePanel, gameBoardPanel,
            gameButtonPanel, howToPanel, howToTitlePanel, titleNamePanel,
            buttonPanel;

    /** JLabels for the title menu. */
    private JLabel titleNameLabel, howToTitleLabel;

    /** Buttons used throughout the GUI. */
    private JButton startButton, howToButton, quitButton, howToBackButton,
            gameBackButton, restartGameButton;

    /** Text holder used in the How To Menu. */
    private JTextArea howToTextArea;

    /** Font size differentiation for the GUI.*/
    private Font titleFont = new Font("arial", Font.BOLD, 80);
    private Font buttonFont = new Font("arial", Font.BOLD, 20);

    /** Listeners for keyboard and mouse */
    protected KeyListener keyboard = new Keyboard();
    TitleScreenHandler titleHandler = new TitleScreenHandler();

    /** World gives the GameGUI its rules/boundaries. */
    private World world = new World();
    private JPanel[][] jPanels ;
    private Obstacle[] obstacles ;
    private Enemy[] enemies ;

    /** Keeps track of the current coordinates of the player. */
    private int currentX ;
    private int currentY ;
    /** Keeps track of the previous coordinates the player was at. */
    private int prevX = 1 ;
    private int prevY = 1 ;

    /** Enemy x-coord and y-coord tracker. */
    private int[] enemyxCoords ;
    private int[] enemyyCoords ;

    /** Keeps track of the previous coordinates the enemies were at. */
    private int[] prevEnemyxCoords ;
    private int[] prevEnemyyCoords ;

    /**
     * Default GameGUI constructor.
     */
    public GameGUI() {
        /** Basic JFrame setup. */
        super("Maze Game");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /** Creates the startMenu when the GameGUI is made. */
        startMenu();
    }

    /** Method to start main menu window */
    public void startMenu() {

        /** Menu window panel */
        menuPanel = new JPanel();
        menuPanel.setBackground(Color.decode("#3d7d00"));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(new Insets(300, 200, 300, 200)));

        /** Title */
        titleNamePanel = new JPanel();
        titleNamePanel.setOpaque(false);
        titleNameLabel = new JLabel("MAZE GAME");
        titleNameLabel.setFont(titleFont);
        titleNameLabel.setForeground(Color.BLACK);

        /** Trees Panel */
        treesPanel = new JPanel();
        treesPanel.setOpaque(false);

        /** Button panel */
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        /** "Start Game" Button */
        startButton = new JButton("START GAME");
        startButton.setFocusPainted(false);
        startButton.setFont(buttonFont);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(titleHandler);

        /** "How To Play" Button */
        howToButton = new JButton("HOW TO PLAY");
        howToButton.setFocusPainted(false);
        howToButton.setFont(buttonFont);
        howToButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToButton.addActionListener(titleHandler);

        /** "Quit" Button */
        quitButton = new JButton("QUIT");
        quitButton.setFocusPainted(false);
        quitButton.setFont(buttonFont);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addActionListener(titleHandler);

        /** Adds panels, labels, buttons to window */
        titleNamePanel.add(titleNameLabel);
        menuPanel.add(titleNamePanel);
        /** Adds tree images */
        try {
            BufferedImage buffImage = ImageIO.read(new File("resources/treeSprite.png")) ;
            for(int i = 0; i < 10; i++){
                treesPanel.add(new JLabel (new ImageIcon(buffImage.getSubimage(0,0,48,64)))) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuPanel.add(treesPanel);
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(howToButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(quitButton);
        menuPanel.add(buttonPanel);
        getContentPane().add(menuPanel);
        setVisible(true);
    }

    /** Method to start game window */
    public void startGame() {

        /** Starts game */
        world = new World();

        /** Game window panel */
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.decode("#3d7d00"));
        gamePanel.setLayout(new BorderLayout());

        /** Game grid panel */
        gameBoardPanel = new JPanel();
        gameBoardPanel.setOpaque(false);
        gameBoardPanel.setLayout(new GridLayout(world.getColumns(), world.getRows()));

        /** Buttons panel at bottom of game window */
        gameButtonPanel = new JPanel();
        gameButtonPanel.setOpaque(false);
        gameButtonPanel.setLayout(new BoxLayout(gameButtonPanel, BoxLayout.X_AXIS));
        gameButtonPanel.setBorder(new EmptyBorder(new Insets(2, 350, 5, 0)));

        /** Reset/Restart game button */
        restartGameButton = new JButton("RESET GAME");
        restartGameButton.setFont(buttonFont);
        restartGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartGameButton.addActionListener(titleHandler);

        /** Back button to return to main menu */
        gameBackButton = new JButton("BACK");
        gameBackButton.setFont(buttonFont);
        gameBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameBackButton.addActionListener(titleHandler);

        /** Add panels, and buttons to window */
        gamePanel.add(gameBoardPanel, BorderLayout.CENTER);
        gameButtonPanel.add(restartGameButton);
        gameButtonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        gameButtonPanel.add(gameBackButton);
        gamePanel.add(gameButtonPanel, BorderLayout.SOUTH);

        /** Holds all the JPanels in the JFrame that are allowed for the game to function.*/
        jPanels = new JPanel[world.getColumns()][world.getRows()] ;

        /** All obstacles to be inserted into the JFrame. */
        obstacles = new Obstacle[80] ;

        /** Adds the borders of the JFrame using Obstacles. */
        for(int a = 0 ; a < 20 ; a++)
        {
            obstacles[a] = new Obstacle(0, a) ;
        }
        for(int a = 0 ; a < 20 ; a++)
        {
            obstacles[a+20] = new Obstacle(19, a) ;
        }
        for(int a = 0 ; a < 20 ; a++)
        {
            obstacles[a+40] = new Obstacle(a, 0) ;
        }
        for(int a = 0 ; a < 20 ; a++)
        {
            obstacles[a+60] = new Obstacle(a, 19) ;
        }

        /** Adds all of the JPanels in the JPanel[] to the JFrame. */
        for(int rows = 0 ; rows < jPanels.length ; rows++)
        {
            for(int columns = 0 ; columns < jPanels[rows].length ; columns++)
            {
                jPanels[columns][rows] = new JPanel() ;
                jPanels[columns][rows].setOpaque(false);
                gameBoardPanel.add(jPanels[columns][rows]) ;
                gameBoardPanel.revalidate();
                gameBoardPanel.repaint();
                gameBoardPanel.setVisible(true);
            }
        }
        /** Adds all of the obstacles in the obstacle[] into the JPanel[]. */
        for(int c = 0 ; c < obstacles.length ; c++)
        {
            if(obstacles[c] != null)
            {
                currentX = obstacles[c].getxCoord() ;
                currentY = obstacles[c].getyCoord() ;
                jPanels[currentX][currentY].add(new JLabel (new ImageIcon(obstacles[c].displayImage()))) ;
                jPanels[currentX][currentY].revalidate();
                jPanels[currentX][currentY].repaint();
                jPanels[currentX][currentY].setVisible(true);
            }
        }

        /**
         * Adding obstacles to the map
         */
        obstacles = world.getAllObstacles();
        for (int i =0; i < obstacles.length; i++) {
            currentX = obstacles[i].getxCoord();
            currentY = obstacles[i].getyCoord();
            jPanels[currentX][currentY].add(new JLabel (new ImageIcon(obstacles[i].displayImage()))) ;
            jPanels[currentX][currentY].revalidate();
            jPanels[currentX][currentY].repaint();
            jPanels[currentX][currentY].setVisible(true);

        }
        /** Adds the player to their starting position. */
        prevX = world.getPlayerXPosition() ;
        prevY = world.getPlayerYPosition() ;

        jPanels[prevX][prevY].removeAll() ;
        jPanels[prevX][prevY].add(new JLabel(new ImageIcon(world.getPlayer().displayDownImage()))) ;
        setVisible(true);

        /** Gets all of the enemies in the World. */
        enemies = world.getAllEnemies() ;
        /** Sets the length of the x and y coordinate tracker to amount enemies in the World. */
        enemyxCoords = new int[enemies.length] ;
        enemyyCoords = new int[enemies.length] ;

        prevEnemyxCoords = new int[enemies.length] ;
        prevEnemyyCoords = new int[enemies.length] ;

        /** Adds the default enemies in their starting position. */
        for(int a = 0; a < enemies.length ; a++)
        {
            prevEnemyxCoords[a] = enemies[a].getxCoord() ;
            prevEnemyyCoords[a] = enemies[a].getyCoord() ;

            jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].removeAll() ;
            jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].add(new JLabel(new ImageIcon(enemies[a].displayDownImage()))) ;
            setVisible(true) ;
        }

        /** Win position distinction. */
        jPanels[world.getWINXPOSITION()][world.getWINYPOSITION()].add(new JLabel(new ImageIcon("resources/pokeball2Resized.png"))) ;


        addKeyListener(keyboard);

        setFocusable(true);
        requestFocusInWindow();

        getContentPane().add(gamePanel);
        revalidate();
        repaint();


        setVisible(true);

    }

    /** Method to start How To Play window */
    public void howToScreen() {

        /** create how to play screen*/
        howToPanel = new JPanel();
        howToPanel.setLayout(new BoxLayout(howToPanel, BoxLayout.Y_AXIS));
        howToPanel.setBorder(new EmptyBorder(new Insets(100, 200, 100, 200)));
        howToPanel.setBackground(Color.decode("#3d7d00"));

        /** How To Play Title */
        howToTitlePanel = new JPanel();
        howToTitlePanel.setOpaque(false);
        howToTitleLabel = new JLabel("HOW TO PLAY");
        howToTitleLabel.setFont(titleFont);
        howToTitleLabel.setForeground(Color.BLACK);

        /** Instruction TextArea */
        howToTextArea = new JTextArea(
                "Use the arrow keys to move the character to " +
                        "the goal mark while avoiding all enemies. Game " +
                        "ends when goal is reached or when you bump " +
                        "into an enemy.");
        howToTextArea.setOpaque(false);
        howToTextArea.setFont(buttonFont);
        howToTextArea.setForeground(Color.BLACK);
        howToTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToTextArea.setEditable(false);
        howToTextArea.setLineWrap(true);
        howToTextArea.setWrapStyleWord(true);

        /** Back to Main Menu Button */
        howToBackButton = new JButton("BACK");
        howToBackButton.setFocusPainted(false);
        howToBackButton.setFont(buttonFont);
        howToBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToBackButton.addActionListener(titleHandler);

        /** Adds panels, labels, text area,buttons to window */
        howToTitlePanel.add(howToTitleLabel);
        howToPanel.add(howToTitlePanel);
        howToPanel.add(howToTextArea);
        howToPanel.add(howToBackButton);
        getContentPane().add(howToPanel);
        repaint();
        revalidate();
        setVisible(true);
    }

    /** Method listener for button clicks */
    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /** Start Game button is clicked */
            if(event.getSource() == startButton) {
                getContentPane().remove(menuPanel);
                getContentPane().revalidate();
                getContentPane().repaint();
                startGame();
            }
            /** How To Play button is clicked */
            else if(event.getSource() == howToButton) {
                getContentPane().remove(menuPanel);
                getContentPane().revalidate();
                getContentPane().repaint();
                howToScreen();
            }
            /** Quit button is clicked */
            else if(event.getSource() == quitButton) {
                System.exit(1);
            }
            /** Back button is clicked in How To Play window */
            else if(event.getSource() == howToBackButton) {
                getContentPane().remove(howToPanel);
                getContentPane().revalidate();
                getContentPane().repaint();
                startMenu();
            }
            /** Restart/Reset Game button is clicked */
            else if(event.getSource() == restartGameButton){
                new World();
                System.gc();
                getContentPane().remove(gamePanel);
                removeKeyListener(keyboard);
                getContentPane().revalidate();
                getContentPane().repaint();
                new World();
                startGame();
            }
            /** Back button is clicked in game window */
            else if(event.getSource() == gameBackButton) {
                new World();
                System.gc();
                getContentPane().remove(gamePanel);
                removeKeyListener(keyboard);
                getContentPane().revalidate();
                getContentPane().repaint();
                startMenu();
            }
        }
    }

    /** Method listener for keyboard key pressed */
    private class Keyboard extends KeyAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            /** Holds the value of the key pushed by the user. */
            int key = e.getExtendedKeyCode();

            /** Moves the player inside of the World object. */

            if(world.playerMove("" + key))
            {


                if(world.winCondition())
                {
                    // Removes the goal symbol to better show the player's position.
                    jPanels[world.getWINXPOSITION()][world.getWINYPOSITION()].removeAll() ;

                    removeKeyListener(keyboard) ;

                    System.out.println("Winner!");
                }

                /** Keeps track of the coordinates of the player. */
                currentX = world.getPlayerXPosition() ;
                currentY = world.getPlayerYPosition() ;

                /** Removes the previous */
                jPanels[prevX][prevY].setVisible(false);
                jPanels[prevX][prevY].removeAll();



                /** Moves the player on the JFrame based on the adjusted move made inside of the World object. */
                switch(key)
                {
                    case 38: // Y position ++
                        jPanels[currentX][currentY].add(new JLabel(new ImageIcon(world.getPlayer().displayUpImage()))) ;
                        break;
                    case 40: // Y position --
                        jPanels[currentX][currentY].add(new JLabel(new ImageIcon(world.getPlayer().displayDownImage()))) ;
                        break;
                    case 37: // X position --
                        jPanels[currentX][currentY].add(new JLabel(new ImageIcon(world.getPlayer().displayLeftImage()))) ;
                        break;
                    case 39: // X position ++
                        jPanels[currentX][currentY].add(new JLabel(new ImageIcon(world.getPlayer().displayRightImage()))) ;
                        break;

                }
                /** Makes the position the player is currently at visible. */
                jPanels[currentX][currentY].revalidate();
                jPanels[currentX][currentY].repaint();
                jPanels[currentX][currentY].setVisible(true);
                /** Assigns the current coordinates to the previous tracker as they are only used after a new move is made. */
                prevX = currentX ;
                prevY = currentY ;

                for(int a = 0 ; a < enemies.length; a++)
                {
                    /** Removes the previous location of the enemy was at. */
                    jPanels[prevEnemyxCoords[a]][prevEnemyyCoords[a]].setVisible(false) ;
                    jPanels[prevEnemyxCoords[a]][prevEnemyyCoords[a]].removeAll() ;
                    /** Records the current location of enemies. */
                    enemyxCoords[a] = enemies[a].getxCoord() ;
                    enemyyCoords[a] = enemies[a].getyCoord() ;

                    /** If the enemy's last movement was an up movement. */
                    if(enemyyCoords[a] < prevEnemyyCoords[a])
                    {
                        jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].add(new JLabel(new ImageIcon(enemies[a].displayUpImage()))) ;
                    }
                    /** If the enemy's last movement was a down movement. */
                    else if(enemyyCoords[a] > prevEnemyyCoords[a])
                    {
                        jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].add(new JLabel(new ImageIcon(enemies[a].displayDownImage()))) ;
                    }
                    /** If the enemy's last movement was a right movement. */
                    else if(enemyxCoords[a] > prevEnemyxCoords[a])
                    {
                        jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].add(new JLabel(new ImageIcon(enemies[a].displayRightImage()))) ;
                    }
                    /** If the enemy's last movement was a left movement. */
                    else if(enemyxCoords[a] < prevEnemyxCoords[a])
                    {
                        jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].add(new JLabel(new ImageIcon(enemies[a].displayLeftImage()))) ;
                    }
                    /** Shows the current location of enemies. */
                    jPanels[enemies[a].getxCoord()][enemies[a].getyCoord()].setVisible(true);

                    /** Adds the old coordinates to the appropriate prevEnemyCoord tracker. */
                    prevEnemyxCoords[a] = enemyxCoords[a] ;
                    prevEnemyyCoords[a] = enemyyCoords[a] ;
                }

                /** Win statement. */
                if(currentX == world.getWINXPOSITION() && currentY == world.getWINYPOSITION())
                {
                    JOptionPane.showMessageDialog(null, "You win!") ;
                }

                /** If the player is hit by an enemy then the game ends. */
                if(world.checkForHit())
                {
                    // Removes the player from the GameGUI.
                    jPanels[currentX][currentY].removeAll() ;
                    jPanels[currentX][currentY].add(new JLabel(new ImageIcon(enemies[0].displayDownImage()))) ;

                    // No more actions allowed.
                    removeKeyListener(keyboard) ;
                    JOptionPane.showMessageDialog(null, "You lose!") ;
                }
            }
        }
    }
}