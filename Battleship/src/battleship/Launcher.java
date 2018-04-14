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
public class Launcher {
    protected Menu menu = new Menu();
    protected GUI gui = new GUI();
    
    protected void runSim(){
        gui.showMenu();
        
    }
}
