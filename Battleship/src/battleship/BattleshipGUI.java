/* 
 * CREATED IN NETBEANS IDE 8.2
 * CS-321-01 Final Project: Battleship
 * Kyle Daigle, Sean Widmier, Robert Womack, Kelly Manley
 */
package battleship;

// import all neccessary packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Constructs the primary GUI that the player will interact with. This includes:
 * Main menu, rules display, game display, and victory/loss windows.
 *
 * @author Robert Womack, Kyle Daigle
 */
public class BattleshipGUI {

    BattleshipGame bsg;
    OceanGUI ocean;
    TargetGUI target;

    Font myFont = new Font("Sanserif", Font.PLAIN, 14);
    Font labelFont = new Font("Sanserif", Font.PLAIN, 20);

    /* Create buttons for main menu */
    JButton startBtn = new JButton("Play Game");
    JButton rulesBtn = new JButton("View Rules");
    JButton exitBtn = new JButton("Exit Game");
    JButton newGameButton = new JButton("New Game");

    /**
     * Constructor that sets up the main menu and rules display
     */
    BattleshipGUI() {

        bsg = BattleshipGame.getInstance();

        /* Create and set size of the main menu */
        final JPanel mainPanel = new JPanel();
        final JFrame mainFrame = new JFrame("Main Menu");
        mainPanel.setPreferredSize(new Dimension(400, 200));

        /* Set Layout for main menu so buttons show properly */
        GridLayout mainLayout = new GridLayout(3, 1);
        mainPanel.setLayout(mainLayout);

        /* Add buttons and apply standard behaviour to main menu */
        mainPanel.add(startBtn, BorderLayout.CENTER);
        mainPanel.add(rulesBtn, BorderLayout.CENTER);
        mainPanel.add(exitBtn, BorderLayout.CENTER);
        mainFrame.add(mainPanel);
        standardizeGUI(mainFrame);

        /* Create and set size of the rules window */
        final JFrame ruleFrame = new JFrame("Game Rules");
        final JPanel rulePanel = new JPanel();
        rulePanel.setPreferredSize(new Dimension(900, 675));

        /* create text field and set text size */
        JTextArea textRules = new JTextArea();
        textRules.setFont(myFont);

        /* Add text field and apply desired behaviour to main menu */
        rulePanel.add(textRules); //wrap in scroll bar 
        ruleFrame.add(rulePanel, BorderLayout.CENTER);
        ruleFrame.pack();
        ruleFrame.setLocationRelativeTo(null);

        /* Add actions for start button */
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainFrame.setVisible(false);
                useNameWindow();
            }
        });

        /* Add actions for rules button */
        rulesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ruleFrame.setVisible(true);
                readFile(textRules);
            }
        });

        /* Add actions for exit button */
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    /**
     * Create the window that asks for the user's name
     */
    public void useNameWindow() {

        /* Create and set size of the name window */
        final JFrame nameFrame = new JFrame("Enter Name");
        final JPanel namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(400, 200));

        /* Create the content for the name window:
           prompt, text field, and submit button. */
        final JLabel prompt = new JLabel("Enter Name");
        final JTextField nameFld = new JTextField(20);
        final JButton submitBtn = new JButton("Submit");

        /* Set the font of the prompt and text field */
        prompt.setFont(myFont);
        nameFld.setFont(myFont);

        /* Set Layout for window and add content. Apply standard behavior. */
        namePanel.setLayout(new GridLayout(3, 1));
        namePanel.add(prompt);
        namePanel.add(nameFld);
        namePanel.add(submitBtn);
        nameFrame.add(namePanel);
        standardizeGUI(nameFrame);

        /* Add actions for submit button */
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                useMainWindow(nameFld.getText());
                nameFrame.setVisible(false);
            }
        });
    }

    /**
     * Creates the primary window for playing the application.
     *
     * @param name The name of the player.
     */
    public void useMainWindow(String name) {

        /* Create main frame to hold all succeeding
           panels and labels. Apply layout to this frame */
        final JFrame gameFrame = new JFrame("Greetings, Admiral " + name + "!");
        BorderLayout gameLayout = new BorderLayout();
        gameFrame.setLayout(gameLayout);

        /* Create label for columns in game display */
        JLabel columnLabel = new JLabel(
                "     Player       A      B      C      D      E      F      G      H       I       J"
                + "                 A      B      C      D      E      F      G      H       I       J"
                + "          Enemy");
        columnLabel.setFont(labelFont);

        /* Create target and ocean panels, each containg
           an instance of their respective GUIs */
        JPanel oceanPanel = new JPanel();
        JPanel targetPanel = new JPanel();
        ocean = new OceanGUI(oceanPanel);
        target = new TargetGUI(targetPanel);

        GridLayout optionsLayout = new GridLayout(1, 3);
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(optionsLayout);
        optionsPanel.add(newGameButton);
        optionsPanel.add(rulesBtn);
        optionsPanel.add(exitBtn);

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                useNewGameWindow();
            }
        });

        /* add all panels to the game frame,
           and apply standard behaviour to the frame */
        gameFrame.add(oceanPanel, BorderLayout.LINE_START);
        gameFrame.add(targetPanel, BorderLayout.CENTER);
        //gameFrame.add(buttonPanel, BorderLayout.LINE_END);
        gameFrame.add(columnLabel, BorderLayout.PAGE_START);
        gameFrame.add(optionsPanel, BorderLayout.SOUTH);
        standardizeGUI(gameFrame);
    }

    /**
     * Function to be used by the rules display. Reads a locally stored
     * text file and prints the output to a given text field.
     *
     * @param txtfld : a given text field to print the output on
     */
    public static void readFile(JTextArea txtfld) {   // function to read file containing rules
        try {
            BufferedReader rules = new BufferedReader(new FileReader("testRules.txt"));

            String line;
            while ((line = rules.readLine()) != null) {
                txtfld.append(line + "\n");
            }
            rules.close();
        } catch (IOException ex) {
            System.out.println("Reader exception");
        }
    }

    /**
     * Create the window used to confirm whether the user whether he/she 
     * desires to play a new game
     */
    public void useNewGameWindow() {
        JFrame newGameFrame = new JFrame("New Game");
        JPanel newGamePanel = new JPanel();

        JLabel newGameLabel = new JLabel("Are you sure you want to start a new game?");
        newGameLabel.setFont(myFont);

        JButton yesBtn = new JButton("Yes");
        JButton noBtn = new JButton("No");

        newGamePanel.setLayout(new GridLayout(3, 1));
        newGamePanel.add(newGameLabel);
        newGamePanel.add(yesBtn);
        newGamePanel.add(noBtn);
        newGamePanel.setPreferredSize(new Dimension(400, 200));

        newGameFrame.getContentPane().add(newGamePanel);
        standardizeGUI(newGameFrame);

        yesBtn.addActionListener(new // add actions for yes button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newGameFrame.setVisible(false);
                ocean.clearGrid();
                target.clearGrid();
                bsg.resetGame();
            }
        });

        noBtn.addActionListener(new // add actions for no button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newGameFrame.setVisible(false);
            }
        });
    }

    /**
     * Applies a standard behavior to a given frame, then shows it.
     * This includes packing the content of a window, making it a
     * fixed size, rendering the window in the center of the display,
     * and making the application close when the user chooses to exit.
     *
     * @param frame: The frame to apply the behavior to
     */
    public void standardizeGUI(JFrame frame) {
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
