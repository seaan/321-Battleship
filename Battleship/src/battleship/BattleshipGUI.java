/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    int sunk;       // counter for enemy ships sunk
    Font myFont = new Font("Sanserif", Font.PLAIN, 20);

    /**
     * Constructor that sets up the main menu and rules display
     */
    BattleshipGUI() {

        bsg = BattleshipGame.getInstance();
        
        /* Create and set size of the main menu */
        final JPanel mainPanel = new JPanel();
        final JFrame mainFrame = new JFrame("Main Menu");
        mainPanel.setPreferredSize(new Dimension(400, 200));

        /* Create buttons for main menu */
        JButton startBtn = new JButton("Play Game");
        JButton rulesBtn = new JButton("View Rules");
        JButton exitBtn = new JButton("Exit Game");

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
        rulePanel.setPreferredSize(new Dimension(1450, 900));

        /* create text field and set text size */
        JTextArea textRules = new JTextArea();
        textRules.setFont(myFont);

        /* Add text field and apply desired behaviour to main menu */
        rulePanel.add(textRules);
        ruleFrame.add(rulePanel);
        ruleFrame.pack();
        ruleFrame.setLocationRelativeTo(null);

        /* Add actions for start button */
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainFrame.setVisible(false);
                useNameWindow();
            }
        });

        /* Add actions for start button */
        rulesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ruleFrame.setVisible(true);
                readFile(textRules);
            }
        });

        /* Add actions for start button */
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
     * Creates the primary window for playing the application
     */
    public void useMainWindow(String name) {
        
        /* Create main frame to hold all succeeding
           panels and labels. Apply layout to this frame */
        final JFrame gameFrame = new JFrame("Greetings, Admiral " + name + "!");
        BorderLayout gameLayout = new BorderLayout();
        gameFrame.setLayout(gameLayout);
        
        sunk = 0; // set counter to zero. this counter tracks enemy ships sunk

        /* Create label for columns in game display */
        JLabel columnLabel = new JLabel(
                "                     A       B      C      D      E      F      G      H       I       J"
               +"                A       B      C      D      E      F      G      H       I       J");
        columnLabel.setFont(myFont);

        /* Create target and ocean panels, each containg
           an instance of their respective GUIs */
        JPanel oceanPanel = new JPanel();
        JPanel targetPanel = new JPanel();
        OceanGUI ocean = new OceanGUI(oceanPanel);
        TargetGUI target = new TargetGUI(targetPanel);

        /* Create buttons to mark enemy ships sunk */
        JButton carrier = new JButton("Carrier");
        JButton battleship = new JButton("Battleship");
        JButton cruiser = new JButton("Cruiser");
        JButton sub = new JButton("Submarine");
        JButton destroyer = new JButton("Destroyer");
        
        gameFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(bsg.checkGameStatus() == 2)
                    useDefeatWindow(gameFrame, name);
                else if(bsg.checkGameStatus() == 1)
                   useVicWindow(gameFrame, name);
            }
        });

        /* Set layout of and add buttons to newly created Panel 
        JPanel buttonPanel = new JPanel();             
        GridLayout buttonLayout = new GridLayout(5, 1);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.add(carrier);
        buttonPanel.add(battleship);
        buttonPanel.add(cruiser);
        buttonPanel.add(sub);
        buttonPanel.add(destroyer);*/

        /* Add actions for all respective ship buttons 
        carrier.addActionListener(new
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (carrier == (JButton) event.getSource()) {
                    carrier.setEnabled(false);
                    if (checkVictory() == true) {
                        useVicWindow(gameFrame, name);
                    }
                }
            }
        });
        battleship.addActionListener(new
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (battleship == (JButton) event.getSource()) {
                    battleship.setEnabled(false);
                    if (checkVictory() == true) {
                        useVicWindow(gameFrame, name);
                    }
                }
            }
        });
        cruiser.addActionListener(new
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (cruiser == (JButton) event.getSource()) {
                    cruiser.setEnabled(false);
                    if (checkVictory() == true) {
                        useVicWindow(gameFrame, name);
                    }
                }
            }
        });
        sub.addActionListener(new
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (sub == (JButton) event.getSource()) {
                    sub.setEnabled(false);
                    if (checkVictory() == true) {
                        useVicWindow(gameFrame, name);
                    }
                }
            }
        });
        destroyer.addActionListener(new
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (destroyer == (JButton) event.getSource()) {
                    destroyer.setEnabled(false);
                    if (checkVictory() == true) {
                        useVicWindow(gameFrame, name);
                    }
                }
            }
        });
        */

        /* add all panels to the game frame,
           and apply standard behaviour to the frame */
        gameFrame.add(oceanPanel, BorderLayout.LINE_START);
        gameFrame.add(targetPanel, BorderLayout.CENTER);
        //gameFrame.add(buttonPanel, BorderLayout.LINE_END);
        gameFrame.add(columnLabel, BorderLayout.PAGE_START);
        standardizeGUI(gameFrame);
    }

    /**
     * Function to be used by the rules display. Reads a locally stored
     * text file and prints the output to a given text field.
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
     * will be replaced by checkGameStatus
     */
    public boolean checkVictory() {
        sunk++;
        if (sunk == 5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates and shows the window for when the user is victorious
     * Contains buttons to start a new game or leave the application.
     * @param frame: a given frame (should be the current gameFrame)
     * @param name: a string to greet the Admiral (should be the same string
     *              currently used by the gameFrame)
     */
    public void useVicWindow(JFrame frame, String name) {
        JFrame winFrame = new JFrame("You win!");
        JPanel winPanel = new JPanel();

        JLabel winLabel = new JLabel("You win!");
        winLabel.setFont(myFont);

        JButton playBtn = new JButton("Play Again");
        JButton exitBtn = new JButton("Exit Game");

        winPanel.setLayout(new GridLayout(3, 1));
        winPanel.add(winLabel);
        winPanel.add(playBtn);
        winPanel.add(exitBtn);
        winPanel.setPreferredSize(new Dimension(400, 200));

        winFrame.getContentPane().add(winPanel);
        standardizeGUI(winFrame);

        playBtn.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                winFrame.setVisible(false);
                frame.setVisible(false);
                useMainWindow(name);
            }
        });

        exitBtn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }
    
    public void useDefeatWindow(JFrame frame, String name) {
        JFrame loseFrame = new JFrame("You lose!");
        JPanel losePanel = new JPanel();

        JLabel loseLabel = new JLabel("You lose!");
        loseLabel.setFont(myFont);

        JButton playBtn = new JButton("Play Again");
        JButton exitBtn = new JButton("Exit Game");

        losePanel.setLayout(new GridLayout(3, 1));
        losePanel.add(loseLabel);
        losePanel.add(playBtn);
        losePanel.add(exitBtn);
        losePanel.setPreferredSize(new Dimension(400, 200));

        loseFrame.getContentPane().add(losePanel);
        standardizeGUI(loseFrame);

        playBtn.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                loseFrame.setVisible(false);
                frame.setVisible(false);
                useMainWindow(name);
            }
        });

        exitBtn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    /**
     * Applies a standard behavior to a given frame, then shows it.
     * This includes packing the content of a window, making it a
     * fixed size, rendering the window in the center of the display,
     * and making the application close when the user chooses to exit.
     * @param frame: The frame to apply the behavior to
     */
    public void standardizeGUI(JFrame frame) {
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
        frame.setVisible(true);
    }
}
