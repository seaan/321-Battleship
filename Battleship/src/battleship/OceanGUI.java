/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

    private JFrame frame;

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
        frame = new JFrame();

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

                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS && board[startRow][startCol]
                            == Peg.EMPTY) {
                        setShip(currentShip, startRow, startCol, endRow, endCol);
                        canvas.repaint();
                    }
                } else if (testState == 1) {
                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS) {
                        if (board[startRow][startCol] == Peg.SHIP
                                || board[startRow][startCol] == Peg.MISS) {
                            board[startRow][startCol] = Peg.HIT;
                        } else if (board[startRow][startCol] == Peg.EMPTY
                                || board[startRow][startCol] == Peg.HIT) {
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
        clearGrid(); // initialize the game board contents and game variables
    }

    /**
     * Initialize the game-board contents and the status
     */
    public void clearGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = Peg.EMPTY; // all cells empty
            }
        }
        canvas.repaint();
    }

    private void showError(String error) {
        if (error == "shipOutOfBounds") {
            JOptionPane.showMessageDialog(frame, "ERROR: Ship placement out of "
                    + "bounds. Please place ship completely on the grid.",
                    "Warning", JOptionPane.ERROR_MESSAGE);
        } else if (error == "nullShipPlacement") {
            JOptionPane.showMessageDialog(frame, "Please select a ship to "
                    + "place.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setShip(ShipToPlace ship, int startRow, int startCol, int endRow,
            int endCol) {
        switch (ship) {
            case NULL:
                showError("nullShipPlacement");
                break;
            case CARRIER:
                if (startRow == endRow) {
                    if (startCol < endCol) {
                        if (startCol >= 0 && startCol < COLS && startCol + 4 >= 0
                                && startCol + 4 < COLS) {

                            og.getFleet().placeShip("Carrier", startCol, startRow, startCol + 4, endRow);
                            for (int i = startCol; i < startCol + 5; i++) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CARRIER;
                        }

                    } else {
                        if (startCol >= 0 && startCol < COLS && startCol - 4 >= 0
                                && startCol - 4 < COLS) {
                            og.getFleet().placeShip("Carrier", startCol, startRow, startCol - 4, endRow);
                            for (int i = startCol; i > startCol - 5; i--) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CARRIER;
                        }
                    }
                } else {
                    if (startRow < endRow) {

                        if (startRow >= 0 && startRow < ROWS && startRow + 4 >= 0
                                && startRow + 4 < ROWS) {
                            og.getFleet().placeShip("Carrier", startCol, startRow, endCol, startRow + 4);
                            for (int i = startRow; i < startRow + 5; i++) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CARRIER;
                        }

                    } else {
                        if (startRow >= 0 && startRow < ROWS && startRow - 4 >= 0
                                && startRow - 4 < ROWS) {
                            og.getFleet().placeShip("Carrier", startCol, startRow, endCol, startRow - 4);
                            for (int i = startRow; i > startRow - 5; i--) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CARRIER;
                        }
                    }
                }
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case BATTLESHIP:
                if (startRow == endRow) {
                    if (startCol < endCol) {
                        if (startCol >= 0 && startCol < COLS && startCol + 3 >= 0
                                && startCol + 3 < COLS) {

                            og.getFleet().placeShip("Battleship", startCol, startRow, startCol + 3, endRow);
                            for (int i = startCol; i < startCol + 4; i++) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.BATTLESHIP;
                        }

                    } else {
                        if (startCol >= 0 && startCol < COLS && startCol - 3 >= 0
                                && startCol - 3 < COLS) {
                            og.getFleet().placeShip("Battleship", startCol, startRow, startCol - 3, endRow);
                            for (int i = startCol; i > startCol - 4; i--) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.BATTLESHIP;
                        }
                    }
                } else {
                    if (startRow < endRow) {

                        if (startRow >= 0 && startRow < ROWS && startRow + 3 >= 0
                                && startRow + 3 < ROWS) {
                            og.getFleet().placeShip("Battleship", startCol, startRow, endCol, startRow + 3);
                            for (int i = startRow; i < startRow + 4; i++) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.BATTLESHIP;
                        }

                    } else {
                        if (startRow >= 0 && startRow < ROWS && startRow - 3 >= 0
                                && startRow - 3 < ROWS) {
                            og.getFleet().placeShip("Battleship", startCol, startRow, endCol, startRow - 3);
                            for (int i = startRow; i > startRow - 4; i--) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.BATTLESHIP;
                        }
                    }
                }
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case CRUISER:
                if (startRow == endRow) {
                    if (startCol < endCol) {
                        if (startCol >= 0 && startCol < COLS && startCol + 2 >= 0
                                && startCol + 2 < COLS) {

                            og.getFleet().placeShip("Cruiser", startCol, startRow, startCol + 2, endRow);
                            for (int i = startCol; i < startCol + 3; i++) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CRUISER;
                        }

                    } else {
                        if (startCol >= 0 && startCol < COLS && startCol - 2 >= 0
                                && startCol - 2 < COLS) {
                            og.getFleet().placeShip("Cruiser", startCol, startRow, startCol - 2, endRow);
                            for (int i = startCol; i > startCol - 3; i--) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CRUISER;
                        }
                    }
                } else {
                    if (startRow < endRow) {

                        if (startRow >= 0 && startRow < ROWS && startRow + 2 >= 0
                                && startRow + 2 < ROWS) {
                            og.getFleet().placeShip("Cruiser", startCol, startRow, endCol, startRow + 2);
                            for (int i = startRow; i < startRow + 3; i++) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CRUISER;
                        }

                    } else {
                        if (startRow >= 0 && startRow < ROWS && startRow - 2 >= 0
                                && startRow - 2 < ROWS) {
                            og.getFleet().placeShip("Cruiser", startCol, startRow, endCol, startRow - 2);
                            for (int i = startRow; i > startRow - 3; i--) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.CRUISER;
                        }
                    }
                }
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case SUBMARINE:
                if (startRow == endRow) {
                    if (startCol < endCol) {
                        if (startCol >= 0 && startCol < COLS && startCol + 2 >= 0
                                && startCol + 2 < COLS) {

                            og.getFleet().placeShip("Submarine", startCol, startRow, startCol + 2, endRow);
                            for (int i = startCol; i < startCol + 3; i++) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.SUBMARINE;
                        }

                    } else {
                        if (startCol >= 0 && startCol < COLS && startCol - 2 >= 0
                                && startCol - 2 < COLS) {
                            og.getFleet().placeShip("Submarine", startCol, startRow, startCol - 2, endRow);
                            for (int i = startCol; i > startCol - 3; i--) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.SUBMARINE;
                        }
                    }
                } else {
                    if (startRow < endRow) {

                        if (startRow >= 0 && startRow < ROWS && startRow + 2 >= 0
                                && startRow + 2 < ROWS) {
                            og.getFleet().placeShip("Submarine", startCol, startRow, endCol, startRow + 2);
                            for (int i = startRow; i < startRow + 3; i++) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.SUBMARINE;
                        }

                    } else {
                        if (startRow >= 0 && startRow < ROWS && startRow - 2 >= 0
                                && startRow - 2 < ROWS) {
                            og.getFleet().placeShip("Submarine", startCol, startRow, endCol, startRow - 2);
                            for (int i = startRow; i > startRow - 3; i--) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.SUBMARINE;
                        }
                    }
                }
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;
            case DESTROYER:
                if (startRow == endRow) {
                    if (startCol < endCol) {
                        if (startCol >= 0 && startCol < COLS && startCol + 1 >= 0
                                && startCol + 1 < COLS) {

                            og.getFleet().placeShip("Destroyer", startCol, startRow, startCol + 1, endRow);
                            for (int i = startCol; i < startCol + 2; i++) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.DESTROYER;
                        }

                    } else {
                        if (startCol >= 0 && startCol < COLS && startCol - 1 >= 0
                                && startCol - 1 < COLS) {
                            og.getFleet().placeShip("Destroyer", startCol, startRow, startCol - 1, endRow);
                            for (int i = startCol; i > startCol - 2; i--) {
                                board[startRow][i] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.DESTROYER;
                        }
                    }
                } else {
                    if (startRow < endRow) {

                        if (startRow >= 0 && startRow < ROWS && startRow + 1 >= 0
                                && startRow + 1 < ROWS) {
                            og.getFleet().placeShip("Destroyer", startCol, startRow, endCol, startRow + 1);
                            for (int i = startRow; i < startRow + 2; i++) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.DESTROYER;
                        }

                    } else {
                        if (startRow >= 0 && startRow < ROWS && startRow - 1 >= 0
                                && startRow - 1 < ROWS) {
                            og.getFleet().placeShip("Destroyer", startCol, startRow, endCol, startRow - 1);
                            for (int i = startRow; i > startRow - 2; i--) {
                                board[i][startCol] = Peg.SHIP;
                            }
                            currentShip = ShipToPlace.NULL;
                            ships++;
                        } else {
                            showError("shipOutOfBounds");
                            currentShip = ShipToPlace.DESTROYER;
                        }
                    }
                }
                if (ships == 5) {
                    currentState = GameState.PLAYING;
                    testState = 1;
                }
                break;

        }
    }

    /**
     * Inner class DrawCanvas (extends JPanel) used for grid drawing.
     */
    class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {  // invoke via repaint()
            super.paintComponent(g);    // fill background
            setBackground(Color.BLUE); // set its background color

            // Draw the Pegs in all the cells if they are not empty
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
