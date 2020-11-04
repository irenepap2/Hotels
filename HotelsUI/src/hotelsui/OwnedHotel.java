/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelsui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ειρηνη
 */
public class OwnedHotel {
    private int hotelID;
    private List<BuildingStage> buildingStages = new ArrayList<>();
    private List<BoardEntryEntrance> EntrancePosition = new ArrayList<>();
    
    OwnedHotel(int hotelID){
        this.hotelID = hotelID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public BuildingStage getLastBuildingStage() {
        return buildingStages.get(buildingStages.size()-1);
    }

    public List<BuildingStage> getBuildingStages() {
        return buildingStages;
    }
    
    public void setBuildingStages(List<BuildingStage> buildingStages) {
        this.buildingStages = buildingStages;
    }

    public List<BoardEntryEntrance> getEntrancePosition() {
        return EntrancePosition;
    }

    public void setEntrancePosition(List<BoardEntryEntrance> EntrancePosition) {
        this.EntrancePosition = EntrancePosition;
    }
    
    public void addBuildingStage(BuildingStage buildingstage){
        this.buildingStages.add(buildingstage);
    }
    
    public void addEntrance(BoardEntryEntrance entrance){
        this.EntrancePosition.add(entrance);
    }
    
}
