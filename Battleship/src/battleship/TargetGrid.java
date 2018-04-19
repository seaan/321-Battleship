package battleship;

/**
 * A singleton that acts as the target grid for a game of Battleship.
 * The target grid is where the user places pegs indicating whether their shots
 * have hit or missed an enemy ship. TargetGrid contains a grid indicating the
 * Status of each location.
 *
 * @author Kyle Daigle, Sean Widmier
 */
public class TargetGrid {
/* A grid containing the Status of each location. */
    private static Position.Status[][] grid;

    /**
     * A private constructor, so that OceanGrid is a singleton. The constructor
     * can only be called in the getInstance function to ensure only one 
     * object of this class exists.
     */
    public TargetGrid() {
        grid = new Position.Status[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = Position.Status.EMPTY;                 //initialize grid to "empty"
            }
        }
    }
    
    /**
     * Sets the status of the location given in position to the held status of
     * position.
     *
     * @param position The Position on which the peg to be set is.
     */
    protected Position.Status setPeg(Position position) {
        grid[position.getX()][position.getY()] = position.getStatus();
        return position.getStatus();
    }

    /**
     * Accesses the Status of the grid at the given Position.
     *
     * @param position The Position to be used when accessing the grid.
     * @return The Status of the grid at position.
     */
    protected Position.Status getGridAt(Position position) {
        return grid[position.getX()][position.getY()];
    }
}
