/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelsui;
import java.util.*;
/**
 *
 * @author Ειρηνη
 */
public class Player {
    private String name;
    private String colour;
    private int balance;
    private int maxBalance;
    private PositionType position;
    private int pathposition;
    private Map<Integer,OwnedHotel> ownedHotels = new HashMap<>();
    
    /**
     * A getter for the name field.
     * @return name of the player
     */
    public String getName() {
        return name;
    }
    /**
     * A setter for the name field.
     * @param name assigned as the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * A getter for the colour field.
     * @return colour of the player
     */
    public String getColour() {
        return colour;
    }
    /**
     * A setter for the colour field.
     * @param colour assigned as the colour of the player
     */
    public void setColour(String colour) {
        this.colour = colour;
    }
    /**
     * A getter for the balance field.
     * @return balance of the player
     */
    public int getBalance() {
        return balance;
    }
    /**
     * A setter for the balance field.
     * @param balance assigned as the balance of the player
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }
    /**
     * A getter for the maxBalance field.
     * @return maxBalance of the player
     */
    public int getMaxBalance() {
        return maxBalance;
    }
    /**
     * A setter for the maxBalance field.
     * @param maxBalance assigned as the maxBalance of the player
     */
    public void setMaxBalance(int maxBalance) {
        this.maxBalance = maxBalance;
    }
    /**
     * A getter for the position field.
     * @return position of the player (x,y coordinates)
     */
    public PositionType getPosition() {
        return position;
    }
    /**
     * A setter for the position field.
     * @param position assigned as the position of the player(x,y coordinates)
     */
    public void setPosition(PositionType position) {
        this.position = position;
    }
    /**
     * A getter for the pathposition field.
     * @return pathposition of the player where path is a list of the squares 
     * the player can go to
     */
    public int getPathposition() {
        return pathposition;
    }
    /**
     * A setter for the pathposition field.
     * @param pathposition assigned as the pathposition of the player where path
     * is a list of the squares the player can go to
     */
    public void setPathposition(int pathposition) {
        this.pathposition = pathposition;
    }
    /**
     * Method to set the player's balance
     * @param num (neg or pos) increases or decreases player's balance
     */ 
    public void EditBalance (int num){
        this.balance = balance + num;
    }
    /*
     * Method to set the player's max balance
     */    
    public void EditMaxBalance (){
        if (this.maxBalance < this.balance){
            this.maxBalance = this.balance;
        }
    }
    /**
     * Method to add a hotel to the player's map of owned hotels
     * @param hotel is the hotel to be added
     */
    public void addOwnedHotel(OwnedHotel hotel){
        this.ownedHotels.put(hotel.getHotelID(),hotel);
    }
    /**
     * Method to remove a hotel from the player's map of owned hotels
     * @param hotel is the hotel to be removed
     */
    public void removeOwnedHotel(OwnedHotel hotel){
        this.ownedHotels.remove(hotel.getHotelID());
    }
    /**
     * Method to add a building stage to the player's hotel
     * @param hotelID is the ID of the hotel where the building stage is added
     * @param buildingStage is the building stage to be added (cost, daily rent, sequence)
     * it calls the method addBuildingStage of the OwnedHotel class
     */    
    public void addBuildingStage(BuildingStage buildingStage, int hotelID){
        this.ownedHotels.get(hotelID).addBuildingStage(buildingStage);
    }
    /**
     * Method to add an entrance to the player's hotel
     * @param hotelID is the ID of the hotel where the entrance is added 
     * @param entrance is the entrance to be added
     * it calls the method addEntrance of the OwnedHotel class
     */
    public void addEntrance (BoardEntryEntrance entrance, int hotelID){
        this.getOwnedHotels().get(hotelID).addEntrance(entrance);
    }
    /**
     * A getter for the ownedHotels field.
     * @return map with keys the hotelIDs and values the OwnedHotel by the player
     */
    public Map<Integer, OwnedHotel> getOwnedHotels() {
        return ownedHotels;
    }
    /**
     * A setter for the owendHotels field.
     * @param ownedHotels assigned as a map(key: hotelID, values: ownedHotels)
     * of the player's hotels
     */
    public void setOwnedHotels(Map<Integer, OwnedHotel> ownedHotels) {
        this.ownedHotels = ownedHotels;
    }
}
