package battleship;

import battleship.OceanGrid;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kyle
 */
public class OceanGUI extends JFrame {

    BattleshipGame bsg;

    // Grid Layout
    public static final int ROWS = 10;
    public static final int COLS = 10;

    // Constants for creating the board
    public static final int CELL_SIZE = 25; // cell width and height (square)
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  // Allows the canvas to be drawn
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 1;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 1;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 4; // width/height

    public enum GameState {
        PLAYING, DRAW, HIT_WON, MISS_WON
        // Represents the value of which player won / lost / drew
    }
    private GameState currentState;  // the current game state

    // Use an enumeration (inner class) to represent the seeds and cell contents
    public enum Peg {
        EMPTY, HIT, MISS
    }

    private Peg[][] board; // Game board of ROWS-by-COLS cells
    private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
    private JLabel statusBar;  // Status Bar

    /**
     * Constructor to setup the game and the GUI components
     */
    public OceanGUI() {
        bsg = BattleshipGame.getInstance();
        Position position = new Position(0, 0, Position.Status.MISS);

        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Code used to create a mouse click so they can place a O or X in the square
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int row = mouseY / CELL_SIZE;
                int col = mouseX / CELL_SIZE;
                // Code above shows the row / colum selected            

                if (currentState == GameState.PLAYING) {
                    if (row >= 0 && row < ROWS && col >= 0
                            && col < COLS && board[row][col] == Peg.EMPTY) {
                        position.setPosition(row, col);
                        if (bsg.updatePeg(position, 0) == Position.Status.HIT) {
                            board[row][col] = Peg.HIT;
                        } else {
                            board[row][col] = Peg.MISS;
                        }
                        //board[row][col] = Peg.HIT;  
                    }
                } else {       // game over
                    initGame(); // restarts the game
                }
                // Refresh the drawing canvas
                repaint();  // Call-back paintComponent().
            }
        });

        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  // pack all the components in this JFrame
        setTitle("Battleship Testing Grid");
        setVisible(true);  // show this JFrame

        board = new Peg[ROWS][COLS]; // allocate array
        initGame(); // initialize the game board contents and game variables
    }

    /**
     * Initialize the game-board contents and the status
     */
    public void initGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = Peg.EMPTY; // all cells empty
            }
        }
        currentState = GameState.PLAYING; // ready to play
//      currentPlayer = Peg.HIT;       // cross plays first
    }

    /**
     * Update the currentState after the player with "thePeg" has placed on
     * (rowSelected, colSelected).
     */
    // Otherwise, no change to current state (still GameState.PLAYING).
    /**
     * Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
     */
    class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {  // invoke via repaint()
            super.paintComponent(g);    // fill background
            setBackground(Color.BLUE); // set its background color

            // Draw the grid-lines
            g.setColor(Color.BLACK);
            for (int row = 0; row <= ROWS; row++) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }

            for (int col = 0; col <= COLS; col++) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            // Draw the Pegs of all the cells if they are not empty
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D) g;

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int x1 = col * CELL_SIZE + CELL_PADDING + 4;
                    int y1 = row * CELL_SIZE + CELL_PADDING + 4;

                    if (board[row][col] == Peg.HIT) {
                        g2d.setColor(Color.RED);
                        g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    } else if (board[row][col] == Peg.MISS) {
                        g2d.setColor(Color.WHITE);
                        g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }
        }
    }

    /**
     * The entry main() method
     */
}