/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Kyle
 */
public class TargetGrid {
    
    private static TargetGrid instance = null;
    private static int[][] grid;                //peg enum wouldn't work. 
                                                //use 0 for empty, 1 for hit,
                                                //2 for miss, 3 for ship.
    
    
    private TargetGrid() {
        grid = new int[10][10];
        
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                grid[i][j] = 0;                 //initialize grid to "empty"
            }
        }           
    }
    
    protected static TargetGrid getInstance() {
        if(instance == null) {
            instance = new TargetGrid();
        }
        return instance;
    }
    
    protected void setPeg(int x, int y, int peg) {
        if (0 <= peg && peg <= 2) {
            grid[x][y] = peg;
        }
        else 
            System.out.println("ERROR: INVALID PEG TYPE AT (" + x + "," + y + 
                    ")");
    }
    
    protected int getGridAt(int x, int y) {
        return grid[x][y];
    }
    
    protected void printGrid() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
    }    
}
