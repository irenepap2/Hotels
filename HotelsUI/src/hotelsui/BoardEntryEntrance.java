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
public class BoardEntryEntrance extends BoardEntry {
    private Player owner;
    private HotelInfo hotel;
    
    BoardEntryEntrance(int x, int y){
        super(x,y);
    }
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public HotelInfo getHotel() {
        return hotel;
    }

    public void setHotel(HotelInfo hotel) {
        this.hotel = hotel;
    }
    
}
