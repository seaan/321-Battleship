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
        BattleshipGame bg = new BattleshipGame();
        OceanGrid og = OceanGrid.getInstance();
        og.getFleet().placeShip("Carrier", 1, 1, 1, 5);
        og.getFleet().placeShip("Battleship", 4, 4, 7, 4);
        og.getFleet().placeShip("Cruiser", 2, 2, 4, 2);
        og.getFleet().placeShip("Submarine", 9, 4, 9, 6);
        og.getFleet().placeShip("Destroyer", 6, 2, 7, 2);
        og.printGrid();
        System.out.println();
        
        //sink carrier
        og.setPeg(1, 1);
        og.setPeg(1, 2);
        og.setPeg(1, 3);
        og.setPeg(1, 4);
        og.setPeg(1, 5);
        
        //sink battleship
        og.setPeg(4, 4);
        og.setPeg(5, 4);
        og.setPeg(6, 4);
        og.setPeg(7, 4);
        
        //sink cruise
        og.setPeg(2, 2);
        og.setPeg(3, 2);
        og.setPeg(4, 2);
        
        //sink sub
        og.setPeg(9, 4);
        og.setPeg(9, 5);
        og.setPeg(9, 6);
        
        //sink destroyer
        og.setPeg(6, 2);
        og.setPeg(7, 2);
        
        og.printGrid();
        
        bg.initializeGame();
        bg.checkGameStatus();
        
    }
}
