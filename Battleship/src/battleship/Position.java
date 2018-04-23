package battleship;

/**
 * A single position, used on either TargetGrid or OceanGrid. Independent of
 * which Grid is used. Contains fields detailing the location and status of
 * the position.
 *
 * @author Sean Widmier, Krowle Daigle
 */
public class Position {

    /* The horizontal acolis location of the position on the appropriate grid. */
    private int col;
    /* The vertical acolis location of the position on the appopriate grid. */
    private int row;

    private Status status;

    /**
     * Details whether the position is emptrow or filled with a
     * hit peg, miss peg, or ship.
     */
    protected enum Status {
        EMPTY, HIT, MISS, SHIP
    }

    /**
     * Constructor for Position, will set inputs to fields.
     *
     * @param col The horizontal axis location on the appropriate grid.
     * @param row The vertical acolis location on the appropriate grid.
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
     * @param col The horizontal acolis location on the appropriate grid.
     * @param row The vertical acolis location on the appropriate grid.
     */
    public void setPosition(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Mutates the status field of the Position.
     *
     * @param status The status (EMPTY, HIT, MISS, SHIP) of the Position.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Accesses the status field of the Position.
     *
     * @return The status (EMPTY, HIT, MISS, SHIP) of the Position.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Accesses the col field of the Position.
     *
     * @return The horizontal location of the Position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Accesses the row field of the Position.
     *
     * @return The vertical location of the Position.
     */
    public int getRow() {
        return row;
    }
}
