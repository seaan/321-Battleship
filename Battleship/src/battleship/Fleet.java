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
    Ship Carrier = new Ship("Carrier", 5);
    Ship Battleship = new Ship("Battleship", 4);
    Ship Cruiser = new Ship("Cruiser", 3);
    Ship Submarine = new Ship("Submarine", 3);
    Ship Destroyer = new Ship("Destroyer", 2);
}
