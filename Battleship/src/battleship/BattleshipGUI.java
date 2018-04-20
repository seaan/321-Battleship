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

    /**
     * @param args the command line arguments
     */
    Frame frame;    // Declare a Frame type variable

    BattleshipGUI() {

        BattleshipGame bg = new BattleshipGame();   // create instance of BattleShipGame

        final JPanel mainPanel = new JPanel();       // main menu
        final JFrame mainFrame = new JFrame("Main Menu");
        mainPanel.setPreferredSize(new Dimension(400, 200));
        mainFrame.getContentPane().add(mainPanel);

        final JFrame rules_frame = new JFrame("Game Rules");    // rules display
        final JTextArea rules_txt = new JTextArea(100, 100);       // txt area to hold rules

        FlowLayout rules_layout = new FlowLayout();     // set the layouts for main menu and rules window
        GridLayout main_layout = new GridLayout(3, 1);   // has 3 rows and one column
        JButton start_btn = new JButton("Play Game");   // create buttons
        JButton rules_btn = new JButton("View Rules");
        JButton exit_btn = new JButton("Exit Game");

        start_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {  // add actions for start button

                //BattleshipGUI window = new BattleshipGUI(); // create instance
                mainFrame.setVisible(false);
                useNameWindow();
                //System.exit(0);
            }
        });

        rules_btn.addActionListener(new // add actions for rules button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                rules_frame.setVisible(true);	// make window visible
                readFile(rules_txt);
            }
        });

        exit_btn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("exit");
                System.exit(0);
            }
        });

        mainPanel.setLayout(main_layout);
        mainPanel.add(start_btn, BorderLayout.CENTER);
        mainPanel.add(rules_btn, BorderLayout.CENTER);
        mainPanel.add(exit_btn, BorderLayout.CENTER);

        rules_frame.setLayout(rules_layout);
        rules_frame.add(rules_txt);
        rules_frame.pack();

        mainPanel.setSize(100, 100);
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
        mainFrame.pack();		// pack all components into minimum space
        mainFrame.setVisible(true);	// make window visible
    }

    public void useNameWindow() {
        final JFrame name_frame = new JFrame("Enter Name");     // window that asks for name
        final JLabel prompt = new JLabel("Enter Name");
        final JTextField name_fld = new JTextField(20);
        final JButton submit_btn = new JButton("Submit");

        name_frame.setLayout(new GridLayout(3, 1));
        name_frame.add(prompt);
        name_frame.add(name_fld);
        name_frame.add(submit_btn);
        name_frame.pack();
        name_frame.setVisible(true);

        submit_btn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println(name_fld.getText());
                useMainWindow(name_fld.getText());
            }
        });

        name_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
    }

    public void useMainWindow(String name) {
        final JFrame gameFrame = new JFrame("Greetings, Admiral " + name + "!");
        BorderLayout gameLayout = new BorderLayout();
        gameFrame.setLayout(gameLayout);
        //bg.initializeGame();

        JPanel test = new JPanel();
        JPanel foo = new JPanel();
        OceanGUI otest = new OceanGUI(test);
        gameFrame.add(test, BorderLayout.LINE_START);
        TargetGUI ttest = new TargetGUI(foo);
        gameFrame.add(foo, BorderLayout.LINE_END);

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
}
