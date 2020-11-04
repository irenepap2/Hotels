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
public class BuildingStagesToBuy {
    
    private int HotelID;
    private String HotelName;
    private int BuildingStageCost;
    
    public BuildingStagesToBuy(int id, String name, int cost){
        this.HotelID = id;
        this.HotelName = name;
        this.BuildingStageCost = cost;
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

    public int getBuildingStageCost() {
        return BuildingStageCost;
    }

    public void setBuildingStageCost(int BuildingStageCost) {
        this.BuildingStageCost = BuildingStageCost;
    }   
}
