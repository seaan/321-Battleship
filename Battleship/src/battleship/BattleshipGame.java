/* 
 * CREATED IN NETBEANS IDE 8.2
 * CS-321-01 Final Project: Battleship
 * Kyle Daigle, Sean Widmier, Robert Womack, Kelly Manley
 */
package battleship;

/**
 * Singleton class that contains elements of a game of battleship
 *
 * @author Sean Widmier, Kyle Daigle
 */
public class BattleshipGame {

    /* Singleton instance of object */
    private static BattleshipGame instance = null;

    /* An instance of both TargetGrid and OceanGrid are needed. */
    static TargetGrid target = new TargetGrid();
    static OceanGrid ocean = new OceanGrid();

    /**
     * A private constructor, so that BattleshipGame is a singleton. The
     * constructor
     * can only be called in the getInstance function to ensure only one
     * object of this class exists.
     */
    private BattleshipGame() {
    }

    /**
     * Checks for an instance of BattleshipGame. If there is no instance, one
     * is created.
     *
     * @return singleton instance of BattleshipGame
     */
    protected static BattleshipGame getInstance() {
        if (instance == null) {
            instance = new BattleshipGame();
        }
        return instance;
    }

    /**
     * Updates the location of a ship on the ocean grid.
     *
     * @param start The start position to move the ship to.
     * @param end The end position to move the ship to.
     * @param type The type of ship to be moved, includes:
     * Carrier, Battleship, Cruiser, Submarine, Destroyer
     */
    protected void updateShip(Position start, Position end, Fleet.GameShip type) {
        ocean.getFleet().placeShip(type, start, end);
    }

    /**
     * Checks the number of friendly and enemy ships sunk.
     * If either are above 4, the game has ended.
     *
     * @return 1 = victory, 2 = defeat, 0 = no change
     */
    protected int checkGameStatus() {
        if (ocean.getFriendlyShipsSunk() == 5) {
            return 2;
        } else if (target.getEnemyShipsSunk() == 5) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Resets each member of BattleshipGame such that it will be a new game.
     */
    protected void resetGame() {
        ocean.resetGrid();
        target.resetGrid();
        ocean.getFleet().resetFleet();
    }

    /**
     * Updates the given position on the given grid to be the given peg.
     *
     * @param position The Position on the grid to be updated.
     * @param gridType The grid upon which the peg will be updated.
     * 0 = Ocean Grid
     * 1 = Target Grid
     * @return The status of the peg set.
     */
    protected Position.Status updatePeg(Position position, int gridType) {
        if (gridType == 0) {
            /* Ocean Grid */
            return ocean.setPeg(position);
        } else {
            /* Target Grid */
            return target.setPeg(position);
        }
    }

    /**
     * Accesses the local instance of OceanGrid.
     *
     * @return instance of OceanGrid
     */
    protected OceanGrid getOceanGrid() {
        return ocean;
    }

    /**
     * Accesses the local instance of TargetGrid.
     *
     * @return instance of TargetGrid
     */
    protected TargetGrid getTargetGrid() {
        return target;
    }
}
