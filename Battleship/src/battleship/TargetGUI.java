/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Kyle
 */
public class TargetGUI extends JFrame {

    TargetGrid tg = TargetGrid.getInstance();
    private int sunk;

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
    public TargetGUI(JPanel panel) {
        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(5, 1);
        JButton carrier = new JButton("Carrier");
        JButton battleship = new JButton("Battleship");
        JButton cruiser = new JButton("Cruiser");
        JButton sub = new JButton("Submarine");
        JButton destroyer = new JButton("Destroyer");

        buttonPanel.add(carrier);
        buttonPanel.add(battleship);
        buttonPanel.add(cruiser);
        buttonPanel.add(sub);
        buttonPanel.add(destroyer);
        buttonPanel.setLayout(buttonLayout);

        carrier.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (carrier == (JButton) event.getSource()) {
                    carrier.setEnabled(false);
                    checkVictory();
                }
            }
        });
        battleship.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (battleship == (JButton) event.getSource()) {
                    battleship.setEnabled(false);
                    checkVictory();
                }
            }
        });
        cruiser.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (cruiser == (JButton) event.getSource()) {
                    cruiser.setEnabled(false);
                    checkVictory();

                }
            }
        });
        sub.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (sub == (JButton) event.getSource()) {
                    sub.setEnabled(false);
                    checkVictory();
                }
            }
        });
        destroyer.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (destroyer == (JButton) event.getSource()) {
                    destroyer.setEnabled(false);
                    checkVictory();
                }
            }
        });

        // Code used to create a mouse click so they can place a O or X in the square
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("I'm also not broken!");
                int mouseX = e.getX();
                int mouseY = e.getY();

                int row = mouseY / CELL_SIZE;
                int col = mouseX / CELL_SIZE;
                // Code above shows the row / colum selected            
                if (row >= 0 && row < ROWS && col >= 0
                        && col < COLS && board[row][col] == Peg.EMPTY) {
                    if (e.getButton() == 1) {
                        board[row][col] = Peg.MISS;
                        tg.setMiss(row, col);
                    } else {
                        board[row][col] = Peg.HIT;
                        tg.setHit(row, col);
                    }
                    //board[row][col] = Peg.HIT;  
                }
                // Refresh the drawing canvas
                canvas.repaint();  // Call-back paintComponent().
            }
        });

        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.LINE_END);

        /*Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(buttonPanel, BorderLayout.LINE_END);*/
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();  // pack all the components in this JFrame
        //setTitle("Target Grid");
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

    /**
     * Update the currentState after the player with the Peg has placed on
     * (rowSelected, colSelected).
     */
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
            Graphics2D g2d = (Graphics2D) g;

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int x1 = col * CELL_SIZE + CELL_PADDING + 5;
                    int y1 = row * CELL_SIZE + CELL_PADDING + 5;

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

    public void checkVictory() {
        sunk++;
        if (sunk == 5) {
            JFrame winFrame = new JFrame("You win!");
            JPanel winPanel = new JPanel();

            JLabel winLabel = new JLabel("YOU WIN!");
            winLabel.setFont(new Font("Sanserif", Font.PLAIN, 20));

            JButton playBtn = new JButton("Play Again");
            JButton exitBtn = new JButton("Exit Game");

            winPanel.setLayout(new GridLayout(3, 1));
            winPanel.add(winLabel);
            winPanel.add(playBtn);
            winPanel.add(exitBtn);
            winPanel.setPreferredSize(new Dimension(400, 200));

            winFrame.getContentPane().add(winPanel);
            winFrame.pack();
            winFrame.setResizable(false);
            winFrame.setLocationRelativeTo(null);
            winFrame.setVisible(true);

            playBtn.addActionListener(new // add actions for rules button
                    ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    System.out.println("play again");
                    winFrame.setVisible(false);
                }
            });

            exitBtn.addActionListener(new // add actions for exit button
                    ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    System.out.println("exit");
                    System.exit(0);
                }
            });
        }
    }
}
