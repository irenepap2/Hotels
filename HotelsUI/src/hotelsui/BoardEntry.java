/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelsui;

/**
 *
 * @author Ειρηνη
 */
public class BoardEntry {
    private PositionType position;
    /**
     * A constructor of BoardEntry
     * @param x: the x coordinate of the board entry
     * @param y: the y coordinate of the board entry
     */
    public BoardEntry(int x, int y){
        this.position = new PositionType(x,y);
    }
    /**
     * A getter for the position field.
     * @return position of the board entry
     */
    public PositionType getPosition() {
        return position;
    }
    /**
     * A setter for the position field.
     * @param position assigned with the position of the board entry
     */
    public void setPosition(PositionType position) {
        this.position = position;
    }
    
    
}
