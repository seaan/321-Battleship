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

    BattleshipGame bsg;
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
    private Position startPosition;
    private Position endPosition;

    private JFrame frame;

    private Fleet.GameShip currentShip = Fleet.GameShip.NULL; //add dialog for null

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
        bsg = BattleshipGame.getInstance();
        Position position = new Position(0, 0, Position.Status.MISS);

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
                currentShip = Fleet.GameShip.CARRIER;
                if (carrier == (JButton) event.getSource()) {
                    carrier.setEnabled(false);
                }
            }
        });
        battleship.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = Fleet.GameShip.BATTLESHIP;
                if (battleship == (JButton) event.getSource()) {
                    battleship.setEnabled(false);
                }
            }
        });
        cruiser.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = Fleet.GameShip.CRUISER;
                if (cruiser == (JButton) event.getSource()) {
                    cruiser.setEnabled(false);
                }
            }
        });
        sub.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = Fleet.GameShip.SUBMARINE;
                if (sub == (JButton) event.getSource()) {
                    sub.setEnabled(false);
                }
            }
        });
        destroyer.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentShip = Fleet.GameShip.DESTROYER;
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
                    startPosition.setPosition(startCol, startRow);
                }
                if (testState == 0 && e.getButton() == 3) {
                    mouseX2 = e.getX();
                    mouseY2 = e.getY();

                    endRow = mouseY2 / CELL_SIZE;
                    endCol = mouseX2 / CELL_SIZE;
                    endPosition.setPosition(endCol, endRow);

                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS && board[startRow][startCol]
                            == Peg.EMPTY) {
                        setShip(currentShip, startPosition, endPosition);
                        canvas.repaint();
                    }
                } else if (testState == 1) {
                    if (startRow >= 0 && startRow < ROWS && startCol >= 0
                            && startCol < COLS && (board[startRow][startCol]
                            == Peg.EMPTY || board[startRow][startCol] == Peg.SHIP)) {

                        position.setPosition(ROWS, COLS);

                        if (bsg.updatePeg(position, 0) == Position.Status.HIT) {
                            board[startRow][startCol] = Peg.HIT;
                            if (bsg.checkGameStatus() == 2) {
                                JOptionPane.showMessageDialog(frame, "YOU LOST!!");
                            }
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
            JOptionPane.showMessageDialog(frame, "ERROR: Ship placement out of"
                    + " bounds. Please place ship completely on the grid.",
                    "Warning", JOptionPane.ERROR_MESSAGE);
        } else if (error == "nullShipPlacement") {
            JOptionPane.showMessageDialog(frame, "Please select a ship to "
                    + "place.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setShip(Fleet.GameShip ship, Position startPosition, Position endPosition) {
        Position newPosition = new Position(0, 0, Position.Status.MISS);
        if (startPosition.getRow() == endPosition.getRow()) {
            CheckShipPlacementCol(ship, startPosition, endPosition);
        } else {
            CheckShipPlacementRow(ship, startPosition, endPosition);
        }

    }

    /**
     *
     * @param type
     * @param startPosition
     * @param endPosition
     */
    private void CheckShipPlacementCol(Fleet.GameShip type, Position startPosition, Position endPosition) {
        if (startPosition.getCol() < endPosition.getCol()) {
            if (startPosition.getCol() >= 0 && startPosition.getCol() < COLS && startPosition.getCol() + type.size >= 0
                    && startPosition.getCol() + type.size < COLS) {

                startPosition.setPosition(startPosition.getCol(), startPosition.getRow());
                endPosition.setPosition(startPosition.getCol() + type.size, endPosition.getRow());
                bsg.updateShip(startPosition, endPosition, type);

                for (int i = startPosition.getCol(); i <= startPosition.getCol() + type.size; i++) {
                    board[startRow][i] = Peg.SHIP;
                }
                currentShip = Fleet.GameShip.NULL;
                ships++;
            } else {
                showError("shipOutOfBounds");
                currentShip = type;
            }
        } else {
            if (startPosition.getCol() >= 0 && startPosition.getCol() < COLS && startPosition.getCol() - type.size >= 0
                    && startPosition.getCol() - type.size < COLS) {

                startPosition.setPosition(startPosition.getCol(), startPosition.getRow());
                endPosition.setPosition(startPosition.getCol() - type.size, endPosition.getRow());
                bsg.updateShip(startPosition, endPosition, type);

                for (int i = startPosition.getCol(); i >= startPosition.getCol() - type.size; i--) {
                    board[startRow][i] = Peg.SHIP;
                }
                currentShip = Fleet.GameShip.NULL;
                ships++;
            } else {
                showError("shipOutOfBounds");
                currentShip = type;
            }
        }
        if (ships == 5) {
            currentState = GameState.PLAYING;
            testState = 1;
        }
    }

    /**
     *
     * @param type
     * @param startPosition
     * @param endPosition
     */
    private void CheckShipPlacementRow(Fleet.GameShip type, Position startPosition, Position endPosition) {
        if (startPosition.getRow() < endPosition.getRow()) {
            if (startPosition.getRow() >= 0 && startPosition.getRow() < ROWS && startPosition.getRow() + type.size >= 0
                    && startPosition.getRow() + 4 < ROWS) {

                startPosition.setPosition(startPosition.getCol(), startPosition.getRow());
                endPosition.setPosition(startPosition.getCol(), endPosition.getRow() + type.size);
                bsg.updateShip(startPosition, endPosition, type);

                for (int i = startPosition.getCol(); i <= startPosition.getCol() + type.size; i++) {
                    board[startRow][i] = Peg.SHIP;
                }
                currentShip = Fleet.GameShip.NULL;
                ships++;
            } else {
                showError("shipOutOfBounds");
                currentShip = type;
            }
        } else {
            if (startPosition.getCol() >= 0 && startPosition.getCol() < COLS && startPosition.getCol() - type.size >= 0
                    && startPosition.getCol() - 4 < COLS) {

                startPosition.setPosition(startPosition.getCol(), startPosition.getRow());
                endPosition.setPosition(startPosition.getCol(), endPosition.getRow() - type.size);
                bsg.updateShip(startPosition, endPosition, type);

                for (int i = startPosition.getCol(); i >= startPosition.getCol() - type.size; i--) {
                    board[startRow][i] = Peg.SHIP;
                }
                currentShip = Fleet.GameShip.NULL;
                ships++;
            } else {
                showError("shipOutOfBounds");
                currentShip = type;
            }
        }
        if (ships == 5) {
            currentState = GameState.PLAYING;
            testState = 1;
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