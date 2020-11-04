package hotelsui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.text.DecimalFormat;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Ειρηνη
 */
public class FXMLHotelsUIController implements Initializable {

    private HotelLogic hotellogic;
    private StackPane[][] stackPanes;
    private FadeTransition fadeTransition1;
    private FadeTransition fadeTransition2;
    private FadeTransition fadeTransition3;
    private int seconds = 0, minutes = 0;
    private Timeline time;
    private static Stage myStage;

    public static void setStage(Stage mystage) {
        myStage = mystage;
    }

    public HotelLogic getHotellogic() {
        return hotellogic;
    }

    @FXML
    private Button RollDice;

    @FXML
    private Button BuyHotel;

    @FXML
    private Button PayEntrance;

    @FXML
    private Button NextPlayer;

    @FXML
    private Button BuyEntrance;

    @FXML
    private Button Request1000fromBank;

    @FXML
    private Button RequestBuilding;

    @FXML
    private MenuItem Start;

    @FXML
    private MenuItem hotels;

    @FXML
    private Label Balance1;

    @FXML
    private Label Balance2;

    @FXML
    private Label Balance3;

    @FXML
    private GridPane Board;

    @FXML
    private Label Player1;

    @FXML
    private Label Player2;

    @FXML
    private Label Player3;

    @FXML
    private MenuItem Exit;

    @FXML
    private MenuItem Cards;

    @FXML
    private MenuItem Entrances;

    @FXML
    private Label AvailableHotels;

    @FXML
    private Label timelabel;

    @FXML
    private MenuItem Profits;

    public FXMLHotelsUIController() {
        this.stackPanes = null;
    }

    private StackPane findInstance(BoardEntry[][] board, int i, int j) {
        Label l;
        StackPane s;
        if (board[i][j] instanceof BoardEntryStart) {
            s = new StackPane();
            l = new Label("Start");
            s.getChildren().add(l);
            s.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            return s;
        }
        if (board[i][j] instanceof BoardEntryPlot) {
            s = new StackPane();
            l = new Label("Plot");
            s.getChildren().add(l);
            s.setStyle("-fx-background-color: pink; -fx-text-fill: black;");
            return s;
        }
        if (board[i][j] instanceof BoardEntryHotel) {
            s = new StackPane();
            int hotelID = ((BoardEntryHotel) board[i][j]).getID();
            l = new Label(hotellogic.getHotels().get(hotelID).getName()); //+ "Hotel " + hotelID);
            if (hotellogic.getHotels().get(hotelID).getOwner() != null) {
                if (!(hotellogic.getHotels().get(hotelID).getOwner().getOwnedHotels()
                        .get(hotelID).getBuildingStages().isEmpty())) {
                    if (hotellogic.getHotels().get(hotelID).getOwner().getBalance() < 0) {
                        l.setText(hotellogic.getHotels().get(hotelID).getName()/*"Hotel " + hotelID*/ 
                                + " \n" + "BANK");
                        s.setStyle("-fx-background-color: orange;");
                    } else {
                        int seq = hotellogic.getHotels().get(hotelID).getOwner()
                                .getOwnedHotels().get(hotelID).getLastBuildingStage().getSequence() + 1;
                        l.setText(hotellogic.getHotels().get(hotelID).getName() /*"Hotel " + hotelID*/
                                + " \n" + seq);
                        String c = hotellogic.getHotels().get(hotelID).getOwner().getColour();
                        s.setStyle("-fx-background-color: " + c + "; -fx-text-fill: black;");
                    }
                } else {
                    if (hotellogic.getHotels().get(hotelID).getOwner().getBalance() < 0) {
                        s.setStyle("-fx-background-color: cyan; -fx-text-fill: black;");
                    } else {
                        String c = hotellogic.getHotels().get(hotelID).getOwner().getColour();
                        s.setStyle("-fx-background-color: " + c + "; -fx-text-fill: black;");
                    }
                }
            } else {
                s.setStyle("-fx-background-color: cyan; -fx-text-fill: black;");
            }
            s.getChildren().add(l);

            return s;
        }
        if (board[i][j] instanceof BoardEntryBank) {
            s = new StackPane();
            l = new Label("Bank");
            s.getChildren().add(l);
            s.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
            return s;
        }
        if (board[i][j] instanceof BoardEntryCourt) {
            s = new StackPane();
            l = new Label("Court");
            s.getChildren().add(l);
            s.setStyle("-fx-background-color: purple; -fx-text-fill: black;");
            return s;
        }
        if (board[i][j] instanceof BoardEntryEntrance) {
            s = new StackPane();
            l = new Label("Entrance");
            s.setStyle("-fx-background-color: grey; -fx-text-fill: black;");
            Player p = ((BoardEntryEntrance) board[i][j]).getOwner();
            if (((BoardEntryEntrance) board[i][j]).getOwner() != null) {
                if (p.getBalance() < 0) {
                    l.setText("Entrance" + ((BoardEntryEntrance) board[i][j]).getHotel().getID() + '\n' + "BANK");
                    l.setStyle("-fx-text-fill: orange;");
                    s.setStyle("-fx-background-color: grey;");
                } else {
                    l.setText("Entrance" + "\n" + ((BoardEntryEntrance) board[i][j]).getHotel().getID());
                    l.setStyle("-fx-text-fill: " + p.getColour() + ";");
                    s.setStyle("-fx-background-color: grey;");
                }
            }
            s.getChildren().add(l);
            return s;
        }
        if (board[i][j] instanceof BoardEntryFree) {
            s = new StackPane();
            l = new Label("Free");
            s.getChildren().add(l);
            s.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            return s;
        }
        return new StackPane();
    }

