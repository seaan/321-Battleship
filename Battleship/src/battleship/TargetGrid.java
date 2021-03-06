/* 
 * CREATED IN NETBEANS IDE 8.2
 * CS-321-01 Final Project: Battleship
 * Kyle Daigle, Sean Widmier, Robert Womack, Kelly Manley
 */
package battleship;

/**
 * This class acts as the target grid for a game of Battleship.
 * The target grid is where the user places pegs indicating whether their shots
 * have hit or missed an enemy ship. TargetGrid contains a grid indicating the
 * Status of each location. Visually represented by the TargetGUI JPanel
 * component
 *
 * @author Kyle Daigle, Sean Widmier
 */
public class TargetGrid {

    /* A grid containing the Status of each location. */
    private static Position.Status[][] grid;
    /* Counter to keep track of the number of friendly ships sunk. */
    private static int shipsSunk;

    /**
     * Makes an instance of TargetGrid such that the resultant grid is empty.
     */
    protected TargetGrid() {
        grid = new Position.Status[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = Position.Status.EMPTY;
            }
        }
    }

    /**
     * Sets the status of the location given in position to the held status of
     * position.
     *
     * @param position The Position on which the peg to be set is.
     * @return The status of the peg at position.
     */
    protected Position.Status setPeg(Position position) {
        grid[position.getCol()][position.getRow()] = position.getStatus();
        return position.getStatus();
    }

    /**
     * Accesses the Status of the grid at the given Position.
     *
     * @param position The Position to be used when accessing the grid.
     * @return The Status of the grid at position.
     */
    protected Position.Status getGridAt(Position position) {
        return grid[position.getCol()][position.getRow()];
    }

    /**
     * Mutates the counter for the number of enemy ships sunk.
     */
    protected void incrementEnemyShipsSunk() {
        shipsSunk++;
    }

    /**
     * Accessor for the counter for the number of enemy ships sunk.
     *
     * @return The number of enemy ships sunk.
     */
    protected int getEnemyShipsSunk() {
        return shipsSunk;
    }

    /**
     * Resets each position such that it is empty.
     */
    protected void resetGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = Position.Status.EMPTY;
            }
        }
        shipsSunk = 0;
    }
}
