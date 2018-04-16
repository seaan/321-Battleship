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
public class Fleet {
    private final Ship Carrier;
    private final Ship Battleship;
    private final Ship Cruiser;
    private final Ship Submarine;
    private final Ship Destroyer;
    
    protected Fleet() {
        Carrier = new Ship("Carrier", 5);
        Battleship = new Ship("Battleship", 4);
        Cruiser = new Ship("Cruiser", 3);
        Submarine = new Ship("Submarine", 3);
        Destroyer = new Ship("Destroyer", 2);
    }

    public Ship getCarrier() {
        return Carrier;
    }

    public Ship getBattleship() {
        return Battleship;
    }

    public Ship getCruiser() {
        return Cruiser;
    }

    public Ship getSubmarine() {
        return Submarine;
    }

    public Ship getDestroyer() {
        return Destroyer;
    }
    
    protected boolean checkShipLocation(int x, int y) {
        if(Carrier.checkPosition(x, y))
            return true;
        else if(Battleship.checkPosition(x, y))
            return true;
        else if(Cruiser.checkPosition(x, y))
            return true;
        else if(Submarine.checkPosition(x, y))
            return true;
        else if(Destroyer.checkPosition(x, y))
            return true;
        else
            return false;
    }
    
    protected void placeShip(String type, int startX, int startY, int endX, int endPosition.getY()) {        //testing function
        switch (type) {
            case "Carrier":
                Carrier.setPosition(startX, startY);
                break;
            case "Battleship":
                Battleship.setPosition(startX, startY);
                break;
            case "Cruiser":
                Cruiser.setPosition(startX, startY);
                break;
            case "Submarine":
                Submarine.setPosition(startX, startY);
                break;
            case "Destroyer":
                Destroyer.setPosition(startX, startY);   
                break;
        }         
    }
    
    protected void printFleet() {
        Carrier.printShip();
        Battleship.printShip();
        Cruiser.printShip();
        Submarine.printShip();
        Destroyer.printShip();
    }
}
