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
public class BoardEntryHotel extends BoardEntry {
   private int hotelID;
   
   public BoardEntryHotel(int x, int y){
        super(x,y);
    }
   public int getID() {
        return hotelID;
    }

    public void setID(int ID) {
        this.hotelID = ID;
    }
}
