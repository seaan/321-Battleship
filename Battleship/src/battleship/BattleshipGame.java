package battleship;

/**
 * Contains elements of a game of battleship, as well as handle the
 * interactions between the GUI and the underlying data structures.
 *
 * @author Sean Widmier, Kyle Daigle
 */
public class BattleshipGame {
    /* Singleton instance of object */
    private static BattleshipGame instance = null;
    
    /* Field to hold the player's name, which will be used in initalizeGame */
    private String playerName;

    /* An instance of Fleet is needed to hold the appropriate ships. */
    Fleet fleet;

    /* An instance of both TargetGrid and OceanGrid are needed. */
    TargetGrid target;
    OceanGrid ocean;

    /**
     * Starts a game of Battleship, greeting the player then initializing
     * grids and ships needed.
     */
    private BattleshipGame() {
//            playerName = gui.promptPlayerName();
//            gui.greetPlayer(playerName);

        ocean = new OceanGrid();
        target = new TargetGrid();

        fleet = new Fleet();
    }
    
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
    protected void updateShip(Position start, Position end, String type) {
        switch (type) {
            case "Carrier":
                fleet.getCarrier().setPosition(start, end);
                break;
            case "Battleship":
                fleet.getBattleship().setPosition(start, end);
                break;
            case "Cruiser":
                fleet.getCruiser().setPosition(start, end);
                break;
            case "Submarine":
                fleet.getSubmarine().setPosition(start, end);
                break;
            case "Destroyer":
                fleet.getDestroyer().setPosition(start, end);
                break;
        }
    }

    /**
     * Checks the number of friendly and enemy ships sunk.
     * If either are above 4, the game has ended.
     */
    protected void checkGameStatus() {
        if (ocean.getFriendlyShipsSunk() > 4) {
//                        gui.showDefeatMesssage();
        }
//        if(gui.getEnemyShipSunk() > 4)
//            gui.showVictoryMessage();
    }

    /**
     * Updates the given position on the given grid to be the given peg.
     *
     * @param position The Position on the grid to be updated.
     * @param gridType The grid upon which the peg will be updated.
     * 0 = Ocean Grid
     * 1 = Target Grid
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
    
    protected OceanGrid getOceanGrid(){
        return ocean;
    }
    
    protected TargetGrid getTargetGrid(){
        return target;
    }
}
