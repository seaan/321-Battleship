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
    private int friendlyShipsSunkCount;
    private int enemyShipsSunkCount;
    
    protected Fleet fleet;
    
    TargetGrid target;
    OceanGrid ocean;
    
    protected BattleshipGame(){
        
    }
    
    protected void initializeGame(){
        System.out.println("Please enter your name: ");
        Scanner input = new Scanner(System.in);
        
        playerName = input.nextLine();
        greetPlayer();
        
        //OceanGrid ocean = OceanGrid.getInstance();
        //TargetGrid target = TargetGrid.getInstance();
        
        initializeAllShips();
    }
    
    private void initializeAllShips(){
        
    }
    
    private void placeAllShips(){
        fleet = new Fleet();
    }
    
    protected void endGame(){
        if(friendlyShipsSunkCount > enemyShipsSunkCount){
            //showVictoryMessage();       *****TODO*****
        }
        else{
            //showDefeatMessage();        *****TODO*****
        }
    }
    
    protected static void lockShips(){
        
    }
    
    private void greetPlayer(){
        System.out.println("Greetings, Admiral " + playerName + "!");
    }
    
    protected void checkGameStatus(){
        
    }
    
    protected void updatePeg(int x, int y, int gridType, int pegType){
        if(gridType == 0){
            ocean.setPeg(x, y, pegType);
        }
        else{
            target.setPeg(x, y, pegType);
        }
    }
    
    protected void addfriendlyShipSunk(){
        friendlyShipsSunkCount++;
    }
    
    protected void addenemyShipSunk(){
        enemyShipsSunkCount++;
    }
    
    protected int getFriendlyShipSunk(){
        return friendlyShipsSunkCount;
    }
    
    protected int getEnemyShipSunk(){
        return enemyShipsSunkCount;
    }
}
