/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Kyle Daigle, Sean Widmier
 */
public class OceanGrid {

    private static OceanGrid instance = null;
    private static Fleet fleet;
    private static int shipsSunk;
    private static Position.Status[][] grid;

    private OceanGrid() {
        grid = new Position.Status[10][10];
        fleet = new Fleet();
        shipsSunk = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = Position.Status.EMPTY;                 //initialize grid to "empty"
            }
        }

    }

    public static Fleet getFleet() {
        return fleet;
    }

    protected static OceanGrid getInstance() {
        if (instance == null) {
            instance = new OceanGrid();
        }
        return instance;
    }

    protected void setPeg(Position position) {
        if (fleet.checkShipLocation(position)) {
            fleet.hitLocation(position);
            grid[position.getX()][position.getY()] = Position.Status.HIT;
        }

        grid[position.getX()][position.getY()] = position.getStatus();
    }

    protected Position.Status getGridAt(Position position) {
        return grid[position.getX()][position.getY()];
    }

    protected void incrementSunkCount() {
        shipsSunk++;
    }

    protected int getFriendlyShipsSunk() {
        return shipsSunk;
    }
}
