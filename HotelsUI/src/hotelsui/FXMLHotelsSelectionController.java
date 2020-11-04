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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLHotelsSelectionController implements Initializable {

    private FXMLHotelsUIController parentController;

    public FXMLHotelsUIController getParentController() {
        return parentController;
    }

    public void setParentController(FXMLHotelsUIController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private Button SelectHotel;

    @FXML
    private TableView<HotelsToBuy> HotelTable;

    @FXML
    private TableColumn<HotelsToBuy, String> HotelID;

    @FXML
    private TableColumn<HotelsToBuy, String> PlotCost;

    @FXML
    private TableColumn<HotelsToBuy, String> HotelName;

    @FXML
    private TableColumn<HotelsToBuy, String> MandatoryPlotCost;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HotelID.setCellValueFactory(new PropertyValueFactory<>("HotelID"));
        HotelName.setCellValueFactory(new PropertyValueFactory<>("HotelName"));
        PlotCost.setCellValueFactory(new PropertyValueFactory<>("PlotCost"));
        MandatoryPlotCost.setCellValueFactory(new PropertyValueFactory<>("MandatoryPlotCost"));
    }

    public void setHotelTable() {
        HotelTable.getItems().setAll(parseHotelList());
    }

    private List<HotelsToBuy> parseHotelList() {
        List<HotelsToBuy> h1 = new ArrayList<>();
        int activeplayer = parentController.getHotellogic().getActiveplayer();
        PositionType position1 = parentController.getHotellogic().getPlayers().get(activeplayer).getPosition();
        for (HotelInfo h : parentController.getHotellogic().getAvailablehotelsmap().values()) {
            int hotelID = h.getID();
            if ((parentController.getHotellogic().isHotelAdjacent(hotelID, position1))
                    && (parentController.getHotellogic().isHotelFree(hotelID)) 
                    && parentController.getHotellogic().getPlayers().get(activeplayer) 
                    != parentController.getHotellogic().getHotels().get(hotelID).getOwner()){
                h1.add(new HotelsToBuy(hotelID, h.getName(), h.getPlotCost(), h.getMandatoryPlotCost()));
            }
        }
        return h1;
    }

    @FXML
    void GetHotel(ActionEvent event) {
        HotelsToBuy hotel = HotelTable.getSelectionModel().getSelectedItem();
        parentController.getHotellogic().BuyHotel(hotel.getHotelID());
        parentController.Refresh();
        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }
}
