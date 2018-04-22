package battleship;

/**
 * A single position, used on either TargetGrid or OceanGrid. Independent of
 * which Grid is used. Contains fields detailing the location and status of
 * the position.
 *
 * @author Sean Widmier, Kyle Daigle
 */
public class Position {

    /* The horizontal axis location of the position on the appropriate grid. */
    private int x;
    /* The vertical axis location of the position on the appopriate grid. */
    private int y;

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
     * @param x The horizontal axis location on the appropriate grid.
     * @param y The vertical axis location on the appropriate grid.
     * @param status The status (EMPTY, HIT, MISS, SHIP) of the Position.
     */
    protected Position(int x, int y, Status status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    /**
     * Mutates the location fields of the Position.
     *
     * @param x The horizontal axis location on the appropriate grid.
     * @param y The vertical axis location on the appropriate grid.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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
     * Accesses the x field of the Position.
     *
     * @return The horizontal location of the Position.
     */
    public int getX() {
        return x;
    }

    /**
     * Accesses the y field of the Position.
     *
     * @return The vertical location of the Position.
     */
    public int getY() {
        return y;
    }
}