    public void Refresh() {
        fadeTransition1.stop();
        fadeTransition2.stop();
        fadeTransition3.stop();

        int activeplayer = hotellogic.getActiveplayer();
        FadeTransition activeTransition = activeplayer == 0 ? fadeTransition1 : activeplayer == 1 ? fadeTransition2 : fadeTransition3;
        activeTransition.play();
        
        if (hotellogic.getPlayers().get(0).getBalance() < 0) {
            fadeTransition1.stop();
            Balance1.setText("Bunkrupt");
        } else {
            Balance1.setText(Integer.toString(hotellogic.getPlayers().get(0).getBalance()));
        }
        if (hotellogic.getPlayers().get(1).getBalance() < 0) {
            fadeTransition2.stop();
            Balance2.setText("Bunkrupt");
        } else {
            Balance2.setText(Integer.toString(hotellogic.getPlayers().get(1).getBalance()));
        }
        if (hotellogic.getPlayers().get(2).getBalance() < 0) {
            fadeTransition3.stop();
            Balance3.setText("Bunkrupt");
        } else {
            Balance3.setText(Integer.toString(hotellogic.getPlayers().get(2).getBalance()));
        }

        AvailableHotels.setText(Integer.toString(hotellogic.getAvailablehotelsmap().size()));

        PositionType position1 = hotellogic.getPlayers().get(0).getPosition();
        PositionType position2 = hotellogic.getPlayers().get(1).getPosition();
        PositionType position3 = hotellogic.getPlayers().get(2).getPosition();

        String colour1 = hotellogic.getPlayers().get(0).getColour();
        String colour2 = hotellogic.getPlayers().get(1).getColour();
        String colour3 = hotellogic.getPlayers().get(2).getColour();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 12; j++) {
                if (stackPanes[i][j] != null) {
                    Board.getChildren().remove(stackPanes[i][j]);
                }
                StackPane s = findInstance(hotellogic.getBoardEntries(), j, i);
                if (hotellogic.getPlayers().get(2).getBalance() >= 0) {
                    if (position3.getX() == j && position3.getY() == i) {
                        Circle circle = new Circle();
                        circle.setRadius(15);
                        switch (colour3) {
                            case "Red":
                                circle.setFill(Color.RED);
                                break;
                            case "Green":
                                circle.setFill(Color.GREEN);
                                break;
                            default:
                                circle.setFill(Color.BLUE);
                                break;
                        }
                        s.getChildren().add(circle);
                    }
                }
                if (hotellogic.getPlayers().get(1).getBalance() >= 0) {
                    if (position2.getX() == j && position2.getY() == i) {
                        Circle circle = new Circle();
                        circle.setRadius(15);
                        switch (colour2) {
                            case "Red":
                                circle.setFill(Color.RED);
                                break;
                            case "Green":
                                circle.setFill(Color.GREEN);
                                break;
                            default:
                                circle.setFill(Color.BLUE);
                                break;
                        }
                        s.getChildren().add(circle);
                    }
                }
                if (hotellogic.getPlayers().get(0).getBalance() >= 0) {
                    if (position1.getX() == j && position1.getY() == i) {
                        Circle circle = new Circle();
                        circle.setRadius(15);
                        switch (colour1) {
                            case "Red":
                                circle.setFill(Color.RED);
                                break;
                            case "Green":
                                circle.setFill(Color.GREEN);
                                break;
                            default:
                                circle.setFill(Color.BLUE);
                                break;
                        }
                        s.getChildren().add(circle);
                    }
                }
                stackPanes[i][j] = s;
                Board.add(s, i, j);
            }
        }

        if (hotellogic.isIsRollDiceAndMoveEnabled()) {
            RollDice.setDisable(false);
        } else {
            RollDice.setDisable(true);
        }
        if (hotellogic.isIsNextPlayerEnabled()) {
            NextPlayer.setDisable(false);
        } else {
            NextPlayer.setDisable(true);
        }
        if (hotellogic.isIsBuyEntranceEnabled()) {
            BuyEntrance.setDisable(false);
        } else {
            BuyEntrance.setDisable(true);
        }
        if (hotellogic.isIsBuyHotelEnabled()) {
            BuyHotel.setDisable(false);
        } else {
            BuyHotel.setDisable(true);
        }
        if (hotellogic.isIsRequest1000fromBankEnabled()) {
            Request1000fromBank.setDisable(false);
        } else {
            Request1000fromBank.setDisable(true);
        }
        if (hotellogic.isIsRequestBuildingEnabled()) {
            RequestBuilding.setDisable(false);
        } else {
            RequestBuilding.setDisable(true);
        }
        if (hotellogic.isIsPayEntranceEnabled()) {
            PayEntrance.setDisable(false);
        } else {
            PayEntrance.setDisable(true);
        }

    }

    @FXML
    void StartClick(ActionEvent event) throws IOException {
        seconds = 0;
        minutes = 0;
        time = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            if (seconds == 60) {
                minutes++;
                seconds = 0;
            }
            timelabel.setText(new DecimalFormat("00").format(minutes) + ":" + (new DecimalFormat("00").format(seconds)));
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
        hotellogic.Start();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Welcome!");
        alert.setHeaderText("Let's Play Hotels");
        alert.setContentText("First Player: " + hotellogic.getPlayers().get(0).getColour()
                + "\nSecond Player: " + hotellogic.getPlayers().get(1).getColour()
                + "\nThird Player: " + hotellogic.getPlayers().get(2).getColour());

        alert.showAndWait();
        String colour = hotellogic.getPlayers().get(0).getColour();
        Player1.setStyle("; -fx-text-fill: " + colour + ";");
        colour = hotellogic.getPlayers().get(1).getColour();
        Player2.setStyle("; -fx-text-fill: " + colour + ";");
        colour = hotellogic.getPlayers().get(2).getColour();
        Player3.setStyle("; -fx-text-fill: " + colour + ";");
        Refresh();
    }

    @FXML
    void StopClick(ActionEvent event) {
        time.stop();
        fadeTransition1.stop();
        fadeTransition2.stop();
        fadeTransition3.stop();
        RollDice.setDisable(true);
        RequestBuilding.setDisable(true);
        BuyHotel.setDisable(true);
        BuyEntrance.setDisable(true);
        Request1000fromBank.setDisable(true);
        PayEntrance.setDisable(true);
        NextPlayer.setDisable(true);
    }

    private String hotelInfo(HotelInfo h) {
        String s = "";
        s = "Name: " + h.getName() + '\n' + "Hotel ID: " + h.getID() + '\n' + "Plot Cost: " + h.getPlotCost() + '\n' + "Mandatory Plot Cost: " + h.getMandatoryPlotCost() + '\n'
                + "Entrance Cost: " + h.getEntranceCost() + '\n' + "Basic Build Cost: " + h.getBuildingStages().get(0).getCost() + '\n' + "Basic Build Rent: " + h.getBuildingStages().get(0).getDailyRent() + '\n';
        for (int i = 1; i < h.getBuildingStages().size(); i++) {
            s = s + "Building Expansion " + (i) + " Cost: " + h.getBuildingStages().get(i).getCost() + '\n'
                    + "Building Expansion " + (i) + " Rent: " + h.getBuildingStages().get(i).getDailyRent() + '\n';
        }
        return s;
    }

    @FXML
    void CardsClick(ActionEvent event) throws IOException {
        Tab[] tabs = new Tab[hotellogic.hotels.size()];
        TabPane tabPane = new TabPane();
        String s = "";
        int i = 0;
        for (HotelInfo h : hotellogic.getHotels().values()) {
            tabs[i] = new Tab();
            tabs[i].setText("Hotel " + h.getName());
            s = hotelInfo(h);
            TextArea txt = new TextArea(s);
            txt.setStyle("-fx-font-alignment: center");
            txt.setDisable(true);
            tabs[i].setContent(txt);
            tabPane.getTabs().add(tabs[i]);
        }
        Popup popup = new Popup();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle(" -fx-background-color: #cfcfcf;");
        Button btn = new Button("Close");
        tabPane.setMinWidth(hotellogic.getHotels().size() * 85);
        tabPane.setMinHeight(400);
        btn.setLayoutX(tabPane.getMinWidth() / 2);
        btn.setLayoutY(350);
        btn.setMinWidth(80);
        btn.setMinHeight(40);
        btn.setAlignment(Pos.CENTER);
        popup.getContent().add(tabPane);
        popup.getContent().add(btn);
        popup.setAutoHide(true);
        popup.show(myStage);
        EventHandler<ActionEvent> closeevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                popup.hide();
            }
        };
        btn.setOnAction(closeevent);
    }

    private String hotels(HotelInfo h) {
        String s = "";
        s = "Name: " + h.getName() + '\n';
        if (h.getOwner() == null) {
            s = s + "Doesn't Have an Owner" + '\n';
        } else if (h.getOwner().getBalance() < 0) {
            s = s + "Owner: Bank" + '\n';
        } else {
            s = s + "Owner: " + h.getOwner().getColour() + '\n';
            s = s + "Current Building Stage: " + h.getOwner().getOwnedHotels()
                    .get(h.getID()).getBuildingStages().size() + '\n';
        }
        s = s + "Max Building Stage: " + h.getBuildingStages().size();
        return s;
    }

    @FXML
    void HotelsClick(ActionEvent event) {
        Tab[] tabs = new Tab[hotellogic.hotels.size()];
        TabPane tabPane = new TabPane();
        String s = "";
        int i = 0;
        for (HotelInfo h : hotellogic.hotels.values()) {
            tabs[i] = new Tab();
            tabs[i].setText("Hotel " + h.getName());
            s = hotels(h);
            TextArea txt = new TextArea(s);
            txt.setStyle("-fx-font-alignment: center");
            txt.setDisable(true);
            tabs[i].setContent(txt);
            tabPane.getTabs().add(tabs[i]);
        }
        Popup popup = new Popup();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle(" -fx-background-color: #cfcfcf;");
        Button btn = new Button("Close");
        tabPane.setMinWidth(hotellogic.hotels.size() * 85);
        tabPane.setMinHeight(200);
        btn.setLayoutX(tabPane.getMinWidth() / 2.0);
        btn.setLayoutY(150);
        btn.setMinWidth(80);
        btn.setMinHeight(40);
        btn.setAlignment(Pos.CENTER);
        popup.getContent().add(tabPane);
        popup.getContent().add(btn);
        popup.setAutoHide(true);
        popup.show(myStage);
        EventHandler<ActionEvent> closeevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                popup.hide();
            }
        };
        btn.setOnAction(closeevent);
    }

    @FXML
    void EntrancesClick(ActionEvent event) {
        String s = "";
        for (int i = 0; i < hotellogic.getPlayers().size(); i++) {
            if (hotellogic.getPlayers().get(i).getBalance() >= 0) {
                int sum = 0;
                s = s + hotellogic.getPlayers().get(i).getColour() + ": ";
                for (OwnedHotel h : hotellogic.getPlayers().get(i).getOwnedHotels().values()) {
                    sum = sum + h.getEntrancePosition().size();
                }
                s = s + sum + "\n";
            }
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Total entrances for each Player");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    @FXML
    void ProfitsClick(ActionEvent event) {
        String s = "";
        for (int i = 0; i < hotellogic.getPlayers().size(); i++) {
            s = s + hotellogic.getPlayers().get(i).getColour() + ": "
                    + hotellogic.getPlayers().get(i).getMaxBalance() + "\n";
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Max Balance for each player");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    @FXML
    void RollDiceClick(ActionEvent event
    ) {
        hotellogic.RollDiceAndMove();
        Refresh();
    }

    @FXML
    void ExitClick(ActionEvent event
    ) {
        hotellogic.Exit();
    }

    @FXML
    void RequestBuildingClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSelectBuildingStage.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Owned hotels to request building");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.setScene(scene);

        FXMLSelectBuildingStageController controller
                = loader.<FXMLSelectBuildingStageController>getController();
        controller.setParentController(this);
        controller.setBuyBuildingStage();

        stage.show();
    }

    @FXML
    void BuyHotelClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHotelsSelection.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Hotels to Buy");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.setScene(scene);

        FXMLHotelsSelectionController controller
                = loader.<FXMLHotelsSelectionController>getController();
        controller.setParentController(this);
        controller.setHotelTable();

        stage.show();
    }

    @FXML
    void BuyEntranceClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSelectEntrance.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Owned hotels to buy an Entrance");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.setScene(scene);

        FXMLSelectEntranceController controller
                = loader.<FXMLSelectEntranceController>getController();
        controller.setParentController(this);
        controller.setBuyEntrance();

        stage.show();
    }

    @FXML
    void Request1000fromBank(ActionEvent event
    ) {
        hotellogic.Request1000fromBank();
        Refresh();
    }

    @FXML
    void PayEntranceClick(ActionEvent event
    ) {
        String s = hotellogic.RollDiceAndPayEntrance();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pay Entrance");
        alert.setHeaderText(s);
        alert.showAndWait();
        Refresh();
    }

    @FXML
    void NextPlayerClick(ActionEvent event
    ) {
        hotellogic.NextPlayer();
        Refresh();
        int aliveplayers = 3;
        String winner = "";
        for (int i = 0; i < hotellogic.getPlayers().size(); i++) {
            if (hotellogic.getPlayers().get(i).getBalance() < 0) {
                aliveplayers--;
            } else {
                winner = hotellogic.getPlayers().get(i).getColour();
            }
        }
        if (aliveplayers == 1) {
            fadeTransition1.stop();
            fadeTransition2.stop();
            fadeTransition3.stop();
            RollDice.setDisable(true);
            RequestBuilding.setDisable(true);
            BuyHotel.setDisable(true);
            BuyEntrance.setDisable(true);
            Request1000fromBank.setDisable(true);
            PayEntrance.setDisable(true);
            NextPlayer.setDisable(true);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Player " + winner + " wins!");
            alert.showAndWait();
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        hotellogic = new HotelLogic();
        stackPanes = new StackPane[15][12];

        fadeTransition1 = new FadeTransition(Duration.seconds(1), Player1);
        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.setCycleCount(Animation.INDEFINITE);

        fadeTransition2 = new FadeTransition(Duration.seconds(1), Player2);
        fadeTransition2.setFromValue(1.0);
        fadeTransition2.setToValue(0.0);
        fadeTransition2.setCycleCount(Animation.INDEFINITE);

        fadeTransition3 = new FadeTransition(Duration.seconds(1), Player3);
        fadeTransition3.setFromValue(1.0);
        fadeTransition3.setToValue(0.0);
        fadeTransition3.setCycleCount(Animation.INDEFINITE);

        RollDice.setDisable(true);
        RequestBuilding.setDisable(true);
        BuyHotel.setDisable(true);
        BuyEntrance.setDisable(true);
        Request1000fromBank.setDisable(true);
        PayEntrance.setDisable(true);
        NextPlayer.setDisable(true);
    }
}
