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
    private static boolean isSunk;

    
    private Position startPosition;
    private Position endPosition;
    
    Orientation orientation;
    private enum Orientation{
        HORIZONTAL, VERTICAL;
    }
    
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
    
    protected void setPosition(int startX, int startY, int endX, int endY){
        startPosition = new Position(startX, startY, Position.Status.SHIP);
        endPosition = new Position(endX, endY, Position.Status.SHIP);
        
        if (this.startPosition.getX() == this.startPosition.getX())
           orientation = Orientation.VERTICAL; 
        else if (this.startPosition.getY() == this.startPosition.getY())
           orientation = Orientation.HORIZONTAL;
    }
    
    protected boolean checkPosition(int x, int y){
        if(x == this.startPosition.getX() && y == this.startPosition.getY())
            return true;
        else if(x == this.endPosition.getX() && y == this.endPosition.getY())
            return true;
        else{
            switch(orientation){
                case VERTICAL:
                    if(x == this.startPosition.getX()){
                        int topY = 0;
                        int botY = 0;
                        
                        if(this.startPosition.getY() > this.endPosition.getY()){
                            topY = this.startPosition.getY();
                            botY = this.endPosition.getY();
                        }
                        else{
                            topY = this.endPosition.getY();
                            botY = this.endPosition.getY();
                        }
                        
                        for(int i  = topY; i > botY; i--){
                            if(y == i)
                                return true;
                        }
                        return false;
                    }
                    
                    return false;
                    
                case HORIZONTAL:
                    if(x == this.startPosition.getX()){
                        int topX = 0;
                        int botX = 0;
                        
                        if(this.startPosition.getY() > this.endPosition.getX()){
                            topX = this.startPosition.getX();
                            botX = this.endPosition.getX();
                        }
                        else{
                            topX = this.endPosition.getX();
                            botX = this.endPosition.getX();
                        }
                        
                        for(int i  = topX; i > botX; i--){
                            if(x == i)
                                return true;
                        }
                        return false;
                    }
                    return false;
                    
            }
            return false;
        }
    }
}