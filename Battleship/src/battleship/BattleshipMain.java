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
import java.awt.event.*;
import javax.swing.*;

/**
 * Contains the main method for the program.
 *
 * @author Sean Widmier, Kyle Daigle, Kelly Manley, Robert Womack
 */
import javax.swing.SwingUtilities;

/**
 * The entry main() method
 */
class BattleshipMain {

    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BattleshipGUI bgui = new BattleshipGUI();
                //OceanGrid o = OceanGrid.getInstance();
                /*o.getFleet().placeShip("Carrier", 1, 1, 5, 1);
            o.getFleet().placeShip("Battleship", 4, 4, 7, 4);
            o.getFleet().placeShip("Cruiser", 7, 7, 7, 5);
            o.getFleet().placeShip("Submarine", 7, 0, 9, 0);
            o.getFleet().placeShip("Destroyer", 7, 2, 8, 2);*/
                //new BattleshipMain(); // Let the constructor do the job

            }
        });
    }
}
