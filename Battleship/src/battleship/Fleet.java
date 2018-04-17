package battleship;

/**
 * Aggregates instances of ship needed for a game of Battleship. This includes:
 * Carrier, Battleship, Cruiser, Submarine, and Destroyer. Also provides methods
 * to access each Ship, as well as methods to easily place, check the position
 * of, and hit multiple or unspecified ships.
 *
 * @author Sean Widmier, Kyle Daigle
 */
public class Fleet {

    /* Instances of each ship needed for a game of Battleship */
    private final Ship Carrier;
    private final Ship Battleship;
    private final Ship Cruiser;
    private final Ship Submarine;
    private final Ship Destroyer;

    /**
     * Constructor for fleet, will create instances of each ship needed for a
     * game of Battleship. Includes: Carrier, Battleship, Cruiser, Submarine,
     * and Destroyer.
     */
    protected Fleet() {
        Carrier = new Ship("Carrier", 5);
        Battleship = new Ship("Battleship", 4);
        Cruiser = new Ship("Cruiser", 3);
        Submarine = new Ship("Submarine", 3);
        Destroyer = new Ship("Destroyer", 2);
    }

    /**
     * Accessor for the Fleet's instance of the Carrier Ship.
     *
     * @return A Ship instance for the Carrier.
     */
    public Ship getCarrier() {
        return Carrier;
    }

    /**
     * Accessor for the Fleet's instance of the Battleship Ship.
     *
     * @return A ship instance for the Battleship.
     */
    public Ship getBattleship() {
        return Battleship;
    }

    /**
     * Accessor for the Fleet's instance of the Cruiser Ship.
     *
     * @return A ship instance for the Cruiser.
     */
    public Ship getCruiser() {
        return Cruiser;
    }

    /**
     * Accessor for the Fleet's instance of the Submarine Ship.
     *
     * @return A ship instance for the Submarine.
     */
    public Ship getSubmarine() {
        return Submarine;
    }

    /**
     * Accessor for the Fleet's instance of the Destroyer Ship.
     *
     * @return A ship instance for the Destroyer.
     */
    public Ship getDestroyer() {
        return Destroyer;
    }

    /**
     * Checks to see if there is a ship within the position given.
     *
     * @param position The Position to be checked.
     *
     * @return True if there is a ship present, false if not.
     */
    protected boolean checkShipLocation(Position position) {
        if (Carrier.checkPosition(position)) {
            return true;
        } else if (Battleship.checkPosition(position)) {
            return true;
        } else if (Cruiser.checkPosition(position)) {
            return true;
        } else if (Submarine.checkPosition(position)) {
            return true;
        } else if (Destroyer.checkPosition(position)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes a given position and increments the hit counter of the appropriate
     * ship.
     *
     * @param position The Position to be checked.
     */
    protected void hitLocation(Position position) {
        if (Carrier.checkPosition(position)) {
            Carrier.incrementHitCount();
        } else if (Battleship.checkPosition(position)) {
            Battleship.incrementHitCount();
        } else if (Cruiser.checkPosition(position)) {
            Cruiser.incrementHitCount();
        } else if (Submarine.checkPosition(position)) {
            Submarine.incrementHitCount();
        } else if (Destroyer.checkPosition(position)) {
            Destroyer.incrementHitCount();
        }
    }

    /**
     * Sets the position of a given ship type within the Fleet aggregation.
     *
     * @param type The type of ship to be placed, includes:
     * Carrier, Battleship, Cruiser, Submarine, and Destroyer.
     * @param start The "start" position of the ship, typically the front.
     * @param end The "end" position of the ship, typically the back.
     */
    protected void placeShip(String type, Position start, Position end) {
        switch (type) {
            case "Carrier":
                Carrier.setPosition(start, end);
                break;
            case "Battleship":
                Battleship.setPosition(start, end);
                break;
            case "Cruiser":
                Cruiser.setPosition(start, end);
                break;
            case "Submarine":
                Submarine.setPosition(start, end);
                break;
            case "Destroyer":
                Destroyer.setPosition(start, end);
                break;
        }
    }
}
