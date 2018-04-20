/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Kyle
 */
public class OceanGUI extends JFrame {

    OceanGrid og = OceanGrid.getInstance();

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

    private enum ShipToPlace {
        CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER
    }

    private ShipToPlace currentShip = ShipToPlace.CARRIER;

    private enum GameState {
        SETUP, PLAYING
        // Represents the value of which player won / lost / drew
    }
    private GameState currentState;
    
    // Use an enumeration (inner class) to represent the seeds and cell contents
    public enum Peg {
        EMPTY, HIT, MISS, SHIP
    }
    
    private int testState;

    private Peg[][] board; // Game board of ROWS-by-COLS cells
    private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
    private int ships;

    /**
     * Constructor to setup the game and the GUI components
     */
    public OceanGUI() {
        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        JButton carrier = new JButton("Carrier");
        JButton battleship = new JButton("Battleship");
        JButton cruiser = new JButton("Cruiser");
        JButton sub = new JButton("Submarine");
        JButton destroyer = new JButton("Destroyer");
        ships = 0;
        testState = 0;
        //currentState = GameState.PLAYING;
        

        // Code used to create a mouse click so they can place a O or X in the square
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Code above shows the row / colum selected  
                int startRow = mouseY / CELL_SIZE;
                int startCol = mouseX / CELL_SIZE;
                if (testState == 0) {
                    canvas.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent f) {
                            int mouseX2 = f.getX();
                            int mouseY2 = f.getY();

                            int endRow = mouseY2 / CELL_SIZE;
                            int endCol = mouseX2 / CELL_SIZE;

                            //if (currentState == GameState.SETUP) {
                            if (startRow >= 0 && startRow < ROWS && startCol >= 0
                                    && startCol < COLS && board[startRow][startCol]
                                    == Peg.EMPTY) {
                                setShip(currentShip, startRow, startCol, endRow, endCol);
                                repaint();
                            }

                        }
                    });
                    System.out.println("I'm not broken!");
                } else if (testState == 1) {
                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS && board[startRow][startCol]
                            == Peg.EMPTY) {
                        if (og.setPeg(startRow, startCol) == 1) {
                            board[startRow][startCol] = Peg.HIT;
                        } else {
                            board[startRow][startCol] = Peg.MISS;
                        }
                    }
                }
                // Refresh the drawing canvas
                repaint();  // Call-back paintComponent().
            }
        });

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);

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
    }

    private void setShip(ShipToPlace ship, int startX, int startY, int endX,
            int endY) {
        switch (currentShip) {
            case CARRIER:
                og.getFleet().placeShip("Carrier", startX,
                        startY, endX, endY);
                if (startX == endX) {
                    if (startY > endY) {
                        for (int i = endY; i < startY; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    } else {
                        for (int i = startY; i < endY; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    }
                } else {
                    if (startX > endX) {
                        for (int i = endX; i < startX; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    } else {
                        for (int i = startY; i < endY; i++) {
                            board[startX][i] = Peg.SHIP;

                        }
                    }
                }
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                }
                break;
            case BATTLESHIP:
                og.getFleet().placeShip("Battleship", startX,
                        startY, endX, endY);
                if (startY > endY && startX == endX) {
                    for (int i = endY; i < startY; i++) {
                        board[startX][i] = Peg.SHIP;
                    }
                }
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                }
                break;
            case CRUISER:
                og.getFleet().placeShip("Cruiser", startX, startY,
                        endX, endY);
                if (startY > endY && startX == endX) {
                    for (int i = endY; i < startY; i++) {
                        board[startX][i] = Peg.SHIP;
                    }
                }
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                }
                break;
            case SUBMARINE:
                og.getFleet().placeShip("Submarine", startX,
                        startY, endX, endY);
                if (startY > endY && startX == endX) {
                    for (int i = endY; i < startY; i++) {
                        board[startX][i] = Peg.SHIP;
                    }
                }
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                }
                break;
            case DESTROYER:
                og.getFleet().placeShip("Destroyer", startX,
                        startY, endX, endY);
                if (startY > endY && startX == endX) {
                    for (int i = endY; i < startY; i++) {
                        board[startX][i] = Peg.SHIP;
                    }
                }
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                }
                break;

        }
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
                    } else if (board[row][col] == Peg.SHIP) {
                        g.setColor(Color.GRAY);
                        g.fillRect(x1, y1, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }
}
