import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MapBuilder {
    File file;
    private final ArrayList<MapCellBase> mapCellsArrayList;
    private final MapCellBase[][] mapCells2d;
    public int mapWidth, mapHeight;

    public MapBuilder(String fileName) throws FileNotFoundException {
        file = new File(fileName);
        if(!file.exists()){
            throw new FileNotFoundException("Map file path invalid");
        }
        mapCellsArrayList = parse();
        assignMapCellsToArrayList();
        mapCells2d = new MapCellBase[mapHeight][mapWidth];
        assignMapCellsTo2d();
        createBridgePair();
//        testPrint();
    }

    private void assignMapCellsTo2d() {
        for (MapCellBase a : mapCellsArrayList){
            mapCells2d[a.getRow()][a.getCol()] = a;
        }
    }

    private void mapPositionOffset(int minRow, int minCol){
        if(minRow < 0 || minCol < 0) {
            for (MapCellBase a : mapCellsArrayList) {
                a.setRow(a.getRow() - minRow);
                a.setCol(a.getCol() - minCol);
            }
        }
    }

    private void assignMapCellsToArrayList(){
        int maxRow = 0, maxCol = 0;
        int minRow = 0, minCol = 0;
        int row = 0, col = 0;
        for (MapCellBase a : mapCellsArrayList){
            a.setRow(row);
            a.setCol(col);
            switch (a.nextCellDir) {
                case "PL" -> col--;
                case "PR" -> col++;
                case "PU" -> row--;
                case "PD" -> row++;
            }
            maxRow = max(maxRow, a.getRow());
            maxCol = max(maxCol, a.getCol());
            minRow = min(minRow, a.getRow());
            minCol = min(minCol, a.getCol());
        }

        mapHeight = maxRow - minRow + 1;
        mapWidth = maxCol - minCol + 1;

        mapPositionOffset(minRow, minCol);
    }

    private void createBridgePair(){
        ArrayList<MapBridgeCell> bridgeStart = new ArrayList<>();
        for (MapCellBase start : mapCellsArrayList) {
            if(start.getCellType().equals("B")){
                bridgeStart.add((MapBridgeCell)start);
            }
            else if(start.getCellType().equals("b")){
                for(MapBridgeCell arrival : bridgeStart){
                    if(arrival.getRow() == start.getRow() && arrival.getCol()+2 == start.getCol()){
                        arrival.setPair((MapBridgeCell)start);
                        ((MapBridgeCell) start).setPair(arrival);
                        bridgeStart.remove(start);
                    }
                }
            }
        }
    }

    private ArrayList<MapCellBase> parse(){
        ArrayList<MapCellBase> mapCells = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            int currLine = 0;
            while (scan.hasNextLine()) {
                String txt = scan.nextLine();
                String txt0, txt1, txt2;
                if(currLine == 0){ //first line
                    txt0 = parseSpecialConverter(txt.charAt(0));
                    txt1 = "PN";
                    txt2 = parseConverter(txt.charAt(1));
                }
                else if(txt.startsWith("E")){ //last line
                    txt0 = parseSpecialConverter(txt.charAt(0));
                    txt1 = "PN";
                    txt2 = "PN";
                }
                else{
                    txt0 = parseConverter(txt.charAt(0));
                    txt1 = parseConverter(txt.charAt(1));
                    txt2 = parseConverter(txt.charAt(2));
                }

                if(txt0.equalsIgnoreCase("b")){
                    mapCells.add(new MapBridgeCell(txt0, txt1, txt2, currLine));
                }
                else {
                    mapCells.add(new MapCell(txt0, txt1, txt2, currLine));
                }
                currLine++;
            }
            int mapLength = currLine;
        } catch (FileNotFoundException e) {
            System.out.println("Unknown File");
            System.exit(1);
        }
        return mapCells;
    }

    private static String parseConverter(char c){
        return switch (c){
            case 'C', 'P', 'H', 'S', 'B', 'b' -> String.valueOf(c); //char to String
            case 'L' -> "PL";
            case 'R' -> "PR";
            case 'U' -> "PU";
            case 'D' -> "PD";
            default -> "";
        };
    }
    private static String parseSpecialConverter(char c){
        return switch (c) {
            case 'S' -> "SS";
            case 'E' -> "SE";
            default -> "";
        };
    }
    public ArrayList<MapCellBase> getMapArrayList(){
        return mapCellsArrayList;
    }

    public MapCellBase[][] getMap2d(){
        return mapCells2d;
    }

    public void testPrint(){
        for(int i = 0; i< mapHeight; i++){
            for(int j = 0; j< mapWidth; j++){
                if(mapCells2d[i][j] != null) {
                    System.out.printf("%3s", mapCells2d[i][j].getCellType());
                }
                else{
                    if(j > 0 && mapCells2d[i][j-1] != null && mapCells2d[i][j-1].getCellType().equals("B")){
                        System.out.print("  -");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
            System.out.println();
        }
    }
}
