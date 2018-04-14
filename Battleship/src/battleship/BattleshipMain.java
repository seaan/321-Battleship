/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Sean Widmier, Kyle Daigle, Kelly Manley, Robert Womack 
 */
public class BattleshipMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OceanGrid oGrid = OceanGrid.getInstance();
        oGrid.setPeg(1, 9, 1);
        oGrid.setPeg(1, 8, 1);
        oGrid.setPeg(1, 7, 2);
        oGrid.setPeg(1, 6, 1);
        oGrid.setPeg(2, 9, 3);
        oGrid.setPeg(3, 9, 3);
        oGrid.setPeg(1, 5, 3);
        oGrid.setPeg(1, 4, 3);
        oGrid.setPeg(5, 5, 5);
        
        
        OceanGrid testGrid = OceanGrid.getInstance();
        System.out.println("Testing singleton grid: ");
        testGrid.printGrid();
        
    }
}
