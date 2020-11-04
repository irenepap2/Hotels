package hotelsui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
import javafx.util.Pair;

public class ReadData {

    public Pair<BoardEntryHotel, HotelInfo> ReadHotel(String filename, int x, int y) throws FileNotFoundException, IOException {
        BoardEntryHotel boardentryhotel = new BoardEntryHotel(x, y);
        HotelInfo hotelinfo = new HotelInfo();
        FileInputStream fstream = new FileInputStream(filename + ".txt");
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(fstream));
        boardentryhotel.setID(Integer.parseInt(filename));
        hotelinfo.setID(boardentryhotel.getID());
        String strLine;
        strLine = br.readLine();
        hotelinfo.setName(strLine);
        strLine = br.readLine();
        List<String> items = Arrays.asList(strLine.split("\\s*,\\s*"));
        hotelinfo.setPlotCost((Integer.parseInt(items.get(0))));
        hotelinfo.setMandatoryPlotCost(Integer.parseInt(items.get(1)));
        strLine = br.readLine();
        hotelinfo.setEntranceCost(Integer.parseInt(strLine));
        List<BuildingStage> buildingStages = new ArrayList<>();
        int i = 0;
        while ((strLine = br.readLine()) != null) {
            items = Arrays.asList(strLine.split("\\s*,\\s*"));
            BuildingStage buildingStage = new BuildingStage();
            buildingStage.setCost(Integer.parseInt(items.get(0)));
            buildingStage.setDailyRent(Integer.parseInt(items.get(1)));
            buildingStage.setSequence(i++);
            buildingStages.add(buildingStage);
        }
        hotelinfo.setBuildingStages(buildingStages);
        return new Pair<>(boardentryhotel, hotelinfo);
    }

    public Pair<BoardEntry[][], Map<Integer, HotelInfo>> ReadBoard() throws FileNotFoundException, IOException {
        Random rand = new Random();
        int rint = rand.nextInt(2);
        File file;
        if (rint == 0) {
            file = new File("simple.txt");
        } else {
            file = new File("default.txt");
        }
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        BoardEntry boards[][] = new BoardEntry[12][15];
        Map<Integer, HotelInfo> hotelmap = new HashMap<>();
        int linecount = 0;
        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {
            List<String> items = Arrays.asList(strLine.split("\\s*,\\s*"));
            for (int i = 0; i < items.size(); i++) {
                String item = items.get(i);
                switch (item) {
                    case "S":
                        boards[linecount][i] = new BoardEntryStart(linecount, i);
                        break;
                    case "C":
                        boards[linecount][i] = new BoardEntryCourt(linecount, i);
                        break;
                    case "B":
                        boards[linecount][i] = new BoardEntryBank(linecount, i);
                        break;
                    case "H":
                        boards[linecount][i] = new BoardEntryPlot(linecount, i);
                        break;
                    case "E":
                        boards[linecount][i] = new BoardEntryEntrance(linecount, i);
                        break;
                    case "F":
                        boards[linecount][i] = new BoardEntryFree(linecount, i);
                        break;
                    default:
                        Pair<BoardEntryHotel, HotelInfo> p = ReadHotel(item, linecount, i);
                        boards[linecount][i] = p.getKey();
                        HotelInfo h = p.getValue();
                        hotelmap.put(h.getID(), h);
                        break;
                }
            }
            linecount++;
        }

        //Close the input stream
        br.close();
        return new Pair<>(boards, hotelmap);
    }

    public List<Player> ReadPlayers(List<BoardEntry> boardEntries) {
        List<Player> players = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        numbers.add(0);
        numbers.add(1);
        numbers.add(2);
        Random rand = new Random();
        int rint = rand.nextInt(3);
        Player player1 = new Player();
        int value = numbers.get(rint);
        player1.setName("Player " + value);
        player1.setColour(value == 0 ? "Red" : value == 1 ? "Green" : "Blue");
        player1.setBalance(12000);
        player1.setMaxBalance(12000);
        numbers.remove(rint);
        rint = rand.nextInt(2);
        value = numbers.get(rint);
        Player player2 = new Player();
        player2.setName("Player " + value);
        player2.setColour(value == 0 ? "Red" : value == 1 ? "Green" : "Blue");
        player2.setBalance(12000);
        player2.setMaxBalance(12000);
        numbers.remove(rint);
        value = numbers.get(0);
        Player player3 = new Player();
        player3.setName("Player " + value);
        player3.setColour(value == 0 ? "Red" : value == 1 ? "Green" : "Blue");
        player3.setBalance(12000);
        player3.setMaxBalance(12000);

        Stream<BoardEntry> startEntry = boardEntries.stream().filter(p -> p instanceof BoardEntryStart);
        BoardEntryStart s = (BoardEntryStart) startEntry.toArray()[0];
        player1.setPosition(s.getPosition());
        player2.setPosition(s.getPosition());
        player3.setPosition(s.getPosition());

        player1.setPathposition(0);
        player2.setPathposition(0);
        player3.setPathposition(0);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        return players;
    }

    List<BoardEntry> ReadPath(BoardEntry[][] boardEntries) {
        List<BoardEntry> listBoardEntry = new ArrayList<>();
        PositionType position, nposition, sposition, pposition;
        sposition = new PositionType(-1, -1);
        position = new PositionType(-1, -1);
        pposition = new PositionType(-1, -1);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 14; j++) {
                if (boardEntries[i][j] instanceof BoardEntryStart) {
                    listBoardEntry.add(boardEntries[i][j]);
                    sposition = new PositionType(i, j);
                    position = new PositionType(i, j);
                    break;
                }
            }
        }
        nposition = new PositionType(-1, -1);

        do {
            nposition.setX(position.getX()); //right position
            nposition.setY(position.getY() + 1);

            if (nposition.getX() <= 11 && nposition.getY() <= 14
                    && !(boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryFree
                    || boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryHotel)
                    && !(nposition.equals(pposition))) {
                listBoardEntry.add(boardEntries[nposition.getX()][nposition.getY()]);
            } else {

                nposition.setX(position.getX() + 1); //down position
                nposition.setY(position.getY());

                if (nposition.getX() <= 11 && nposition.getY() <= 14
                        && !(boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryFree
                        || boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryHotel)
                        && !(nposition.equals(pposition))) {
                    listBoardEntry.add(boardEntries[nposition.getX()][nposition.getY()]);
                } else {
                    nposition.setX(position.getX()); //left position
                    nposition.setY(position.getY() - 1);

                    if (nposition.getX() <= 11 && nposition.getY() <= 14
                            && !(boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryFree
                            || boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryHotel)
                            && !(nposition.equals(pposition))) {
                        listBoardEntry.add(boardEntries[nposition.getX()][nposition.getY()]);
                    } else {
                        nposition.setX(position.getX() - 1); //up position
                        nposition.setY(position.getY());

                        if (nposition.getX() <= 11 && nposition.getY() <= 14
                                && !(boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryFree
                                || boardEntries[nposition.getX()][nposition.getY()] instanceof BoardEntryHotel)
                                && !(nposition.equals(pposition))) {
                            listBoardEntry.add(boardEntries[nposition.getX()][nposition.getY()]);
                        }
                    }
                }
            }
            pposition.setX(position.getX());
            pposition.setY(position.getY());
            position.setX(nposition.getX());
            position.setY(nposition.getY());

        } while (!(position.equals(sposition)));

        return listBoardEntry;
    }
}
