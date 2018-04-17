package battleship;

/**
 *
 * @author Kyle Daigle, Sean Widmier
 */
public class TargetGrid {

    private static TargetGrid instance = null;
    private static Position.Status[][] grid;

    private TargetGrid() {
        grid = new Position.Status[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = Position.Status.EMPTY;                 //initialize grid to "empty"
            }
        }
    }

    protected static TargetGrid getInstance() {
        if (instance == null) {
            instance = new TargetGrid();
        }
        return instance;
    }

    protected void setPeg(Position position) {
        grid[position.getX()][position.getY()] = position.getStatus();
    }

    protected Position.Status getGridAt(Position position) {
        return grid[position.getX()][position.getY()];
    }
}
