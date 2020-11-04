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
public class BoardEntryPlot extends BoardEntry{
    private HotelInfo hotel;
    private Player owner;
    public BoardEntryPlot(int x, int y){
        super(x,y);
    }

    public HotelInfo getHotel() {
        return hotel;
    }

    public void setHotel(HotelInfo hotel) {
        this.hotel = hotel;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }    
}
