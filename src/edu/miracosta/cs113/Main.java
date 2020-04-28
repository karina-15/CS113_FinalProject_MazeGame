
package edu.miracosta.cs113;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Public class main will create world object, all characters, and start the game.
 * Authors: Karina Elias, Juan Hernandez Diaz, & Alfredo Hernandez Jr.
 * Date: November 6th, 2019.
 * Problem: Make a program that utilizes the Graph search algorithm.
 * Solution: Incorporate a Graph search into a non-friendly npc to chase the user.
 */
public class Main {
    public static void main(String[] args)
    {
        // Creates the GUI for the game to be displayed/played.
        GameGUI test = new GameGUI();
        test.setVisible(true);
    }
}