public class MapBridgeCell extends MapCellBase{
    MapBridgeCell pair;
    public MapBridgeCell(String cellType, String prevCellPos, String nextCellPos, int index){
        super(cellType, prevCellPos, nextCellPos, index);
    }
    public void setPair(MapBridgeCell target){
        pair = target;
    }
    public MapBridgeCell getPair(){
        return pair;
    }
}
