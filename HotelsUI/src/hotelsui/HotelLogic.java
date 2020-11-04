package hotelsui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author Ειρηνη
 */
public class HotelLogic {

    private List<Player> players;
    private BoardEntry[][] boardEntries;
    private List<BoardEntry> path;
    Map<Integer, HotelInfo> hotels = new HashMap<>();
    Map<Integer, HotelInfo> availablehotelsmap = new HashMap<>();
    //epeidi exw polla koina id alla ta stoixeia kathe ksexoristou id anoikoun se 1 hotel
    private int activeplayer = 0;
    private boolean isRollDiceAndMoveEnabled = false;
    private boolean isRequestBuildingEnabled = false;
    private boolean isBuyHotelEnabled = false;
    private boolean isBuyEntranceEnabled = false;
    private boolean isRequest1000fromBankEnabled = false;
    private boolean isNextPlayerEnabled = false;
    private boolean isPayEntranceEnabled = false;

    public Map<Integer, HotelInfo> getAvailablehotelsmap() {
        return availablehotelsmap;
    }

    public boolean isIsPayEntranceEnabled() {
        return isPayEntranceEnabled;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public BoardEntry[][] getBoardEntries() {
        return boardEntries;
    }

    public void setBoardEntries(BoardEntry[][] boardEntries) {
        this.boardEntries = boardEntries;
    }

    public List<BoardEntry> getPath() {
        return path;
    }

    public void setPath(List<BoardEntry> path) {
        this.path = path;
    }

    public Map<Integer, HotelInfo> getHotels() {
        return hotels;
    }

    public void setHotels(Map<Integer, HotelInfo> hotels) {
        this.hotels = hotels;
    }

    public int getActiveplayer() {
        return activeplayer;
    }

    public void setActiveplayer(int activeplayer) {
        this.activeplayer = activeplayer;
    }

    public boolean isIsRollDiceAndMoveEnabled() {
        return isRollDiceAndMoveEnabled;
    }

    public boolean isIsRequestBuildingEnabled() {
        return isRequestBuildingEnabled;
    }

    public boolean isIsBuyHotelEnabled() {
        return isBuyHotelEnabled;
    }

    public boolean isIsBuyEntranceEnabled() {
        return isBuyEntranceEnabled;
    }

    public boolean isIsRequest1000fromBankEnabled() {
        return isRequest1000fromBankEnabled;
    }

    public boolean isIsNextPlayerEnabled() {
        return isNextPlayerEnabled;
    }

    public void Start() throws IOException {
        ReadData r = new ReadData();
        Pair<BoardEntry[][], Map<Integer, HotelInfo>> p = r.ReadBoard();
        boardEntries = p.getKey(); //olos o disdiastatos pinakas
        hotels = p.getValue(); //gia kathe hotel id exoume ta stoixiea tou hotel
        availablehotelsmap = new HashMap<>(hotels);
        path = r.ReadPath(boardEntries); //to monopati tou paixnidiou
        players = r.ReadPlayers(path);  //dimiourgia twn players
        players.get(0).setPosition(path.get(0).getPosition());
        players.get(1).setPosition(path.get(0).getPosition());
        players.get(2).setPosition(path.get(0).getPosition());
        isRollDiceAndMoveEnabled = true;
        isRequestBuildingEnabled = false;
        isBuyHotelEnabled = false;
        isBuyEntranceEnabled = false;
        isRequest1000fromBankEnabled = false;
        isNextPlayerEnabled = false;
    }

    public void Exit() {
        System.exit(0);
    }

    public void RollDiceAndMove() {
        if (isRollDiceAndMoveEnabled) {
            Random rand = new Random();
            int rint = rand.nextInt(6) + 1;
            int position = players.get(activeplayer).getPathposition();
            int nposition = rint + position;
            if (nposition >= path.size() - 1) {
                nposition -= path.size() - 1;
            }
            //skip tous players an erthei o active se koini thesi
            boolean flag = false;
            for (Player player : players) {
                if (player.getBalance()<0){
                    PositionType out = new PositionType(-1,-1);
                    player.setPathposition(-1);
                    player.setPosition(out);
                }
                if (player.getPathposition() == nposition) {
                    flag = true;
                }
                //players.get(0).EditBalance(-12000);
            }
            while (flag) {
                nposition++;
                if (nposition >= path.size() - 1) {
                    nposition -= path.size() - 1;
                }
                flag = false;
                for (Player player : players) {
                    if (player.getPathposition() == nposition) {
                        flag = true;
                    }
                }
            }
            players.get(activeplayer).setPathposition(nposition);
            players.get(activeplayer).setPosition(path.get(nposition).getPosition());
            for (int i = position + 1; i <= nposition; i++) {
                if (path.get(i) instanceof BoardEntryBank) {
                    isRequest1000fromBankEnabled = true;
                }
                if (path.get(i) instanceof BoardEntryCourt && !players.get(activeplayer).getOwnedHotels().isEmpty()) {
                    isBuyEntranceEnabled = true;
                }
            }
            isNextPlayerEnabled = true;
            if (path.get(nposition) instanceof BoardEntryPlot) {
                isBuyHotelEnabled = true;
            } else if (path.get(nposition) instanceof BoardEntryEntrance) {
                if ((((BoardEntryEntrance) path.get(nposition)).getOwner() != null
                        && ((BoardEntryEntrance) path.get(nposition)).getOwner().getBalance() >= 0
                        && ((BoardEntryEntrance) path.get(nposition)).getOwner() != players.get(activeplayer))) {
                    isPayEntranceEnabled = true;
                    isNextPlayerEnabled = false;

                } else if (!players.get(activeplayer).getOwnedHotels().isEmpty()) {
                    isBuyEntranceEnabled = true;
                    isRequestBuildingEnabled = true;
                }
            }
            isRollDiceAndMoveEnabled = false;
        }
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public String RequestBuilding(int hotelID) {
        String Message;
        BuildingStage buildingstage;
        Message = "Can't Request Building";
        if (isRequestBuildingEnabled) {
            int buildingseq = players.get(activeplayer).getOwnedHotels()
                    .get(hotelID).getBuildingStages().size();
            Random rand = new Random();
            int rint = rand.nextInt(100);
            if (isBetween(rint, 0, 49)) {
                if (hotels.get(hotelID).getBuildingStages().size() > buildingseq) {
                    buildingstage = hotels.get(hotelID).getBuildingStages().get(buildingseq);
                    players.get(activeplayer).addBuildingStage(buildingstage, hotelID);
                    players.get(activeplayer).EditBalance(-buildingstage.getCost());
                    if (players.get(activeplayer).getBalance() < 0) {
                        isRequestBuildingEnabled = false;
                        isBuyHotelEnabled = false;
                        isBuyEntranceEnabled = false;
                        isRequest1000fromBankEnabled = false;
                        isRollDiceAndMoveEnabled = false;
                    }
                    availablehotelsmap.remove(hotelID);
                    int seq = buildingstage.getSequence() + 1;
                    Message = "Bought Building Expansion " + seq + " with cost "
                            + buildingstage.getCost();
                } else {
                    Message = "No more availabe Building Expansions";
                }
            } else if (isBetween(rint, 50, 69)) {
                Message = "Denied Request for Building";
            } else if (isBetween(rint, 70, 84)) {
                if (hotels.get(hotelID).getBuildingStages().size() > buildingseq) {
                    buildingstage = hotels.get(hotelID).getBuildingStages().get(buildingseq);
                    players.get(activeplayer).addBuildingStage(buildingstage, hotelID);
                    availablehotelsmap.remove(hotelID);
                    int seq = buildingstage.getSequence() + 1;
                    Message = "Bought Building Expansion " + seq
                            + " with zero cost";
                } else {
                    Message = "No more availabe Building Expansions";
                }
            } else {
                if (hotels.get(hotelID).getBuildingStages().size() > buildingseq) {
                    buildingstage = hotels.get(hotelID).getBuildingStages().get(buildingseq);
                    players.get(activeplayer).addBuildingStage(buildingstage, hotelID);
                    players.get(activeplayer).EditBalance(-2 * buildingstage.getCost());
                    if (players.get(activeplayer).getBalance() < 0) {
                        isRequestBuildingEnabled = false;
                        isBuyHotelEnabled = false;
                        isBuyEntranceEnabled = false;
                        isRequest1000fromBankEnabled = false;
                        isRollDiceAndMoveEnabled = false;
                    }
                    availablehotelsmap.remove(hotelID);
                    int seq = buildingstage.getSequence() + 1;
                    Message = "Bought Building Expansion " + seq
                            + " with double cost " + 2 * buildingstage.getCost();
                } else {
                    Message = "No more availabe Building Expansions";
                }
            }
            isRequestBuildingEnabled = false;
            isBuyEntranceEnabled = false;
        }
        return Message;
    }

    public void NextPlayer() {
        if (isNextPlayerEnabled) {
            activeplayer++;
            if (activeplayer > 2) {
                activeplayer = 0;
            }
            if (players.get(activeplayer).getBalance() < 0) {
                NextPlayer();
            }
            isRequestBuildingEnabled = false;
            isBuyHotelEnabled = false;
            isBuyEntranceEnabled = false;
            isRequest1000fromBankEnabled = false;
            isNextPlayerEnabled = false;
            isRollDiceAndMoveEnabled = true;

        }
    }

    public boolean isHotelAdjacent(int hotelID, PositionType position) {
        PositionType checkpos = new PositionType(-1, -1);
        boolean isTrue = false;
        // up position
        checkpos.setX((position.getX() - 1) >= 0 ? position.getX() - 1 : position.getX());
        checkpos.setY(position.getY());
        BoardEntry boardentry = boardEntries[checkpos.getX()][checkpos.getY()];
        if ((boardentry instanceof BoardEntryHotel) && ((BoardEntryHotel) boardentry).getID() == hotelID) {
            isTrue = true;
        }
        // down position
        checkpos.setX((position.getX() + 1) <= 11 ? position.getX() + 1 : position.getX());
        checkpos.setY(position.getY());
        boardentry = boardEntries[checkpos.getX()][checkpos.getY()];
        if (boardentry instanceof BoardEntryHotel && ((BoardEntryHotel) boardentry).getID() == hotelID) {
            isTrue = true;
        }
        // right position
        checkpos.setX(position.getX());
        checkpos.setY((position.getY() + 1) <= 14 ? position.getY() + 1 : position.getY());
        boardentry = boardEntries[checkpos.getX()][checkpos.getY()];
        if (boardentry instanceof BoardEntryHotel && ((BoardEntryHotel) boardentry).getID() == hotelID) {
            isTrue = true;
        }
        // left position
        checkpos.setX(position.getX());
        checkpos.setY((position.getY() - 1) >= 0 ? position.getY() - 1 : position.getY());
        boardentry = boardEntries[checkpos.getX()][checkpos.getY()];

        if (boardentry instanceof BoardEntryHotel && ((BoardEntryHotel) boardentry).getID() == hotelID) {
            isTrue = true;
        }
        return isTrue;
    }

    public boolean isHotelFree(int hotelID) {

        if (hotels.get(hotelID).getOwner() == null) {
            return true;
        }
        return (hotels.get(hotelID).getOwner().getOwnedHotels().get(hotelID).getBuildingStages().isEmpty());
    }

    public void BuyHotel(int hotelID) {
        if (isBuyHotelEnabled) {
            PositionType position = players.get(activeplayer).getPosition();
            if (isHotelAdjacent(hotelID, position) && isHotelFree(hotelID)) {
                OwnedHotel ownedhotel = new OwnedHotel(hotelID);
                if (hotels.get(hotelID).getOwner() != null) {
                    hotels.get(hotelID).getOwner().removeOwnedHotel(ownedhotel);
                    hotels.get(hotelID).setOwner(players.get(activeplayer));
                    players.get(activeplayer).EditBalance(-hotels.get(hotelID).getMandatoryPlotCost());
                    if (players.get(activeplayer).getBalance() < 0) {
                        isRequestBuildingEnabled = false;
                        isBuyHotelEnabled = false;
                        isBuyEntranceEnabled = false;
                        isRequest1000fromBankEnabled = false;
                        isRollDiceAndMoveEnabled = false;
                    }
                    players.get(activeplayer).addOwnedHotel(ownedhotel);
                    ((BoardEntryPlot) this.boardEntries[position.getX()][position.getY()])
                            .setOwner(players.get(activeplayer));
                    ((BoardEntryPlot) this.boardEntries[position.getX()][position.getY()])
                            .setHotel(hotels.get(hotelID));
                } else {
                    hotels.get(hotelID).setOwner(players.get(activeplayer));
                    players.get(activeplayer).EditBalance(-hotels.get(hotelID).getPlotCost());
                    if (players.get(activeplayer).getBalance() < 0) {
                        isRequestBuildingEnabled = false;
                        isBuyHotelEnabled = false;
                        isBuyEntranceEnabled = false;
                        isRequest1000fromBankEnabled = false;
                        isRollDiceAndMoveEnabled = false;
                    }
                    players.get(activeplayer).addOwnedHotel(ownedhotel);
                    ((BoardEntryPlot) this.boardEntries[position.getX()][position.getY()])
                            .setOwner(players.get(activeplayer));
                    ((BoardEntryPlot) this.boardEntries[position.getX()][position.getY()])
                            .setHotel(hotels.get(hotelID));
                }
            }
            isBuyHotelEnabled = false;
        }
    }

    public String BuyEntrance(int hotelID) {
        String Message;
        boolean isBuilded = false;
        Message = "Can't Buy Entrance";
        if (isBuyEntranceEnabled) {
            if ((players.get(activeplayer).getOwnedHotels().get(hotelID) != null)
                    && !players.get(activeplayer).getOwnedHotels().get(hotelID).getBuildingStages().isEmpty()) {
                for (int index = 0; index < path.size(); index++) {
                    if (path.get(index) instanceof BoardEntryEntrance
                            && isHotelAdjacent(hotelID, path.get(index).getPosition())
                            && ((BoardEntryEntrance) path.get(index)).getOwner() == null) {
                        ((BoardEntryEntrance) path.get(index)).setOwner(players.get(activeplayer));
                        ((BoardEntryEntrance) path.get(index)).setHotel(hotels.get(hotelID));
                        players.get(activeplayer).addEntrance(((BoardEntryEntrance) path.get(index)), hotelID);
                        players.get(activeplayer).EditBalance(-hotels.get(hotelID).getEntranceCost());
                        if (players.get(activeplayer).getBalance() < 0) {
                            isRequestBuildingEnabled = false;
                            isBuyHotelEnabled = false;
                            isBuyEntranceEnabled = false;
                            isRequest1000fromBankEnabled = false;
                            isRollDiceAndMoveEnabled = false;
                        }
                        Message = "Entrance Builded";
                        isBuilded = true;
                        break;
                    }
                }
                if (!isBuilded) {
                    Message = "Entrance Not Found";
                }
            } else {
                Message = "Can't buy Entrance. The base is not builded! ";
            }
            isRequestBuildingEnabled = false;
            isBuyEntranceEnabled = false;
        }
        return Message;
    }

    public void Request1000fromBank() {
        if (isRequest1000fromBankEnabled) {
            players.get(activeplayer).EditBalance(1000);
            players.get(activeplayer).EditMaxBalance();
            isRequest1000fromBankEnabled = false;
        }
    }

    public String RollDiceAndPayEntrance() {
        if (isPayEntranceEnabled) {
            Random rand = new Random();
            int rint = rand.nextInt(6) + 1;
            PositionType position = players.get(activeplayer).getPosition();
            Player paidplayer = ((BoardEntryEntrance) this.boardEntries[position.getX()][position.getY()]).getOwner();
            int hotelIDtopay = ((BoardEntryEntrance) this.boardEntries[position.getX()][position.getY()]).getHotel().getID();
            int buildingstage = paidplayer.getOwnedHotels().get(hotelIDtopay).getBuildingStages().size();
            int dailyrent = paidplayer.getOwnedHotels().get(hotelIDtopay)
                    .getBuildingStages().get(buildingstage - 1).getDailyRent();
            dailyrent *= rint;
            players.get(activeplayer).EditBalance(-dailyrent);
            if (players.get(activeplayer).getBalance() < 0) {
                isRequestBuildingEnabled = false;
                isBuyHotelEnabled = false;
                isBuyEntranceEnabled = false;
                isRequest1000fromBankEnabled = false;
                isRollDiceAndMoveEnabled = false;
            }
            paidplayer.EditBalance(dailyrent);
            paidplayer.EditMaxBalance();
            String s = "Player " + players.get(activeplayer).getColour() + " stayed for "
                    + rint + " days with daily rent " + dailyrent / rint + " so paid a total of " + dailyrent;
            isNextPlayerEnabled = true;
            isPayEntranceEnabled = false;
            return s;
        }
        return "Can't Pay Entrance";
    }
}
