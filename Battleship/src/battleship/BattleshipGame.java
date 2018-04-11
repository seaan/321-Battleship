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
    
    protected BattleshipGame(){
        
    }
    
    protected void initializeGame(){
        System.out.println("Please enter your name: ");
        Scanner input = new Scanner(System.in);
        
        playerName = input.nextLine();
        greetPlayer();
        
        //Grid target = new Grid()    *****TODO*****
        //Grid ocean = new Grid()     *****TODO*****
        
        initializeAllShips();
    }
    
    private void initializeAllShips(){
        Ship Carrier = new Ship("Carrier", 5);  // **** NEED TO REFERENCE PHYSICAL GAME RULES
        Ship Battleship = new Ship("Battleship", 4);
        Ship Cruiser = new Ship("Cruiser", 3);
        Ship Submarine = new Ship("Submarine", 3);
        Ship Destroyer = new Ship("Destroyer", 2);
    }
    
    private void placeAllShips(){
        
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