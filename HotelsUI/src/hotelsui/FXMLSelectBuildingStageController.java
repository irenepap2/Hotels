/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelsui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLSelectBuildingStageController implements Initializable {

    private FXMLHotelsUIController parentController;

    public FXMLHotelsUIController getParentController() {
        return parentController;
    }

    public void setParentController(FXMLHotelsUIController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private TableView<BuildingStagesToBuy> BuyBuildingStageTable;

    @FXML
    private TableColumn<BuildingStagesToBuy, String> HotelID;

    @FXML
    private TableColumn<BuildingStagesToBuy, String> HotelName;

    @FXML
    private TableColumn<BuildingStagesToBuy, String> BuildingStage;
    
    @FXML
    private Button SelectHotel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HotelID.setCellValueFactory(new PropertyValueFactory<>("HotelID"));
        HotelName.setCellValueFactory(new PropertyValueFactory<>("HotelName"));
        BuildingStage.setCellValueFactory(new PropertyValueFactory<>("BuildingStageCost"));
    }

    public void setBuyBuildingStage() {
        BuyBuildingStageTable.getItems().setAll(parseBuildingStageList());
    }

    private List<BuildingStagesToBuy> parseBuildingStageList() {
        List<BuildingStagesToBuy> h1 = new ArrayList<>();
        int activeplayer = parentController.getHotellogic().getActiveplayer();
        for (OwnedHotel h : parentController.getHotellogic().getPlayers().get(activeplayer).getOwnedHotels().values()) {
            int hotelID = h.getHotelID();
            int buildingStageCost;
            if(h.getBuildingStages().isEmpty()){
                buildingStageCost = parentController.getHotellogic().getHotels().get(hotelID).getBuildingStages().get(0).getCost();
            }
            else{
                buildingStageCost = parentController.getHotellogic().getHotels().get(hotelID).getBuildingStages().get(h.getBuildingStages().size()).getCost();
            }
            h1.add(new BuildingStagesToBuy(hotelID, parentController.getHotellogic().hotels.get(hotelID).getName(),
                    buildingStageCost));
        }
        return h1;
    }
    
    @FXML
    void SelectHotel(ActionEvent event) {
        BuildingStagesToBuy hotel = BuyBuildingStageTable.getSelectionModel().getSelectedItem();
        String message = parentController.getHotellogic().RequestBuilding(hotel.getHotelID());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Request Building");
        alert.setHeaderText(message);
        alert.showAndWait();
        parentController.Refresh();
        
        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }
}
