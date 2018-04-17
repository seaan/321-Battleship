/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 * Contains the main method for the program.
 *
 * @author Sean Widmier, Kyle Daigle, Kelly Manley, Robert Womack
 */
public class BattleshipMain {

    static BattleshipGame bsg = new BattleshipGame();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* TESTING, DELETE */
        bsg.initializeGame();

        /* Carrier 5 */
        Position startPosition = new Position(1, 1, Position.Status.SHIP);
        Position endPosition = new Position(1, 6, Position.Status.SHIP);
        bsg.updateShip(startPosition, endPosition, "Carrier");

        /* Battleship 4 */
        startPosition.setPosition(2, 1);
        endPosition.setPosition(2, 5);
        bsg.updateShip(startPosition, endPosition, "Battleship");

        /* Cruiser 3 */
        startPosition.setPosition(3, 1);
        endPosition.setPosition(3, 4);
        bsg.updateShip(startPosition, endPosition, "Cruiser");

        /* Submarine 3 */
        startPosition.setPosition(4, 1);
        endPosition.setPosition(4, 4);
        bsg.updateShip(startPosition, endPosition, "Submarine");

        /* Destroyer 2 */
        startPosition.setPosition(5, 1);
        endPosition.setPosition(5, 3);
        bsg.updateShip(startPosition, endPosition, "Destroyer");

        bsg.ocean.printGrid();

//        Launcher launcher = new Launcher();
//        launcher.runGame();
    }
}
