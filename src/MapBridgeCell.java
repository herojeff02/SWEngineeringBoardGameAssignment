public class MapBridgeCell extends MapCellBase{
    Boolean isPrimary;
    MapBridgeCell pair;
    public MapBridgeCell(String cellType, String prevCellPos, String nextCellPos, int index){
        super(cellType, prevCellPos, nextCellPos, index);
        if(cellType.equals("B")){
            isPrimary = true;
        }
        else if(cellType.equals("b")){
            isPrimary = false;
        }
    }
    public void setPair(MapBridgeCell target){
        pair = target;
    }
    public MapBridgeCell getPair(){
        return pair;
    }
    public Boolean isPrimary(){
        return isPrimary;
    }
}
