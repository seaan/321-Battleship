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
    
    protected void checkShipLocation(int x, int y) {
        Carrier.checkPosition(x, y);
        Battleship.checkPosition(x, y);
        Cruiser.checkPosition(x, y);
        Submarine.checkPosition(x, y);
        Destroyer.checkPosition(x, y);
    }
}
