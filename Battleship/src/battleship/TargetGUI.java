package battleship;

import battleship.TargetGrid;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Kyle Daigle, Robert Womack
 */
public class TargetGUI extends JFrame {

    
    BattleshipGame bsg;

    /* Number of Rows */
    public static final int ROWS = 10;
    /* Number of Cols */
    public static final int COLS = 10;
    /* Size of each square */
    public static final int CELL_SIZE = 55;
    /* Width of the canvas */    
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  
    /* Height of the canvas */
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    /* Width of the grid lines */
    public static final int GRID_WIDTH = 1;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 4; // width/height

    int row;
    int col;
    int mouseX;
    int mouseY;

    /**
     * Enumerated data type for each peg type. This is used to paint the canvas 
     */
    public enum Peg {
        EMPTY, HIT, MISS
    }

    private Peg[][] board; // Game board of ROWS-by-COLS cells
    private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
    private JLabel statusBar;  // Status Bar

    /**
     * Constructor to setup the game and the GUI components.
     * It controls the entire Target GUI and handles actions involving it.
     * @Param JPanal grid will be contained in.
     */
    public TargetGUI(JPanel panel) {
        
        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        bsg = BattleshipGame.getInstance();
        Position position = new Position(0, 0, Position.Status.MISS);

        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                row = mouseY / CELL_SIZE;
                col = mouseX / CELL_SIZE;

                position.setPosition(col, row);

                if (row >= 0 && row < ROWS && col >= 0
                        && col < COLS) {
                    if (e.getButton() == 1) {
                        board[row][col] = Peg.MISS;
                        position.setStatus(Position.Status.MISS);
                    } else {
                        board[row][col] = Peg.HIT;
                        position.setStatus(Position.Status.HIT);

                    }
                    canvas.repaint();
                }
            }
        }
        );

        statusBar = new JLabel("  ");

        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        JPanel guidePanel = new JPanel();
        BoxLayout guideLayout = new BoxLayout(guidePanel, BoxLayout.PAGE_AXIS);
        guidePanel.setLayout(guideLayout);
        
        for (int i = 0; i < 10; i++) {
            JLabel pos = new JLabel("" + i + "    ");
            pos.setFont(new Font("Sanserif", Font.PLAIN, 25));
            Box.createVerticalGlue();
            guidePanel.add(pos);
            guidePanel.add(Box.createVerticalGlue());
        }

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(guidePanel, BorderLayout.LINE_START);

        board = new Peg[ROWS][COLS]; // allocate array

        clearGrid(); 
    }

    /**
     * Clears grid for new game.
     */
    protected void clearGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = Peg.EMPTY; // all cells empty
            }
        }
        canvas.repaint();
    }

    /**
     * Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
     * Canvas can be redrawn with repaint()     
     */
    class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {    //invoke via repaint()
            super.paintComponent(g);                //fill background
            setBackground(Color.BLUE);              //set its background color

            // Draw the grid-lines
            g.setColor(Color.BLACK);
            for (int row = 0; row <= ROWS; row++) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH,
                        CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }

            for (int col = 0; col <= COLS; col++) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH, 0,
                        GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            //Draw the Pegs in all the cells if they are not empty
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int x1 = col * CELL_SIZE + CELL_PADDING + 5;
                    int y1 = row * CELL_SIZE + CELL_PADDING + 5;

                    if (board[row][col] == Peg.HIT) {
                        g.setColor(Color.RED);
                        g.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    } else if (board[row][col] == Peg.MISS) {
                        g.setColor(Color.WHITE);
                        g.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }
        }
    }
}
