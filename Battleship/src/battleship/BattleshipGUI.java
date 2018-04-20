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

    /**
     * @param args the command line arguments
     */
    Frame frame;    // Declare a Frame type variable

    BattleshipGUI() {

        BattleshipGame bg = new BattleshipGame();   // create instance of BattleShipGame
        final JPanel mainPanel = new JPanel();       // main menu
        final JFrame mainFrame = new JFrame("Main Menu");
        final JFrame ruleFrame = new JFrame("Game Rules");    // rules display

        mainPanel.setPreferredSize(new Dimension(400, 200));
        mainFrame.getContentPane().add(mainPanel);

        final JTextArea textRules = new JTextArea(30, 70);    // txt area to hold rules
        textRules.setFont(new Font("Sanserif", Font.PLAIN, 20));

        FlowLayout rules_layout = new FlowLayout();     // set the layouts for main menu and rules window
        GridLayout main_layout = new GridLayout(3, 1);  // has 3 rows and one column
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
                ruleFrame.setVisible(true);	// make window visible
                readFile(textRules);
            }
        });

        exit_btn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("exit");
                System.exit(0);
            }
        });
        // add content to mainPanel

        mainPanel.setLayout(main_layout);
        mainPanel.add(start_btn, BorderLayout.CENTER);
        mainPanel.add(rules_btn, BorderLayout.CENTER);
        mainPanel.add(exit_btn, BorderLayout.CENTER);

        // add content to ruleFrame
        ruleFrame.setLayout(rules_layout);
        ruleFrame.add(textRules);
        ruleFrame.pack();
        ruleFrame.setResizable(false);
        ruleFrame.setLocationRelativeTo(null);

        // put mainPanel inside respective frame
        mainFrame.add(mainPanel);
        mainFrame.pack();		// pack all components into minimum space
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
        mainFrame.setVisible(true);	// make window visible
    }

    public void useNameWindow() {
        final JFrame nameFrame = new JFrame("Enter Name");     // window that asks for name
        final JPanel namePanel = new JPanel();

        final JLabel prompt = new JLabel("Enter Name");
        final JTextField nameFld = new JTextField(20);
        final JButton submitBtn = new JButton("Submit");
        prompt.setFont(new Font("Sanserif", Font.PLAIN, 20));
        nameFld.setFont(new Font("Sanserif", Font.PLAIN, 20));

        namePanel.setLayout(new GridLayout(3, 1));
        namePanel.add(prompt);
        namePanel.add(nameFld);
        namePanel.add(submitBtn);
        namePanel.setPreferredSize(new Dimension(400, 200));

        nameFrame.getContentPane().add(namePanel);
        nameFrame.pack();
        nameFrame.setResizable(false);
        nameFrame.setLocationRelativeTo(null);
        nameFrame.setVisible(true);

        submitBtn.addActionListener(new // add actions for exit button
                ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println(nameFld.getText());
                useMainWindow(nameFld.getText());
                nameFrame.setVisible(false);
            }
        });

        nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // default close behaviour
    }

    public void useMainWindow(String name) {
        final JFrame gameFrame = new JFrame("Greetings, Admiral " + name + "!");
        BorderLayout gameLayout = new BorderLayout();
        GridLayout guideLayout = new GridLayout(10, 1);
        for (int i = 0; i < 10; i++) {
            JLabel label = new JLabel("test" + i);
        }
        gameFrame.setLayout(gameLayout);

        JPanel oceanPanel = new JPanel();
        JPanel targetPanel = new JPanel();
        JPanel guidePanel = new JPanel();
        guidePanel.setLayout(guideLayout);
        OceanGUI ogui = new OceanGUI(oceanPanel);
        gameFrame.add(oceanPanel, BorderLayout.LINE_START);
        TargetGUI tgui = new TargetGUI(targetPanel);
        gameFrame.add(targetPanel, BorderLayout.LINE_END);
        gameFrame.add(guidePanel, BorderLayout.CENTER);

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
}
