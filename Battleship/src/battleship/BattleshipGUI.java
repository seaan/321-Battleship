/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

// import all neccessary packages
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Robert Womack
 */
public class BattleshipGUI {

    Frame frame;    // Declare a Frame type variable
    int sunk;       // counter for enemy ships sunk

    /**
     * Constructor that sets up the main menu and rules display
     */
    BattleshipGUI() {

        final JPanel mainPanel = new JPanel();              // content for main menu
        final JFrame mainFrame = new JFrame("Main Menu");   // frame to hold content   
        final JFrame ruleFrame = new JFrame("Game Rules");  // frame to contain rules

        mainPanel.setPreferredSize(new Dimension(400, 200));    // set display size
        mainFrame.getContentPane().add(mainPanel);              // add content to frame

        final JTextArea textRules = new JTextArea(30, 70);      // txt area to hold rules
        textRules.setFont(new Font("Sanserif", Font.PLAIN, 20));// adjust font (easier to read)

        FlowLayout rulesLayout = new FlowLayout();      // create layout for rules window
        GridLayout mainLayout = new GridLayout(3, 1);   // has 3 rows and one column
        JButton startBtn = new JButton("Play Game");    // button to start game
        JButton rulesBtn = new JButton("View Rules");   // button to view rules
        JButton exitBtn = new JButton("Exit Game");     // button to leave game

        startBtn.addActionListener(new // add actions for start button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainFrame.setVisible(false);    // hide window
                useNameWindow();                // call window to get player name
            }
        });

        rulesBtn.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ruleFrame.setVisible(true);	// show window
                readFile(textRules);            // read text file to show rules
            }
        });

        exitBtn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0); // quit app
            }
        });

        // add content to mainPanel
        mainPanel.setLayout(mainLayout);
        mainPanel.add(startBtn, BorderLayout.CENTER);
        mainPanel.add(rulesBtn, BorderLayout.CENTER);
        mainPanel.add(exitBtn, BorderLayout.CENTER);

        // add content to ruleFrame
        ruleFrame.setLayout(rulesLayout);
        ruleFrame.add(textRules);
        ruleFrame.pack();
        ruleFrame.setResizable(false);
        ruleFrame.setLocationRelativeTo(null);

        mainFrame.add(mainPanel);               // put mainPanel inside respective frame
        mainFrame.pack();                       // pack all components into minimum space
        mainFrame.setResizable(false);          // window size is fixed
        mainFrame.setLocationRelativeTo(null);  // show window in center of screen
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
        mainFrame.setVisible(true);             // show main window
    }

    /**
     * Creates the primary window for playing the application
     */
    public void useNameWindow() {
        final JFrame nameFrame = new JFrame("Enter Name");  // window asking for name
        final JPanel namePanel = new JPanel();              // content panel

        final JLabel prompt = new JLabel("Enter Name");     // user prompt
        final JTextField nameFld = new JTextField(20);      // txt field to enter name
        final JButton submitBtn = new JButton("Submit");        // button to submit name
        prompt.setFont(new Font("Sanserif", Font.PLAIN, 20));   // set font for prompt
        nameFld.setFont(new Font("Sanserif", Font.PLAIN, 20));  // set font for txt field

        namePanel.setLayout(new GridLayout(3, 1));  // create and set layout for content panel
        namePanel.add(prompt);      // add content to panel
        namePanel.add(nameFld);     // add txt field
        namePanel.add(submitBtn);   // add button
        namePanel.setPreferredSize(new Dimension(400, 200));    // set window size

        nameFrame.getContentPane().add(namePanel);  // and panel to respective frame
        nameFrame.pack();
        nameFrame.setResizable(false);              // window size fixed
        nameFrame.setLocationRelativeTo(null);      // show window in center of screen
        nameFrame.setVisible(true);                 // show window

        submitBtn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                useMainWindow(nameFld.getText());   // call main game window
                nameFrame.setVisible(false);        // hide current window
            }
        });

        nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
    }

    public void useMainWindow(String name) {
        final JFrame gameFrame = new JFrame("Greetings, Admiral " + name + "!");    //label addressing player
        BorderLayout gameLayout = new BorderLayout();       // create layout for content
        sunk = 0;                           // counter to hold number of enemy ships sunk
        gameFrame.setLayout(gameLayout);    // set layout for frame

        JPanel oceanPanel = new JPanel();   // create panels to hold ->
        JPanel targetPanel = new JPanel();  // -> target and ocean grids
        OceanGUI ocean = new OceanGUI(oceanPanel);      // create instances of ->
        TargetGUI target = new TargetGUI(targetPanel);  // -> target and ocean grids

        JPanel buttonPanel = new JPanel();              // create panel to hold buttons             
        GridLayout buttonLayout = new GridLayout(5, 1); // create panel layout
        JButton carrier = new JButton("Carrier");       // create buttons to ->
        JButton battleship = new JButton("Battleship"); // -> mark enemy ships sunk
        JButton cruiser = new JButton("Cruiser");
        JButton sub = new JButton("Submarine");
        JButton destroyer = new JButton("Destroyer");

        buttonPanel.add(carrier);       // add buttons to panel
        buttonPanel.add(battleship);
        buttonPanel.add(cruiser);
        buttonPanel.add(sub);
        buttonPanel.add(destroyer);
        buttonPanel.setLayout(buttonLayout);    // set panel layout

        carrier.addActionListener(new // add actions for rules button
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
        battleship.addActionListener(new // add actions for rules button
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
        cruiser.addActionListener(new // add actions for rules button
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
        sub.addActionListener(new // add actions for rules button
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
        destroyer.addActionListener(new // add actions for rules button
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

        // End Grid Buttons
        gameFrame.add(oceanPanel, BorderLayout.LINE_START);
        gameFrame.add(targetPanel, BorderLayout.CENTER);
        gameFrame.add(buttonPanel, BorderLayout.LINE_END);

        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour

    }

    public static void readFile(JTextArea txtfld) {   // function to read file containing rules
        try {
            BufferedReader rules = new BufferedReader(new FileReader("rules.txt"));

            String line;
            while ((line = rules.readLine()) != null) {
                txtfld.append(line + "\n");
            }
            rules.close();
        } catch (IOException ex) {
            System.out.println("Reader exception");
        }
    }

    public boolean checkVictory() {
        sunk++;
        if (sunk == 5) {
            return true;
        } else {
            return false;
        }
    }

    public void useVicWindow(JFrame frame, String name) {
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
                frame.setVisible(false);
                useMainWindow(name);
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
