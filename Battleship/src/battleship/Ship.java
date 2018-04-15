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
    
    private String shipType;    
    private int shipLength;      
    private int hitCount;
    private boolean isSunk;
    

    
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
    
    protected String getType() {
        return shipType;
    }
    
    protected int getLength() {
        return shipLength;
    }
    
    protected Boolean getStatus() {
        return isSunk;
    }
    
    protected void incrementHitCount() {
        hitCount ++;
        
        if(hitCount == shipLength) {
            isSunk = true;
            
            OceanGrid og = OceanGrid.getInstance();
            og.incrementSunkCount();
        }
    }
    
    protected void setPosition(int startX, int startY, int endX, int endY){
        OceanGrid og = OceanGrid.getInstance();
        
        startPosition = new Position(startX, startY, Position.Status.SHIP);
        endPosition = new Position(endX, endY, Position.Status.SHIP);
        
        if (this.startPosition.getX() == this.endPosition.getX())
           orientation = Orientation.VERTICAL; 
        else if (this.startPosition.getY() == this.endPosition.getY())
           orientation = Orientation.HORIZONTAL;
        
        if(startX == endX)
        {
            if(startY < endY)
            {
                for(int i = startY; i <= endY; i++)
                {
                    og.setShip(startX, i);
                }
            }
            else {
                for(int i = endY; i <= startY; i++)
                {
                    og.setShip(startX, i);
                }
            }
        }
        else{
            if(startX < endX)
            {
                for(int i = startX; i <= endX; i++)
                {
                    og.setShip(i, startY);
                }
            }
            else {
                for(int i = endX; i <= startX; i++)
                {
                    og.setShip(i, startY);
                }
            }
        }
    }
    
    protected boolean checkPosition(int x, int y){
        OceanGrid og = OceanGrid.getInstance();
        /*switch(orientation){
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

        }*/
        if((startPosition.getX() <= x && x <=endPosition.getX()) &&
                ((startPosition.getY() <= y && y <= endPosition.getY())))
        {
            incrementHitCount();
            return true;
        }
        else if((startPosition.getX() >= x && x >=endPosition.getX()) &&
                ((startPosition.getY() >= y && y >= endPosition.getY())))
        {
            incrementHitCount();
            return true;
        }
        else 
            return false;
    }
    
    protected void printShip() {
        System.out.println("Type: " + this.shipType);
        System.out.println("Length: " + this.shipLength);
        System.out.println("Hit count: " + this.hitCount);
        System.out.println("isSunk: " + this.isSunk);
    }
}