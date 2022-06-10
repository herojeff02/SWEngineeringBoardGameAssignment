public class MapCellBase {

//    SS = Special Start Cell
//    SE = Special End Cell

//    C = Normal/Typical Cell
//    P = Philips Driver Cell : 1 point
//    H = Hammer Cell : 2 points
//    S = Saw Cell : 3 points
//    B = Bridge Start Cell
//    b = Bridge Arrival Cell

//    GT = General Tool Cell : P, H, S
//    GB = General Bridge Cell : B, b
//    GS = General Special Cell : SS, SE
//    GC = General Normal Cell : C

//    PU, PD, PL, PR = 4 Cell Positions : Up Down Left Right
//    PN = Null Position

    String cellType;
    String prevCellDir, nextCellDir;
    private int mapIndex;
    private int row, col;
    public MapCellBase(String cellType, String prevCellDir, String nextCellDir, int mapIndex){
        this.cellType = cellType;
        this.prevCellDir = prevCellDir;
        this.nextCellDir = nextCellDir;
        this.mapIndex = mapIndex;
    }
    public String getGeneralCellType(){
        return switch (cellType) {
            case "P", "H", "S" -> "GT";
            case "B", "b" -> "GB";
            case "SS", "SE" -> "GS";
            case "C" -> "GC";
            default -> "";
        };
    }


    public String getCellType(){
        return cellType;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public int getRow() {
        return row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getCol() {
        return col;
    }

    public int getMapIndex() {
        return mapIndex;
    }
}
