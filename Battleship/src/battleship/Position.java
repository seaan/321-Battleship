/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Sean
 */
public class Position {
    private int x;
    private int y;

    protected enum Status{
        EMPTY, HIT, MISS, SHIP
    }
    
    private Status status; 
    
    protected Position(int x, int y, Status status){
        this.x = x;
        this.y = y;
        this.status = status;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
