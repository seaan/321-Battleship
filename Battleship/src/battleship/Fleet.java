package battleship;

/**
 *
 * @author Sean
 */
public class Fleet {

    private final Ship Carrier;
    private final Ship Battleship;
    private final Ship Cruiser;
    private final Ship Submarine;
    private final Ship Destroyer;

    protected Fleet() {
        Carrier = new Ship("Carrier", 5);
        Battleship = new Ship("Battleship", 4);
        Cruiser = new Ship("Cruiser", 3);
        Submarine = new Ship("Submarine", 3);
        Destroyer = new Ship("Destroyer", 2);
    }

    public Ship getCarrier() {
        return Carrier;
    }

    public Ship getBattleship() {
        return Battleship;
    }

    public Ship getCruiser() {
        return Cruiser;
    }

    public Ship getSubmarine() {
        return Submarine;
    }

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
     *
     * @param type the value of type
     * @param start the value of start
     * @param end the value of end
     */
    protected void placeShip(String type, Position start, Position end) {        //testing function
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
