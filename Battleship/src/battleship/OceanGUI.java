/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import static battleship.BattleshipGUI.readFile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
    public static final int CELL_SIZE = 55; // cell width and height (square)
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  // Allows the canvas to be drawn
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 1;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 1;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 4; // width/height

    //Variables for mouse locations
    private int mouseX;
    private int mouseY;
    private int mouseX2;
    private int mouseY2;
    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    private enum ShipToPlace {
        CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER, NULL
    }

    private ShipToPlace currentShip = ShipToPlace.NULL; //add dialog for null

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
    public OceanGUI(JPanel panel) {
        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        ships = 0;

        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(5, 1);
        JButton carrier = new JButton("Carrier");
        JButton battleship = new JButton("Battleship");
        JButton cruiser = new JButton("Cruiser");
        JButton sub = new JButton("Submarine");
        JButton destroyer = new JButton("Destroyer");

        carrier.setPreferredSize(new Dimension(100, 40));
        battleship.setPreferredSize(new Dimension(100, 40));
        cruiser.setPreferredSize(new Dimension(100, 40));
        sub.setPreferredSize(new Dimension(100, 40));
        destroyer.setPreferredSize(new Dimension(100, 40));

        buttonPanel.add(carrier);
        buttonPanel.add(battleship);
        buttonPanel.add(cruiser);
        buttonPanel.add(sub);
        buttonPanel.add(destroyer);
        buttonPanel.setLayout(buttonLayout);
        ships = 0;
        testState = 0;
        //currentState = GameState.PLAYING;

        carrier.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = ShipToPlace.CARRIER;
                if (carrier == (JButton) event.getSource()) {
                    carrier.setEnabled(false);
                }
            }
        });
        battleship.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = ShipToPlace.BATTLESHIP;
                if (battleship == (JButton) event.getSource()) {
                    battleship.setEnabled(false);
                }
            }
        });
        cruiser.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = ShipToPlace.CRUISER;
                if (cruiser == (JButton) event.getSource()) {
                    cruiser.setEnabled(false);
                }
            }
        });
        sub.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = ShipToPlace.SUBMARINE;
                if (sub == (JButton) event.getSource()) {
                    sub.setEnabled(false);
                }
            }
        });
        destroyer.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = ShipToPlace.DESTROYER;
                if (destroyer == (JButton) event.getSource()) {
                    destroyer.setEnabled(false);
                }
            }
        });

        // Code used to create a mouse click so they can place a O or X in the square
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                    startRow = mouseY / CELL_SIZE;
                    startCol = mouseX / CELL_SIZE;
                }
                if (testState == 0 && e.getButton() == 3) {
                    mouseX2 = e.getX();
                    mouseY2 = e.getY();

                    endRow = mouseY2 / CELL_SIZE;
                    endCol = mouseX2 / CELL_SIZE;

                    //if (currentState == GameState.SETUP) {
                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS && board[startRow][startCol]
                            == Peg.EMPTY) {
                        setShip(currentShip, startRow, startCol, endRow, endCol);
                        canvas.repaint();
                    }

                    System.out.println("I'm not broken!");
                } else if (testState == 1) {
                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS) {
                        if (board[startRow][startCol] == Peg.SHIP) {
                            board[startRow][startCol] = Peg.HIT;
                        } else {
                            board[startRow][startCol] = Peg.MISS;
                        }
                        canvas.repaint();
                    }
                }
            }
        });

        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.LINE_START);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  // pack all the components in this JFrame
        setTitle("Battleship Testing Grid");
        //setVisible(true);  // show this JFrame

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
        switch (ship) {
            case CARRIER:
                if (startX == endX) {
                    if (startY < endY) {
                        og.getFleet().placeShip("Carrier", startY, startX, startY + 4, endX);
                        for (int i = startY; i < startY + 5; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Carrier", endY, startX, endY + 4, endX);
                        for (int i = endY; i < endY + 5; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    }
                } else {
                    og.getFleet().placeShip("Carrier", startY, startX, endY, startX + 4);
                    if (startX < endX) {
                        for (int i = startX; i < startX + 5; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Carrier", startY, endX, endY, endX + 4);
                        for (int i = endX; i < endX + 5; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    }
                }
                currentShip = ShipToPlace.NULL;
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case BATTLESHIP:
                if (startX == endX) {
                    if (startY < endY) {
                        og.getFleet().placeShip("Battleship", startY, startX, startY + 3, endX);
                        for (int i = startY; i < startY + 4; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Battleship", endY, startX, endY + 3, endX);
                        for (int i = endY; i < endY + 4; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    }
                } else {
                    og.getFleet().placeShip("Battleship", startY, startX, endY, startX + 3);
                    if (startX < endX) {
                        for (int i = startX; i < startX + 4; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Battleship", startY, endX, endY, endX + 3);
                        for (int i = endX; i < endX + 4; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    }
                }
                currentShip = ShipToPlace.NULL;
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case CRUISER:
                if (startX == endX) {
                    if (startY < endY) {
                        og.getFleet().placeShip("Cruiser", startY, startX, startY + 2, endX);
                        for (int i = startY; i < startY + 3; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Cruiser", endY, startX, endY + 2, endX);
                        for (int i = endY; i < endY + 3; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    }
                } else {
                    og.getFleet().placeShip("Cruiser", startY, startX, endY, startX + 2);
                    if (startX < endX) {
                        for (int i = startX; i < startX + 3; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Cruiser", startY, endX, endY, endX + 2);
                        for (int i = endX; i < endX + 3; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    }
                }
                currentShip = ShipToPlace.NULL;
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case SUBMARINE:
                if (startX == endX) {
                    if (startY < endY) {
                        og.getFleet().placeShip("Submarine", startY, startX, startY + 2, endX);
                        for (int i = startY; i < startY + 3; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Submarine", endY, startX, endY + 2, endX);
                        for (int i = endY; i < endY + 3; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    }
                } else {
                    og.getFleet().placeShip("Submarine", startY, startX, endY, startX + 2);
                    if (startX < endX) {
                        for (int i = startX; i < startX + 3; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Submarine", startY, endX, endY, endX + 2);
                        for (int i = endX; i < endX + 3; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    }
                }
                currentShip = ShipToPlace.NULL;
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case DESTROYER:
                if (startX == endX) {
                    if (startY < endY) {
                        og.getFleet().placeShip("Destroyer", startY, startX, startY + 1, endX);
                        for (int i = startY; i < startY + 2; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Destroyer", endY, startX, endY + 1, endX);
                        for (int i = endY; i < endY + 2; i++) {
                            board[startX][i] = Peg.SHIP;
                        }
                    }
                } else {
                    og.getFleet().placeShip("Destroyer", startY, startX, endY, startX + 1);
                    if (startX < endX) {
                        for (int i = startX; i < startX + 2; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    } else {
                        og.getFleet().placeShip("Destroyer", startY, endX, endY, endX + 1);
                        for (int i = endX; i < endX + 2; i++) {
                            board[i][startY] = Peg.SHIP;
                        }
                    }
                }
                currentShip = ShipToPlace.NULL;
                ships++;
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
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

            // Draw the Pegs of all the cells if they are not empty
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D) g;

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int x1 = col * CELL_SIZE + CELL_PADDING + 4;
                    int y1 = row * CELL_SIZE + CELL_PADDING + 4;
                    int x2 = col * CELL_SIZE;
                    int y2 = row * CELL_SIZE;

                    if (board[row][col] == Peg.HIT) {
                        g2d.setColor(Color.RED);
                        g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    } else if (board[row][col] == Peg.MISS) {
                        g2d.setColor(Color.WHITE);
                        g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    } else if (board[row][col] == Peg.SHIP) {
                        g.setColor(Color.GRAY);
                        g.fillRect(x2, y2, CELL_SIZE, CELL_SIZE);
                    }
                }
            }

            //Draw the grid lines after painting pegs so it can go over the ships
            g.setColor(Color.BLACK);
            for (int row = 0; row <= ROWS; row++) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 0; col <= COLS; col++) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }
        }
    }
}
