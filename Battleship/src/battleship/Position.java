/* 
 * CREATED IN NETBEANS IDE 8.2
 * CS-321-01 Final Project: Battleship
 * Kyle Daigle, Sean Widmier, Robert Womack, Kelly Manley
 */
package battleship;

/**
 * A single position, used on either TargetGrid or OceanGrid. Independent of
 * which Grid is used. Contains fields detailing the location and status of
 * the position.
 *
 * @author Sean Widmier, Krowle Daigle
 */
public class Position {

    /* The horizontal axis location of the position on the appropriate grid. */
    private int col;
    /* The vertical axis location of the position on the appopriate grid. */
    private int row;

    private Status status;

    /**
     * Details whether the position is empty or filled with a
     * hit peg, miss peg, or ship.
     */
    protected enum Status {
        EMPTY, HIT, MISS, SHIP
    }

    /**
     * Constructor for Position, will set inputs to fields.
     *
     * @param col The horizontal axis location on the appropriate grid.
     * @param row The vertical axis location on the appropriate grid.
     * @param status The status (EMPTY, HIT, MISS, SHIP) of the Position.
     */
    protected Position(int col, int row, Status status) {
        this.col = col;
        this.row = row;
        this.status = status;
    }

    /**
     * Mutates the location fields of the Position.
     *
     * @param col The horizontal axis location on the appropriate grid.
     * @param row The vertical axis location on the appropriate grid.
     */
    protected void setPosition(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Mutates the status field of the Position.
     *
     * @param status The status (EMPTY, HIT, MISS, SHIP) of the Position.
     */
    protected void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Accesses the status field of the Position.
     *
     * @return The status (EMPTY, HIT, MISS, SHIP) of the Position.
     */
    protected Status getStatus() {
        return status;
    }

    /**
     * Accesses the col field of the Position.
     *
     * @return The horizontal location of the Position.
     */
    protected int getCol() {
        return col;
    }

    /**
     * Accesses the row field of the Position.
     *
     * @return The vertical location of the Position.
     */
    protected int getRow() {
        return row;
    }
}
