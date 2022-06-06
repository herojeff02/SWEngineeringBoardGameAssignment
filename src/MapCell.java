public class MapCell extends MapCellBase{
    public MapCell(String cellType, String prevCellPos, String nextCellPos, int index) {
        super(cellType, prevCellPos, nextCellPos, index);
    }

    public void removeTool(){
        if(getGeneralCellType().equals("GT")){
            cellType = "C";
        }
    }
}
