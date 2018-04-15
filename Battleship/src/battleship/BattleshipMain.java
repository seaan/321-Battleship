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
        OceanGrid og = OceanGrid.getInstance();
        og.getFleet().placeShip(1, 1, 5, 1, 1);
        og.getFleet().placeShip(2, 7, 4, 4, 4);
        og.getFleet().placeShip(3, 2, 2, 4, 2);
        og.getFleet().placeShip(4, 9, 4, 9, 6);
        og.getFleet().placeShip(5, 8, 0, 9, 0);
        og.printGrid();
        System.out.println();
        og.setPeg(1, 1);
        og.setPeg(0, 0);
        og.setPeg(2, 1);
        og.setPeg(4, 9);
        og.setPeg(3, 1);
        
        og.setPeg(4, 4);
        og.setPeg(5, 4);
        og.setPeg(6, 4);
        og.setPeg(7, 4);
        og.printGrid();
        
    }
}
