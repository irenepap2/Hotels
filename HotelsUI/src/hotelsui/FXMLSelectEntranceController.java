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

public class FXMLSelectEntranceController implements Initializable {

    private FXMLHotelsUIController parentController;

    public FXMLHotelsUIController getParentController() {
        return parentController;
    }

    public void setParentController(FXMLHotelsUIController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private TableView<EntrancesToBuy> BuyEntranceTable;

    @FXML
    private TableColumn<EntrancesToBuy, String> HotelID;

    @FXML
    private TableColumn<EntrancesToBuy, String> EntranceCost;

    @FXML
    private TableColumn<EntrancesToBuy, String> HotelName;

    @FXML
    private Button SelectedEntrance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HotelID.setCellValueFactory(new PropertyValueFactory<>("HotelID"));
        HotelName.setCellValueFactory(new PropertyValueFactory<>("HotelName"));
        EntranceCost.setCellValueFactory(new PropertyValueFactory<>("EntranceCost"));
    }

    public void setBuyEntrance() {
        BuyEntranceTable.getItems().setAll(parseEntranceList());
    }

    private List<EntrancesToBuy> parseEntranceList() {
        List<EntrancesToBuy> h1 = new ArrayList<>();
        int activeplayer = parentController.getHotellogic().getActiveplayer();
        for (OwnedHotel h : parentController.getHotellogic().getPlayers().get(activeplayer).getOwnedHotels().values()) {
            int hotelID = h.getHotelID();
            h1.add(new EntrancesToBuy(hotelID, parentController.getHotellogic().hotels.get(hotelID).getName(),
                     parentController.getHotellogic().hotels.get(hotelID).getEntranceCost()));
        }
        return h1;
    }
    
    @FXML
    void SelectEntrance(ActionEvent event) {
        EntrancesToBuy hotel = BuyEntranceTable.getSelectionModel().getSelectedItem();
        String message = parentController.getHotellogic().BuyEntrance(hotel.getHotelID());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Entrance");
        alert.setHeaderText(message);
        alert.showAndWait();
        parentController.Refresh();
        
        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }
}
