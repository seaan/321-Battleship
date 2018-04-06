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
public class Ship {
    
    private static String shipType;    
    private static int shipLength;      
    private static int hitCount;
    private static Boolean isSunk;
    
    protected Ship(String type, int length) {
        shipType = type;
        shipLength = length;
        hitCount = 0;
        isSunk = false;
    }
    
    protected static String getType() {
        return shipType;
    }
    
    protected static int getLength() {
        return shipLength;
    }
    
    protected static Boolean getStatus() {
        return isSunk;
    }
    
    protected static void incrementHitCount() {
        hitCount ++;
        
        if(hitCount == shipLength)
            isSunk = true;               
    }
    
    
}
