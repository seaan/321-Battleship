package battleship;

/**
 * A singleton that acts as the ocean grid for a game of Battleship.
 * The ocean grid is where the user places their ships, and marks where the
 * enemy has hit their ships or missed a shot. OceanGrid contains an instance of
 * Fleet, as well as a counter for the number of ships sunk and a grid
 * indicating the Status of each location.
 *
 * @author Kyle Daigle, Sean Widmier
 */
public class OceanGrid {

    /* Instance of Fleet to hold all of the ships necessary for OceanGrid */
    private static Fleet fleet;
    /* Counter to keep track of the number of friendly ships sunk. */
    private static int shipsSunk;
    /* A grid containing information about the Status of each location. */
    private static Position.Status[][] grid;

    /**
     * Makes an instance of OceanGrid such that the resultant grid is empty.
     */
    protected OceanGrid() {
        grid = new Position.Status[10][10];
        fleet = new Fleet();
        shipsSunk = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = Position.Status.EMPTY;
            }
        }

    }

    /**
     * Accesses the instance of Fleet in OceanGrid.
     *
     * @return A fleet instance.
     */
    public static Fleet getFleet() {
        return fleet;
    }

    /**
     * Sets the status of the location given in position to the held status of
     * position. Also checks whether the location is currently filled by a ship,
     * if it is call hitLocation(), and set the status to HIT.
     *
     * @param position The Position on which the peg to be set is.
     * @return The Status of the peg set.
     */
    protected Position.Status setPeg(Position position) {
        if(position.getStatus() == Position.Status.SHIP){
            grid[position.getCol()][position.getRow()] = position.getStatus();
            return Position.Status.SHIP;
        }
        else if (fleet.checkShipLocation(position)) {
            fleet.hitLocation(position);
            grid[position.getCol()][position.getRow()] = Position.Status.HIT;
            return Position.Status.HIT;
        }
        else{
            grid[position.getCol()][position.getRow()] = position.getStatus();
            return position.getStatus();
        }        
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
     * Mutates the counter for the number of friendly ships sunk.
     */
    protected void incrementFriendlyShipsSunk() {
        shipsSunk++;
    }

    /**
     * Accessor for the counter for the number of friendly ships sunk.
     *
     * @return The number of friendly ships sunk.
     */
    protected int getFriendlyShipsSunk() {
        return shipsSunk;
    }
    
    protected void printGrid() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++)
            {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Resets the grid such that each position is empty.
     */
    protected void resetGrid() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++)
            {
                grid[i][j] = Position.Status.EMPTY;
            }
        }
        shipsSunk = 0;
    }
}
