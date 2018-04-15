/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Scanner;
/**
 *
 * @author Sean Widmier, Kyle Daigle, Kelly Manley, Robert Womack 
 */
public class BattleshipGame extends Menu {
    private String playerName;
    
    protected Fleet fleet;
    
    TargetGrid target;
    OceanGrid ocean;
    
    protected BattleshipGame(){
        
    }
    
    protected void initializeGame() {
        System.out.println("Please enter your name: ");
        Scanner input = new Scanner(System.in);
        
        playerName = input.nextLine();
        greetPlayer();
        
        ocean = OceanGrid.getInstance();
        target = TargetGrid.getInstance();
        
        initializeAllShips();
    }
    
    private void initializeAllShips() {
        //fleet = new Fleet();
    }
    
    protected void updateShip(Position position, String type){
        switch(type){
            case "Carrier":
                break;
            case "Battleship":
                break;
            case "Cruiser":
                break;
            case "Submarine":
                break;
            case "Destroyer":
                break;
        }
    }
    
    private void greetPlayer() {
        System.out.println("Greetings, Admiral " + playerName + "!");
    }
    
    protected void checkGameStatus() {
//        if(ocean.getFriendlyShipSunk() > 4)
//            gui.showDefeatMessage();
//        if(gui.getEnemyShipSunk() > 4)
//            gui.showVictoryMessage();
    }
    
    protected void updatePeg(int x, int y, int gridType, int pegType) {
        if(gridType == 0) {                 // Ocean
            ocean.setPeg(x, y);
        }
        else {                              // Target
            if(pegType == 1)
                target.setHit(x, y);
            else
                target.setMiss(x, y);
        }
    }
}
