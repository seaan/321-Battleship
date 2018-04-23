/* 
 * CREATED IN NETBEANS IDE 8.2
 * CS-321-01 Final Project: Battleship
 * Kyle Daigle, Sean Widmier, Robert Womack, Kelly Manley
 */
package battleship;

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
            }
        });
    }
}
