package mines;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

// Source: http://zetcode.com/tutorials/javagamestutorial/minesweeper/

/**
 * 
 * The Mines class represents the main class of the Minesweeper game.
 * 
 * It creates an instance of the Mines game and initializes the game window.
 * 
 * The game window contains a Board panel and a status bar.
 * 
 * 
 * @version 1.0
 */
// and this is the main class where the main function we call Board that call
// MinesAdapter
public class Mines extends JFrame {
    private static final long serialVersionUID = 4772165125287256837L;

    // change to static
    private static final int WIDTH = 250;
    private static final int HEIGHT = 290;

    private JLabel statusbar;

    /**
     * 
     * Constructs a Mines object and initializes the game window.
     * 
     * It sets the window size, location, and title.
     * 
     * It creates a status bar and adds it to the bottom of the window.
     * 
     * It also creates a Board panel and adds it to the window.
     * 
     * The window is set to be non-resizable and visible.
     */
    public Mines() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        add(new Board(statusbar));

        setResizable(false);
        setVisible(true);
    }

    /**
     * 
     * The entry point of the Minesweeper game.
     * It creates an instance of the Mines class, which starts the game.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new Mines();
    }
}