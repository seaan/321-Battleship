/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 * TO DO: Test ship placements. Integrate fleet.
 * @author Kyle
 */
public class OceanGrid {
    
    private static OceanGrid instance = null;
    private static Fleet fleet;
    private static int shipsSunk;
    private static int[][] grid;                //peg enum wouldn't work. 
                                                //use 0 for empty, 1 for hit,
                                                //2 for miss, 3 for ship.
    
    
    
    private OceanGrid() {
        grid = new int[10][10];
        fleet = new Fleet();
        shipsSunk = 0;
        
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                grid[i][j] = 0;                 //initialize grid to "empty"
            }
        }  
        
    }

    public static Fleet getFleet() {
        return fleet;
    }
    
    protected static OceanGrid getInstance() {
        if(instance == null) {
            instance = new OceanGrid();
        }
        return instance;
    }
    
    protected void setPeg(Position position) {
        if(fleet.checkShipLocation(position))
            grid[position.getX()][position.getY()] = 1;
        else 
            grid[position.getX()][position.getY()] = 2;
    }
    
    protected void setShip(int x, int y) {
        grid[x][y] = 3;
    }
    
    protected int getGridAt(int x, int y) {
        return grid[x][y];
    }
    
    protected void printGrid() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                System.out.print(grid[j][i] + "  ");
            }
            System.out.println();
        }
        fleet.printFleet();
    } 
    
    protected void incrementSunkCount() {
        shipsSunk++;
    }
    
    protected int getFriendlyShipsSunk()
    {
        return shipsSunk;
    }
}
