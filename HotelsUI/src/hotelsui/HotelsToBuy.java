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
public class HotelsToBuy {
    private int HotelID;
    private String HotelName;
    private int PlotCost;
    private int MandatoryPlotCost;
    
    public HotelsToBuy(int id, String name, int plotCost, int mandatoryPlotCost){
        this.HotelID = id;
        this.PlotCost = plotCost;
        this.HotelName = name;
        this.MandatoryPlotCost = mandatoryPlotCost;
    }
    
    public int getMandatoryPlotCost() {
        return MandatoryPlotCost;
    }

    public void setMandatoryPlotCost(int MandatoryPlotCost) {
        this.MandatoryPlotCost = MandatoryPlotCost;
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

    public int getPlotCost() {
        return PlotCost;
    }

    public void setPlotCost(int PlotCost) {
        this.PlotCost = PlotCost;
    }  
}
