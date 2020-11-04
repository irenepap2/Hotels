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
public class HotelInfo {
   private int ID;
   private String name;
   private int plotCost;
   private int mandatoryPlotCost;
   private int entranceCost;
   private Player owner;
   private List<BuildingStage> buildingStages = new ArrayList<>();
   
   public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlotCost() {
        return plotCost;
    }

    public void setPlotCost(int plotCost) {
        this.plotCost = plotCost;
    }

    public int getMandatoryPlotCost() {
        return mandatoryPlotCost;
    }

    public void setMandatoryPlotCost(int mandatoryPlotCost) {
        this.mandatoryPlotCost = mandatoryPlotCost;
    }

    public int getEntranceCost() {
        return entranceCost;
    }

    public void setEntranceCost(int entranceCost) {
        this.entranceCost = entranceCost;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    public List<BuildingStage> getBuildingStages() {
        return buildingStages;
    }

    public void setBuildingStages(List<BuildingStage> buildingStages) {
        this.buildingStages = buildingStages;
    }
    
}
