/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 * A single ship within the game, has fields detailing the type, length,
 * hit count, sunk status, and position, as well as appropriate accessors and
 * mutators. Meant to be aggregated within an instance of Fleet.
 *
 * @author Sean Widmier, Kyle Daigle
 */
public class Ship {

    /* The field for type of ship is held by a string. This can include:
     * Carrier, Battleship, Cruiser, Submarine, Destroyer.
     */
    private final String shipType;
    /* The number of grid squares that the ship takes up. */
    private final int shipLength;
    /* The number of hits the ship has sustained. */
    private int hitCount;
    /* Indicates whether the ship object has been sunk or not. */
    private boolean isSunk;

    /* Holds the "start" position of the ship, typically the front. */
    private Position startPosition;
    /* Holds the "end" position of the ship, typically the back. */
    private Position endPosition;

    Orientation orientation;

    /**
     * The orientation of the ship, either horizontal (x locations are the same)
     * or vertical (y locations are the same), to be used in setPosition().
     */
    private enum Orientation {
        HORIZONTAL, VERTICAL;
    }

    /**
     * Constructor for ship, will create a not sunk, not hit ship of
     * given type and length.
     *
     * @param type The type of ship to be created as a string, includes:
     * Carrier, Battleship, Cruiser, Submarine, Destroyer
     * @param length Appropriate lengths given below:
     * Carrier = 5, Battleship = 4, Cruiser = 3, Submarine = 2, Destroyer = 2
     */
    protected Ship(String type, int length) {
        shipType = type;
        shipLength = length;
        hitCount = 0;
        isSunk = false;

        startPosition = new Position(0, 0, Position.Status.EMPTY);
        endPosition = new Position(0, 0, Position.Status.EMPTY);
    }

    /**
     * Accessor for shipType.
     *
     * @return The type of ship as a string, examples:
     * "Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"
     */
    protected String getType() {
        return shipType;
    }

    /**
     * Accessor for shipLength.
     *
     * @return length of the ship as an int
     */
    protected int getLength() {
        return shipLength;
    }

    /**
     * Accessor for isSunk.
     *
     * @return The ship's sunk status as a boolean
     */
    protected Boolean getStatus() {
        return isSunk;
    }

    /**
     * Mutator for hit count. Will also check to see if the ship has been
     * sunk, and change isSunk if necessary,
     * as well as sunkCount in OceanGrid.
     */
    protected void incrementHitCount() {
        hitCount++;

        if (hitCount == shipLength) {
            isSunk = true;

            BattleshipGame bsg = BattleshipGame.getInstance();
            bsg.getOceanGrid().incrementFriendlyShipsSunk();
        }
    }

    /**
     * Sets the start and end positions of a ship, then fills in the other
     * necessary spaces.
     *
     * @param start The new "start" position of the ship, usually the front.
     * @param end The new "end" position of the ship, usually the back.
     */
    protected void setPosition(Position start, Position end) {
        /* Create a reference to the OceanGrid singleton. */
        BattleshipGame bsg = BattleshipGame.getInstance();
        /* Create a position to be used when setPeg is called. */
        Position newPosition = new Position(start.getCol(), start.getRow(), Position.Status.SHIP);

        /* Copy the contents of the parameters to the Ship fields. */
        startPosition.setPosition(start.getCol(), start.getRow());
        startPosition.setStatus(start.getStatus());

        endPosition.setPosition(end.getCol(), end.getRow());
        endPosition.setStatus(end.getStatus());

        /* First set the orientation of the ship. */
        if (startPosition.getCol() == endPosition.getCol()) {
            orientation = Orientation.VERTICAL;
        } else if (startPosition.getRow() == endPosition.getRow()) {
            orientation = Orientation.HORIZONTAL;
        }

        /*
                 * Based on the orientation of the ship, we'll need to fill 
                 * in the rest of the positions.
         */
        switch (orientation) {
            case VERTICAL:
                /* Check to see which position is the top and which is the bottom. */
                if (startPosition.getRow() < endPosition.getRow()) {
                    /* For the length of the ship, add ship pegs. */
                    for (int i = startPosition.getRow(); i <= endPosition.getRow(); i++) {
                        newPosition.setPosition(startPosition.getCol(), i);
                        bsg.getOceanGrid().setPeg(newPosition);
                    }
                } else {
                    for (int i = endPosition.getRow(); i <= startPosition.getRow(); i++) {
                        /* For the length of the ship, add ship pegs. */
                        newPosition.setPosition(startPosition.getCol(), i);
                        bsg.getOceanGrid().setPeg(newPosition);
                    }
                }
            case HORIZONTAL:
                /* Check to see which position is the left and which is the right. */
                if (startPosition.getCol() < endPosition.getCol()) {
                    for (int i = startPosition.getCol(); i <= endPosition.getCol(); i++) {
                        /* For the length of the ship, add ship pegs. */
                        newPosition.setPosition(i, startPosition.getRow());
                        bsg.getOceanGrid().setPeg(newPosition);
                    }
                } else {
                    for (int i = endPosition.getCol(); i <= startPosition.getCol(); i++) {
                        /* For the length of the ship, add ship pegs. */
                        newPosition.setPosition(i, startPosition.getRow());
                        bsg.getOceanGrid().setPeg(newPosition);
                    }
                }
        }
    }

    /**
     * Checks to see if the Ship is located within the given position.
     *
     * @param position The position to be checked
     *
     * @return True indicates the ship is present, false indicates it is not.
     */
    protected boolean checkPosition(Position position) {
        /* Check to see if the position is within the range of the ship. */
        switch(orientation){
            case HORIZONTAL:
                if(position.getRow() == startPosition.getRow()){
                    if((position.getCol() >= startPosition.getCol()) && (position.getCol() <= endPosition.getCol()))
                        return true;
                    else if((position.getCol() <= startPosition.getCol()) && (position.getCol() >= endPosition.getCol()))
                        return true;
                }
                return false;
                
            case VERTICAL:
                if(position.getCol() == startPosition.getCol()){
                    if((position.getRow() >= startPosition.getRow()) && (position.getRow() <= endPosition.getRow()))
                        return true;
                    else if((position.getRow() <= startPosition.getRow()) && (position.getRow() >= endPosition.getRow()))
                        return true;
                }
                return false;
        }
        return false;
    }
}