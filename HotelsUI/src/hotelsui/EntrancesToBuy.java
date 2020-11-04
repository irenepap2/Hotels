/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelsui;

public class EntrancesToBuy {
    private int HotelID;
    private String HotelName;
    private int EntranceCost;
    
    public EntrancesToBuy(int id, String name, int entranceCost){
        this.HotelID = id;
        this.HotelName = name;
        this.EntranceCost = entranceCost;
    }

    public int getHotelID() {
        return HotelID;
    }

    public void setHotelID(int HotelID) {
        this.HotelID = HotelID;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String HotelName) {
        this.HotelName = HotelName;
    }

    public int getEntranceCost() {
        return EntranceCost;
    }

    public void setEntranceCost(int EntranceCost) {
        this.EntranceCost = EntranceCost;
    }  
}
